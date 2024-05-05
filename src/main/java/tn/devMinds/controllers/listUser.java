package tn.devMinds.controllers;

import javafx.stage.Stage;
import tn.devMinds.controllers.updateController;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import tn.devMinds.entities.user;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import tn.devMinds.iservices.userService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class listUser {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TableColumn<user, Void> actions;
    @FXML
    private TableView<user> list_user;

    @FXML
    private TableColumn<user, Integer> id;
    @FXML
    private TableColumn<user, String> email;
    @FXML
    private TableColumn<user, String> prenom;
    @FXML
    private TableColumn<user, String> nom;
    @FXML
    private Button add_user_button;


    userService userService = new userService();
    //Redirect to Add user

    @FXML
    void add_user(ActionEvent event) throws IOException {
        // Call loadFXML method from mainController to load the addUser.fxml
        mainController.loadFXML("/banque/addUser.fxml");
    }





    //Table View initialization
    @FXML
    void initialize() {
        ObservableList<user> list = FXCollections.observableList(userService.getallUserdata());
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));

        actions.setCellFactory(createActionsCellFactory());
        list_user.setItems(list);
    }

    //Action buttons and their functionality
    private Callback<TableColumn<user, Void>, TableCell<user, Void>> createActionsCellFactory() {
        return new Callback<TableColumn<user, Void>, TableCell<user, Void>>() {
            @Override
            public TableCell<user, Void> call(final TableColumn<user, Void> param) {
                return new TableCell<user, Void>() {
                    private final Button btnUpdate = new Button("Update");
                    private final Button btnDelete = new Button("Delete");
                    {
                        btnUpdate.setOnAction(event -> {
                            user user = getTableView().getItems().get(getIndex());
                            System.out.println(user);
                            try {
                                FXMLLoader loader = mainController.loadFXML("/banque/updateUser.fxml");
                                updateController updateController = loader.getController();
                                updateController.setUser(user);
                                System.out.println("Selected Personne: " + user);

                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
                        btnDelete.setOnAction(event -> {
                            user user = getTableView().getItems().get(getIndex());
                            showDeleteConfirmation(user);
                            refreshlist();
                        });
                    }
                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            user currentUser = getTableView().getItems().get(getIndex());
                            if (currentUser != null) {
                                HBox buttonsBox = new HBox(btnUpdate, btnDelete);
                                setGraphic(buttonsBox);
                            } else {
                                setGraphic(null);
                            }
                        }
                    }
                };
            }
        };
    }

    //Table view refresh
    public void refreshlist(){
        ObservableList<user> updatedList = FXCollections.observableList(userService.getallUserdata());
        list_user.setItems(updatedList);
    }
    private void showDeleteConfirmation(user user) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Delete User");
        alert.setContentText("Are you sure you want to delete this user: " + user.getEmail() + "?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                userService.deleteUser(user.getId());
            }
        });
    }


}

