package tn.devMinds.controllers.admin.credit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import tn.devMinds.models.Credit;
import tn.devMinds.sercices.CreditCrud;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminUpdateCreditController implements Initializable {
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
        ObservableList<String> statutOptions = FXCollections.observableArrayList("Option 1", "Option 2", "Option 3");
        ObservableList<String> typeOptions = FXCollections.observableArrayList("Type 1", "Type 2", "Type 3");
        ObservableList<String> categorieOptions = FXCollections.observableArrayList("Category 1", "Category 2", "Category 3");
        ObservableList<String> secteurOptions = FXCollections.observableArrayList("Sector 1", "Sector 2", "Sector 3");

        // Set items for choice boxes
        statutCredit.setItems(statutOptions);
        typeCredit.setItems(typeOptions);
        categorieProfessionelle.setItems(categorieOptions);
        typeSecteur.setItems(secteurOptions);
        secteurActivite.setItems(secteurOptions); // Example, can be different data
    }
    public void setCredit(Credit credit) {
        this.credit = credit;
        CompteId.setText(String.valueOf(credit.getCompteId()));
        montantCredit.setText(String.valueOf(credit.getMontantCredit()));
        duree.setText(String.valueOf(credit.getDuree()));
        tauxInteret.setText(String.valueOf(credit.getTauxInteret()));
        dateObtention.setValue(credit.getDateObtention());
        statutCredit.setValue(credit.getStatutCredit());
        typeCredit.setValue(credit.getTypeCredit());
        salaire.setText(String.valueOf(credit.getSalaire())) ;
        categorieProfessionelle.setValue(credit.getCategorieProfessionelle());
        typeSecteur.setValue(credit.getTypeSecteur());
        secteurActivite.setValue(credit.getSecteurActivite());
    }

    @FXML
    private void handleUpdate() throws SQLException {
        if (isInputValid()) {
            credit.setCompteId(Integer.parseInt(CompteId.getText()));
            credit.setMontantCredit(Double.parseDouble(montantCredit.getText()));
            credit.setDuree(Integer.parseInt(duree.getText()));
            credit.setTauxInteret(Double.parseDouble(tauxInteret.getText()));
            credit.setDateObtention(dateObtention.getValue());
            credit.setStatutCredit(statutCredit.getValue());
            credit.setTypeCredit(typeCredit.getValue());
            credit.setSalaire(Double.parseDouble(salaire.getText()));
            credit.setCategorieProfessionelle(categorieProfessionelle.getValue());
            credit.setTypeSecteur(typeSecteur.getValue());
            credit.setSecteurActivite(secteurActivite.getValue());
            creditCrud.updateCredit(credit);
        }
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
