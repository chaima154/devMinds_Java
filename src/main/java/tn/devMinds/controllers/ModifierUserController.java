package tn.devMinds.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.devMinds.entities.Role;
import tn.devMinds.entities.User;
import tn.devMinds.iservices.UserService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class ModifierUserController extends BackendHome {
    @FXML
    private VBox container;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
    @FXML
    private ComboBox<Role> roleComboBox;

    private UserService userService;
    private User user;


    public ModifierUserController() {
        userService = new UserService();
        this.user = user;

    }


    public void updateUser(int userId) {
        // Récupérer les nouvelles informations de l'utilisateur depuis les champs du formulaire
        String nom = firstNameField.getText();
        String prenom = lastNameField.getText();
        String email = emailField.getText();
        String mdp = passwordField.getText();
        Role role = roleComboBox.getValue();

        // Créer un nouvel objet User avec les nouvelles informations
        User updatedUser = new User();
        updatedUser.setNom(nom);
        updatedUser.setPrenom(prenom);
        updatedUser.setEmail(email);
        updatedUser.setMdp(mdp);
        updatedUser.setRole(role);

        // Appeler la méthode de mise à jour dans le service UserService
        String errorMessage = userService.update(updatedUser, userId);
        if (errorMessage == null) {
            // La mise à jour a réussi
            System.out.println("L'utilisateur a été mis à jour avec succès.");
        } else {
            // Afficher un message d'erreur
            System.out.println("Erreur lors de la mise à jour de l'utilisateur : " + errorMessage);
        }
    }

    public void setUser(User user) {
        this.user = user;
        // Pré-remplir les champs du formulaire avec les informations de l'utilisateur
        firstNameField.setText(user.getNom());
        lastNameField.setText(user.getPrenom());
        emailField.setText(user.getEmail());
        passwordField.setText(user.getMdp());
        roleComboBox.setValue(user.getRole());
    }

    private boolean validateFields() {
        if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() ||
                emailField.getText().isEmpty() || passwordField.getText().isEmpty() ||
                roleComboBox.getValue() == null) {
            // Display an error message for empty fields
            System.out.println("Veuillez remplir tous les champs.");
            return false;
        }

        // Validate email format using a regular expression
        String email = emailField.getText();
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern emailPattern = Pattern.compile(emailRegex);
        if (!emailPattern.matcher(email).matches()) {
            // Display an error message for invalid email format
            System.out.println("Veuillez entrer une adresse e-mail valide.");
            return false;
        }

        return true;
    }

    public void handleUpdateUser(ActionEvent actionEvent) {
        if (!validateFields()) {
            return;
        }

        // Confirmation dialog for update action
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Confirmation");
        confirmationDialog.setHeaderText("Mise à jour de l'utilisateur");
        confirmationDialog.setContentText("Êtes-vous sûr de vouloir mettre à jour cet utilisateur ?");
        Optional<ButtonType> result = confirmationDialog.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Update user details
            String nom = firstNameField.getText();
            String prenom = lastNameField.getText();
            String email = emailField.getText();
            String mdp = passwordField.getText();
            Role role = roleComboBox.getValue();

            user.setNom(nom);
            user.setPrenom(prenom);
            user.setEmail(email);
            user.setMdp(mdp);
            user.setRole(role);

            String errorMessage = userService.update(user, user.getId());
            if (errorMessage == null) {
                // Update successful
                System.out.println("L'utilisateur a été mis à jour avec succès.");
            } else {
                // Display an error message
                System.out.println("Erreur lors de la mise à jour de l'utilisateur : " + errorMessage);
            }

            // Close the form window
            Stage stage = (Stage) firstNameField.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    public void handleDeleteUser(ActionEvent actionEvent) throws SQLException {
        // Confirmation dialog for delete action
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Confirmation");
        confirmationDialog.setHeaderText("Suppression de l'utilisateur");
        confirmationDialog.setContentText("Êtes-vous sûr de vouloir supprimer cet utilisateur ?");
        Optional<ButtonType> result = confirmationDialog.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            String errorMessage = String.valueOf(userService.delete(user.getId()));
            if (errorMessage == null) {
                // Deletion successful
                System.out.println("L'utilisateur a été supprimé avec succès.");
            } else {
                // Display an error message
                System.out.println("Erreur lors de la suppression de l'utilisateur : " + errorMessage);
            }
        }
    }
}