package tn.devMinds.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.devMinds.entities.User;
import tn.devMinds.iservices.UserService;

import java.io.IOException;
import java.util.ArrayList;

public class ListeUsersController extends BackendHome {
    @FXML
    private VBox container;
    @FXML
    private TableView<User> userTableView;
    @FXML
    private TextField searchTermField;

    private UserService userService;

    public ListeUsersController() {
        userService = new UserService();
    }

    @FXML
    public void initialize() {
        // Appelé lorsque le contrôleur est initialisé (après le chargement du FXML)
        searchUsers();
    }

    public void searchUsers() {
        String searchTerm = searchTermField.getText().trim().toLowerCase();

        // Récupérer tous les utilisateurs
        ArrayList<User> allUsers = userService.getAllData();

        // Filtrer les utilisateurs en fonction du terme de recherche
        ArrayList<User> filteredUsers = new ArrayList<>();
        for (User user : allUsers) {
            // Vérifier si le terme de recherche est contenu dans l'un des champs de l'utilisateur
            if (user.getNom().toLowerCase().contains(searchTerm) ||
                    user.getPrenom().toLowerCase().contains(searchTerm) ||
                    user.getEmail().toLowerCase().contains(searchTerm)) {
                filteredUsers.add(user);
            }
        }

        // Mettre à jour la table avec les utilisateurs filtrés
        updateTableView(filteredUsers);
    }

    private void updateTableView(ArrayList<User> users) {
        // Effacer les éléments de la table
        userTableView.getItems().clear();

        // Ajouter les utilisateurs filtrés à la table
        userTableView.getItems().addAll(users);
    }

    public void openAddUserDialog(ActionEvent actionEvent) {
        try {
            // Charger la vue AjoutUser.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/AjoutUser.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la vue chargée
            Scene scene = new Scene(root);

            // Obtenir la fenêtre principale et afficher la nouvelle scène
            Stage stage = (Stage) container.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}