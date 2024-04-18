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

public class  AjoutTypeTransactionController extends SideBarre_adminController {
    public TypeTransactionListController typeTransactionListController;
    @FXML
    public BorderPane borderPane;
    @FXML
    private TextField libelle;

    private TypeTransactionService typetransactionservice = new TypeTransactionService();

    public Boolean verif_libelle(TextField t) {
        String champ = t.getText().trim();
        return !champ.isEmpty();
    }

    @FXML
    void retour(ActionEvent event) throws IOException {
        retourner();
    }
    private SideBarre_adminController sidebarController;

    public void setSidebarController(SideBarre_adminController sidebarController) {
        this.sidebarController = sidebarController;
    }
    private void retourner() throws IOException {
        // Chargez la vue de liste appropriée (ListTypeTransaction.fxml)
    }

    @FXML
    void AddTypeTransaction(ActionEvent event) throws IOException {
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/ListTypeTransaction.fxml"));
                Parent parent = loader.load();
                TypeTransactionListController typetransaction = loader.getController();
                if (loader.getController() instanceof TypeTransactionListController) {
                    ((TypeTransactionListController) typetransaction).setSidebarController(this);
                    this.borderPane.setCenter(parent);
                }
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
