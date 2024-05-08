package tn.devMinds.iservices;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import tn.devMinds.tools.MyConnection;
import org.mindrot.jbcrypt.BCrypt;

public class PasswordService {

    private static final int PASSWORD_LENGTH = 10; // Longueur du mot de passe par défaut

    // Caractères autorisés pour le mot de passe
    private static final String PASSWORD_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+";

    // Générateur de nombres aléatoires sécurisé
    private static final SecureRandom RANDOM = new SecureRandom();

    // Méthode pour générer un mot de passe aléatoire
    public static String generateRandomPassword() {
        StringBuilder sb = new StringBuilder(PASSWORD_LENGTH);
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            // Générer un index aléatoire pour les caractères du mot de passe
            int randomIndex = RANDOM.nextInt(PASSWORD_CHARACTERS.length());
            // Ajouter le caractère correspondant à l'index généré au mot de passe
            sb.append(PASSWORD_CHARACTERS.charAt(randomIndex));
        }

        // Retourner le mot de passe non haché
        return sb.toString();
    }


    // Méthode pour mettre à jour le mot de passe dans la base de données
    public static void updatePasswordInDatabase(String userEmail, String newPassword) {
        // Hacher le nouveau mot de passe
        String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

        // Définir la requête SQL pour mettre à jour le mot de passe dans la base de données
        String query = "UPDATE user SET mdp = ? WHERE email = ?";

        try (Connection connection = MyConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);) {

            // Définir les paramètres de la requête
            preparedStatement.setString(1, hashedPassword);
            preparedStatement.setString(2, userEmail);

            // Exécuter la requête
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Mot de passe mis à jour avec succès pour l'utilisateur: " + userEmail);
            } else {
                System.out.println("Échec de la mise à jour du mot de passe pour l'utilisateur: " + userEmail);
            }
        } catch (SQLException e) {
            // Gérer l'exception SQL
            System.out.println("Erreur SQL lors de la mise à jour du mot de passe dans la base de données: " + e.getMessage());
            e.printStackTrace(); // Affichez la trace de la pile pour plus de détails sur l'exception
        } catch (Exception e) {
            // Gérer toutes les autres exceptions
            System.out.println("Une erreur s'est produite lors de la mise à jour du mot de passe dans la base de données: " + e.getMessage());
            e.printStackTrace(); // Affichez la trace de la pile pour plus de détails sur l'exception
        }
    }
    public boolean matchPassword(String newPassword, String hashedPassword) {
        return BCrypt.checkpw(newPassword, hashedPassword);
    }
}