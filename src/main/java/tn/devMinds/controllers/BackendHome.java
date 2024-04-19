package tn.devMinds.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import tn.devMinds.Views.AdminMenuOptions;
import tn.devMinds.entities.Model;

import java.net.URL;
import java.util.ResourceBundle;

public class BackendHome implements Initializable {



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
    void goShop(MouseEvent event) {

    }

    @FXML
    void goUsers(MouseEvent event) {

    }
    @FXML
    private Button clients_btn;

    @FXML
    private Button deconnecter;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    private void addListeners() {
        clients_btn.setOnAction(event -> onClients());
        deconnecter.setOnAction(event -> onDeconnect());
    }

    @FXML
    private void onClients() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(String.valueOf(AdminMenuOptions.CLIENTS));
    }

    @FXML
    private void onDeconnect() {
        // Handle logout or deconnection logic here
    }



}
