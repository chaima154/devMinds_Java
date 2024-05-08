package tn.devMinds.controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import tn.devMinds.controllers.ClientMenuController;
import tn.devMinds.entities.Transaction;
import tn.devMinds.iservices.TransactionService;
import tn.devMinds.iservices.TypeTransactionService;
import tn.devMinds.test.MainFx;
import tn.devMinds.tools.MyConnection;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class TransactionClientController implements Initializable {
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
    private TableColumn<Transaction, String> typeTransactionColumn;
    @FXML
    private TableColumn<Transaction, Integer> compteColumn;
    @FXML
    private TableColumn<Transaction, Integer> destinataireColumn;
    @FXML
    private TableColumn<Transaction, Integer> actionColumn;
    @FXML
    private TableColumn<Transaction, Double> comissionTransaction;

    @FXML
    private BorderPane borderPane;
    private final TransactionService transactionService = new TransactionService();
    private final TypeTransactionService typeTransactionService = new TypeTransactionService();
    private ClientMenuController clientMenuController;
    private Preferences preferences;

    public TransactionClientController() throws SQLException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int clientId = getClientIdFromPreferences();
        ObservableList<Transaction> clientTransactions = getAllTransactionsForClient(clientId);
        showList(clientTransactions);
    }

    private int getClientIdFromPreferences() {
        preferences = Preferences.userRoot().node(LoginController.class.getName());
        String savedValue = preferences.get("Id_Client", "0");
        System.out.println(savedValue);
        return Integer.parseInt(savedValue); // Default value "0" if not found
    }

    private ObservableList<Transaction> getAllTransactionsForClient(int clientId) {
        ObservableList<Transaction> allTransactions = getAllList();
        ObservableList<Transaction> clientTransactions = FXCollections.observableArrayList();

        for (Transaction transaction : allTransactions) {
            if (transaction.getCompte_id() == clientId) {
                clientTransactions.add(transaction);
            }
        }
        return clientTransactions;
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
        typeTransactionColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(typeTransactionService.getTypeTransactionName(data.getValue().getTypetransaction_id())));
        comissionTransaction.setCellValueFactory(data -> {
            Double commission = (Double) typeTransactionService.getComissionTypeTransaction(data.getValue().getTypetransaction_id());
            return commission != null ? new SimpleObjectProperty<>(commission) : new SimpleObjectProperty<>(0.0);
        });

        compteColumn.setCellValueFactory(data -> new SimpleIntegerProperty(getRibForAccount(data.getValue().getCompte_id())).asObject());
        destinataireColumn.setCellValueFactory(data -> new SimpleIntegerProperty(getRibForAccount(data.getValue().getDestinataire_compte_id_id())).asObject());

        // Initialize columns
        table.getColumns().setAll(idColumn, dateColumn, statutColumn, montantColumn, numChequeColumn, typeTransactionColumn, comissionTransaction, compteColumn, destinataireColumn, actionColumn);

        table.setItems(observableList);
    }

    private int getRibForAccount(int accountId) {
        int rib = 0;
        try {
            Connection connection = MyConnection.getConnection();
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

    public void clientMenuController(ClientMenuController clientMenuController) {
    }
}
