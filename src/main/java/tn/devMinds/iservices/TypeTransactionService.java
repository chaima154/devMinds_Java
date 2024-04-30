package tn.devMinds.iservices;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.devMinds.entities.TypeTransaction;
import tn.devMinds.tools.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TypeTransactionService implements IService<TypeTransaction> {
    private Connection cnx;

    public TypeTransactionService() {
        cnx = MyConnection.getInstance().getCnx();
    }

    @Override
    public String add(TypeTransaction typeTransaction) {
        // Volet 1: Les champs de texte d'un formulaire ne doivent pas être null/vide
        if (typeTransaction.getLibelle() == null || typeTransaction.getLibelle().isEmpty()) {
            return "Le libellé ne peut pas être vide.";
        }

        // Volet 3: the name should only be letters, doesn't include numbers
        if (!typeTransaction.getLibelle().matches("[a-zA-Z]+")) {
            return "Le libellé ne peut contenir que des lettres.";
        }

        // Volet 4: Commission cannot be greater than 100
        if (typeTransaction.getCommission() > 100) {
            return "La commission ne peut pas être supérieure à 100.";
        }

        // Volet 2: On ne peut pas ajouter deux produits avec les mêmes informations deux fois dans la base
        String checkUniqueQuery = "SELECT COUNT(*) FROM type_transaction WHERE libelle = ?";
        try {
            PreparedStatement checkUniqueStmt = MyConnection.getInstance().getCnx().prepareStatement(checkUniqueQuery);
            checkUniqueStmt.setString(1, typeTransaction.getLibelle());
            ResultSet resultSet = checkUniqueStmt.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                return "Un type de transaction avec ce libellé existe déjà.";
            }
        } catch (SQLException e) {
            return "Erreur lors de la vérification de l'unicité : " + e.getMessage();
        }

        // If all validations passed, proceed with inserting into the database
        String requete = "INSERT INTO type_transaction(libelle,Commission) VALUES (?,?)";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, typeTransaction.getLibelle());
            pst.setDouble(2, typeTransaction.getCommission());
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                return null; // No error
            } else {
                return "Aucune ligne n'a été affectée lors de l'ajout.";
            }
        } catch (SQLException e) {
            return "Erreur lors de l'ajout du type de transaction : " + e.getMessage();
        }
    }


    @Override
    public boolean delete(TypeTransaction typeTransaction) {

        String req = "DELETE FROM type_transaction "
                + "WHERE id='" + typeTransaction.getId() + "';";

        Statement st;
        int er = 0;
        try {
            st = cnx.createStatement();
            er = st.executeUpdate(req);
            System.out.println("Suppression effectuée");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return er == -1;
    }



    @Override
    public String update(TypeTransaction typeTransaction, int id) {
        // Volet 1: Les champs de texte d'un formulaire ne doivent pas être null/vide
        if (typeTransaction.getLibelle() == null || typeTransaction.getLibelle().isEmpty()) {
            return "Le libellé ne peut pas être vide.";
        }

        // Volet 3: the name should only be letters, doesn't include numbers
        if (!typeTransaction.getLibelle().matches("[a-zA-Z]+")) {
            return "Le libellé ne peut contenir que des lettres.";
        }

        // Volet 4: Commission cannot be greater than 100
        if (typeTransaction.getCommission() > 100) {
            return "La commission ne peut pas être supérieure à 100.";
        }

        // If all validations passed, proceed with updating the database
        String req = "UPDATE type_transaction SET libelle = ?, Commission = ? WHERE id = ?";
        try (PreparedStatement pst = cnx.prepareStatement(req)) {
            pst.setString(1, typeTransaction.getLibelle());
            pst.setDouble(2, typeTransaction.getCommission());
            pst.setInt(3, id);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                return null; // No error
            } else {
                return "Aucune ligne n'a été affectée lors de la mise à jour.";
            }
        } catch (SQLException e) {
            return "Erreur lors de la mise à jour: " + e.getMessage();
        }
    }


    public String validateInput(TypeTransaction typeTransaction) {
        // Volet 1: Les champs de texte d'un formulaire ne doivent pas être null/vide
        if (typeTransaction.getLibelle() == null || typeTransaction.getLibelle().isEmpty()) {
            return "Le libellé ne peut pas être vide.";
        }

        // Volet 3: the name should only be letters, doesn't include numbers
        if (!typeTransaction.getLibelle().matches("[a-zA-Z]+")) {
            return "Le libellé ne peut contenir que des lettres.";
        }

        // Volet 2: On ne peut pas ajouter deux produits avec les mêmes informations deux fois dans la base
        String checkUniqueQuery = "SELECT COUNT(*) FROM type_transaction WHERE libelle = ? AND id != ?";
        try {
            PreparedStatement checkUniqueStmt = cnx.prepareStatement(checkUniqueQuery);
            checkUniqueStmt.setString(1, typeTransaction.getLibelle());
            checkUniqueStmt.setInt(2, typeTransaction.getId());
            ResultSet resultSet = checkUniqueStmt.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                return "Un type de transaction avec ce libellé existe déjà.";
            }
        } catch (SQLException e) {
            return "Erreur lors de la vérification de l'unicité : " + e.getMessage();
        }

        return null; // No validation issues
    }

    @Override
    public ArrayList<TypeTransaction> getAllData() {
        List<TypeTransaction> data = new ArrayList<>();
        String requete = "SELECT * FROM type_transaction";
        try (Statement st = cnx.createStatement(); ResultSet rs = st.executeQuery(requete)) {
            while (rs.next()) {
                TypeTransaction typeTransaction = new TypeTransaction();
                typeTransaction.setId(rs.getInt("id"));
                typeTransaction.setLibelle(rs.getString("libelle"));
                typeTransaction.setCommission(rs.getDouble("Commission"));
                data.add(typeTransaction);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des données: " + e.getMessage());
        }
        return (ArrayList<TypeTransaction>) data;
    }

    // Cette méthode retourne tous les noms des types de transaction pour peupler le ComboBox
    public ObservableList<String> getAllTypeTransactionNames() {
        ObservableList<String> types = FXCollections.observableArrayList();
        String query = "SELECT libelle FROM type_transaction";
        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                String libelle = rs.getString("libelle");
                types.add(libelle);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des types de transactions: " + e.getMessage());
        }
        return types;
    }

    public String getTypeTransactionName(int typeId) {
        String query = "SELECT libelle FROM type_transaction WHERE id = ?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setInt(1, typeId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getString("libelle");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
