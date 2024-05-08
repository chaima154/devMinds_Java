package tn.devMinds.iservices;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.devMinds.entities.Credit;
import tn.devMinds.entities.Credit;
import tn.devMinds.tools.MyConnection;

import java.sql.*;
import java.util.ArrayList;

public class CreditCrud implements IService<Credit> {
    public String test="test";
    Connection cnx2;
    public CreditCrud() throws SQLException {
        cnx2 = MyConnection.getConnection();
    }

    public String add(Credit credit){
        String requete = "INSERT INTO credit (compte_id, montant_credit, duree, taux_interet, date_obtention, montant_restant, statut_credit, type_credit, document_cin, salaire, categorie_professionelle, type_secteur, secteur_activite) " +
                "VALUES ('" + credit.getCompteId() + "', '" + credit.getMontantCredit() + "', '" + credit.getDuree() + "', '" + credit.getTauxInteret() + "', '" + credit.getDateObtention() + "', '" + credit.getMontantRestant() + "', '" +
                credit.getStatutCredit() + "', '" + credit.getTypeCredit() + "', '" + credit.getDocumentcin() + "', '" + credit.getSalaire() + "', '" + credit.getCategorieProfessionelle() + "', '" +
                credit.getTypeSecteur() + "', '" + credit.getSecteurActivite() + "')";
        try {
            Statement st = cnx2.createStatement();
            st.executeUpdate(requete);
            System.out.println("Crédit ajouté avec succès");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return test;
    }

    @Override
    public boolean delete(Credit credit) throws SQLException {
        return false;
    }

    @Override
    public String update(Credit credit, int id) {
        return null;
    }

    @Override
    public ArrayList<Credit> getAllData() throws SQLException {
        return null;
    }


    public void update(Credit credit) throws SQLException {
        String requete="UPDATE `credit` SET `id`=?,`compte_id`=?,`montant_credit`=?,`duree`=?,`taux_interet`=?,`date_obtention`=?,`montant_restant`=?," +
                "`statut_credit`=?,`type_credit`=?,`document_cin`=?,`salaire`=?,`categorie_professionelle`=?,`type_secteur`=?,`secteur_activite`=?"
                + " WHERE id=?";
        try (PreparedStatement pst = cnx2.prepareStatement(requete)){
            pst.setInt(1,credit.getId());
            pst.setInt(2,credit.getCompteId());
            pst.setDouble(3, credit.getMontantCredit());
            pst.setInt(4,credit.getDuree());
            pst.setDouble(5, credit.getTauxInteret());
            pst.setDate(6, Date.valueOf(credit.getDateObtention()));
            pst.setDouble(7,credit.getMontantRestant());
            pst.setString(8,credit.getStatutCredit());
            pst.setString(9,credit.getTypeCredit());
            pst.setString(10,credit.getDocumentcin());
            pst.setDouble(11,credit.getSalaire());
            pst.setString(12,credit.getCategorieProfessionelle());
            pst.setString(13,credit.getTypeSecteur());
            pst.setString(14,credit.getSecteurActivite());
            pst.setInt(15,credit.getId());

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("credit with ID " + credit.getId() + " updated successfully");
            } else {
                System.out.println("No credit found with ID: " + credit.getId());
            }
        }
    }


    public void delete(int id) throws SQLException {

        String requete = "DELETE FROM credit WHERE id=?";
        try (PreparedStatement pst = cnx2.prepareStatement(requete)) {
            pst.setInt(1, id);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Credit with ID " + id + " deleted successfully");
            } else {
                System.out.println("No credit found with ID: " + id);
            }
        }
    }


    public ObservableList<Credit> show() {
        ObservableList<Credit> mylist = FXCollections.observableArrayList();
        try {
            String requete = "SELECT * FROM credit";
            Statement st = cnx2.createStatement();
            ResultSet rs = st.executeQuery(requete);
            showItems(mylist, rs);
            System.out.println("Records retrieved: " + mylist.size()); // Print number of records retrieved
        } catch (SQLException e) {
            System.out.println("test");
            System.err.println(e.getMessage());
        }
        return mylist;
    }
    private void showItems(ObservableList<Credit> mylist, ResultSet rs) throws SQLException {
        while (rs.next()) {
            Credit credit = new Credit();
            credit.setId(rs.getInt("id"));
            credit.setMontantCredit(rs.getDouble("montant_credit"));
            credit.setDuree(rs.getInt("duree"));
            credit.setTauxInteret(rs.getDouble("taux_interet"));

            // Convert DATETIME to LocalDate
            Date date = rs.getDate("date_obtention");
            credit.setDateObtention(date.toLocalDate());

            credit.setMontantRestant(rs.getDouble("montant_restant"));
            credit.setStatutCredit(rs.getString("statut_credit"));
            credit.setTypeCredit(rs.getString("type_credit"));
            credit.setCompteId(rs.getInt("compte_id"));
            credit.setDocumentcin(rs.getString("document_cin"));
            credit.setSalaire(rs.getDouble("salaire"));
            credit.setCategorieProfessionelle(rs.getString("categorie_professionelle"));
            credit.setTypeSecteur(rs.getString("type_secteur"));
            credit.setSecteurActivite(rs.getString("secteur_activite"));

            mylist.add(credit);
        }
    }



    public ObservableList<Credit> readById(int id) {
        ObservableList<Credit> mylist = FXCollections.observableArrayList();
        try {
            String requete = "SELECT * FROM credit WHERE compte_id = ?";
            PreparedStatement ps = cnx2.prepareStatement(requete);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            showItems(mylist, rs);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return mylist;
    }
}
