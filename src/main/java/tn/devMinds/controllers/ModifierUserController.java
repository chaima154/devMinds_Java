package tn.devMinds.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import tn.devMinds.entities.Role;
import tn.devMinds.entities.User;
import tn.devMinds.iservices.UserService;

public class ModifierUserController {
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

    public ModifierUserController() {
        userService = new UserService();
    }

    public void updateUser(int userId) {
        // Récupérer les nouvelles informations de l'utilisateur depuis les champs du formulaire
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        Role role = roleComboBox.getValue();

        // Créer un nouvel objet User avec les nouvelles informations
        User updatedUser = new User();
        updatedUser.setId(userId); // Utiliser l'ID fourni en paramètre
        updatedUser.setFirstName(firstName);
        updatedUser.setLastName(lastName);
        updatedUser.setEmail(email);
        updatedUser.setPassword(password);
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
}
