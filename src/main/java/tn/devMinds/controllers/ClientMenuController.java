package tn.devMinds.controllers;

import com.twilio.rest.microvisor.v1.App;
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
import tn.devMinds.controllers.GestionCard.ShowCardClient;
import tn.devMinds.controllers.assurance.frontassurance;
import tn.devMinds.controllers.client.credit.ClientCreditController;
import tn.devMinds.controllers.comptecontroller.Ajoutercompte;
import tn.devMinds.controllers.demande.DemandefrontListController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class ClientMenuController implements Initializable {
    public Button dashboard_btn;
    public Button transactin_btn;

    @FXML
    public BorderPane borderPane;
    public Button accounts_btn;
    public Button profile_btn;
    public Button credit_btn;
    public Button logout_btn;
    public Button report_btn;

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

    @FXML
    void goCredit(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/client/Credit/ClientCreditSimulation.fxml"));
        Parent parent = loader.load();
        System.out.println("test working");
        ClientCreditController clientcredit = loader.getController();
        if (loader.getController() instanceof ClientCreditController) {
            ((ClientCreditController) clientcredit).clientMenuController(this);
            this.borderPane.setCenter(parent);
        }
    }
    @FXML
    public void godemandefront(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/DEMANDE/ListDemandeFront.fxml"));
        Parent parent = loader.load();
        DemandefrontListController Demandelistfront = loader.getController();
        if (loader.getController() instanceof DemandefrontListController) {
            Demandelistfront.setSidebarController(this);
            this.borderPane.setCenter(parent);
        }

    }
    @FXML
    public void goCarte(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/GestionCard/showCardClient.fxml"));
        Parent parent = loader.load();
        System.out.println("test working");
        ShowCardClient showcardclient = loader.getController();
        if (loader.getController() instanceof ShowCardClient) {
            ((ShowCardClient) showcardclient).clientMenuController(this);
            this.borderPane.setCenter(parent);
        }
    }

    @FXML
    public void goAssurancefront(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/ASSURANCE/frontassurance.fxml"));
        Parent parent = loader.load();
        frontassurance Frontassurance = loader.getController();
        if (loader.getController() instanceof frontassurance) {
            Frontassurance.setSidebarController(this);
            this.borderPane.setCenter(parent);
        }
    }
    @FXML
    void goDeconect(MouseEvent event) {
        try {
            // Load the login page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/Login.fxml"));
            Parent root = loader.load();

            // Clear user preferences
            Preferences prefs = Preferences.userNodeForPackage(App.class);
            prefs.clear();

            // Switch to the login scene
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BackingStoreException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void goAccount(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/compte/ajouter.fxml"));
        Parent parent = loader.load();
        System.out.println("test working");
        Ajoutercompte ajoutercompte = loader.getController();
        if (loader.getController() instanceof Ajoutercompte) {
            ((Ajoutercompte) ajoutercompte).clientMenuController(this);
            this.borderPane.setCenter(parent);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/dashboard.fxml"));
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("test working");
        ClientDashboardController dashboard = loader.getController();
        if (loader.getController() instanceof ClientDashboardController) {
            ((ClientDashboardController) dashboard).clientMenuController(this);
            this.borderPane.setCenter(parent);
        }

    }


}