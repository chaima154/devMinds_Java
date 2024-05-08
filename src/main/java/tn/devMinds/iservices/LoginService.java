package tn.devMinds.iservices;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.mindrot.jbcrypt.BCrypt;
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
                String query = "SELECT mdp FROM user WHERE email = ? AND role = ?";
                try (Connection connection = MyConnection.getConnection();
                     PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, email);
                    preparedStatement.setString(2, role);

                    // Exécuter la requête
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        // Vérifier si l'utilisateur existe dans la base de données
                        if (resultSet.next()) {
                            String hashedPassword = resultSet.getString("mdp");
                            // Vérifier si le mot de passe fourni correspond au mot de passe haché dans la base de données
                            return BCrypt.checkpw(mdp, hashedPassword);
                        }
                        return false; // Aucun utilisateur trouvé
                    }
                } catch (SQLException e) {
                    System.out.println("Erreur lors de l'authentification: " + e.getMessage());
                    return false;
                }
            }
        };
    }


}