package tn.devMinds.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.devMinds.entities.TypeTransaction;
import tn.devMinds.iservices.TypeTransactionService;

import java.io.IOException;

public class AjoutTypeTransactionController {
    public TypeTransactionListController typeTransactionListController;
    @FXML
    private TextField libelle;

    private TypeTransactionService typetransactionservice = new TypeTransactionService();

    public Boolean verif_libelle(TextField t) {
        String champ = t.getText().trim();
        return !champ.isEmpty();
    }

    @FXML
    void retour(ActionEvent event) {
        closePopup();
    }

    private void closePopup() {
        // Get the current stage and close it
        Stage stage = (Stage) libelle.getScene().getWindow();
        stage.close();
    }

    @FXML
    void AddTypeTransaction(ActionEvent event) {
        if (!verif_libelle(libelle)) {
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setTitle("Alert");
            al.setContentText("Veuillez saisir un libellé valide.");
            al.show();
        } else {
            TypeTransaction typeTransaction = new TypeTransaction();
            typeTransaction.setLibelle(libelle.getText());

            String errorMessage = typetransactionservice.add(typeTransaction);
            if (errorMessage == null) {
                Alert al = new Alert(Alert.AlertType.CONFIRMATION);
                al.setTitle("Confirmation");
                al.setContentText("Le type de transaction a été ajouté avec succès.");
                al.showAndWait();

                // Close the current window
                closePopup();

                // Refresh the transaction type list
                typeTransactionListController.showList(typeTransactionListController.getAllList());
            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle("Erreur");
                al.setContentText(errorMessage);
                al.show();
            }
        }
    }

    public void setTypeTransactionListController(TypeTransactionListController controller) {
        this.typeTransactionListController = controller;
    }
}
