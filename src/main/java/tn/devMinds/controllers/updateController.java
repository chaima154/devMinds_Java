package tn.devMinds.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import tn.devMinds.iservices.userService;
import tn.devMinds.entities.user;

public class updateController {

    private userService userService = new userService();
    private user user;

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TextField email_input;
    @FXML
    private TextField firstname_input;
    @FXML
    private TextField lastname_input;
    @FXML
    private Text error;

    public void setUser(user user) {
        this.user = user;
        if (user != null) {
            email_input.setText(user.getEmail());
            firstname_input.setText(user.getPrenom());
            lastname_input.setText(user.getNom());
        }
    }

    @FXML
    void back_to_list(ActionEvent event) throws IOException {
        mainController.loadFXML("/banque/listUser.fxml");
    }

    @FXML
    void reset_input(ActionEvent event) {
        email_input.clear();
        firstname_input.clear();
        lastname_input.clear();
        error.setVisible(false);
    }

    @FXML
    void submit_user(ActionEvent event) {
        if (validateForm()) {
            user updatedUser = new user(
                    user.getId(),
                    email_input.getText(),
                    firstname_input.getText(),
                    lastname_input.getText()
            );
            showUpdateConfirmation(updatedUser);
        }
    }

    @FXML
    void initialize() {
        error.setVisible(false);
    }

    private boolean validateForm() {
        if (email_input.getText().isEmpty() || firstname_input.getText().isEmpty() ||
                lastname_input.getText().isEmpty() ) {
            error.setText("Please fill in all required fields.");
            error.setVisible(true);
            return false;
        } else {
            return true;
        }
    }

    private void showUpdateConfirmation(user user){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Update User");
        alert.setContentText("Are you sure you want to update this user: " + user.getEmail() + "?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                userService.updateUser(user, user.getId());
            }
        });
    }

    // Email validation method
    private boolean isEmailValid(String email){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @FXML
    void handleSubmit(ActionEvent event) {
        if (validateForm()) {
            user updatedUser = new user(
                    user.getId(),
                    email_input.getText(),
                    firstname_input.getText(),
                    lastname_input.getText()
            );
            userService.updateUser(updatedUser, updatedUser.getId());
            showUpdateConfirmation(updatedUser);
        }
    }

}
