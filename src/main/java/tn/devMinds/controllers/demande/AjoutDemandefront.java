package tn.devMinds.controllers.demande;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.devMinds.controllers.SideBarre_adminController;
import tn.devMinds.entities.Assurence;
import tn.devMinds.entities.Demande;
import tn.devMinds.iservices.AssuranceService;
import tn.devMinds.iservices.ServiceDemande;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AjoutDemandefront extends SideBarre_adminController {
    @FXML
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

    @FXML
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

        // Validate email
        class EmailValidator {

            private static final String EMAIL_REGEX =
                    "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

            private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

            public static boolean validateEmail(String email) {
                Matcher matcher = EMAIL_PATTERN.matcher(email);
                return matcher.matches();
            }
        }
        if (!EmailValidator.validateEmail(email)) {
            showAlert("Veuillez saisir une adresse email valide !");
            return;
        }


        double montant;
        try {
            montant = Double.parseDouble(montantStr);
        } catch (NumberFormatException e) {
            showAlert("Le champ Montant doit être un nombre valide !");
            return;
        }

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
            showAlert("Le champ Nom est vide !");
            return;
        }

        // Validate emailtxt
        if (email.isEmpty()) {
            showAlert("Le champ Email est vide !");
            return;
        }

        // Validate montanttxt
        if (montantStr.isEmpty()) {
            showAlert("Le champ Montant est vide !");
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

        // Call the service to add the demand
        ServiceDemande serviceDemande = new ServiceDemande();
        String result = serviceDemande.add(demande);

        // Check the result of the add operation
        if (result == null) {
            // Success
            showAlert("Demande ajoutée avec succès !");
            handleSuccessfulAddition();
        } else {
            // Error occurred
            showAlert("Erreur lors de l'ajout de la demande : " + result);
        }
    }

    public void handleSuccessfulAddition() {
        try {
            // Get the current scene
            Scene scene = adddembtn.getScene();

            // Get the stage (window) of the current scene
            Stage stage = (Stage) scene.getWindow();

            // Close the current stage
            stage.close();

            // Open DemandefrontList stage
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/DEMANDE/ListDemandeFront.fxml"));
            Parent parent = loader.load();
            DemandefrontListController Demandelistfront = loader.getController();
            if (loader.getController() instanceof DemandefrontListController) {
                Demandelistfront.setSidebarController(this);
                // Create a new stage and set the scene
                Stage newStage = new Stage();
                newStage.setScene(new Scene(parent));
                newStage.show();
            }
        } catch (IOException e) {
            showAlert("Erreur lors de l'ouverture de la liste des demandes : " + e.getMessage());
            e.printStackTrace(); // Print stack trace for debugging
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);
        // Add event handler for closing the alert when OK button is clicked
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(okButton);
        alert.showAndWait().ifPresent(response -> {
            if (response == okButton) {
                // OK button is clicked, close the alert
                alert.close();
            }
        });
    }

    public void setSelectedAssuranceName(String selectedAssuranceName) {
        this.selectedAssuranceName = selectedAssuranceName;
        assuranceField.setText(selectedAssuranceName);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        assuranceField.setText(String.valueOf(selectedAssuranceName));
        super.initialize(url, resourceBundle); // Call superclass initialize
        modepaimenttxt.setValue("mensuel");
        modepaimenttxt.setItems(modeplist);
        System.out.println("Modepaiment items: " + modeplist);
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
    }}


