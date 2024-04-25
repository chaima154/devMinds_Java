package tn.devMinds.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import tn.devMinds.entities.User;
import tn.devMinds.entities.Role;
import tn.devMinds.iservices.UserService;

import java.sql.SQLException;
import java.util.ArrayList;

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
        // Vérifier si les champs obligatoires sont vides
        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || mdp.isEmpty() || selectedRoleString.isEmpty()) {
            System.out.println("Veuillez remplir tous les champs obligatoires.");
            return;
        }

        // Vérifier si l'email existe déjà dans la base de données
        UserService userService = new UserService();
        ArrayList<User> existingUsers = userService.getAllData();
        for (User user : existingUsers) {
            if (user.getEmail().equals(email)) {
                System.out.println("Un utilisateur avec cet e-mail existe déjà.");
                return;
            }
        }

        // Vérifier si l'email est valide
        if (!email.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}")) {
            System.out.println("Veuillez saisir une adresse e-mail valide.");
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