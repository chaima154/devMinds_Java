package tn.devMinds.iservices;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import tn.devMinds.tools.MyConnection;
public class EmailService extends Service<Void> {
    private final String recipientEmail;
    private final String verificationCode;
    private boolean sentSuccessfully;

    public EmailService(String recipientEmail, String verificationCode, String message) {
        this.recipientEmail = recipientEmail;
        this.verificationCode = verificationCode;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<>() {
            @Override
            protected Void call() {
                sendEmail();
                return null;
            }
        };
    }

    private void sendEmail() {
        final String username = "zinelabidinefatma1@gmail.com"; // Your Gmail address
        final String password = "thgy vskk cwfe wgtj"; // Your Gmail password

        // Configure properties for sending email via SMTP
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Create a secure SMTP session
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create a MIME message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username)); // Sender's address
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail)); // Recipient's address
            message.setSubject("Verification Code"); // Message subject
            message.setText("Your verification code is: " + verificationCode); // Message content

            // Send the message
            Transport.send(message);

            System.out.println("Email sent successfully to: " + recipientEmail);
        } catch (MessagingException e) {
            System.out.println("Error sending email: " + e.getMessage());
        }
    }
    public void updatePasswordInDatabase(String userEmail, String newPassword) {
        // Définir la requête SQL pour mettre à jour le mot de passe dans la base de données
        String query = "UPDATE user SET mdp = ? WHERE email = ?";

        try (Connection connection = MyConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // Définir les paramètres de la requête
            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, userEmail);

            // Exécuter la requête
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Mot de passe mis à jour avec succès pour l'utilisateur: " + userEmail);
            } else {
                System.out.println("Échec de la mise à jour du mot de passe pour l'utilisateur: " + userEmail);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour du mot de passe dans la base de données: " + e.getMessage());
        }
    }
    public boolean isSentSuccessfully() {
        return sentSuccessfully;
    }
}
