package tn.devMinds.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.devMinds.iservices.LoginService;

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
                    // Ici, vous pouvez charger la page client (client.fxml)
                }
            } else {
                // Afficher un message d'erreur si l'authentification échoue
                errorLabel.setText("Email ou mot de passe incorrect !");
            }
        });

        loginService.start();
    }
}