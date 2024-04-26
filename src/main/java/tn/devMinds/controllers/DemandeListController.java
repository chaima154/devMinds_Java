package tn.devMinds.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import tn.devMinds.entities.Assurence;
import tn.devMinds.entities.Demande;
import tn.devMinds.iservices.ServiceDemande;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DemandeListController implements Initializable {
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
    private TableColumn<Demande, Void> actionColumn;
    @FXML
    private TextField searchTerm;
    private SideBarre_adminController sidebarController;

    public void setSidebarController(SideBarre_adminController sidebarController) {
        this.sidebarController = sidebarController;
    }

    private ServiceDemande demandeService = new ServiceDemande();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            showList(getAllList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        searchTerm.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                try {
                    showList(getAllList());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                // Implement search functionality if required
            }
        });
    }

    private ObservableList<Demande> getAllList() throws SQLException {
        return FXCollections.observableArrayList(demandeService.selectAll());
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
                return new SimpleStringProperty("");
            }
        });
        D_dColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getDateDebutContrat().toString()));
        D_fColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getDureeContrat().toString()));
        montantColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(String.valueOf(data.getValue().getMontantCouverture())));
        modeColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getModePaiement()));

        table.setItems(observableList);
    }






    private void openUpdateFXML(Demande demande) {
        // Implement update functionality here
    }

    @FXML
    void ajout(ActionEvent event) {
        // Implement add functionality here
    }

    @FXML
    void delete(ActionEvent event) {
        // Implement delete functionality here
    }
}
