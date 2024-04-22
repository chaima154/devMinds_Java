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

public class UpdateAssuranceController extends SideBarre_adminController {

    @FXML
    private BorderPane borderPane;
    private SideBarre_adminController sidebarController;

    @FXML
    private TextField nom;

    @FXML
    private TextField description;

    @FXML
    private TextField prime;

    @FXML
    private TextField franchise;

    private final AssuranceService assuranceService = new AssuranceService();
    private Assurence assuranceToUpdate;

    public void initializeData(Assurence assurance) {
        this.assuranceToUpdate = assurance;
        nom.setText(assurance.getNom());
        description.setText(assurance.getDescription());
        prime.setText(String.valueOf(assurance.getPrime()));
        franchise.setText(String.valueOf(assurance.getFranchise()));
    }

    @FXML
    void retour(ActionEvent event) throws IOException {
        retourner();
    }

    private void retourner() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/ListAssurance.fxml"));
        Parent parent = loader.load();
        AssuranceListController assuranceListController = loader.getController();
        assuranceListController.setSidebarController(this.sidebarController);
        borderPane.setCenter(parent);
    }

    @FXML
    void UpdateAssurance(ActionEvent event) throws IOException {
        Assurence updatedAssurance = new Assurence();
        updatedAssurance.setId(assuranceToUpdate.getId());
        updatedAssurance.setNom(nom.getText());
        updatedAssurance.setDescription(description.getText());
        updatedAssurance.setPrime(Integer.parseInt(prime.getText()));
        updatedAssurance.setFranchise(Double.parseDouble(franchise.getText()));

        String validationMessage = assuranceService.validateInput(updatedAssurance);
        if (validationMessage != null) {
            displayAlert("Alerte", validationMessage, Alert.AlertType.WARNING);
        } else {
            String updateResult = assuranceService.update(updatedAssurance, updatedAssurance.getId());
            if (updateResult == null) {
                displayAlert("Confirmation", "L'Assurance a été mise à jour avec succès.", Alert.AlertType.CONFIRMATION);
                retourner();
            } else {
                displayAlert("Erreur", "Une erreur s'est produite lors de la mise à jour de l'Assurance: " + updateResult, Alert.AlertType.ERROR);
            }
        }
    }

    private void displayAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }

}
