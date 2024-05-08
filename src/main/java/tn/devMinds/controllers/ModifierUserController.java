package tn.devMinds.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.devMinds.entities.Role;
import tn.devMinds.entities.User;
import tn.devMinds.iservices.UserService;
import javafx.scene.layout.VBox;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import org.mindrot.jbcrypt.BCrypt;


public class ModifierUserController extends BackendHome {

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
    @FXML
    private GridPane container;
    @FXML
    private ImageView logoImageView;


    private UserService userService;
    private ListeUsersController listeUsersController;
    private User user;

    public void initData(User user, ListeUsersController listeUsersController) {
        this.user = user;
        this.listeUsersController = listeUsersController;
        // Pré-remplir les champs du formulaire avec les informations de l'utilisateur
        firstNameField.setText(user.getNom());
        lastNameField.setText(user.getPrenom());
        emailField.setText(user.getEmail());
        passwordField.setText(user.getMdp());
        roleComboBox.setValue(user.getRole());
    }



    public ModifierUserController() throws SQLException {
        userService = new UserService();
        this.user = user;

    }


    public void updateUser(int userId) {
        // Retrieve new user information from form fields
        String nom = firstNameField.getText();
        String prenom = lastNameField.getText();
        String email = emailField.getText();
        Role role = roleComboBox.getValue();

        // Hash the password with BCrypt if it's not empty
        String mdp = passwordField.getText();
        if (!mdp.isEmpty()) {
            String hashedPassword = BCrypt.hashpw(mdp, BCrypt.gensalt());
            user.setMdp(hashedPassword);
        }

        // Create a new User object with the updated information
        User updatedUser = new User();
        updatedUser.setNom(nom);
        updatedUser.setPrenom(prenom);
        updatedUser.setEmail(email);
        updatedUser.setMdp(user.getMdp()); // Use the hashed password
        updatedUser.setRole(role);

        // Call the update method in the UserService
        String errorMessage = userService.update(updatedUser, userId);
        if (errorMessage == null) {
            // Update successful
            System.out.println("L'utilisateur a été mis à jour avec succès.");
        } else {
            // Display an error message
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

        if (firstNameField.getText().isEmpty()) {
            firstNameError.setText("Le prénom est obligatoire");

        } else {
            firstNameError.setText("");
        }

        if (lastNameField.getText().isEmpty()) {
            lastNameError.setText("Le nom est obligatoire");

        } else {
            lastNameError.setText("");
        }

        if (emailField.getText().isEmpty()) {
            emailError.setText("L'email est obligatoire");

        } else {
            emailError.setText("");
        }

        if (passwordField.getText().isEmpty()) {
            mdpError.setText("Le mot de passe est obligatoire");

        } else {
            mdpError.setText("");
        }

        if (roleComboBox.getValue() == null) {
            // Display an error message for role selection
            // You can handle error message for roleComboBox separately if needed

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
            Role role = roleComboBox.getValue();

            user.setNom(nom);
            user.setPrenom(prenom);
            user.setEmail(email);
            user.setRole(role);

            // Call updateUser method to update user data
            updateUser(user.getId());

            if (listeUsersController != null) {
                listeUsersController.refreshUserTableView();
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