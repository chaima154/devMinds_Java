package tn.devMinds.iservices;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import tn.devMinds.tools.MyConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginService extends Service<Boolean> {

    private String email;
    private String mdp;
    private String role;

    public LoginService(String email, String mdp, String role) {
        this.email = email;
        this.mdp = mdp;
        this.role = role;
    }

    @Override
    protected Task<Boolean> createTask() {
        return new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                // Créer une requête SQL en fonction du rôle
                String query = "SELECT * FROM user WHERE email = ? AND mdp = ? AND role = ?";
                try (Connection connection = MyConnection.getConnection();
                     PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, email);
                    preparedStatement.setString(2, mdp);
                    preparedStatement.setString(3, role);

                    // Exécuter la requête
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        // Vérifier si l'utilisateur existe dans la base de données
                        return resultSet.next(); // true si l'utilisateur existe, sinon false
                    }
                } catch (SQLException e) {
                    System.out.println("Erreur lors de l'authentification: " + e.getMessage());
                    return false;
                }
            }
        };
    }
}