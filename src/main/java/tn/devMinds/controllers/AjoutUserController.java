package tn.devMinds.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import tn.devMinds.entities.User;
import tn.devMinds.entities.Role;
import tn.devMinds.iservices.UserService;

import java.sql.SQLException;

public class AjoutUserController extends BackendHome {
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
        // Retrieve the values from the form fields
        String nom = firstNameField.getText();
        String prenom = lastNameField.getText();
        String email = emailField.getText();
        String mdp = passwordField.getText();
        String selectedRoleString = String.valueOf(roleComboBox.getValue());  // Get the selected role as a String

        // Convert the selected role String to Role enum
        Role role;
        if ("ADMIN".equals(selectedRoleString)) {
            role = Role.ROLE_ADMIN;
        } else if ("USER".equals(selectedRoleString)) {
            role = Role.ROLE_USER;
        } else {
            // Handle invalid role selection
            System.out.println("Invalid role selection.");
            return;
        }

        // Create a new User object with the field values
        User newUser = new User();
        newUser.setNom(nom);
        newUser.setPrenom(prenom);
        newUser.setEmail(email);
        newUser.setMdp(mdp);
        newUser.setRole(role);


        // Add the user to the database using the UserService
        UserService userService = new UserService();
        String result = userService.add(newUser);

        // Handle the result of the addition operation
        if (result == null) {
            // User added successfully
            System.out.println("Utilisateur ajouté avec succès.");
        } else {
            // Error occurred while adding the user
            System.out.println("Erreur lors de l'ajout de l'utilisateur: " + result);
        }
    }}