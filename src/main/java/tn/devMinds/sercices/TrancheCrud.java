package tn.devMinds.sercices;

import tn.devMinds.models.Credit;
import tn.devMinds.models.Tranche;
import tn.devMinds.tools.MyConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class TrancheCrud {
    Connection cnx2;
    public TrancheCrud(){
        cnx2 = MyConnection.getInstance().getCnx();
    }
    public void addTranche(Tranche tranche){
        String requete = "INSERT INTO tranche (date_echeance, montant_paiement, statut_paiement,credit_id"
                + "VALUES ('" + tranche.getDateEcheance() +"','" +tranche.getMontantPaiement()+"','" +tranche.getStatutPaiement()+"','"+tranche.getCreditId()+"')";
        try {
            Statement st = cnx2.createStatement();
            st.executeUpdate(requete);
            System.out.println("Tranche ajouté avec succés");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    public boolean updateTranche(Tranche tranche) throws SQLException {
        String requete="UPDATE `tranche` SET `date_echeance`=?,`montant_paiement`=?,`statut_paiement`=?,`credit_id`=?"
                + " WHERE id=?";
        try (PreparedStatement pst = cnx2.prepareStatement(requete)){
            pst.setDate(1,java.sql.Date.valueOf(tranche.getDateEcheance()));
            pst.setDouble(2, tranche.getMontantPaiement());
            pst.setString(3,tranche.getStatutPaiement());
            pst.setInt(4,tranche.getCreditId());
            pst.setInt(5,tranche.getId());

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Tranche with ID " + tranche.getId() + " updated successfully");
                return true;
            } else {
                System.out.println("No tranche found with ID: " + tranche.getId());
                return false;
            }
        }
    }

    public boolean deleteTranche(Tranche tranche) throws SQLException {

        String requete = "DELETE FROM tranche WHERE id=?";
        try (PreparedStatement pst = cnx2.prepareStatement(requete)) {
            pst.setInt(1, tranche.getId());
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Tranche with ID " + tranche.getId() + " deleted successfully");
                return true;
            } else {
                System.out.println("No tranche found with ID: " + tranche.getId());
                return false;
            }
        }
    }
}
