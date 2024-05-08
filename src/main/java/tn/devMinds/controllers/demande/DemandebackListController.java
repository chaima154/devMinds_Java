package tn.devMinds.controllers.demande;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;
import com.itextpdf.text.DocumentException;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import tn.devMinds.controllers.GMailer;
import tn.devMinds.controllers.GeneratePdf;
import tn.devMinds.controllers.SideBarre_adminController;
import tn.devMinds.entities.Assurence;
import tn.devMinds.entities.Demande;
import tn.devMinds.iservices.ServiceDemande;

import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DemandebackListController implements Initializable {
    @FXML
    public ChoiceBox filterChoiceBox;
    @FXML
    private BorderPane borderPane;
    @FXML
    private TableView<Demande> table;
    @FXML
    private TableColumn<Demande, String> nomClientColumn;
    @FXML
    private TableColumn<Demande, String> assuranceColumn;
    @FXML
    private TableColumn<Demande, String> D_dColumn;
    @FXML
    private TableColumn<Demande, String> D_fColumn;
    @FXML
    private TableColumn<Demande, String> montantColumn;
    @FXML
    private TableColumn<Demande, String> modeColumn;
    @FXML
    private TableColumn<Demande, String> etatColumn;
    @FXML
    private TableColumn<Demande, Void> actionColumn;

    @FXML
    void filtrerDemandes(ActionEvent event) {
        String selectedEtat = (String) filterChoiceBox.getValue();
        ObservableList<Demande> filteredList = FXCollections.observableArrayList();
        try {
            if (selectedEtat.equals("All")) {
                filteredList.addAll(getAllList());
            } else {
                for (Demande demande : getAllList()) {
                    if (demande.getEtat().equals(selectedEtat)) {
                        filteredList.add(demande);
                    }
                }
            }
            showList(filteredList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setSidebarController(SideBarre_adminController sidebarController) {
    }

    private ServiceDemande demandeService = new ServiceDemande();

    @FXML
    private TextField searchTerm;

    private ObservableList<Demande> allDemandes;
    private FilteredList<Demande> filteredDemandes;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        filterChoiceBox.setValue("All");
        try {
            allDemandes = getAllList();
            showList(allDemandes);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setupActionColumn();

        // Create a filtered list based on the allDemandes list
        filteredDemandes = new FilteredList<>(allDemandes, p -> true);

        // Set the filter predicate whenever the searchTerm changes
        searchTerm.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredDemandes.setPredicate(demande -> {
                // If searchTerm is empty, show all demandes
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Convert the searchTerm to lowercase for case-insensitive search
                String lowerCaseFilter = newValue.toLowerCase();

                // Check if the demande's nomClient contains the searchTerm
                if (demande.getNomClient().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Show the demande
                }

                return false; // Hide the demande
            });
        });

        // Wrap the filtered list in a SortedList
        SortedList<Demande> sortedDemandes = new SortedList<>(filteredDemandes);

        // Bind the sorted list to the table
        sortedDemandes.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedDemandes);
    }

    private ObservableList<Demande> getAllList() throws SQLException {
        return FXCollections.observableArrayList(demandeService.getAllData());
    }

    private void showList(ObservableList<Demande> observableList) {
        nomClientColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getNomClient()));
        assuranceColumn.setCellValueFactory(cellData -> {
            // Access the Assurence object associated with the Demande
            Assurence assurance = cellData.getValue().getA();
            if (assurance != null) {
                // Return the name of the assurance
                return new SimpleStringProperty(assurance.getNom());
            } else {
                // If assurance is null, return an empty string
                return new SimpleStringProperty("assurance is null");
            }
        });
        D_dColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getDateDebutContrat().toString()));
        D_fColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getDureeContrat().toString()));
        montantColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(String.valueOf(data.getValue().getMontantCouverture())));
        modeColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getModePaiement()));
        etatColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getEtat()));

        table.setItems(observableList);
    }
    private void setupActionColumn() {
        actionColumn.setCellFactory(param -> new TableCell<Demande, Void>() {
            private final Button acceptBtn = new Button("Accept");
            private final Button refuseBtn = new Button("Refuse");
            private final HBox hbox = new HBox(5, acceptBtn, refuseBtn);

            {
                GMailer gMailer = null;
                try {
                    gMailer = new GMailer();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                GMailer finalGMailer = gMailer;
                acceptBtn.setOnAction(event -> {
                    Demande demande = getTableView().getItems().get(getIndex());
                    demande.setEtat("Accepted");
                    updateDemandeEtat(demande);
                    try {
                        // Generate the PDF contract
                        String pdfFileName = "Contract_" + demande.getId() + ".pdf";
                        GeneratePdf.generateContract(demande, pdfFileName);

                        // Send email to the owner of the demand using GMailer
                        Session session = Session.getDefaultInstance(new Properties());
                        finalGMailer.sendMail(session, demande.getAdresseClient(), "Contract Attached", "Please find attached contract.", pdfFileName);

                    } catch (Exception e) {
                        e.printStackTrace();
                        // Handle PDF generation or email sending error
                    }
                });





                refuseBtn.setOnAction(event -> {
                    Demande demande = getTableView().getItems().get(getIndex());
                    demande.setEtat("Refused");
                    updateDemandeEtat(demande);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(hbox);
                }
            }
        });
    }

    private void updateDemandeEtat(Demande demande) {
        try {
            ServiceDemande.updateEtat(demande);
            showList(getAllList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void ajout(ActionEvent actionEvent) {
    }


}
