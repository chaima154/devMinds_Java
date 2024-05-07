package tn.devMinds.controllers.demande;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import tn.devMinds.controllers.SideBarre_adminController;
import tn.devMinds.entities.Assurence;
import tn.devMinds.entities.Demande; // Add import for Demande
import tn.devMinds.iservices.ServiceDemande; // Add import for ServiceDemande

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class ModifDemandeController extends SideBarre_adminController {

    @FXML
    private BorderPane borderPane;

    private final ObservableList<String> modeplist = FXCollections.observableArrayList("mensuel", "trimestiel", "annuel");

    @FXML
    private Button retourbtn;

    @FXML
    private Button updatebtn;

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

    private Demande demandeToUpdate;
    private final ServiceDemande serviceDemande = new ServiceDemande();
    private SideBarre_adminController sidebarController;

    public void initializeData(Demande demande) {
        this.demandeToUpdate = demande;
        assuranceField.setText(demande.getA().getNom());
        nomtxt.setText(demande.getNomClient());
        emailtxt.setText(demande.getAdresseClient());
        ddebuttxt.setValue(LocalDate.parse(demande.getDateDebutContrat().toString()));
        dfintxt.setValue(LocalDate.parse(demande.getDureeContrat().toString()));
        datenaissancetxt.setValue(LocalDate.parse(demande.getDateNaissanceClient().toString()));
        montanttxt.setText(String.valueOf(demande.getMontantCouverture()));
        modepaimenttxt.setValue(demande.getModePaiement());
        modepaimenttxt.setItems(modeplist);
    }

    @FXML
    void retour(ActionEvent event) throws IOException {
        retourner();
    }

    private void retourner() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/DEMANDE/ListDemandeFront.fxml"));
        Parent parent = loader.load();
        DemandefrontListController demandefrontListController = loader.getController();
        demandefrontListController.setSidebarController(this.sidebarController);
        demandefrontListController.setBorderPane(borderPane); // Initialize the borderPane
        borderPane.setCenter(parent);
    }


    @FXML
    void UpdateDemande(ActionEvent event) {
        Demande updatedDemande = new Demande();
        updatedDemande.setId(demandeToUpdate.getId());
        updatedDemande.setNomClient(nomtxt.getText());
        updatedDemande.setAdresseClient(emailtxt.getText());
        updatedDemande.setDateDebutContrat(ddebuttxt.getValue() != null ? Date.valueOf(ddebuttxt.getValue()) : null);
        updatedDemande.setDureeContrat(dfintxt.getValue() != null ? Date.valueOf(dfintxt.getValue()) : null);
        updatedDemande.setDateNaissanceClient(datenaissancetxt.getValue() != null ? Date.valueOf(datenaissancetxt.getValue()) : null);
        updatedDemande.setMontantCouverture(Double.parseDouble(montanttxt.getText()));
        updatedDemande.setModePaiement(modepaimenttxt.getValue());

        serviceDemande.update(updatedDemande, updatedDemande.getId());
        displayAlert("Succès", "Demande mise à jour avec succès !", Alert.AlertType.INFORMATION);
    }

    private void displayAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }

    public void setSidebarController(DemandefrontListController demandefrontListController) {
    }

    // Other methods, such as initializeData and displayAlert, remain the same
}

