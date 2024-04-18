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
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import tn.devMinds.entities.TypeTransaction;
import tn.devMinds.iservices.TypeTransactionService;


import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ResourceBundle;


public class TypeTransactionListController implements Initializable {
    public SideBarre_adminController backendHome;

    @FXML
    private TableView<TypeTransaction> table;
    @FXML
    private TableColumn<TypeTransaction, String> libelleColumn;  // Renamed for consistency with FXML
    @FXML
    private TableColumn<TypeTransaction, Void> actionColumn;

    @FXML
    private Button ajout;
    @FXML
    private TextField searchTerm;

    private TypeTransactionService typeTransactionService = new TypeTransactionService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showList(getAllList());  // Corrected method name
        setupActionColumn();
        searchTerm.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                showList(getAllList());
            } else {
                // Implement search functionality if required
            }
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
        SideBarre_adminController.borderPane.setCenter(parent);
        showList(getAllList());  // Refresh list after adding
    }

    private void setupActionColumn() {
        actionColumn.setCellFactory(param -> new TableCell<TypeTransaction, Void>() {
            private final Button deleteBtn = new Button("Delete");
            private final Button updateBtn = new Button("Update");
            private final HBox hbox = new HBox(5, updateBtn, deleteBtn);  // Adjust spacing as needed

            {
                deleteBtn.setOnAction(event -> {
                    TypeTransaction tt = getTableView().getItems().get(getIndex());
                    typeTransactionService.delete(tt);  // Assuming delete method in your service
                    showList(getAllList());  // Refresh list after delete
                });
                updateBtn.setOnAction(event -> {
                    TypeTransaction tt = getTableView().getItems().get(getIndex());
                    // Here, you would need some form of dialog or UI to handle updating
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : hbox);
            }
        });
    }

    public void showList(ObservableList<TypeTransaction> observableList) {
        libelleColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getLibelle()));
        table.setItems(observableList);
    }
}

