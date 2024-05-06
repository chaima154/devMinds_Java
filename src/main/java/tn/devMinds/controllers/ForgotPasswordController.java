package tn.devMinds.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import tn.devMinds.iservices.EmailService;
import tn.devMinds.iservices.PasswordService;

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
        String newPassword = PasswordService.generateRandomPassword(); // Générer un nouveau mot de passe

        // Envoyer l'e-mail avec le nouveau mot de passe
        String subject = "Hello, " + userEmail + "\n\nYour new password is: " + newPassword;
        String message = "Hello,\n\nYour new password is: " + newPassword; // Utilisez le nouveau mot de passe dans le message
        EmailService emailService = new EmailService(userEmail, subject, message);
        emailService.setOnSucceeded(event -> {
            // Mettre à jour le mot de passe dans la base de données après l'envoi de l'e-mail
            if (emailService.isSentSuccessfully()) {
                PasswordService.updatePasswordInDatabase(userEmail, newPassword);
            }
        });
        emailService.start();
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
