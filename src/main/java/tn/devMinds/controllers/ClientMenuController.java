package tn.devMinds.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import tn.devMinds.controllers.assurance.frontassurance;
import tn.devMinds.controllers.demande.DemandefrontListController;

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
    public Button assurance_btn;
    public Button demande_btn;
    private ClientMenuController clientMenuController;
    public void setBorderPane(BorderPane borderPane) {
        this.borderPane = borderPane;
    }



    public void setSidebarController(ClientMenuController clientMenuController) {
    }
    @FXML
    public void demandefront(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/DEMANDE/ListDemandeFront.fxml"));
        Parent parent = loader.load();
        DemandefrontListController Demandelistfront = loader.getController();
        if (loader.getController() instanceof DemandefrontListController) {
            Demandelistfront.setSidebarController(this);
            this.borderPane.setCenter(parent);
        }

    }

    @FXML
    public void Assurancefront(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/ASSURANCE/frontassurance.fxml"));
        Parent parent = loader.load();
        frontassurance Frontassurance = loader.getController();
        if (loader.getController() instanceof frontassurance) {
            Frontassurance.setSidebarController(this);
            this.borderPane.setCenter(parent);
        }
    }


    @FXML
    void goTransaction(MouseEvent event) throws IOException {

      /*  FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/TransactionClient.fxml"));
        Parent parent = loader.load();
        System.out.println("test working");
        TransactionClientController transaction = loader.getController();
        if (loader.getController() instanceof TransactionClientController) {
            ((TransactionClientController) transaction).clientMenuController(this);
            this.borderPane.setCenter(parent);
        }*/
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


}