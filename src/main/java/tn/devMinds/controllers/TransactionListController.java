package tn.devMinds.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import tn.devMinds.entities.Transaction;
import tn.devMinds.iservices.TransactionService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TransactionListController implements Initializable {

    @FXML
    private TableView<Transaction> table;
    @FXML
    private TableColumn<Transaction, Integer> idColumn;
    @FXML
    private TableColumn<Transaction, String> dateColumn;
    @FXML
    private TableColumn<Transaction, String> statutColumn;
    @FXML
    private TableColumn<Transaction, Double> montantColumn;
    @FXML
    private TableColumn<Transaction, String> numChequeColumn;
    @FXML
    private TableColumn<Transaction, Integer> typeTransactionColumn;
    @FXML
    private TableColumn<Transaction, Integer> compteColumn;
    @FXML
    private TableColumn<Transaction, Integer> destinataireColumn;
    @FXML
    private BorderPane borderPane;

    private final TransactionService transactionService = new TransactionService();
    private SideBarre_adminController sidebarController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showList(getAllList());
        setupActionColumn();
    }

    private void setupActionColumn() {
        // Setup your action column here
    }

    @FXML
    void ajout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/AjouterTransaction.fxml"));
        Parent parent = loader.load();
        AjouterTransactionController controller = loader.getController();
        controller.setTransactionListController(this);
        this.borderPane.setCenter(parent); // Changed to use 'this'
        showList(getAllList());  // Refresh list after adding
    }

    private ObservableList<Transaction> getAllList() {
        return FXCollections.observableArrayList(transactionService.getAllTransactions());
    }

    private void showList(ObservableList<Transaction> observableList) {
        idColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getId()));
        dateColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getDate()));
        statutColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getStatut()));
        montantColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getMontant_transaction()));
        numChequeColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getNumcheque()));
        typeTransactionColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getTypetransaction_id()));
        compteColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getCompte_id()));
        destinataireColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getDestinataire_compte_id_id()));
        table.setItems(observableList);
    }

    public void setSidebarController(SideBarre_adminController sideBarreAdminController) {
        this.sidebarController = sidebarController;
    }
}
