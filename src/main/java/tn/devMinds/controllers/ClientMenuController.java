package tn.devMinds.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientMenuController implements Initializable {
    public Button dashboard_btn;
    public Button transactin_btn;
    public Button accounts_btn;
    public Button profile_btn;
    public Button logout_btn;
    public Button report_btn;
    public BorderPane borderPane;
    private ClientMenuController clientMenuController;
    public void setBorderPane(BorderPane borderPane) {
        this.borderPane = borderPane;
    }
    @FXML
    void goTransaction(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/TransactionClient.fxml"));
        Parent parent = loader.load();
        System.out.println("test working");
        TransactionClientController transaction = loader.getController();
        if (loader.getController() instanceof TransactionClientController) {
            ((TransactionClientController) transaction).clientMenuController(this);
            this.borderPane.setCenter(parent);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}