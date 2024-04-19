package tn.devMinds.controllers.Admin;

import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import tn.devMinds.entities.Model;
import tn.devMinds.tools.MyConnection;
import javafx.scene.control.ButtonType;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class CreateClientController implements Initializable {
    public TextField fName_fid;
    public TextField lName_fid;
    public TextField password_fid;
    public CheckBox pAddress_box;
    public CheckBox ch_acc_box;
    public TextField ch_amount_fid;
    public CheckBox sv_acc_box;
    public TextField sv_amount_fid;
    public Label error_lbl;
    public Label pAddress_lbl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize UI components and event handlers
    }

    // Method to add a new client
    public void addClient() {
        // Validate input fields
        if (validateInput()) {
            // Add client to the database
            MyConnection connection = Model.getInstance().getCnx();
            // Implement insert operation using your database connection

            // Show success message
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Success");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Client added successfully!");
            successAlert.showAndWait();

            // Clear input fields
            clearFields();
        } else {
            // Show error message
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Please fill all required fields.");
            errorAlert.showAndWait();
        }
    }

    // Method to validate input fields
    private boolean validateInput() {
        // Volet 1: Check if any required fields are empty
        if (fName_fid.getText().isEmpty() || lName_fid.getText().isEmpty() || password_fid.getText().isEmpty()) {
            error_lbl.setText("Please fill all required fields.");
            return false;
        }

        // Volet 3: Validate email format
        // Example: Check if email contains '@'
        if (!isValidEmailFormat(pAddress_lbl.getText())) {
            error_lbl.setText("Invalid email format.");
            return false;
        }

        // Volet 2: Check uniqueness constraint
        // Example: Check if email already exists in the database
        if (isDuplicateEmail(pAddress_lbl.getText())) {
            error_lbl.setText("Email already exists.");
            return false;
        }

        // Volet 4: Confirmation alert for adding client
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirmation");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to add this client?");
        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() != ButtonType.OK) {
            return false;
        }

        // All validations passed
        return true;
    }

    // Method to clear input fields
    private void clearFields() {
        fName_fid.clear();
        lName_fid.clear();
        password_fid.clear();
        pAddress_box.setSelected(false);
        ch_acc_box.setSelected(false);
        ch_amount_fid.clear();
        sv_acc_box.setSelected(false);
        sv_amount_fid.clear();
    }

    // Volet 3: Validate email format
    private boolean isValidEmailFormat(String email) {
        // Implement email format validation logic
        return email.contains("@");
    }

    // Volet 2: Check uniqueness constraint for email
    private boolean isDuplicateEmail(String email) {
        // Implement database check for email uniqueness
        return false; // Placeholder, replace with actual database check
    }
}
