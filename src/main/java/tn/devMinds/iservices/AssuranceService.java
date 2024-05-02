package tn.devMinds.iservices;

import tn.devMinds.entities.Assurence;
import tn.devMinds.tools.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class AssuranceService implements IService<Assurence> {
    public Connection cnx;

    public AssuranceService() {
        cnx = MyConnection.getInstance().getCnx();
    }

    @Override
    public String add(Assurence assurance) {
        if (assurance.getNom() == null || assurance.getNom().isEmpty()) {
            return "Le nom ne peut pas être vide.";
        }

        // Add other validation rules as needed

        String checkUniqueQuery = "SELECT COUNT(*) FROM assurance WHERE nom = ?";
        try {
            PreparedStatement checkUniqueStmt = MyConnection.getInstance().getCnx().prepareStatement(checkUniqueQuery);
            checkUniqueStmt.setString(1, assurance.getNom());
            ResultSet resultSet = checkUniqueStmt.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                return "Une Assurance avec ce nom existe déjà.";
            }
        } catch (SQLException e) {
            return "Erreur lors de la vérification de l'unicité : " + e.getMessage();
        }

        String requete = "INSERT INTO assurance(nom, description, prime, franchise) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, assurance.getNom());
            pst.setString(2, assurance.getDescription());
            pst.setInt(3, assurance.getPrime());
            pst.setDouble(4, assurance.getFranchise());
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                return null; // No error
            } else {
                return "Aucune ligne n'a été affectée lors de l'ajout.";
            }
        } catch (SQLException e) {
            return "Erreur lors de l'ajout de l'Assurance : " + e.getMessage();
        }
    }

    @Override
    public boolean delete(Assurence assurance) {
        String req = "DELETE FROM assurance WHERE id=?";
        try (PreparedStatement pst = cnx.prepareStatement(req)) {
            pst.setInt(1, assurance.getId());
            int rowsAffected = pst.executeUpdate();
            System.out.println("Suppression effectuée");
            return rowsAffected == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String update(Assurence assurance, int id) {
        if (assurance.getNom() == null || assurance.getNom().isEmpty()) {
            return "Le nom ne peut pas être vide.";
        }

        // Add other validation rules as needed

        String checkUniqueQuery = "SELECT COUNT(*) FROM assurance WHERE nom = ? AND id != ?";
        try {
            PreparedStatement checkUniqueStmt = cnx.prepareStatement(checkUniqueQuery);
            checkUniqueStmt.setString(1, assurance.getNom());
            checkUniqueStmt.setInt(2, id);
            ResultSet resultSet = checkUniqueStmt.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                return "Une Assurance avec ce nom existe déjà.";
            }
        } catch (SQLException e) {
            return "Erreur lors de la vérification de l'unicité : " + e.getMessage();
        }

        String req = "UPDATE assurance SET nom=?, description=?, prime=?, franchise=? WHERE id=?";
        try (PreparedStatement pst = cnx.prepareStatement(req)) {
            pst.setString(1, assurance.getNom());
            pst.setString(2, assurance.getDescription());
            pst.setInt(3, assurance.getPrime());
            pst.setDouble(4, assurance.getFranchise());
            pst.setInt(5, id);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                return null; // No error
            } else {
                return "Aucune ligne n'a été affectée lors de la mise à jour.";
            }
        } catch (SQLException e) {
            return "Erreur lors de la mise à jour de l'Assurance : " + e.getMessage();
        }
    }

    @Override
    public ArrayList<Assurence> getAllData() {
        List<Assurence> data = new ArrayList<>();
        String requete = "SELECT * FROM assurance";
        try (Statement st = cnx.createStatement(); ResultSet rs = st.executeQuery(requete)) {
            while (rs.next()) {
                Assurence assurance = new Assurence();
                assurance.setId(rs.getInt("id"));
                assurance.setNom(rs.getString("nom"));
                assurance.setDescription(rs.getString("description"));
                assurance.setPrime(rs.getInt("prime"));
                assurance.setFranchise(rs.getDouble("franchise"));
                data.add(assurance);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des données: " + e.getMessage());
        }
        return (ArrayList<Assurence>) data;
    }

    public String validateInput(Assurence assurance) {
        if (assurance.getNom() == null || assurance.getNom().isEmpty()) {
            return "Le nom ne peut pas être vide.";
        }
        // Add other validation rules as needed
        return null; // Return null if validation passes
    }
    public Assurence selectOne(int id) throws SQLException {
        String query = "SELECT * FROM assurance WHERE id = ?";
        PreparedStatement pstmt = cnx.prepareStatement(query);
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            Assurence assurance = new Assurence();
            assurance.setId(rs.getInt("id"));
            assurance.setNom(rs.getString("nom"));
            assurance.setDescription(rs.getString("description"));
            assurance.setFranchise(rs.getInt("franchise"));
            assurance.setPrime(rs.getInt("prime"));
            return assurance;
        }
        return null; // Retourner null si aucune assurance correspondante n'est trouvée
    }
    public Assurence getByNom(String nom) throws SQLException {
        String query = "SELECT * FROM assurance WHERE nom = ?";
        PreparedStatement pstmt = cnx.prepareStatement(query);
            pstmt.setString(1, nom);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    Assurence assurance = new Assurence();
                    assurance.setId(resultSet.getInt("id"));
                    assurance.setNom(resultSet.getString("nom"));
                    // Set other attributes as needed
                    return assurance;
                }
            }

        return null; // Assurance with the given name not found
    }}

