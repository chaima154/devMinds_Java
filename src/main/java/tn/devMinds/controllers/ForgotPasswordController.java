package tn.devMinds.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import tn.devMinds.iservices.EmailService;

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
        String resetCode = generateResetCode(); // Generate a unique reset code
        String subject = "Password Reset"; // Subject of the email
        String message = "Hello,\n\nYour password reset code is: " + resetCode; // Message body

        // Send the password reset email
        EmailService emailService = new EmailService(userEmail, subject, message);
        emailService.start();

        // Store the reset code temporarily in your storage system for later verification
        storeResetCode(userEmail, resetCode);
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
}
