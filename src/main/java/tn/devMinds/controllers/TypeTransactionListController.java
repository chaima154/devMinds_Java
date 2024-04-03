package tn.devMinds.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import tn.devMinds.entities.TypeTransaction;
import tn.devMinds.iservices.TypeTransactionService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TypeTransactionListController implements Initializable {
    public BackendHome backendHome;

    @FXML
    private TableView<TypeTransaction> table;
    @FXML
    private TableColumn<TypeTransaction, String> Libelle;
    @FXML
    private TableColumn<TypeTransaction, Void> btn;
    @FXML
    private Button ajout;
    @FXML
    private TextField searchTerm;

    private TypeTransactionService typeTransactionService = new TypeTransactionService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        shoxwList(getAllList());
        searchTerm.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                shoxwList(getAllList());
            }
            // Ajoutez ici la logique de recherche si nécessaire
        });
    }

    private ObservableList<TypeTransaction> getAllList() {
        return FXCollections.observableArrayList(typeTransactionService.getAllData());
    }

    @FXML
    void ajout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AjoutTypeTransaction.fxml"));
        Parent parent = loader.load();
        AjoutTypeTransactionController controller = loader.getController();
        controller.setTypeTransactionListController(this);
        backendHome.borderPane.setCenter(parent);
    }

    public void shoxwList(ObservableList<TypeTransaction> observableList) {
        Libelle.setCellValueFactory(data -> {
            TypeTransaction yourData = data.getValue();
            return new SimpleObjectProperty<>(yourData.getLibelle());
        });

        btn.setCellFactory(param -> new TableCell<>() {
            private final Button button = new Button("Supprimer Type Transaction");

            {
                button.setStyle("-fx-background-color: red;-fx-text-fill: white;");
                button.setOnAction(event -> {
                    TypeTransaction typeTransaction = getTableView().getItems().get(getIndex());
                    // Ajoutez ici la logique de suppression si nécessaire
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(button);
                }
            }
        });

        table.setItems(observableList);
    }
}
