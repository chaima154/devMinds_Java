package tn.devMinds.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SideBarre_adminController implements Initializable {

    @FXML
    public BorderPane borderPane; // Add @FXML annotation here
    @FXML
    private Button deconnecter;

    @FXML
    void goAcceuil(MouseEvent event) {

    }

    @FXML
    void goDeconect(MouseEvent event) {

    }

    @FXML
    void goCredit(MouseEvent event) {

    }

    @FXML


    void goTransaction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/ListeTransaction.fxml"));
        Parent parent = loader.load();
        TransactionListController transaction = loader.getController();
        if (loader.getController() instanceof TransactionListController) {
            ((TransactionListController) transaction).setSidebarController(this);
            this.borderPane.setCenter(parent);
        }
    }

    @FXML
    void goConvertisseur(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/ConvertisseurApi.fxml"));
        Parent parent = loader.load();
        ConvertisseurApi convertisseurApi = loader.getController();
        if (loader.getController() instanceof ConvertisseurApi) {
            ((ConvertisseurApi) convertisseurApi).setSidebarController(this);
            this.borderPane.setCenter(parent);
        }
    }





    @FXML
    void goTransactionType(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/ListTypeTransaction.fxml"));
        Parent parent = loader.load();
        TypeTransactionListController typetransaction = loader.getController();
        if (loader.getController() instanceof TypeTransactionListController) {
            ((TypeTransactionListController) typetransaction).setSidebarController(this);
            this.borderPane.setCenter(parent);
        }
    }
    @FXML
    void goStatistics(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/TransactionStatistics.fxml"));
        Parent parent = loader.load();
        TransactionStatisticsController transactionStatistics = loader.getController();
        if (loader.getController() instanceof TransactionStatisticsController) {
            ((TransactionStatisticsController) transactionStatistics).setSidebarController(this);
            this.borderPane.setCenter(parent);
        }
    }
    @FXML
    void goAssurance(MouseEvent event) {

    }

    @FXML
    void goCompteBancaire(MouseEvent event) {

    }

    @FXML
    void goCarteBancaire(MouseEvent event) {

    }

    @FXML
    void goUsers(MouseEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
