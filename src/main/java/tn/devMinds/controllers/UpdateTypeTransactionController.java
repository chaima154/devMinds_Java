package tn.devMinds.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import tn.devMinds.entities.TypeTransaction;
import tn.devMinds.iservices.TypeTransactionService;

import java.io.IOException;

public class UpdateTypeTransactionController extends SideBarre_adminController {
    private TypeTransactionListController typeTransactionListController;
    private SideBarre_adminController sidebarController;
    @FXML
    private TextField libelle;
    @FXML
    private TextField Commission;

    public void setSidebarController(SideBarre_adminController sidebarController) {
        this.sidebarController = sidebarController;
    }

    private TypeTransactionService typeTransactionService = new TypeTransactionService();
    private TypeTransaction typeTransactionToUpdate;

    public void initializeData(TypeTransaction typeTransaction) {
        this.typeTransactionToUpdate = typeTransaction;
        libelle.setText(typeTransaction.getLibelle());
        Commission.setText(String.valueOf(typeTransaction.getCommission()));
    }

    @FXML
    void retour(ActionEvent event) throws IOException {
        retourner();
    }

    private void retourner() throws IOException {
        // Close the current window
        libelle.getScene().getWindow().hide();
    }

    @FXML
    void UpdateTypeTransaction(ActionEvent event) throws IOException {
        TypeTransaction updatedTypeTransaction = new TypeTransaction();
        updatedTypeTransaction.setId(typeTransactionToUpdate.getId());
        updatedTypeTransaction.setLibelle(libelle.getText());

        String commissionText = Commission.getText().trim(); // Trim the text
        if (commissionText.isEmpty()) {
            displayAlert("Alert", "Veuillez saisir une valeur pour la commission.", Alert.AlertType.WARNING);
            return;
        }

        try {
            Double commissionValue = Double.valueOf(commissionText);
            updatedTypeTransaction.setCommission(commissionValue);

            String validationMessage = typeTransactionService.validateInput(updatedTypeTransaction);
            if (validationMessage != null) {
                displayAlert("Alert", validationMessage, Alert.AlertType.WARNING);
            } else {
                String updateResult = typeTransactionService.update(updatedTypeTransaction, updatedTypeTransaction.getId());
                if (updateResult == null) {
                    displayAlert("Confirmation", "Le type de transaction a été mis à jour avec succès.", Alert.AlertType.CONFIRMATION);
                    retourner(); // Close the popup
                } else {
                    displayAlert("Erreur", "Une erreur s'est produite lors de la mise à jour du type de transaction: " + updateResult, Alert.AlertType.ERROR);
                }
            }
        } catch (NumberFormatException e) {
            displayAlert("Alert", "Veuillez saisir une valeur numérique valide pour la commission.", Alert.AlertType.WARNING);
        }
    }


    private boolean verif_libelle(TextField t) {
        String champ = t.getText().trim();
        return !champ.isEmpty();
    }

    public void displayAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }

    public void setTypeTransactionListController(TypeTransactionListController controller) {
        this.typeTransactionListController = controller;
    }
}
