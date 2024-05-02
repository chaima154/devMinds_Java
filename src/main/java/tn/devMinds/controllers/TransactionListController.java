package tn.devMinds.controllers;

import javafx.beans.property.SimpleIntegerProperty;
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
import tn.devMinds.entities.Transaction;
import tn.devMinds.entities.TypeTransaction;
import tn.devMinds.iservices.TransactionService;
import tn.devMinds.iservices.TypeTransactionService;
import tn.devMinds.tools.MyConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private TableColumn<Transaction, String> typeTransactionColumn; // Changed from Integer to String
    @FXML
    private TableColumn<Transaction, Integer> compteColumn;
    @FXML
    private TableColumn<Transaction, Integer> destinataireColumn;
    @FXML
    private TableColumn<Transaction, Integer> actionColumn;
    @FXML
    private TableColumn<Transaction, Double> comissionTransaction; // Changed from Integer to String

    @FXML
    private BorderPane borderPane;
    private Connection connection;
    @FXML
    private TextField ribSearchField;

    private final TransactionService transactionService = new TransactionService();
    private final TypeTransactionService typeTransactionService = new TypeTransactionService(); // Create an instance of TypeTransactionService
    private SideBarre_adminController sidebarController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showList(getAllList());
        setupActionColumn();
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

    @FXML
    void searchByRib() {
        String rib = ribSearchField.getText();
        ObservableList<Transaction> filteredList = FXCollections.observableArrayList();
        if (!rib.isEmpty()) {
            filteredList = FXCollections.observableArrayList(transactionService.getTransactionsByRib(Integer.parseInt(rib)));
        } else {
            filteredList.addAll(getAllList());
        }
        showList(filteredList);
    }
    private void showList(ObservableList<Transaction> observableList) {
        idColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getId()));
        dateColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getDate()));
        statutColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getStatut()));
        montantColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getMontant_transaction()));
        numChequeColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getNumcheque()));
        // Fetch type transaction name by ID
        typeTransactionColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(typeTransactionService.getTypeTransactionName(data.getValue().getTypetransaction_id())));
        comissionTransaction.setCellValueFactory(data -> {
            Double commission = (Double) typeTransactionService.getComissionTypeTransaction(data.getValue().getTypetransaction_id());
            return commission != null ? new SimpleObjectProperty<>(commission) : new SimpleObjectProperty<>(0.0);
        });

        compteColumn.setCellValueFactory(data -> new SimpleIntegerProperty(getRibForAccount(data.getValue().getCompte_id())).asObject());
        destinataireColumn.setCellValueFactory(data -> new SimpleIntegerProperty(getRibForAccount(data.getValue().getDestinataire_compte_id_id())).asObject());

        table.setItems(observableList);
    }


    private int getRibForAccount(int accountId) {
        int rib = 0;
        try {
            Connection connection = MyConnection.getInstance().getCnx();
            PreparedStatement statement = connection.prepareStatement("SELECT rib FROM compte WHERE id = ?");
            statement.setInt(1, accountId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                rib = resultSet.getInt("rib");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rib;
    }


    private void UpdateTransaction(int transactionId) {
        String query = "UPDATE transaction SET statut = ? WHERE id = ?";
        String getMontantQuery = "SELECT montant_transaction, compte_id, destinataire_compte_id_id, commission FROM transaction WHERE id = ?";
        try (PreparedStatement statement = MyConnection.getInstance().getCnx().prepareStatement(query);
             PreparedStatement getMontantStatement = MyConnection.getInstance().getCnx().prepareStatement(getMontantQuery)) {
            // Start transaction
            MyConnection.getInstance().getCnx().setAutoCommit(false);

            // Update transaction status
            statement.setString(1, "Validé");
            statement.setInt(2, transactionId);
            statement.executeUpdate();

            // Get transaction details
            getMontantStatement.setInt(1, transactionId);
            ResultSet resultSet = getMontantStatement.executeQuery();
            if (resultSet.next()) {
                double montantTransaction = resultSet.getDouble("montant_transaction");
                double commission = resultSet.getDouble("Commission");
                double totalTransactionAmount = montantTransaction + commission;

                int compteId = resultSet.getInt("compte_id");
                int destinataireId = resultSet.getInt("destinataire_compte_id_id");

                // Deduct from the emitting account
                PreparedStatement updateCompteStatementDecrease = MyConnection.getInstance().getCnx().prepareStatement("UPDATE compte SET solde = solde - ? WHERE id = ?");
                updateCompteStatementDecrease.setDouble(1, totalTransactionAmount);
                updateCompteStatementDecrease.setInt(2, compteId);
                updateCompteStatementDecrease.executeUpdate();

                // Add to the receiving account
                PreparedStatement updateCompteStatementIncrease = MyConnection.getInstance().getCnx().prepareStatement("UPDATE compte SET solde = solde + ? WHERE id = ?");
                updateCompteStatementIncrease.setDouble(1, montantTransaction);
                updateCompteStatementIncrease.setInt(2, destinataireId);
                updateCompteStatementIncrease.executeUpdate();

                MyConnection.getInstance().getCnx().commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (MyConnection.getInstance().getCnx() != null) {
                    MyConnection.getInstance().getCnx().rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }





    private void setupActionColumn() {
        actionColumn.setCellFactory(param -> new TableCell<Transaction, Integer>() {
            private final Button deleteBtn = new Button("Delete");
            private final Button updateBtn = new Button("Valider");
            private final HBox hbox = new HBox(5, updateBtn, deleteBtn); // Adjust spacing as needed
            private final HBox hbox2 = new HBox(5, deleteBtn); // Adjust spacing as needed

            {
                deleteBtn.setOnAction(event -> {
                    Transaction transaction = getTableView().getItems().get(getIndex());
                    boolean deleted = transactionService.delete(transaction);
                    if (deleted) {
                        showList(getAllList());  // Refresh list after delete
                    } else {
                        System.out.println("Failed to delete transaction");
                    }
                });

                updateBtn.setOnAction(event -> {
                    Transaction transaction = getTableView().getItems().get(getIndex());
                    if (isTransactionStatusPending(transaction.getId())) {
                        UpdateTransaction(transaction.getId()); // Pass transaction ID to the update method
                        showList(getAllList());  // Refresh list after update
                    } else {
                        System.out.println("Transaction cannot be validated.");
                    }
                });
            }

            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Transaction transaction = getTableView().getItems().get(getIndex());
                    if (isTransactionStatusPending(transaction.getId())) {
                        setGraphic(new HBox(5, updateBtn, deleteBtn));
                    } else {
                        setGraphic(null);
                    }
                }
            }


            private boolean isTransactionStatusPending(int transactionId) {
                String status = "";
                try {
                    Connection connection = MyConnection.getInstance().getCnx();
                    PreparedStatement statement = connection.prepareStatement("SELECT statut FROM transaction WHERE id = ?");
                    statement.setInt(1, transactionId);
                    ResultSet resultSet = statement.executeQuery();
                    if (resultSet.next()) {
                        status = resultSet.getString("statut");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return status.equals("En attente");
            }
        });
    }








    public void setSidebarController(SideBarre_adminController sideBarreAdminController) {
        this.sidebarController = sidebarController;
    }
}