package tn.devMinds.iservices;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;
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
            protected Void call() throws Exception {
                // Generate a new password
                String newPassword = PasswordService.generateRandomPassword();

                // Send the email with the new password
                sendEmail(recipientEmail,newPassword);

                // Update the password in the database
                updatePasswordInDatabase(recipientEmail, newPassword);

                return null;
            }
        };
    }
    private void sendEmailAndUpdateDatabase(String userEmail, String newPassword) {
        // Send the email with the new password
        sendEmail(userEmail, newPassword);

        // Update the password in the database
        updatePasswordInDatabase(userEmail, newPassword);
    }


    private void sendEmail(String recipientEmail, String newPassword) {
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
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username)); // Sender's address
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail)); // Recipient's address
            message.setSubject("Password Reset"); // Message subject

            // Create a multipart message
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Your new password is: " + newPassword); // Include new password in the message content

            // Create a Multipart object to add the message body parts
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            // Set the content of the message to the multipart object
            message.setContent(multipart);

            // Send the message
            Transport.send(message);

            System.out.println("Email sent successfully to: " + recipientEmail);
        } catch (MessagingException e) {
            System.out.println("Error sending email: " + e.getMessage());
        }
    }


    public void updatePasswordInDatabase(String userEmail, String newPassword) {
        // Hash the new password
        String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

        // Define the SQL query to update the password in the database
        String query = "UPDATE user SET mdp = ? WHERE email = ?";

        try (Connection connection = MyConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the parameters for the query
            preparedStatement.setString(1, hashedPassword);
            preparedStatement.setString(2, userEmail);

            // Execute the query
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Password updated successfully for user: " + userEmail);
            } else {
                System.out.println("Failed to update password for user: " + userEmail);
            }
        } catch (SQLException e) {
            System.out.println("SQL error while updating password: " + e.getMessage());
        }
    }


    public boolean isSentSuccessfully() {
        return sentSuccessfully;
    }
}
