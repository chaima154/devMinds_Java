package tn.devMinds.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import tn.devMinds.entities.TypeTransaction;
import tn.devMinds.iservices.TypeTransactionService;
import java.io.IOException;
public class UpdateTypeTransactionController extends SideBarre_adminController {
    private TypeTransactionListController typeTransactionListController;
    @FXML
    private BorderPane borderPane;
    private SideBarre_adminController sidebarController;
    @FXML
    private TextField libelle;
    public void setSidebarController(SideBarre_adminController sidebarController) {
        this.sidebarController = sidebarController;
    }
    private TypeTransactionService typeTransactionService = new TypeTransactionService();
    private TypeTransaction typeTransactionToUpdate;

    public void initializeData(TypeTransaction typeTransaction) {
        this.typeTransactionToUpdate = typeTransaction;
        libelle.setText(typeTransaction.getLibelle());
    }

    @FXML
    void retour(ActionEvent event) throws IOException {
        retourner();
    }

    private void retourner() throws IOException {
        // Navigate back to the appropriate list view (ListTypeTransaction.fxml)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/ListTypeTransaction.fxml"));
        Parent parent = loader.load();
        TypeTransactionListController typeTransactionListController = loader.getController();
        typeTransactionListController.setSidebarController(this.sidebarController);
        borderPane.setCenter(parent);
    }

    @FXML
    void UpdateTypeTransaction(ActionEvent event) throws IOException {
        TypeTransaction updatedTypeTransaction = new TypeTransaction();
        updatedTypeTransaction.setId(typeTransactionToUpdate.getId());
        updatedTypeTransaction.setLibelle(libelle.getText());

        String validationMessage = typeTransactionService.validateInput(updatedTypeTransaction);
        if (validationMessage != null) {
            displayAlert("Alert", validationMessage, Alert.AlertType.WARNING);
        } else {
            String updateResult = typeTransactionService.update(updatedTypeTransaction, updatedTypeTransaction.getId());
            if (updateResult == null) {
                displayAlert("Confirmation", "Le type de transaction a été mis à jour avec succès.", Alert.AlertType.CONFIRMATION);
                retourner(); // Navigate back to the list view after update
            } else {
                displayAlert("Erreur", "Une erreur s'est produite lors de la mise à jour du type de transaction: " + updateResult, Alert.AlertType.ERROR);
            }
        }
    }

    private boolean verif_libelle(TextField t) {
        String champ = t.getText().trim();
        return !champ.isEmpty();
    }

    private void displayAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }

    public void setTypeTransactionListController(TypeTransactionListController controller) {
        this.typeTransactionListController = controller;
    }
}
