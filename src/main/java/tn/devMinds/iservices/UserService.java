package tn.devMinds.iservices;

import tn.devMinds.entities.Role;
import tn.devMinds.entities.User;
import tn.devMinds.tools.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService implements IService<User> {
    private Connection cnx;

    public UserService() throws SQLException {
        cnx = MyConnection.getConnection();
    }

    @Override
    public ArrayList<User> getAllData() throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        if (cnx != null) { // Check if the connection is initialized
            String requete = "SELECT * FROM user";
            try (Statement st = cnx.createStatement();
                 ResultSet rs = st.executeQuery(requete)) {
                while (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setNom(rs.getString("nom"));
                    user.setPrenom(rs.getString("prenom"));
                    user.setEmail(rs.getString("email"));
                    user.setMdp(rs.getString("mdp"));
                    user.setRole(Role.valueOf(rs.getString("role"))); // Convert the string to a role
                    users.add(user);
                }
            } catch (SQLException e) {
                System.out.println("Error while retrieving data: " + e.getMessage());
            }
        } else {
            System.out.println("Database connection is not initialized.");
        }
        return users;
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
    public boolean delete(User user) {
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


    public String delete(int id) throws SQLException {
        String query = "DELETE FROM user WHERE id = ?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setInt(1, id);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                return null; // No errors
            } else {
                return "No user found with the specified ID.";
            }
        } catch (SQLException e) {
            return "Error while deleting the user: " + e.getMessage();
        }
    }

    @Override
    public String update(User user, int id) {
        String req = "UPDATE user SET nom=?, prenom=?, email=?, mdp=?, role=? WHERE id=?";
        try (PreparedStatement pst = cnx.prepareStatement(req)) {
            pst.setString(1, user.getNom());
            pst.setString(2, user.getPrenom());
            pst.setString(3, user.getEmail());
            pst.setString(4, user.getMdp());
            pst.setString(5, user.getRole().toString());
            pst.setInt(6, id);

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                return null; // No errors
            } else {
                return "No rows were affected during the update.";
            }
        } catch (SQLException e) {
            return "Error while updating the user: " + e.getMessage();
        }
    }
}
