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
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import tn.devMinds.entities.User;
import tn.devMinds.iservices.UserService;
import org.mindrot.jbcrypt.BCrypt;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.scene.control.TableCell;
import org.apache.poi.ss.usermodel.*;
import javafx.stage.FileChooser;


import java.io.FileOutputStream;
import java.io.IOException;

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

    public ListeUsersController() throws SQLException {
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
        try {
            // Call the delete method in the UserService
            String errorMessage = String.valueOf(userService.delete(user.getId()));
            if (errorMessage == null) {
                // Deletion successful
                System.out.println("L'utilisateur a été supprimé avec succès.");

                // Remove the user from the table view
                userTableView.getItems().remove(user);
            } else {
                // Display an error message
                System.out.println("Erreur lors de la suppression de l'utilisateur : " + errorMessage);
            }
        } catch (SQLException e) {
            // Handle the SQLException
            System.out.println("Erreur SQL lors de la suppression de l'utilisateur : " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void downloadUsersAsExcel() {
        try {
            // Créer un nouveau classeur Excel
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Utilisateurs");

            // Créer l'en-tête du tableau Excel
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("Nom");
            headerRow.createCell(2).setCellValue("Prénom");
            headerRow.createCell(3).setCellValue("Email");
            headerRow.createCell(4).setCellValue("Rôle");

            // Remplir les données des utilisateurs
            int rowNum = 1;
            for (User user : userTableView.getItems()) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(user.getId());
                row.createCell(1).setCellValue(user.getNom());
                row.createCell(2).setCellValue(user.getPrenom());
                row.createCell(3).setCellValue(user.getEmail());
                row.createCell(4).setCellValue(user.getRole().toString());
            }

            // Afficher un FileChooser pour permettre à l'utilisateur de choisir l'emplacement et le nom du fichier Excel
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Enregistrer le fichier Excel");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichier Excel", "*.xlsx"));
            File file = fileChooser.showSaveDialog(container.getScene().getWindow());

            if (file != null) {
                // Enregistrer le fichier Excel dans l'emplacement choisi par l'utilisateur
                FileOutputStream fileOut = new FileOutputStream(file);
                workbook.write(fileOut);
                fileOut.close();

                // Fermer le classeur Excel
                workbook.close();

                System.out.println("Fichier Excel enregistré avec succès : " + file.getAbsolutePath());
            } else {
                System.out.println("Opération d'enregistrement annulée par l'utilisateur.");
            }

        } catch (IOException e) {
            e.printStackTrace();
            // Gérer les exceptions IO lors de la création du fichier Excel
        }
    }
}