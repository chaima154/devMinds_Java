package tn.devMinds.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.devMinds.iservices.LoginService;
import org.mindrot.jbcrypt.BCrypt;

import java.awt.event.MouseEvent;
import java.io.IOException;

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

        LoginService loginService = new LoginService(enteredEmail, enteredPassword, selectedRole);
        loginService.setOnSucceeded(event -> {
            boolean isAuthenticated = loginService.getValue();
            if (isAuthenticated) {
                // Authentification réussie, rediriger vers la page appropriée
                if ("ROLE_ADMIN".equals(selectedRole)) {
                    // Redirection vers la page admin
                    System.out.println("Authentification réussie pour l'administrateur");
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/sidebarre_admin.fxml"));
                    Parent adminPage = null;
                    try {
                        adminPage = loader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Scene scene = new Scene(adminPage);

                    Stage stage = (Stage) login_btn.getScene().getWindow(); // Assuming login_btn is a control in your login.fxml file
                    stage.setScene(scene);
                    stage.show();
                } else if ("ROLE_USER".equals(selectedRole)) {
                    // Redirection vers la page client
                    System.out.println("Authentification réussie pour le client");
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/client.fxml"));
                    Parent clientPage = null;
                    try {
                        clientPage = loader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Scene scene = new Scene(clientPage);
                    Stage stage = (Stage) login_btn.getScene().getWindow(); // Assuming login_btn is a control in your login.fxml file
                    stage.setScene(scene);
                    stage.show();
                }
            } else {
                // Afficher un message d'erreur si l'authentification échoue
                errorLabel.setText("Email ou mot de passe incorrect !");
            }
        });

        loginService.start();
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


}