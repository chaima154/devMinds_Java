package tn.devMinds.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import tn.devMinds.entities.Assurence;
import tn.devMinds.iservices.AssuranceService;


import java.io.IOException;

public class AjoutAssuranceController extends SideBarre_adminController {
    public AssuranceListController assuranceListController;
    @FXML
    public BorderPane borderPane;
    @FXML
    private TextField nom;
    @FXML
    private TextField description;
    @FXML
    private TextField prime;
    @FXML
    private TextField franchise;


    private AssuranceService assuranceService = new AssuranceService();

    public Boolean verif_nom(TextField t) {
        String champ = t.getText().trim();
        return !champ.isEmpty();
    }

    @FXML
    void retour(ActionEvent event) throws IOException {
        retourner();
    }

    private void retourner() throws IOException {
        // Load the appropriate list view (ListAssurence.fxml)
    }

    @FXML
    void AddAssurance(ActionEvent event) throws IOException {
        if (!verif_nom(nom)) {
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setTitle("Alerte");
            al.setContentText("Veuillez saisir un nom valide .");
            al.show();
        } else {
            try {
                Assurence assurance = new Assurence();
                assurance.setNom(nom.getText());
                assurance.setDescription(description.getText());
                assurance.setPrime(Integer.parseInt(prime.getText()));
                assurance.setFranchise(Double.parseDouble(franchise.getText()));

                String errorMessage = assuranceService.add(assurance);
                if (errorMessage == null) {
                    Alert al = new Alert(Alert.AlertType.CONFIRMATION);
                    al.setTitle("Confirmation");
                    al.setContentText("L'Assurance a été ajoutée avec succès.");
                    al.showAndWait();

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/ListAssurance.fxml"));
                    Parent parent = loader.load();
                    AssuranceListController assuranceListController = loader.getController();
                    if (loader.getController() instanceof AssuranceListController) {
                        assuranceListController.setSidebarController(this);
                        this.borderPane.setCenter(parent);
                    }
                } else {
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle("Erreur");
                    al.setContentText(errorMessage);
                    al.show();
                }
            } catch (NumberFormatException e) {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle("Erreur");
                al.setContentText("Veuillez saisir un nombre valide pour la prime et la franchise.");
                al.show();
            }
        }
    }

    public void setAssuranceListController(AssuranceListController controller) {
        this.assuranceListController = controller;
    }
}
