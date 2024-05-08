package tn.devMinds.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import tn.devMinds.entities.User;
import tn.devMinds.entities.Role;
import tn.devMinds.iservices.UserService;
import org.mindrot.jbcrypt.BCrypt;
import tn.devMinds.controllers.PasswordComplexityCalculator;


import java.sql.SQLException;
import java.util.ArrayList;

public class AjoutUserController extends BackendHome {
    @FXML
    private Label complexityLabel;
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
    @FXML
    private Label firstNameError;

    @FXML
    private Label lastNameError;

    @FXML
    private Label emailError;
    @FXML
    private Label mdpError;


//    private void displayPasswordComplexity() {
//        String password = passwordField.getText();
//        int complexity = PasswordComplexityCalculator.calculateComplexity(password);
//        String message;
//        if (complexity >= 80) {
//            message = "Complexité du mot de passe : Forte";
//        } else if (complexity >= 50) {
//            message = "Complexité du mot de passe : Moyenne";
//        } else {
//            message = "Complexité du mot de passe : Faible";
//        }
//        // Display the complexity message as a notification
//        // You can use a Label or other UI element to display the message
//        complexityLabel.setText(message);
//    }


    // Méthode appelée lorsque le bouton "Ajouter" est cliqué
    public void addUser() throws SQLException {
        // Retrieve the values from the form fields
        String nom = firstNameField.getText();
        String prenom = lastNameField.getText();
        String email = emailField.getText();
        String mdp = passwordField.getText();
        String selectedRoleString = String.valueOf(roleComboBox.getValue());

        int complexity = PasswordComplexityCalculator.calculateComplexity(mdp);
        String complexityNotification = "";

        switch (complexity) {
            case 1:
                complexityNotification = "Faible";
                break;
            case 2:
                complexityNotification = "Moyenne";
                break;
            case 3:
                complexityNotification = "Bonne";
                break;
            case 4:
                complexityNotification = "Très bonne";
                break;
            case 5:
                complexityNotification = "Excellente";
                break;
            default:
                complexityNotification = "Inconnue";
                break;
        }

        System.out.println("Complexité du mot de passe: " + complexityNotification);

        // Get the selected role as a String
        String hashedPassword = BCrypt.hashpw(mdp, BCrypt.gensalt());

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
        if (nom.isEmpty()) {
            firstNameError.setText("Le prénom est obligatoire");
        } else {
            firstNameError.setText(""); // Effacez le message d'erreur s'il n'y a pas d'erreur
            // Autres validations et logique d'ajout de l'utilisateur
        }
        if (prenom.isEmpty()) {
            lastNameError.setText("Le nom est obligatoire");
        } else {
            lastNameError.setText(""); // Effacez le message d'erreur s'il n'y a pas d'erreur
            // Autres validations et logique d'ajout de l'utilisateur
        }

        if (email.isEmpty()) {
            emailError.setText("L'email est obligatoire");
        } else {
            emailError.setText(""); // Effacez le message d'erreur s'il n'y a pas d'erreur
            // Autres validations et logique d'ajout de l'utilisateur
        }
        if (mdp.isEmpty()) {
            mdpError.setText("Le mot de passe est obligatoire");
        } else {
            mdpError.setText(""); // Effacez le message d'erreur s'il n'y a pas d'erreur
            // Autres validations et logique d'ajout de l'utilisateur
        }



        // Vérifier si l'email existe déjà dans la base de données
        UserService userService = new UserService();
        ArrayList<User> existingUsers = userService.getAllData();
        for (User user : existingUsers) {
            if (user.getEmail().equals(email)) {
                System.out.println("Un utilisateur avec cet e-mail existe déjà.");
                emailError.setText("Un utilisateur avec cet e-mail existe déjà.");
                return;
            }
        }

        // Vérifier si l'email est valide
        if (!email.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}")) {
            System.out.println("Veuillez saisir une adresse e-mail valide.");
            emailError.setText("Veuillez saisir une adresse e-mail valide.");
            return;
        }

        // Create a new User object with the field values
        User newUser = new User();
        newUser.setNom(nom);
        newUser.setPrenom(prenom);
        newUser.setEmail(email);
        newUser.setMdp(hashedPassword);
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
        Notification.display("Complexité du mot de passe: ", complexityNotification);
    }
}