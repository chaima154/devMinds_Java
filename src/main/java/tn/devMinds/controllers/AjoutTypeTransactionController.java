package tn.devMinds.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.devMinds.entities.TypeTransaction;
import tn.devMinds.iservices.TypeTransactionService;

import java.io.IOException;
import java.sql.SQLException;

public class AjoutTypeTransactionController {
    public TypeTransactionListController typeTransactionListController;
    @FXML
    private TextField libelle;
    @FXML
    private TextField Commission;

    private TypeTransactionService typetransactionservice = new TypeTransactionService();

    public AjoutTypeTransactionController() throws SQLException {
    }

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
        } else if (!verif_commission(Commission)) {
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setTitle("Alert");
            al.setContentText("Veuillez saisir une commission valide.");
            al.show();
        } else {
            String commissionText = Commission.getText().trim(); // Trim the text
            Double commissionValue = Double.valueOf(commissionText);

            TypeTransaction typeTransaction = new TypeTransaction();
            typeTransaction.setLibelle(libelle.getText());
            typeTransaction.setCommission(commissionValue);
            System.out.println(commissionValue);

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


    public Boolean verif_commission(TextField t) {
        String champ = t.getText().trim();
        return !champ.isEmpty() && champ.matches("\\d+(\\.\\d+)?");
    }




    public void setTypeTransactionListController(TypeTransactionListController controller) {
        this.typeTransactionListController = controller;
    }
}
