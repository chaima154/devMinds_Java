package tn.devMinds.controllers.client.credit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tn.devMinds.models.Credit;
import tn.devMinds.sercices.CreditCrud;
import tn.devMinds.tools.MyConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ClientCreditController implements Initializable {

    @FXML
    public ChoiceBox <String> typeCredit;
    @FXML
    public TextField montantCredit;
    @FXML
    public TextField tauxInteret;

    @FXML
    public TextField duree;
    @FXML
    public Button listeCredit_btn;
    @FXML
    public Label montantredit_val;
    @FXML
    public Label tauxInteret_val;
    @FXML
    public Label duree_val;
    @FXML
    public Label mensualite_val;
    private final CreditCrud creditCrud = new CreditCrud();
    public VBox demande_vbox;
    private Credit credit;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeChoiceBoxes();
    }

    private void initializeChoiceBoxes(){
        ObservableList<String> typeOptions = FXCollections.observableArrayList("Crédit à la consommation ", "Crédit automobile", "Crédit immobilier", "Prêts étudiants", "Prêts commerciaux");
        typeCredit.setItems(typeOptions);
    }

    @FXML
    private void openForm(){
        if (credit != null) {
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/client/Credit/clientcreateCredit.fxml"));
                Parent root = loader.load();
                ClientCreateCredit controller = loader.getController();
                controller.showInfo(credit);
                Stage stage = new Stage();
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(new Scene(root));
                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace(System.out);
            }
        }
    }



    @FXML
    private void handleCreation(Credit credit) {
        demande_vbox.setVisible(!demande_vbox.isVisible());
        montantredit_val.setText(String.valueOf(credit.getMontantCredit()));
        tauxInteret_val.setText(String.valueOf(credit.getTauxInteret()));
        duree_val.setText(String.valueOf(credit.getDuree()));
        double mensualite = calculateMensualite(credit);
        mensualite_val.setText(String.valueOf(mensualite));
        this.credit=credit;

    }

    double calculateMensualite(Credit credit){
        double pay = ((((credit.getMontantCredit() * credit.getTauxInteret()) / 100) + credit.getMontantCredit()) / credit.getDuree());
        final DecimalFormat decimalForm = new DecimalFormat("0.00");
        return (Double.parseDouble(decimalForm.format(pay)));
    }

    @FXML
    private void handleSimulation() {
        if (isInputValid()) {
            int Compteid = 5;
            double MontantCredit = Double.parseDouble(montantCredit.getText());
            int Duree = Integer.parseInt(duree.getText());
            double TauxInteret = Double.parseDouble(tauxInteret.getText());
            String TypeCredit = typeCredit.getValue();
            Credit credit = new Credit(MontantCredit, Duree, TauxInteret, TypeCredit, Compteid);
            handleCreation(credit);
        }
    }

    private boolean isInputValid() {
        String errorMessage = "";

        ///////////////////////////ASSURER NON VIDE//////////////////////////////
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


    @FXML
    private void showCreditInterface() {
        int id =5;
        if (creditCrud.readById(id).isEmpty()) {
            Alert alert;
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message d'information");
            alert.setHeaderText(null);
            alert.setContentText("Vous n'avez pas de demandes crédits");
            alert.showAndWait();
        }else{
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/client/Credit/Credit.fxml"));
                Parent root = loader.load();
                ClientIndexCreditController controller = loader.getController();
                controller.showCredits(id);
                Stage stage = new Stage();
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(new Scene(root));
                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace(System.out);
            }
        }
    }
}
