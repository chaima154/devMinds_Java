package tn.devMinds.iservices;

import tn.devMinds.entities.Assurence;
import tn.devMinds.entities.Demande;
import tn.devMinds.tools.MyConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceDemande implements IService<Demande> {

    private static Connection cnx;

    public ServiceDemande() {
        cnx = MyConnection.getInstance().getCnx();
    }

    @Override
    public String add(Demande demande) throws SQLException {
        String query = "INSERT INTO demande (assurance_id, nom_client, date_naissance_client, adresse_client, date_debut_contrat, duree_contrat, montant_couverture, mode_paiement, etat) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = cnx.prepareStatement(query);
        pstmt.setInt(1, demande.getA().getId());
        pstmt.setString(2, demande.getNomClient());
        pstmt.setDate(3, new java.sql.Date(demande.getDateNaissanceClient().getTime()));
        pstmt.setString(4, demande.getAdresseClient());
        pstmt.setDate(5, new java.sql.Date(demande.getDateDebutContrat().getTime()));
        pstmt.setDate(6, new java.sql.Date(demande.getDureeContrat().getTime()));
        pstmt.setDouble(7, demande.getMontantCouverture());
        pstmt.setString(8, demande.getModePaiement());
        pstmt.setString(9, demande.getEtat());
        pstmt.executeUpdate();
        return query;
    }


    @Override
    public String update(Demande demande,int id) {
        // Validate input
        if (demande.getNomClient() == null || demande.getNomClient().isEmpty()) {
            return "Le nom du client ne peut pas être vide.";
        }
        // Add other validation rules as needed

        // Check for uniqueness if necessary
        // (This depends on your business logic and database schema)

        String query = "UPDATE demande SET assurance_id = ?, nom_client = ?, date_naissance_client = ?, adresse_client = ?, date_debut_contrat = ?, duree_contrat = ?, montant_couverture = ?, mode_paiement = ?, etat = ? WHERE id = ?";
        try (PreparedStatement pstmt = cnx.prepareStatement(query)) {
            // Set parameters
            pstmt.setInt(1, demande.getA().getId());
            pstmt.setString(2, demande.getNomClient());
            pstmt.setDate(3, new java.sql.Date(demande.getDateNaissanceClient().getTime()));
            pstmt.setString(4, demande.getAdresseClient());
            pstmt.setDate(5, new java.sql.Date(demande.getDateDebutContrat().getTime()));
            pstmt.setDate(6, new java.sql.Date(demande.getDureeContrat().getTime()));
            pstmt.setDouble(7, demande.getMontantCouverture());
            pstmt.setString(8, demande.getModePaiement());
            pstmt.setString(9, demande.getEtat());
            pstmt.setInt(10, demande.getId());

            // Execute the update
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                return null; // No error
            } else {
                return "Aucune ligne n'a été affectée lors de la mise à jour.";
            }
        } catch (SQLException e) {
            return "Erreur lors de la mise à jour de la demande : " + e.getMessage();
        }
    }

    @Override
    public boolean delete (Demande demande) {
        String req = "DELETE FROM demande WHERE id=?";
        try (PreparedStatement pst = cnx.prepareStatement(req)) {
            pst.setInt(1, demande.getId());
            int rowsAffected = pst.executeUpdate();
            System.out.println("Suppression effectuée");
            return rowsAffected == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    @Override
    public ArrayList<Demande> getAllData() throws SQLException {
        List<Demande> demandes = new ArrayList<>();
        String query = "SELECT * FROM demande";
        PreparedStatement pstmt = cnx.prepareStatement(query);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Demande demande = new Demande();
            demande.setId(rs.getInt("id"));
            AssuranceService serviceAssurance = new AssuranceService();
            Assurence assurance = serviceAssurance.selectOne(rs.getInt("assurance_id"));
            demande.setA(assurance);
            demande.setNomClient(rs.getString("nom_client"));
            demande.setDateNaissanceClient(rs.getDate("date_naissance_client"));
            demande.setAdresseClient(rs.getString("adresse_client"));
            demande.setDateDebutContrat(rs.getDate("date_debut_contrat"));
            demande.setDureeContrat(rs.getDate("duree_contrat"));
            demande.setMontantCouverture(rs.getDouble("montant_couverture"));
            demande.setModePaiement(rs.getString("mode_paiement"));
            demande.setEtat(rs.getString("etat"));
            demandes.add(demande);
        }
        return (ArrayList<Demande>) demandes;
    }
    public static void updateEtat(Demande demande) throws SQLException {
        String query = "UPDATE demande SET etat = ? WHERE id = ?";
        PreparedStatement pstmt = cnx.prepareStatement(query);
        pstmt.setString(1, demande.getEtat());
        pstmt.setInt(2, demande.getId());
        pstmt.executeUpdate();
    }
}
