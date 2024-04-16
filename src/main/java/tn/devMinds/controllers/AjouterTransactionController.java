package tn.devMinds.controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import javafx.scene.layout.VBox;
import tn.devMinds.entities.Transaction;
import tn.devMinds.entities.TypeTransaction;
import tn.devMinds.iservices.TransactionService;
import tn.devMinds.iservices.TypeTransactionService;

public class AjouterTransactionController {

    @FXML
    private ComboBox<String> typeTransactionComboBox;
    @FXML
    private VBox formContainer;
    @FXML
    private TextField compteIdField, destinataireCompteIdField, montantField;
    @FXML
    private DatePicker datePicker;

    private TypeTransactionService typeTransactionService = new TypeTransactionService();
    private TransactionService transactionService = new TransactionService();

    @FXML
    private void initialize() {
        loadTypeTransactions();
    }

    private void loadTypeTransactions() {
        ObservableList<String> typeTransactionNames = typeTransactionService.getAllTypeTransactionNames();
        typeTransactionComboBox.setItems(typeTransactionNames);
    }
    @FXML
    private void handleShowForm() {
        String selectedType = typeTransactionComboBox.getValue();
        // Afficher le formulaire uniquement pour certains types, par exemple "Virement"
        if (selectedType != null && selectedType.equals("Virement")) {
            formContainer.setVisible(true);
        } else {
            formContainer.setVisible(false);
            // Gérer d'autres types si nécessaire
        }
    }

    @FXML
    private void handleSubmitTransaction() {
        int compteId = Integer.parseInt(compteIdField.getText());
        int destinataireCompteId = Integer.parseInt(destinataireCompteIdField.getText());
        double montant = Double.parseDouble(montantField.getText());
        String date = datePicker.getValue().toString();  // Assurez-vous de gérer les exceptions de parsing

        Transaction transaction = new Transaction(0, date, "En cours", montant, "", 1, compteId, destinataireCompteId);
        transactionService.add(transaction);
    }
}