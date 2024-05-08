package tn.devMinds.controllers.client.credit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.devMinds.controllers.admin.credit.getData;
import tn.devMinds.entities.Credit;
import tn.devMinds.iservices.CreditCrud;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ClientCreateCredit implements Initializable {
    public TextField montantCredit;
    public TextField duree;
    public TextField tauxInteret;
    public ChoiceBox <String> typeCredit;
    public ChoiceBox <String> categorieProfessionelle;
    public TextField salaire;
    public ChoiceBox <String> typeSecteur;
    public ChoiceBox <String> secteurActivite;
    public AnchorPane form_block;
    public ImageView cinImage;
    private Image image;

    private Credit credit;
    private final CreditCrud creditCrud = new CreditCrud();

    public ClientCreateCredit() throws SQLException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeChoiceBoxes();
    }

    private void initializeChoiceBoxes() {

        ObservableList<String> typeOptions = FXCollections.observableArrayList("Crédit à la consommation ", "Crédit automobile", "Crédit immobilier", "Prêts étudiants", "Prêts commerciaux");
        ObservableList<String> categorieOptions = FXCollections.observableArrayList("Salarié", "Retraité", "Professionnel Libéral");
        ObservableList<String> secteurOptions = FXCollections.observableArrayList("Privé", "Public");
        ObservableList<String> activiteOptions = FXCollections.observableArrayList("Hotels et restaurants", "Transport", "Activités immobilières", "Administration publiques", "Educations", "Santé", "Construction", "Industrie", "Services");

        typeCredit.setItems(typeOptions);
        categorieProfessionelle.setItems(categorieOptions);
        typeSecteur.setItems(secteurOptions);
        secteurActivite.setItems(activiteOptions);
    }
    void showInfo(Credit credit){
        montantCredit.setText(String.valueOf(credit.getMontantCredit()));
        duree.setText(String.valueOf(credit.getDuree()));
        tauxInteret.setText(String.valueOf(credit.getTauxInteret()));
        typeCredit.setValue(credit.getTypeCredit());
        this.credit=credit;
    }

    ///////////////////////////////////////save/////////////////////////////////////

    @FXML
    private void handleAddCredit() {
        if(handleSave()){
            if(credit.getSalaire()<1000){
                Alert alert;
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message d'information");
                alert.setHeaderText(null);
                alert.setContentText("Désolé, Vos information ne vous permet pas de créer une demande");
                alert.showAndWait();
            }else{
                Alert alert;
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message d'information");
                alert.setHeaderText(null);
                alert.setContentText("Demande subit avec succès!!");
                creditCrud.add(credit);
                alert.showAndWait();
            }
            Stage stage = (Stage) montantCredit.getScene().getWindow();
            stage.close();

        }
    }

    private boolean handleSave() {
        if (isInputValid()) {
            int Compteid = credit.getCompteId();
            double MontantCredit = Double.parseDouble(montantCredit.getText());
            int Duree = Integer.parseInt(duree.getText());
            double TauxInteret = Double.parseDouble(tauxInteret.getText());
            LocalDate DateObtention = LocalDate.now().plusMonths(1);
            double MontantRestant = Double.parseDouble(montantCredit.getText());
            String StatutCredit = "En Attente";
            String TypeCredit = typeCredit.getValue();
            double Salaire = Double.parseDouble(salaire.getText());
            String CategorieProfessionelle = categorieProfessionelle.getValue();
            String TypeSecteur = typeSecteur.getValue();
            String SecteurActivite = secteurActivite.getValue();
            String uri = addClientUploadImage();
            uri = uri.replace("\\", "\\\\");
            String DocumentCin = uri;
            Credit newcredit = new Credit(MontantCredit, Duree, TauxInteret, DateObtention, MontantRestant, StatutCredit, TypeCredit, DocumentCin, Salaire, CategorieProfessionelle, TypeSecteur, SecteurActivite, null, Compteid);
            this.credit = newcredit;
            return true;
        }else return false;
    }

    private boolean isInputValid() {
        String errorMessage = "";

        //montantCredit vide
        if (montantCredit.getText() == null || montantCredit.getText().isEmpty()) {
            errorMessage += "Montant de crédit est vide!\n";
        } else {
            try {
                double value = Double.parseDouble(montantCredit.getText());
                // Check if montant is below a certain number
                if (value < 1000) {
                    errorMessage += "Montant de crédit est inférieur à " + 1000 + "!\n";
                }
                // Check if montant is above a certain number
                if (value > 10000000) {
                    errorMessage += "Montant de crédit est supérieur à " + 10000000 + "!\n";
                }
            } catch (NumberFormatException ex) {
                errorMessage += "Montant invalide!\n";
            }
        }


        //duree vide
        if (duree.getText() == null || duree.getText().isEmpty()) {
            errorMessage += "Durée est vide!\n";
        } else {
            try {
                double value = Integer.parseInt(duree.getText());
                // Check if durée is below a certain number
                if (value < 3) {
                    errorMessage += "Durée de crédit est inférieur à " + 3 + "!\n";
                }
                // Check if durée is above a certain number
                if (value > 300) {
                    errorMessage += "Durée de crédit est supérieur à " + 300 + "!\n";
                }
            } catch (NumberFormatException ex) {
                errorMessage += "Durée invalide!\n";
            }
        }

        //tauxInteret vide
        if (tauxInteret.getText() == null || tauxInteret.getText().isEmpty()) {
            errorMessage += "Taux d'intérêt est vide!\n";
        } else {
            try {
                double value = Double.parseDouble(tauxInteret.getText());
                // Check if Taux d'intérêt is below a certain number
                if (value < 5) {
                    errorMessage += "Taux d'intérêt de crédit est inférieur à " + 5 + "!\n";
                }
                // Check if Taux d'intérêt is above a certain number
                if (value > 15) {
                    errorMessage += "Taux d'intérêt de crédit est supérieur à " + 15 + "!\n";
                }
            } catch (NumberFormatException ex) {
                errorMessage += "Taux d'intérêt invalide!\n";
            }
        }

        //typeCredit vide
        if (typeCredit.getValue() == null) {
            errorMessage += "Veuillez sélectionner un type de crédit!\n";
        }

        //categorieProfessionelle vide
        if (categorieProfessionelle.getValue() == null) {
            errorMessage += "Veuillez sélectionner une catégorie professionnelle!\n";
        }

        //salaire vide
        if (salaire.getText() == null || salaire.getText().isEmpty()) {
            errorMessage += "Salaire est vide!\n";
        } else {
            try {
                double value = Double.parseDouble(salaire.getText());
                // Check if Salaire is below a certain number
                if (value < 700) {
                    errorMessage += "Salaire est inférieur à " + 700 + "!\n";
                }
                // Check if Salaire is above a certain number
                if (value > 100000) {
                    errorMessage += "Salaire est supérieur à " + 100000 + "!\n";
                }
            } catch (NumberFormatException ex) {
                errorMessage += "Salaire invalide!\n";
            }
        }

        //typeSecteur vide
        if (typeSecteur.getValue() == null) {
            errorMessage += "Veuillez sélectionner un type de secteur!\n";
        }

        //secteurActivite vide
        if (secteurActivite.getValue() == null) {
            errorMessage += "Veuillez sélectionner une activité de secteur!\n";
        }

        //documentCin vide
        if (addClientUploadImage() == null){
            errorMessage += "Veuillez choisir une image de CIN!\n";
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            showAlertDialog(errorMessage);
            return false;
        }
    }

    private void showAlertDialog(String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Champs invalides");
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void addClientInsertImage() {

        FileChooser open = new FileChooser();
        File file = open.showOpenDialog(form_block.getScene().getWindow());

        if (file != null) {
            getData.path = file.toPath();

            image = new Image(file.toURI().toString(), 236, 127, false, true);
            cinImage.setImage(image);
        }
    }

    public String addClientUploadImage() {
        if (getData.path != null) {
            String cinName = "Cin_" + credit.getId() + ".png";

            File cinDirectory = new File("tn.devMinds/controllers/admin/credit/cin_copies");
            if (!cinDirectory.exists()) {
                cinDirectory.mkdirs();
            }

            File cinCopy = new File(cinDirectory, cinName);

            try {
                Files.copy(getData.path, cinCopy.toPath(), StandardCopyOption.REPLACE_EXISTING);

                // Load the copy as an image
                Image image = new Image(cinCopy.toURI().toString(), 236, 127, false, true);

                // Set the image view with the copy
                cinImage.setImage(image);

                return (cinCopy.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace(System.out);
            }
        }return null;
    }
}
