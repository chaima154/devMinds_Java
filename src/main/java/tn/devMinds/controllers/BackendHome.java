package tn.devMinds.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;



import javafx.scene.Node;
import javafx.scene.Parent;
import java.io.IOException;

public class BackendHome {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button deconnecter;

    @FXML
    void goAcceuil(MouseEvent event) {

    }

    @FXML
    void goDeconect(MouseEvent event) {

    }

    @FXML
    void goDonation(MouseEvent event) {

    }

    @FXML
    void goEvenement(MouseEvent event) {

    }

    @FXML
    void goReclamation(MouseEvent event) {

    }

    @FXML
    void goRoles(MouseEvent event) {

    }

    @FXML
    void goCard(MouseEvent event){
        try {
            root = FXMLLoader.load(getClass().getResource("/banque/GestionCard/addCardAdmin.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void goUsers(MouseEvent event) {

    }

}
