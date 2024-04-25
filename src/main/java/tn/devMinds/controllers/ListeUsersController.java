package tn.devMinds.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.devMinds.entities.User;
import tn.devMinds.iservices.UserService;

import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.control.TableCell;

public class ListeUsersController extends BackendHome {
    @FXML
    private VBox container;
    @FXML
    private TableView<User> userTableView;
    @FXML
    private TextField searchTermField;
    @FXML
    private TableColumn<User, Void> actionsColumn;

    private UserService userService;

    public ListeUsersController() {
        userService = new UserService();
    }

    @FXML
    public void initialize() {
        // Called when the controller is initialized (after FXML loading)
        searchUsers();
        configureActionsColumn();
    }

    public void searchUsers() {
        String searchTerm = searchTermField.getText().trim().toLowerCase();

        // Retrieve all users
        ArrayList<User> allUsers = userService.getAllData();

        // Filter users based on the search term
        ArrayList<User> filteredUsers = new ArrayList<>();
        for (User user : allUsers) {
            // Check if the search term is contained in any of the user's fields
            if (user.getNom().toLowerCase().contains(searchTerm) ||
                    user.getPrenom().toLowerCase().contains(searchTerm) ||
                    user.getEmail().toLowerCase().contains(searchTerm)) {
                filteredUsers.add(user);
            }
        }

        // Update the table with the filtered users
        updateTableView(filteredUsers);
    }

    private void updateTableView(ArrayList<User> users) {
        // Clear the table items
        userTableView.getItems().clear();

        // Add the filtered users to the table
        userTableView.getItems().addAll(users);
    }

    private void configureActionsColumn() {
        actionsColumn.setCellFactory(column -> {
            return new TableCell<User, Void>() {
                private final Button updateButton = new Button("Modifier");
                private final Button deleteButton = new Button("Supprimer");

                {
                    updateButton.setOnAction(event -> {
                        User user = getTableView().getItems().get(getIndex());
                        handleUpdateUser(user);
                    });

                    deleteButton.setOnAction(event -> {
                        User user = getTableView().getItems().get(getIndex());
                        handleDeleteUser(user);
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(new HBox(updateButton, deleteButton));
                    }
                }
            };
        });
    }

    public void openAddUserDialog(ActionEvent actionEvent) {
        try {
            // Load the AjoutUser.fxml view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/AjoutUser.fxml"));
            Parent root = loader.load();

            // Create a new scene with the loaded view
            Scene scene = new Scene(root);

            // Get the main window and display the new scene
            Stage stage = (Stage) container.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleUpdateUser(User user) {
        openModifierUserForm(user);
    }

    private void openModifierUserForm(User user) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/banque/ModifierUser.fxml"));
            Parent root = loader.load();

            ModifierUserController modifierUserController = loader.getController();
            modifierUserController.setUser(user); // Pré-remplir le formulaire avec les informations de l'utilisateur

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void handleDeleteUser(User user) {
        // Handle delete logic for the selected user (confirmation dialog, remove from the table, etc.)
    }
}