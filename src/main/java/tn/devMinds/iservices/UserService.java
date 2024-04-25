package tn.devMinds.iservices;


import tn.devMinds.entities.Role;
import tn.devMinds.entities.User;
import tn.devMinds.tools.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService implements IService<User> {
    private Connection cnx;

    public UserService() {
        cnx = MyConnection.getInstance().getCnx();
    }


    @Override
    public ArrayList<User> getAll() throws SQLException {
        List<User> data = new ArrayList<>();
        String requete = "SELECT * FROM user";
        try (Statement st = cnx.createStatement(); ResultSet rs = st.executeQuery(requete)) {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setNom(rs.getString("nom"));
                user.setPrenom(rs.getString("prenom"));
                user.setEmail(rs.getString("email"));
                user.setMdp(rs.getString("mdp"));
                user.setRole(Role.valueOf(rs.getString("role"))); // Convertir la chaîne de caractères en rôle
                data.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des données: " + e.getMessage());
        }
        return (ArrayList<User>) data;
    }


    @Override

    public String add(User user) {
        String query = "INSERT INTO user(nom, prenom, email, mdp, role) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setString(1, user.getNom());
            pst.setString(2, user.getPrenom());
            pst.setString(3, user.getEmail());
            pst.setString(4, user.getMdp());
            pst.setString(5, user.getRole().toString());

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                return null; // No errors
            } else {
                return "No rows were affected during the insertion.";
            }
        } catch (SQLException e) {
            return "Error while adding the user: " + e.getMessage();
        }
    }



    @Override
    public boolean delete(User user) throws SQLException {
        String req = "DELETE FROM user WHERE id=?";
        try (PreparedStatement pst = cnx.prepareStatement(req)) {
            pst.setInt(1, user.getId());
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return false;
    }


    @Override
    public String update(User user, int id) {
        // Ajouter la logique pour mettre à jour les informations d'un utilisateur dans la base de données
        String req = "UPDATE user SET nom=?, prenom=?, email=?, mdp=?, role=? WHERE id=?";
        try (PreparedStatement pst = cnx.prepareStatement(req)) {
            pst.setString(1, user.getNom());
            pst.setString(2, user.getPrenom());
            pst.setString(3, user.getEmail());
            pst.setString(4, user.getMdp());
            pst.setString(5, user.getRole().toString()); // Convertir le rôle en chaîne de caractères
            pst.setInt(6, id);

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                return null; // Aucune erreur
            } else {
                return "Aucune ligne n'a été affectée lors de la mise à jour.";
            }
        } catch (SQLException e) {
            return "Erreur lors de la mise à jour de l'utilisateur : " + e.getMessage();
        }
    }


    public ArrayList<User> getAllData() {

            // Récupérer tous les utilisateurs depuis la base de données
            ArrayList<User> users = new ArrayList<>();
            String requete = "SELECT * FROM user";
            try (Statement st = cnx.createStatement(); ResultSet rs = st.executeQuery(requete)) {
                while (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setNom(rs.getString("nom"));
                    user.setPrenom(rs.getString("prenom"));
                    user.setEmail(rs.getString("email"));
                    user.setMdp(rs.getString("mdp"));
                    user.setRole(Role.valueOf(rs.getString("role"))); // Convertir la chaîne de caractères en rôle
                    users.add(user);
                }
            } catch (SQLException e) {
                System.out.println("Erreur lors de la récupération des données: " + e.getMessage());
            }
            return users;

    }
}

