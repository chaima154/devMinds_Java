package tn.devMinds.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import tn.devMinds.controllers.SideBarre_adminController;
import tn.devMinds.controllers.TransactionListController;
import tn.devMinds.controllers.TypeTransactionListController;
import tn.devMinds.entities.TypeTransaction;
import tn.devMinds.iservices.TypeTransactionService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AjouterTransactionController extends SideBarre_adminController {

    @FXML
    private BorderPane borderPane;

    @FXML
    private ChoiceBox<String> typeTransactionChoiceBox;

    @FXML
    private VBox dynamicFieldsContainer;
    private SideBarre_adminController sidebarController;
    private TransactionListController TransactionListController;

    public void setSidebarController(SideBarre_adminController sidebarController) {
        this.sidebarController = sidebarController;
    }

    private TypeTransactionService typeTransactionService = new TypeTransactionService();
    private String selectedTypeTransaction;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Load types of transaction into the choice box
        ObservableList<String> typeTransactionNames = typeTransactionService.getAllTypeTransactionNames();
        typeTransactionChoiceBox.setItems(typeTransactionNames);

        // Initialize dynamic fields
        selectedTypeTransaction = typeTransactionNames.get(0); // Set the default selected item
        initializeDynamicFields();

        // Listen for changes in the selected item of the choice box
        typeTransactionChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedTypeTransaction = newValue;
            initializeDynamicFields();
        });

    }

    private void initializeDynamicFields() {
        dynamicFieldsContainer.getChildren().clear();

        if (selectedTypeTransaction != null) {
            ObservableList<Label> labels = FXCollections.observableArrayList();
            ObservableList<TextField> textFields = FXCollections.observableArrayList();

            // Add specific fields based on the selected type of transaction
            switch (selectedTypeTransaction) {
                case "Virement":
                    addField("Montant Transaction", labels, textFields);
                    addField("Date", labels, textFields);
                    addField("Compte ID", labels, textFields);
                    addField("Destinataire Compte ID", labels, textFields);
                    break;
                case "Versement":
                    addField("Montant Transaction", labels, textFields);
                    addField("Date", labels, textFields);
                    addField("Destinataire Compte ID", labels, textFields);
                    break;
                case "Encaissement de chèque":
                    addField("Montant Transaction", labels, textFields);
                    addField("Date", labels, textFields);
                    addField("Numéro de chèque", labels, textFields);
                    addField("Destinataire Compte ID", labels, textFields);
                    break;
            }

            VBox fieldContainer = new VBox();
            fieldContainer.getChildren().addAll(labels);
            fieldContainer.getChildren().addAll(textFields);
            dynamicFieldsContainer.getChildren().add(fieldContainer);
        }
    }

    private void addField(String labelText, ObservableList<Label> labels, ObservableList<TextField> textFields) {
        Label label = new Label(labelText);
        TextField textField = new TextField();
        labels.add(label);
        textFields.add(textField);
    }

    @FXML
    void AddTransaction(ActionEvent event) throws IOException {
        if (selectedTypeTransaction == null) {
            displayAlert("Alert", "Veuillez sélectionner un type de transaction.", Alert.AlertType.WARNING);
            return;
        }

        // Perform validation on dynamic fields
        for (TextField textField : getTextFieldList()) {
            if (textField.getText().isEmpty()) {
                displayAlert("Alert", "Veuillez remplir tous les champs.", Alert.AlertType.WARNING);
                return;
            }
        }

        // Show a pop-up window based on the selected type of transaction
        FXMLLoader loader = new FXMLLoader();
        Parent root = null;
        switch (selectedTypeTransaction) {
            case "virement":
                loader.setLocation(getClass().getResource("/banque/AjoutVirement.fxml"));
                root = loader.load();
                break;
            case "Versement":
                loader.setLocation(getClass().getResource("/banque/AjoutVersement.fxml"));
                root = loader.load();
                break;
            case "Encaissement de chèque":
                loader.setLocation(getClass().getResource("/banque/AjoutEncaissementCheque.fxml"));
                root = loader.load();
                break;
            default:
                break;
        }

        if (root != null) {
            // Show the pop-up window
            javafx.stage.Stage stage = new javafx.stage.Stage();
            stage.setScene(new javafx.scene.Scene(root));
            stage.show();
        }
    }


    // Here, you can retrieve all the data from dynamic fields and perform your add transaction logic


    private ObservableList<TextField> getTextFieldList() {
        ObservableList<TextField> textFields = FXCollections.observableArrayList();
        for (Label label : getLabelList()) {
            for (int i = 0; i < dynamicFieldsContainer.getChildren().size(); i++) {
                if (dynamicFieldsContainer.getChildren().get(i).equals(label)) {
                    TextField textField = (TextField) dynamicFieldsContainer.getChildren().get(i + 1);
                    textFields.add(textField);
                }
            }
        }
        return textFields;
    }

    private ObservableList<Label> getLabelList() {
        ObservableList<Label> labels = FXCollections.observableArrayList();
        for (int i = 0; i < dynamicFieldsContainer.getChildren().size(); i++) {
            if (dynamicFieldsContainer.getChildren().get(i) instanceof Label) {
                labels.add((Label) dynamicFieldsContainer.getChildren().get(i));
            }
        }
        return labels;
    }

    @FXML
    void retour(ActionEvent event) throws IOException {
        retourner();
    }

    private void retourner() throws IOException {
        // Navigate back to the appropriate list view (ListTypeTransaction.fxml)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/ListeTransaction.fxml"));
        Parent parent = loader.load();
        TransactionListController TransactionListController = loader.getController();
        TransactionListController.setSidebarController(this.sidebarController);

        // Check if borderPane is initialized before setting its center
        if (borderPane != null) {
            borderPane.setCenter(parent);
        } else {
            System.out.println("BorderPane is null!");
        }
    }


    @FXML
    public void initialize() {
        // Load types of transaction into the choice box
        ObservableList<String> typeTransactionNames = typeTransactionService.getAllTypeTransactionNames();
        typeTransactionChoiceBox.setItems(typeTransactionNames);

        // Listen for changes in the selected item of the choice box
        typeTransactionChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedTypeTransaction = newValue;
            initializeDynamicFields();
        });
    }

    public void displayAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }
    public void setTransactionListController(TransactionListController controller) {
        this.TransactionListController = controller;
    }
}
