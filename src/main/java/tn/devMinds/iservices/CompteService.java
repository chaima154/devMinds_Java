package tn.devMinds.iservices;

import tn.devMinds.entities.Compte;
import tn.devMinds.tools.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CompteService implements IService<Compte> {

    public String test = "test";

    @Override
    public boolean delete(Compte compte) {
        String query = "DELETE FROM compte WHERE id=?";
        try (Connection connection = MyConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, compte.getId());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
            return false;
        }
    }

    @Override
    public String update(Compte compte, int id) {
        // Your update method implementation
        return null;
    }

    @Override
    public ArrayList<Compte> getAllData() {
        String query = "SELECT * FROM compte";
        ArrayList<Compte> comptes = new ArrayList<>();
        try (Connection connection = MyConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)) {
            while (rs.next()) {
                Compte c = new Compte();
                c.setId(rs.getInt("id"));
                c.setSolde(rs.getInt("solde"));
                c.setTypecompte(rs.getString("typecompte"));
                c.setAgence(rs.getString("agence"));
                c.setRib(rs.getString("rib"));
                comptes.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comptes;
    }

    @Override
    public String add(Compte compte) {
        String sql = "insert into compte (agence,typecompte,solde,rib,user_id) values (?,?,?,?,?)";
        try (Connection connection = MyConnection.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, compte.getAgence());
            pst.setString(2, compte.getTypecompte());
            pst.setInt(3, compte.getSolde());
            pst.setString(4, compte.getRib());
            pst.setInt(5, compte.getUser_id());

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Compte ajouté avec succès");
            } else {
                System.out.println("Échec de l'ajout du compte");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du compte : " + e.getMessage());
        }
        return test;
    }

    public boolean read(Compte compte) {
        String sql = "select * from compte";
        try (Connection connection = MyConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            List<Compte> comptes = new ArrayList<>();
            while (rs.next()) {
                Compte c = new Compte();
                c.setId(rs.getInt("id"));
                c.setSolde(rs.getInt("solde"));
                c.setTypecompte(rs.getString("typecompte"));
                c.setAgence(rs.getString("agence"));
                c.setRib(rs.getString("rib"));
                comptes.add(c);
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Compte compte) {
        String sql = "UPDATE compte SET typecompte = ?, solde = ?, agence = ?, rib = ? WHERE id = ?";
        try (Connection connection = MyConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, compte.getTypecompte());
            ps.setInt(2, compte.getSolde());
            ps.setString(3, compte.getAgence());
            ps.setString(4, compte.getRib());
            ps.setInt(5, compte.getId());
            ps.executeUpdate();
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String generateUniqueNumero(int x) {
        String randomNumero = generateRandomNumberString(x);
        boolean numeroExists = checkIfNumeroExists(randomNumero);
        while (numeroExists) {
            randomNumero = generateRandomNumberString(x);
            numeroExists = checkIfNumeroExists(randomNumero);
        }
        return randomNumero;
    }

    public String generateRandomNumberString(int length) {
        String characters = "0123456789";
        Random random = new Random();
        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < length; i++) {
            randomString.append(characters.charAt(random.nextInt(characters.length())));
        }
        return randomString.toString();
    }

    public boolean checkIfNumeroExists(String numero) {
        String request = "SELECT rib FROM compte WHERE rib=?";
        try (Connection connection = MyConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(request)) {
            preparedStatement.setString(1, numero);
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next(); // Returns true if ResultSet contains any rows (numero exists), false otherwise
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false in case of exception
        }
    }
}
