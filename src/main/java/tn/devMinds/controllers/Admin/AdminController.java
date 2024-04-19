package tn.devMinds.controllers.Admin;

import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import tn.devMinds.entities.Client;
import tn.devMinds.entities.Model;
import tn.devMinds.tools.MyConnection;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    public AnchorPane admin_parent;
    public ListView<Client> clients_listview;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Populate the list of clients
        displayClients();

        // Add listener to the clients listview
        clients_listview.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Enable or disable buttons based on selection
            // For example, enable delete button if a client is selected
            // delete_btn.setDisable(newValue == null);
        });
    }

    // Method to add a new client
    public void addClient() {
        // Show create client window
        Model.getInstance().getViewFactory().showCreateClientWindow();
    }

    // Method to delete a client
    public void deleteClient() {
        Client selectedClient = clients_listview.getSelectionModel().getSelectedItem();
        if (selectedClient != null) {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm Deletion");
            confirmAlert.setHeaderText(null);
            confirmAlert.setContentText("Are you sure you want to delete this client?");

            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Delete the client from the database
                MyConnection connection = Model.getInstance().getCnx();
                // Implement delete operation using your database connection

                // Refresh client list after deletion
                displayClients();
            }
        } else {
            // Show error message if no client selected
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Please select a client to delete.");
            errorAlert.showAndWait();
        }
    }

    // Method to display the list of clients
    public void displayClients() {
        // Retrieve the list of clients from the database
        MyConnection connection = Model.getInstance().getCnx();
        // Implement retrieval operation using your database connection

        // Populate the ListView with retrieved clients
        clients_listview.getItems().clear(); // Clear previous items
        // Add retrieved clients to the ListView
    }
}
