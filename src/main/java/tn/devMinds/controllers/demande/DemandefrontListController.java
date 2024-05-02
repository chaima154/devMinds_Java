package tn.devMinds.controllers.demande;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import tn.devMinds.controllers.SideBarre_adminController;
import tn.devMinds.entities.Assurence;
import tn.devMinds.entities.Demande;
import tn.devMinds.iservices.ServiceDemande;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DemandefrontListController implements Initializable {
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
    private TextField searchTerm;

    public void setSidebarController(SideBarre_adminController sidebarController) {
    }

    private ServiceDemande demandeService = new ServiceDemande();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            showList(getAllList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setupActionColumn();
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

    private void setupActionColumn() {
        actionColumn.setCellFactory(param -> new TableCell<Demande, Void>() {
            private final Button deleteBtn = new Button("supprimer");
            private final Button updateBtn = new Button("Modifier");
            private final HBox hbox = new HBox(5, updateBtn, deleteBtn);

            {
                deleteBtn.setOnAction(event -> {
                    Demande demande = getTableView().getItems().get(getIndex());
                    try {
                        demandeService.deleteOne(demande);
                        showList(getAllList());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });

                updateBtn.setOnAction(event -> {
                    Demande demande = getTableView().getItems().get(getIndex());
                    openUpdateFXML(demande);
                });

            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : hbox);
            }
        });
    }

    private void openUpdateFXML(Demande demande) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/DEMANDE/ModifDemande.fxml"));
            Parent root = loader.load();

            ModifDemandeController controller = loader.getController();
            controller.initializeData(demande);

            borderPane.setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void delete(ActionEvent event) {
        Demande demande = table.getSelectionModel().getSelectedItem();
        if (demande != null) {
            try {
                if (demandeService.deleteOne(demande)) {
                    showList(getAllList());
                } else {
                    System.err.println("Deletion failed.");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.err.println("No item selected for deletion.");
        }
    }

    private void showList(ObservableList<Demande> observableList) {
        nomClientColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getNomClient()));
        assuranceColumn.setCellValueFactory(cellData -> {
            // Access the Assurence object associated with the Demande
            Demande demande = cellData.getValue();
            Assurence assurance = demande.getA();
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
        etatColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getEtat()));

        table.setItems(observableList);
    }

}