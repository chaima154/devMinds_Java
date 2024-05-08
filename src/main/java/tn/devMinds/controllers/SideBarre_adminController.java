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
import tn.devMinds.controllers.admin.credit.AdminIndexCreditController;
import tn.devMinds.controllers.assurance.AssuranceListController;
import tn.devMinds.controllers.comptecontroller.Affichagecompte;
import tn.devMinds.controllers.demande.DemandebackListController;

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
    void goCredit(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/admin/credit/credit.fxml"));
        Parent parent = loader.load();
        AdminIndexCreditController creditcontroller = loader.getController();
        if (loader.getController() instanceof AdminIndexCreditController) {
            ((AdminIndexCreditController) creditcontroller).setSidebarController(this);
            this.borderPane.setCenter(parent);
        }

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
    void goAssurance(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/ASSURANCE/ListAssurance.fxml"));
        Parent parent = loader.load();
        AssuranceListController assuranceListController = loader.getController(); // Corrected variable name
        if (loader.getController() instanceof AssuranceListController) {
            assuranceListController.setSidebarController(this); // Corrected method call
            this.borderPane.setCenter(parent);
        }
    }

    @FXML
    void demandeback(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/DEMANDE/ListDemande.fxml"));
        Parent parent = loader.load();
        DemandebackListController Demandelist = loader.getController();
        if (loader.getController() instanceof DemandebackListController) {
            Demandelist.setSidebarController(this);
            this.borderPane.setCenter(parent);
        }
    }

    @FXML
    void goCompteBancaire(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/compte/affichage.fxml"));
        Parent parent = loader.load();
        Affichagecompte affichagecompte = loader.getController();
        if (loader.getController() instanceof Affichagecompte) {
            ((Affichagecompte) affichagecompte).setSidebarController(this);
            this.borderPane.setCenter(parent);
        }

    }

    @FXML
    void goCarteBancaire(MouseEvent event) {

    }

    @FXML
    void goUsers(MouseEvent event) {
        try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            Scene scene = stage.getScene();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/ListeUsers.fxml"));
            scene.setRoot(loader.load());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
