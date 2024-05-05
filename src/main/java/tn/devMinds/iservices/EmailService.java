package tn.devMinds.iservices;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailService extends Service<Void> {
    private final String recipientEmail;
    private final String verificationCode;

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
}
