package tn.devMinds.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.devMinds.iservices.EmailService;
import tn.devMinds.iservices.PasswordService;
import org.mindrot.jbcrypt.BCrypt;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Random;

public class ForgotPasswordController {

    @FXML
    private TextField emailField;

    @FXML
    private Button resetPasswordButton;


    @FXML
    public void resetPassword() {
        String userEmail = emailField.getText();

        // Generate a new random password
        String newPassword = PasswordService.generateRandomPassword();

        // Hash the new password
        String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

        // Update the password in the database
        PasswordService.updatePasswordInDatabase(userEmail, hashedPassword);

        // Send the email with the new password
        String subject = "Password Reset";
        String message = "Hello,\n\nYour new password is: " + newPassword;
        EmailService emailService = new EmailService(userEmail, subject, message);
        emailService.setOnSucceeded(event -> {
            if (emailService.isSentSuccessfully()) {
                System.out.println("Password reset successful. New password sent to user.");
            } else {
                System.out.println("Failed to send the new password to the user.");
            }
        });
        emailService.start();

        navigateToLoginPage();
    }




    private void navigateToLoginPage() {
        try {
            // Chargez le fichier FXML de la page de connexion
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/login.fxml"));
            Parent root = loader.load();

            // Obtenez le contrôleur de la page de connexion
            LoginController loginController = loader.getController();

            // Obtenez la scène actuelle à partir du bouton "resetPasswordButton"
            Stage stage = (Stage) resetPasswordButton.getScene().getWindow();

            // Remplacez la scène actuelle par la scène de la page de connexion
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer les exceptions liées au chargement de la page de connexion
        }
    }









    // Define the length of your reset code
    private static final int CODE_LENGTH = 6;

    // Allowed characters for the reset code
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    // Secure random number generator
    private static final Random RANDOM = new SecureRandom();

    private String generateResetCode() {
        StringBuilder sb = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            // Generate a random index for the characters
            int randomIndex = RANDOM.nextInt(CHARACTERS.length());
            // Add the character corresponding to the generated index to the code
            sb.append(CHARACTERS.charAt(randomIndex));
        }
        return sb.toString();
    }

    private void storeResetCode(String userEmail, String resetCode) {
        // Implement temporary storage of the reset code in your storage system
    }
    private String generateRandomPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+";
        StringBuilder sb = new StringBuilder();
        Random random = new SecureRandom();
        for (int i = 0; i < 10; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }

}