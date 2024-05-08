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
import javafx.util.StringConverter;
import tn.devMinds.controllers.ClientMenuController;
import tn.devMinds.entities.Credit;
import tn.devMinds.iservices.CreditCrud;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class ClientCreditController implements Initializable {

    @FXML
    public ChoiceBox <String> typeCredit;
    @FXML
    public Spinner <Double> montantCredit;
    @FXML
    public Spinner <Double> tauxInteret;
    @FXML
    public Spinner <Integer> duree;
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
    public ClientMenuController clientMenuController;
    private final CreditCrud creditCrud = new CreditCrud();
    public VBox demande_vbox;
    private Credit credit;

    public ClientCreditController() throws SQLException {
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeChoiceBoxes();
        setupSpinners();
    }

    private void initializeChoiceBoxes(){
        ObservableList<String> typeOptions = FXCollections.observableArrayList("Crédit à la consommation ", "Crédit automobile", "Crédit immobilier", "Prêts étudiants", "Prêts commerciaux");
        typeCredit.setItems(typeOptions);
    }

    private void setupSpinners() {
        // Setup montantCredit spinner
        SpinnerValueFactory.DoubleSpinnerValueFactory montantCreditFactory =
                new SpinnerValueFactory.DoubleSpinnerValueFactory(1000, 10000000, 1000);
        montantCreditFactory.setConverter(new StringConverter<Double>() {
            @Override
            public String toString(Double value) {
                if (value == null) {
                    return "";
                }
                return new DecimalFormat("#,###").format(value); // Format value with comma separators
            }

            @Override
            public Double fromString(String string) {
                try {
                    if (string == null || string.isEmpty()) {
                        return 1000.0; // Default value if input is empty
                    }
                    // Parse the string as a double and return it
                    return Double.parseDouble(string.replaceAll(",", "")); // Remove comma separators
                } catch (NumberFormatException ex) {
                    // Return 0 if the string cannot be parsed
                    return 1000.0; // Default value if input is invalid
                }
            }
        });
        montantCredit.setValueFactory(montantCreditFactory);
        montantCredit.setEditable(true); // Allow editing

        // Setup duree spinner
        SpinnerValueFactory.IntegerSpinnerValueFactory dureeFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(3, 300, 3);
        dureeFactory.setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer value) {
                if (value == null) {
                    return "";
                }
                return value.toString();
            }

            @Override
            public Integer fromString(String string) {
                try {
                    if (string == null || string.isEmpty()) {
                        return 3; // Default value if input is empty
                    }
                    // Parse the string as an integer and return it
                    return Integer.parseInt(string);
                } catch (NumberFormatException ex) {
                    // Return 0 if the string cannot be parsed
                    return 3; // Default value if input is invalid
                }
            }
        });
        duree.setValueFactory(dureeFactory);
        duree.setEditable(true); // Allow editing

        // Setup tauxInteret spinner
        SpinnerValueFactory.DoubleSpinnerValueFactory tauxInteretFactory =
                new SpinnerValueFactory.DoubleSpinnerValueFactory(5, 15, 5);
        tauxInteretFactory.setConverter(new StringConverter<Double>() {
            @Override
            public String toString(Double value) {
                if (value == null) {
                    return "";
                }
                return new DecimalFormat("#,##").format(value); // Format value with comma separators
            }

            @Override
            public Double fromString(String string) {
                try {
                    if (string == null || string.isEmpty()) {
                        return 5.0; // Default value if input is empty
                    }
                    // Parse the string as a double and return it
                    return Double.parseDouble(string.replaceAll(",", "")); // Remove comma separators
                } catch (NumberFormatException ex) {
                    // Return 0 if the string cannot be parsed
                    return 5.0; // Default value if input is invalid
                }
            }
        });
        tauxInteret.setValueFactory(tauxInteretFactory);
        tauxInteret.setEditable(true); // Allow editing
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

    public void clientMenuController(ClientMenuController clientMenuController) {
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
        String payString = decimalForm.format(pay).replace(',', '.'); // Replace comma with dot
        return Double.parseDouble(payString);
    }

    @FXML
    private void handleSimulation() {
        if (isInputValid()) {
            int Compteid =9;
            // Get the MontantCredit value as a string, replace comma with dot, and then parse it to double
            String montantCreditValue = montantCredit.getEditor().getText().replaceAll("[^0-9,.]", "");
            double MontantCredit = Double.parseDouble(montantCreditValue.replace(',', '.'));

            int Duree = duree.getValue();
            double TauxInteret = tauxInteret.getValue();
            String TypeCredit = typeCredit.getValue();
            Credit credit = new Credit(MontantCredit, Duree, TauxInteret, TypeCredit, Compteid);
            handleCreation(credit);
        }
    }



    private boolean isInputValid() {
        String errorMessage = "";

        ///////////////////////////ASSURER NON VIDE//////////////////////////////
        //montantCredit vide
        if (montantCredit.getValue() == null) {
            errorMessage += "Montant de crédit est vide!\n";
        } else {
            try {
                double value = montantCredit.getValue();
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
        if (duree.getValue() == null) {
            errorMessage += "Durée est vide!\n";
        } else {
            try {
                double value = duree.getValue();
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
        if (tauxInteret.getValue() == null) {
            errorMessage += "Taux d'intérêt est vide!\n";
        } else {
            try {
                double value = tauxInteret.getValue();
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
        int id =9;
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
