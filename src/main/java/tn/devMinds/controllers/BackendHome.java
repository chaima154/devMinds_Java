package tn.devMinds.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;



import javafx.scene.Node;
import javafx.scene.Parent;
import tn.devMinds.controllers.GestionCard.AdminCardList;

import java.io.IOException;

public class BackendHome {
    @FXML
    private Pane container;
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

        try {
            FXMLLoader var =new FXMLLoader((getClass().getResource("/banque/GestionCard/addTypeCardAdmin.fxml")));

            Pane page = FXMLLoader.load(getClass().getResource("/banque/GestionCard/addTypeCardAdmin.fxml"));
            container.getChildren().setAll(page);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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

    public Pane getContainer() {
        return container;
    }

    public void setContainer(Pane container) {
        this.container = container;
    }

    @FXML
    void goCard(MouseEvent event){
            try {
                FXMLLoader var =new FXMLLoader((getClass().getResource("/banque/GestionCard/showCardAdmin.fxml")));

                Pane page = FXMLLoader.load(getClass().getResource("/banque/GestionCard/showCardAdmin.fxml"));
                container.getChildren().setAll(page);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }

    @FXML
    void goUsers(MouseEvent event) {

    }

}
