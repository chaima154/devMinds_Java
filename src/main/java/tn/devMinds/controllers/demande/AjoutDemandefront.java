package tn.devMinds.controllers.demande;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.devMinds.entities.Assurence;
import tn.devMinds.entities.Demande;
import tn.devMinds.iservices.AssuranceService;
import tn.devMinds.iservices.ServiceDemande;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class AjoutDemandefront {
    ObservableList<String> modeplist = FXCollections.observableArrayList("mensuel","trimestiel","annual");
    @FXML
    private Button retourbtn;

    @FXML
    private Button adddembtn;

    @FXML
    private TextField assuranceField;

    @FXML
    private TextField montanttxt;

    @FXML
    private DatePicker ddebuttxt;

    @FXML
    private DatePicker dfintxt;

    @FXML
    private DatePicker datenaissancetxt;

    @FXML
    private TextField nomtxt;

    @FXML
    private ChoiceBox<String> modepaimenttxt;

    @FXML
    private TextField emailtxt;


    private String selectedAssuranceName; // Moved declaration here

    @FXML
    void addDemande(ActionEvent event) {

        String montantStr = montanttxt.getText();
        String modepaimentStr = modepaimenttxt.getValue();
        if (modepaimentStr == null || modepaimentStr.isEmpty()) {
            // Handle the case where mode_paiement is not selected
            System.err.println("Mode de paiement non sélectionné !");
            return; // Exit the method without proceeding
        }
        String nm = nomtxt.getText();
        String email = emailtxt.getText();

        double montant;
        try {
            montant = Double.parseDouble(montantStr);
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Le champ Montant doit être un nombre valide !");
            return;}



        // Check if ddebuttxt and dfintxt are not null before using their values
        Date debut = ddebuttxt.getValue() != null ? Date.valueOf(ddebuttxt.getValue()) : null;
        Date fin = dfintxt.getValue() != null ? Date.valueOf(dfintxt.getValue()) : null;
        Date naissance = datenaissancetxt.getValue() != null ? Date.valueOf(datenaissancetxt.getValue()) : null;


        String selectedAssu = assuranceField.getText();

        // Check if an assurance name is selected
        if (selectedAssu == null) {
            // Handle the case where no assurance is selected (show error message, etc.)
            return;
        }
        // Validate nomtxt
        if (nm.isEmpty()) {
            showAlert("Erreur", "Le champ Nom est vide !");
            return;
        }

        // Validate emailtxt
        if (email.isEmpty()) {
            showAlert("Erreur", "Le champ Email est vide !");
            return;
        }

        // Validate montanttxt
        if (montantStr.isEmpty()) {
            showAlert("Erreur", "Le champ Montant est vide !");
            return;
        }


        // Retrieve the assurance instance based on its name
        Assurence selectedAssurance = null;
        AssuranceService sa =new AssuranceService();
        List<Assurence> assurances = sa.getAllData();
        for (Assurence assurance : assurances) {
            if (assurance.getNom().equals(selectedAssu)) {
                selectedAssurance = assurance;
                break;
            }
        }
        Demande demande = new Demande();
        demande.setA(selectedAssurance);
        demande.setNomClient(nm);
        demande.setAdresseClient(email);
        demande.setDateDebutContrat(debut);
        demande.setDureeContrat(fin);
        demande.setDateNaissanceClient(naissance);
        demande.setMontantCouverture(montant);
        demande.setModePaiement(modepaimentStr);

        try {
            ServiceDemande serviceDemande = new ServiceDemande();
            serviceDemande.insertOne(demande);
            System.out.println("Demande ajoutée avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void setSelectedAssuranceName(String selectedAssuranceName) {
        this.selectedAssuranceName = selectedAssuranceName;
        assuranceField.setText(selectedAssuranceName);
    }

    public void initialize() {
        assuranceField.setText(selectedAssuranceName != null ? selectedAssuranceName : "");
        modepaimenttxt.setValue("mensuel");
        modepaimenttxt.setItems(modeplist);
        retourbtn.setOnAction(event -> {
            // Get the current scene
            Scene scene = retourbtn.getScene();

            // Get the stage (window) of the current scene
            Stage stage = (Stage) scene.getWindow();

            // Close the current stage
            stage.close();

            // Alternatively, if you want to go back to the previous scene without closing the window:
            // stage.setScene(previousScene);
        });

    }

}
