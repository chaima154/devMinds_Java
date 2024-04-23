package tn.devMinds.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import tn.devMinds.entities.User;
import tn.devMinds.entities.Role;
import tn.devMinds.iservices.UserService;

import java.sql.SQLException;

public class AjoutUserController {
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

    // Méthode appelée lorsque le bouton "Ajouter" est cliqué
    public void addUser() throws SQLException {
        // Récupérer les valeurs des champs du formulaire
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        Role role = roleComboBox.getValue();

        // Valider les champs (vous pouvez ajouter des validations ici si nécessaire)

        // Créer un nouvel objet User avec les valeurs des champs
        User newUser = new User();
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setRole(role);

        // Ajouter cet utilisateur à votre système en utilisant le service UserService
        UserService userService = new UserService();
        String result = userService.add(newUser);

        // Traiter le résultat de l'opération d'ajout
        if (result == null) {
            // L'utilisateur a été ajouté avec succès
            System.out.println("Utilisateur ajouté avec succès.");
            // Vous pouvez également afficher un message à l'utilisateur pour confirmer l'ajout
        } else {
            // Il y a eu une erreur lors de l'ajout de l'utilisateur
            System.out.println("Erreur lors de l'ajout de l'utilisateur : " + result);
            // Afficher un message d'erreur à l'utilisateur
        }
    }
}
