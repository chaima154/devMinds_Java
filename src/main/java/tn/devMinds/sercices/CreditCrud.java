package tn.devMinds.sercices;

import tn.devMinds.iservices.IService;
import tn.devMinds.models.Credit;
import tn.devMinds.tools.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CreditCrud implements IService<Credit> {
    Connection cnx2;
    public CreditCrud(){
        cnx2 = MyConnection.getInstance().getCnx();
    }
    @Override
    public void addCredit(Credit credit){
        String requete = "INSERT INTO credit (montant_credit, duree, taux_interet,date_obtention,montant_restant, statut_credit, type_credit," +
                " document_cin, salaire, categorie_professionelle, type_secteur, secteur_activite)"
                + "VALUES ('" + credit.getMontantCredit() +"','" +credit.getDuree()+"','" +credit.getTauxInteret()+"','"+credit.getDateObtention()+"','"+credit.getMontantRestant()+"','"
                + credit.getStatutCredit()+"','"+credit.getTypeCredit()+"','"+credit.getDocumentcin()+"','"+credit.getSalaire()+"','"+credit.getCategorieProfessionelle()+"','"
                +credit.getTypeSecteur()+"','"+credit.getSecteurActivite()+"')";

        try {
            Statement st = cnx2.createStatement();
            st.executeUpdate(requete);
            System.out.println("Crédit ajouté avec succés");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public boolean updateCredit(Credit credit) throws SQLException {
        String requete="UPDATE `credit` SET `id`=?,`montant_credit`=?,`duree`=?,`taux_interet`=?,`date_obtention`=?,`montant_restant`=?," +
                "`statut_credit`=?,`type_credit`=?,`document_cin`=?,`salaire`=?,`categorie_professionelle`=?,`type_secteur`=?,`secteur_activite`=?"
                + " WHERE id=?";
        try (PreparedStatement pst = cnx2.prepareStatement(requete)){
            pst.setInt(1,credit.getId());
            pst.setDouble(2, credit.getMontantCredit());
            pst.setInt(3,credit.getDuree());
            pst.setDouble(4, credit.getTauxInteret());
            pst.setDate(5, Date.valueOf(credit.getDateObtention()));
            pst.setDouble(6,credit.getMontantRestant());
            pst.setString(7,credit.getStatutCredit());
            pst.setString(8,credit.getTypeCredit());
            pst.setString(9,credit.getDocumentcin());
            pst.setDouble(10,credit.getSalaire());
            pst.setString(11,credit.getCategorieProfessionelle());
            pst.setString(12,credit.getTypeSecteur());
            pst.setString(13,credit.getSecteurActivite());
            pst.setInt(14,credit.getId());

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("credit with ID " + credit.getId() + " updated successfully");
                return true;
            } else {
                System.out.println("No credit found with ID: " + credit.getId());
                return false;
            }
        }
    }

    @Override
    public void deleteCredit(int id) throws SQLException {

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

    @Override
    public List<Credit> showCredit() {
        List<Credit> mylist = new ArrayList<>();
        try {
            String requete = "SELECT * FROM credit";
            Statement st = cnx2.createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next())
            {
                Credit credit=new Credit();
                credit.setId(rs.getInt(1));
                credit.setMontantCredit(rs.getDouble(2));
                credit.setDuree(rs.getInt(3));
                credit.setTauxInteret(rs.getDouble(4));
                credit.setDateObtention(rs.getDate(5).toLocalDate());
                credit.setMontantRestant(rs.getDouble(6));
                credit.setStatutCredit(rs.getString(7));
                credit.setTypeCredit(rs.getString(8));
                credit.setDocumentcin(rs.getString(10));
                credit.setSalaire(rs.getDouble(11));
                credit.setCategorieProfessionelle(rs.getString(12));
                credit.setTypeSecteur(rs.getString(13));
                credit.setSecteurActivite(rs.getString(14));

                mylist.add(credit);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }return mylist;
    }
}
