package tn.devMinds.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.devMinds.entities.User;
import tn.devMinds.iservices.LoginService;
import org.mindrot.jbcrypt.BCrypt;
import tn.devMinds.tools.MyConnection;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.prefs.Preferences;

public class LoginController {

    @FXML
    private ChoiceBox<String> role;

    @FXML
    private TextField email;

    @FXML
    private TextField mdp;

    @FXML
    private Label errorLabel;
    @FXML
    private Button login_btn;
    @FXML
    private Hyperlink forgotPasswordLink;

    private int userID;

    private Preferences preferences;


    @FXML
    public void initialize() {
        // Initialiser le choix du rôle (Admin ou Client)
        role.getItems().addAll("ROLE_ADMIN", "ROLE_USER");
        // Par défaut, sélectionner "Client"
        role.getSelectionModel().select("ROLE_USER");
    }

    @FXML
    public void handleLogin() {
        String selectedRole = role.getValue();
        String enteredEmail = email.getText();
        String enteredPassword = mdp.getText();


        // Check for null or empty password
        if (enteredPassword == null || enteredPassword.isEmpty()) {
            // Handle empty password (e.g., display an error message)
            errorLabel.setText("Password is required!");
            return;
        }

        // Retrieve hashed password and client ID from the database based on entered email
        User user = getUserFromDatabase(enteredEmail);
        System.out.println(user.getId());
        userID = user.getId();

        // Check if user is retrieved successfully
        if (user == null) {
            // Handle case where user email is not found (e.g., display an error message)
            errorLabel.setText("Email not found!");
            return;
        }

        boolean isAuthenticated = BCrypt.checkpw(enteredPassword, user.getMdp());
        System.out.println(isAuthenticated);

        LoginService loginService = new LoginService(enteredEmail, enteredPassword, selectedRole, user.getId()); // Pass the plain text password
        loginService.setOnSucceeded(event -> {
            if (isAuthenticated) {
                preferences = Preferences.userRoot().node(this.getClass().getName());

                // Example of saving a variable
                preferences.put("Id_Client", String.valueOf(userID));

                String savedValue = preferences.get("Id_Client", "02");

                // Authentication successful, redirect to the appropriate page
                if ("ROLE_ADMIN".equals(selectedRole)) {
                    // Redirect to the admin page
                    System.out.println("Authentication successful for the administrator");


                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/sidebarre_admin.fxml"));
                    Parent adminPage = null; // Load the FXML file
                    try {
                        adminPage = loader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Scene scene = new Scene(adminPage); // Create a new scene with the loaded parent node
                    Stage stage = (Stage) login_btn.getScene().getWindow(); // Assuming login_btn is a control in your login.fxml file
                    stage.setScene(scene); // Set the new scene to the stage
                    stage.show(); // Show the stage

                } else if ("ROLE_USER".equals(selectedRole)) {


                    // Redirect to the client page
                    System.out.println("Authentication successful for the client");
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/sidebarre_client.fxml"));
                    Parent clientPage = null; // Load the FXML file
                    try {
                        clientPage = loader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Scene scene = new Scene(clientPage); // Create a new scene with the loaded parent node
                    Stage stage = (Stage) login_btn.getScene().getWindow(); // Assuming login_btn is a control in your login.fxml file
                    stage.setScene(scene); // Set the new scene to the stage
                    stage.show(); // Show the stage

                }
            } else {
                // Display an error message if authentication fails
                errorLabel.setText("Email or password incorrect!");
            }
        });


        loginService.start();

    }

    private User getUserFromDatabase(String userEmail) {
        User user = null;
        String query = "SELECT id, mdp FROM user WHERE email = ?";

        try (Connection connection = MyConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, userEmail);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setMdp(resultSet.getString("mdp"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database access error
        }

        return user;
    }



/*
    @FXML
    public void handleSendVerificationCode(ActionEvent event) {
        // Récupérer l'email de l'utilisateur (peut-être à partir d'un champ de texte dans votre interface utilisateur)
        String userEmail = emailField.getText(); // Supposons que emailField soit le champ de texte où l'utilisateur saisit son adresse e-mail

        // Générer un code de vérification
        String verificationCode = VerificationCodeGenerator.generateVerificationCode(userEmail);

        // Envoyer le code de vérification par e-mail
        EmailService emailService = new EmailService(userEmail, verificationCode);
        emailService.start();
    }*/

    @FXML
    public void handleForgotPassword(ActionEvent event) {
        try {
            // Charger la vue de la page de réinitialisation de mot de passe depuis son fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/ForgotPassword.fxml"));
            Parent root = loader.load();

            // Obtenir le contrôleur de la page de réinitialisation de mot de passe
            ForgotPasswordController forgotPasswordController = loader.getController();

            // Créer une nouvelle scène avec la vue chargée
            Scene scene = new Scene(root);

            // Récupérer la fenêtre principale à partir du bouton de login
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Changer la scène de la fenêtre principale pour afficher la page de réinitialisation de mot de passe
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer les erreurs lors du chargement de la vue de la page de réinitialisation de mot de passe
        }
    }


//    private String getPasswordFromDatabase(String userEmail) {
//        // Connexion à la base de données
//        try (Connection connection = MyConnection.getConnection()) {
//            String query = "SELECT mdp FROM user WHERE email = ?";
//            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//                preparedStatement.setString(1, userEmail);
//                try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                    if (resultSet.next()) {
//                        return resultSet.getString("mdp");
//                    }
//                }
//            }
//        } catch (SQLException e) {
//            System.out.println("Error retrieving password from database: " + e.getMessage());
//        }
//        return null; // Retourne null si l'utilisateur n'est pas trouvé ou s'il y a une erreur
//    }
}