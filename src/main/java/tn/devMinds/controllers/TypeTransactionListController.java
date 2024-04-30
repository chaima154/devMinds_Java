package tn.devMinds.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import tn.devMinds.entities.TypeTransaction;
import tn.devMinds.iservices.TypeTransactionService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TypeTransactionListController implements Initializable {

    public SideBarre_adminController backendHome;
    @FXML
    public BorderPane borderPane;

    @FXML
    private TableView<TypeTransaction> table;
    @FXML
    private TableColumn<TypeTransaction, String> libelleColumn;  // Renamed for consistency with FXML
    @FXML
    private TableColumn<TypeTransaction, Void> actionColumn;
    @FXML
    private TableColumn<TypeTransaction, Double> commisiontypetransaction;

    @FXML
    private Button ajout;
    @FXML
    private TextField searchTerm;
    private SideBarre_adminController sidebarController;

    public void setSidebarController(SideBarre_adminController sidebarController) {
        this.sidebarController = sidebarController;
    }

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

    ObservableList<TypeTransaction> getAllList() {
        return FXCollections.observableArrayList(typeTransactionService.getAllData());
    }

    @FXML
    void ajout(ActionEvent event) {
        openPopup("/banque/AjoutTypeTransaction.fxml", "Ajouter Type Transaction");
    }

    @FXML
    void update(ActionEvent event) {
        TypeTransaction tt = table.getSelectionModel().getSelectedItem();
        if (tt != null) {
            openUpdatePopup(tt);
        } else {
            // Handle no selection error
        }
    }

    private void openPopup(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent parent = loader.load();

            // Get the controller
            AjoutTypeTransactionController controller = loader.getController();

            // Set the reference of TypeTransactionListController
            controller.setTypeTransactionListController(this);

            // Create a new stage
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show(); // Show the popup
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openUpdatePopup(TypeTransaction tt) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/UpdateTypeTransaction.fxml"));
            Parent root = loader.load();

            UpdateTypeTransactionController controller = loader.getController();
            controller.initializeData(tt); // Pass the TypeTransaction object to the controller

            // Create a new stage
            Stage stage = new Stage();
            stage.setTitle("Modifier Type Transaction");
            stage.setScene(new Scene(root));
            stage.showAndWait(); // Show the popup and wait for it to close

            showList(getAllList()); // Reload the list after update
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void setupActionColumn() {
        actionColumn.setCellFactory(param -> new TableCell<TypeTransaction, Void>() {
            private final Button deleteBtn = new Button("Delete");
            private final Button updateBtn = new Button("Update");
            private final HBox hbox = new HBox(5, updateBtn, deleteBtn); // Adjust spacing as needed

            {
                deleteBtn.setOnAction(event -> {
                    TypeTransaction tt = getTableView().getItems().get(getIndex());
                    typeTransactionService.delete(tt);  // Assuming delete method in your service
                    showList(getAllList());  // Refresh list after delete
                });

                updateBtn.setOnAction(event -> {
                    TypeTransaction tt = getTableView().getItems().get(getIndex());
                    openUpdatePopup(tt); // Open the update FXML
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : hbox);
            }
        });
    }

    @FXML
    private void delete(ActionEvent event) {
        TypeTransaction tt = table.getSelectionModel().getSelectedItem();
        if (tt != null) {
            if (typeTransactionService.delete(tt)) {
                // Successfully deleted
                showList(getAllList());  // Refresh list after delete
            } else {
                // Handle deletion failure
                System.err.println("Deletion failed.");
            }
        } else {
            // No item selected, handle accordingly
            System.err.println("No item selected for deletion.");
        }
    }
    public void showList(ObservableList<TypeTransaction> observableList) {
        libelleColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getLibelle()));
        commisiontypetransaction.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getCommission().doubleValue()));

        table.setItems(observableList);
    }

}