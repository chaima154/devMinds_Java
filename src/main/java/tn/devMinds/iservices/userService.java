package tn.devMinds.iservices;

import tn.devMinds.controllers.mainController;

import tn.devMinds.entities.user;
import tn.devMinds.interfaces.Iuser;
import tn.devMinds.tools.MyConnection;
import tn.devMinds.tools.MyConnection;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class userService implements Iuser<user> {

    //Add user methode
    @Override
    public void addUser(user user) throws SQLException, IOException {
        if(isEmailTaken(user.getEmail())){
            System.out.println("User already exist");
            return;
        }
        String passwordencrypted = encrypt(user.getMdp());
        String query = "INSERT INTO user (email, mdp, prenom, nom,role) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = MyConnection.getInstance().getCnx().prepareStatement(query);
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, passwordencrypted);
            preparedStatement.setString(3, user.getPrenom());
            preparedStatement.setString(4, user.getNom());
            preparedStatement.setString(5, user.getRole());


            preparedStatement.executeUpdate();
            System.out.println("User added!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Update User methode
    @Override
    public void updateUser(user user, int id) {
        String passwordencrypted = encrypt(user.getMdp());

        String query = "UPDATE user " +
                "SET email = ?, mdp = ?, penom = ?, nom = ? "+
                "WHERE user_id = ?";
        try {
            PreparedStatement preparedStatement = MyConnection.getInstance().getCnx().prepareStatement(query);
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, passwordencrypted);
            preparedStatement.setString(3, user.getPrenom());
            preparedStatement.setString(4, user.getNom());
            preparedStatement.setInt(5, id);

            preparedStatement.executeUpdate();
            System.out.println("User updated!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Delete User methode
    @Override
    public void deleteUser(int id) {
        String query = "DELETE FROM user WHERE id = ?";
        try {
            PreparedStatement preparedStatement = MyConnection.getInstance().getCnx().prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("User with the id = "+ id+" is deleted!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Retrieving data from database
    @Override
    public List<user> getallUserdata() {
        List<user> list = new ArrayList<>();
        String query = "SELECT * FROM user";
        try {
            Statement srt = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = srt.executeQuery(query);
            while(rs.next()){
                user user = new user();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setMdp(rs.getString("mdp"));
                user.setPrenom(rs.getString("prenom"));
                user.setNom(rs.getString("nom"));
                user.setRole(rs.getString("role"));

                list.add(user);
            }
            System.out.println("All users are added to the list!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    //Methode to login
    public user loginUser(String email, String mdp){
        String query = "SELECT * FROM user WHERE email = ? AND mdp = ?";
        String encryptedPassword = encrypt(mdp);

        try (PreparedStatement preparedStatement = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, encryptedPassword);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // User login successful, create a user object and return it
                    int userId = resultSet.getInt("id");
                    String userEmail = resultSet.getString("email");
                    String userPassword = resultSet.getString("mdp");
                    String userFirstname = resultSet.getString("prenom");
                    String userLastname = resultSet.getString("nom");
                    String userRole = resultSet.getString("role");
                    return new user(userId, userEmail, userPassword, userFirstname, userLastname, userRole);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //isEmailTaken methodes to check the uniqueness of a user

    public boolean isEmailTaken(String email) throws SQLException {
        String query = "SELECT * FROM user WHERE email = ?";
        PreparedStatement preparedStatement = MyConnection.getInstance().getCnx().prepareStatement(query);
        preparedStatement.setString(1, email);
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            return resultSet.next();
        }
    }

    //Methode to encrypt the Username password
    public static String encrypt(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateforgottenpassword(String email, String mdp) {
        String passwordencrypted = encrypt(mdp);

        String query = "UPDATE user " +
                "SET mdp = ? WHERE email = ?";
        try {
            PreparedStatement preparedStatement = MyConnection.getInstance().getCnx().prepareStatement(query);
            preparedStatement.setString(1, passwordencrypted);
            preparedStatement.setString(2, email);
            preparedStatement.executeUpdate();
            System.out.println("Password updated!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }





}