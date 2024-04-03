package tn.devMinds.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import tn.devMinds.entities.TypeTransaction;
import tn.devMinds.iservices.TypeTransactionService;
import java.io.IOException;

public class  AjoutTypeTransactionController {
    public TypeTransactionListController typeTransactionListController;

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

            boolean ajoutReussi = typetransactionservice.add(typeTransaction);
            if (ajoutReussi) {
                System.out.println(typeTransaction);
                Alert al = new Alert(Alert.AlertType.CONFIRMATION);
                al.setTitle("Confirmation");
                al.setContentText("Le type de transaction a été ajouté avec succès.");
                al.show();
                this.retourner(); // Rediriger vers la vue de liste après l'ajout
            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle("Erreur");
                al.setContentText("Une erreur s'est produite lors de l'ajout du type de transaction.");
                al.show();
            }
        }
    }

    public void setTypeTransactionListController(TypeTransactionListController controller) {
        this.typeTransactionListController = controller;
    }
}
