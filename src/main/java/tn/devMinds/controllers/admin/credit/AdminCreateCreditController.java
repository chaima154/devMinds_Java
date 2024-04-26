package tn.devMinds.controllers.admin.credit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import tn.devMinds.models.Credit;
import tn.devMinds.sercices.CreditCrud;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminCreateCreditController implements Initializable {
    public TextField CompteId;
    public TextField montantCredit;
    public TextField duree;
    public TextField tauxInteret;
    public DatePicker dateObtention;
    public ChoiceBox <String> statutCredit;
    public ChoiceBox <String> typeCredit;
    public ChoiceBox <String> categorieProfessionelle;
    public TextField salaire;
    public ChoiceBox <String> typeSecteur;
    public ChoiceBox <String> secteurActivite;
    public Button upload_btn;
    public Button create_btn;

    private final CreditCrud creditCrud = new CreditCrud();
    private AdminCreditCell adminCreditCell;
    private Credit credit;

    public void setIndexCreditController(AdminCreditCell adminCreditCell) {
        this.adminCreditCell = adminCreditCell;
    }

    private void initializeChoiceBoxes() {
        // Sample data for choice boxes, replace with your actual data
        ObservableList<String> statutOptions = FXCollections.observableArrayList("En Attente", "Approuvé", "Réfusé", "Remboursé");
        ObservableList<String> typeOptions = FXCollections.observableArrayList("Crédit à la consommation ", "Crédit automobile", "Crédit immobilier", "Prêts étudiants", "Prêts commerciaux");
        ObservableList<String> categorieOptions = FXCollections.observableArrayList("Salarié", "Retraité", "Proffesionel Libéral");
        ObservableList<String> secteurOptions = FXCollections.observableArrayList("Privé", "Public");

        // Set items for choice boxes
        statutCredit.setItems(statutOptions);
        typeCredit.setItems(typeOptions);
        categorieProfessionelle.setItems(categorieOptions);
        typeSecteur.setItems(secteurOptions);
        secteurActivite.setItems(secteurOptions); // Example, can be different data
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (CompteId.getText() == null || CompteId.getText().isEmpty()) {
            errorMessage += "ID de compte est vide!\n";
        }

        if (montantCredit.getText() == null || montantCredit.getText().isEmpty()) {
            errorMessage += "Montant de crédit est vide!\n";
        }

        if (duree.getText() == null || duree.getText().isEmpty()) {
            errorMessage += "Durée est vide!\n";
        }

        if (tauxInteret.getText() == null || tauxInteret.getText().isEmpty()) {
            errorMessage += "Taux d'intérêt est vide!\n";
        }

        if (dateObtention.getValue() == null) {
            errorMessage += "Veuillez sélectionner une date d'obtention!\n";
        }

        if (statutCredit.getValue() == null) {
            errorMessage += "Veuillez sélectionner un statut de crédit!\n";
        }

        if (typeCredit.getValue() == null) {
            errorMessage += "Veuillez sélectionner un type de crédit!\n";
        }

        if (categorieProfessionelle.getValue() == null) {
            errorMessage += "Veuillez sélectionner une catégorie professionnelle!\n";
        }

        if (salaire.getText() == null || salaire.getText().isEmpty()) {
            errorMessage += "Salaire est vide!\n";
        }

        if (typeSecteur.getValue() == null) {
            errorMessage += "Veuillez sélectionner un type de secteur!\n";
        }

        if (secteurActivite.getValue() == null) {
            errorMessage += "Veuillez sélectionner une activité de secteur!\n";
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            showAlertDialog(Alert.AlertType.ERROR, "Erreur", "Champs invalides", errorMessage);
            return false;
        }
    }

    private void showAlertDialog(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeChoiceBoxes();
    }


}
