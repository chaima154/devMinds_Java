package tn.devMinds.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import tn.devMinds.controllers.assurance.AssuranceListController;
import tn.devMinds.controllers.assurance.frontassurance;
import tn.devMinds.controllers.demande.DemandebackListController;
import tn.devMinds.controllers.demande.DemandefrontListController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SideBarre_adminController implements Initializable {

    @FXML
    public BorderPane borderPane; // Add @FXML annotation here
    @FXML
    private Button deconnecter;
    @FXML
    private Label exit;

    @FXML
    private StackPane contentArea;

    @FXML
    void goAcceuil(MouseEvent event) {

    }

    @FXML
    void goDeconect(MouseEvent event) {

    }

    @FXML
    void goCredit(MouseEvent event) {

    }





    /* @FXML
    void goAssurance(MouseEvent event) {
        try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            Scene scene = stage.getScene();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("src/main/resources/banque/ListAssurance.fxml"));
            scene.setRoot(loader.load());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }*/
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
    void Assurence(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/ASSURANCE/frontassurance.fxml"));
        Parent parent = loader.load();
        frontassurance Frontassurance = loader.getController();
        if (loader.getController() instanceof frontassurance) {
            Frontassurance.setSidebarController(this);
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
    void demandefront(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/DEMANDE/ListDemandeFront.fxml"));
        Parent parent = loader.load();
        DemandefrontListController Demandelistfront = loader.getController();
        if (loader.getController() instanceof DemandefrontListController) {
            Demandelistfront.setSidebarController(this);
            this.borderPane.setCenter(parent);
        }
    }



    @FXML
    void goTypeTransaction(MouseEvent event) {

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
