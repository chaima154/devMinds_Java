package tn.devMinds.controllers.assurance;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import tn.devMinds.controllers.SideBarre_adminController;
import tn.devMinds.entities.Assurence;
import tn.devMinds.iservices.AssuranceService;


import java.io.IOException;

public class AjoutAssuranceController extends SideBarre_adminController {
    public AssuranceListController assuranceListController;
    @FXML
    public BorderPane borderPane;
    public Button retourbtn;
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
        retourbtn.setOnAction(event -> {
            // Get the current scene
            Scene scene = retourbtn.getScene();

            // Get the stage (window) of the current scene
            Stage stage = (Stage) scene.getWindow();

            // Close the current stage
            stage.close();

            // Alternatively, if you want to go back to the previous scene without closing the window:
            // stage.setScene(previousScene);
        });    }


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

                    // Close the stage
                    Stage stage = (Stage) borderPane.getScene().getWindow();
                    stage.close();

                    assuranceListController.showList(assuranceListController.getAllList()); // Assuming this method exists
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