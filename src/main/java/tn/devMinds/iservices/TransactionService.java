package tn.devMinds.iservices;

import tn.devMinds.entities.Transaction;
import tn.devMinds.tools.MyConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionService implements IService<Transaction> {

    private Connection cnx ;
    public TransactionService() {
        cnx= MyConnection.getInstance().getCnx();
    }


    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        String requete = "SELECT * FROM transaction";
        try {
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setId(rs.getInt("id"));
                transaction.setDate(rs.getString("date"));
                transaction.setStatut(rs.getString("statut"));
                transaction.setMontant_transaction(rs.getDouble("montant_transaction"));
                transaction.setNumcheque(rs.getString("numcheque"));
                transaction.setTypetransaction_id(rs.getInt("typetransaction_id"));
                transaction.setCompte_id(rs.getInt("compte_id"));
                transaction.setDestinataire_compte_id_id(rs.getInt("destinataire_compte_id_id"));
                transactions.add(transaction);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return transactions;
    }

    private int getRibForAccount(int accountId) {
        int rib = 0;
        try {
            Connection connection = MyConnection.getInstance().getCnx();
            PreparedStatement statement = connection.prepareStatement("SELECT rib FROM compte WHERE id = ?");
            statement.setInt(1, accountId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                rib = resultSet.getInt("rib");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rib;
    }
    public String add(Transaction transaction) {
        String requete = "INSERT INTO transaction (date, statut, montant_transaction, numcheque, typetransaction_id, compte_id, destinataire_compte_id_id) VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(1, transaction.getDate());
            pst.setString(2, transaction.getStatut());
            pst.setDouble(3, transaction.getMontant_transaction());
            pst.setString(4, transaction.getNumcheque());
            pst.setInt(5, transaction.getTypetransaction_id());
            pst.setInt(6, transaction.getCompte_id());
            pst.setInt(7, transaction.getDestinataire_compte_id_id());
            pst.executeUpdate();
            System.out.println("Transaction ajoutée avec succès !");
        }
        catch (SQLException e) {
            return "Erreur lors de l'ajout du type de transaction : " + e.getMessage();
        }
        return requete;
    }




    @Override
    public boolean delete(Transaction transaction) {
        try {
            PreparedStatement statement = cnx.prepareStatement("DELETE FROM transaction WHERE id = ?");
            statement.setInt(1, transaction.getId());
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public String update(Transaction transaction, int id) {
        return null;
    }

    @Override
    public ArrayList<Transaction> getAllData() throws SQLException {
        return null;
    }


    public List<Transaction> getTransactionsByRib(int rib) {
        List<Transaction> transactions = new ArrayList<>();
        String requete = "SELECT * FROM transaction WHERE compte_id IN (SELECT id FROM compte WHERE rib = ?)";
        try {
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, rib);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setId(rs.getInt("id"));
                transaction.setDate(rs.getString("date"));
                transaction.setStatut(rs.getString("statut"));
                transaction.setMontant_transaction(rs.getDouble("montant_transaction"));
                transaction.setNumcheque(rs.getString("numcheque"));
                transaction.setTypetransaction_id(rs.getInt("typetransaction_id"));
                transaction.setCompte_id(rs.getInt("compte_id"));
                transaction.setDestinataire_compte_id_id(rs.getInt("destinataire_compte_id_id"));
                transactions.add(transaction);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return transactions;
    }

}
