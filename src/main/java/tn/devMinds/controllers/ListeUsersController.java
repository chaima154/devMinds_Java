package tn.devMinds.controllers;


import javafx.fxml.FXML;

import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import tn.devMinds.entities.User;

import tn.devMinds.iservices.UserService;

import java.util.ArrayList;


public class ListeUsersController {
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

    public void searchUsers() {
        String searchTerm = searchTermField.getText().trim().toLowerCase();

        // Récupérer tous les utilisateurs
        ArrayList<User> allUsers = userService.getAllData();

        // Filtrer les utilisateurs en fonction du terme de recherche
        ArrayList<User> filteredUsers = new ArrayList<>();
        for (User user : allUsers) {
            // Vérifier si le terme de recherche est contenu dans l'un des champs de l'utilisateur
            if (user.getFirstName().toLowerCase().contains(searchTerm) ||
                    user.getLastName().toLowerCase().contains(searchTerm) ||
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
}
