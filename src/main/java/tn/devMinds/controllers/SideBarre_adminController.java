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
import tn.devMinds.entities.Assurance;

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
    void goAssurance(MouseEvent event) {

    }
    /* @FXML
    void goAssurance(MouseEvent event) {
        try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            Scene scene = stage.getScene();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("src/main/resources/banque/ListTypeTransaction.fxml"));
            scene.setRoot(loader.load());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }*/
    @FXML
    void goAssurance(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/ListTypeTransaction.fxml"));
        Parent parent = loader.load();
        AssuranceListController typeAssurance = loader.getController();
        if (loader.getController() instanceof AssuranceListController) {
            ((AssuranceListController) Assurance).setSidebarController(this);
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
