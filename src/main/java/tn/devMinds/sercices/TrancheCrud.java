package tn.devMinds.sercices;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.devMinds.iservices.IService;
import tn.devMinds.models.Credit;
import tn.devMinds.models.Tranche;
import tn.devMinds.tools.MyConnection;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class TrancheCrud implements IService<Tranche> {
    Connection cnx2;
    public TrancheCrud(){
        cnx2 = MyConnection.getInstance().getCnx();
    }
    public void add(Tranche tranche){
        String requete = "INSERT INTO echeance (date_echeance, montant_paiement, statut_paiement, credit_id )" +
                "VALUES ('" + tranche.getDateEcheance() + "', '" +tranche.getMontantPaiement()+ "', '" +tranche.getStatutPaiement()+ "', '" +tranche.getCreditId()+ "')";
        try {
            Statement st = cnx2.createStatement();
            st.executeUpdate(requete);
            System.out.println("Tranche ajouté avec succés");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void create(Credit credit){
        double pay = ((((credit.getMontantCredit() * credit.getTauxInteret()) / 100) + credit.getMontantCredit()) / credit.getDuree());
        LocalDate currentDate = credit.getDateObtention();
        final DecimalFormat decfor = new DecimalFormat("0.00");
        for (int duree = 1; duree <= credit.getDuree(); duree++){
            Tranche tranche = new Tranche();
            tranche.setCreditId(credit.getId());
            tranche.setMontantPaiement(Double.parseDouble(decfor.format(pay)));
            tranche.setStatutPaiement("Non Payée");
            tranche.setDateEcheance(currentDate);
            System.out.println("here is what you want" + credit.getId());
            tranche.setCreditId(credit.getId());
            currentDate = currentDate.plusMonths(1);
            add(tranche);
        }
    }

    public void update(Tranche tranche) throws SQLException {
        String requete="UPDATE `echeance` SET `date_echeance`=?,`montant_paiement`=?,`statut_paiement`=?,`credit_id`=?"
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
            } else {
                System.out.println("No tranche found with ID: " + tranche.getId());
            }
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String requete = "DELETE FROM echeance WHERE id=?";
        try (PreparedStatement pst = cnx2.prepareStatement(requete)) {
            pst.setInt(1, id);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Tranche with ID " + id + " deleted successfully");
            } else {
                System.out.println("No tranche found with ID: " + id);
            }
        }
    }

    @Override
    public List<Tranche> show() {
        return null;
    }

    @Override
    public ObservableList<Tranche> readById(int id) {
        ObservableList<Tranche> mylist = FXCollections.observableArrayList();
        try {
            String requete = "SELECT * FROM echeance WHERE credit_id = ?";
            PreparedStatement ps = cnx2.prepareStatement(requete);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Tranche tranche = new Tranche();
                tranche.setId(rs.getInt(1));
                tranche.setDateEcheance(rs.getDate(2).toLocalDate());
                tranche.setMontantPaiement(rs.getDouble(3));
                tranche.setStatutPaiement(rs.getString(4));
                tranche.setCreditId(rs.getInt(5));

                mylist.add(tranche);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return mylist;
    }
}
