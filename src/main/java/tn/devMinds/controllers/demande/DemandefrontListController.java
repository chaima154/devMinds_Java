package tn.devMinds.controllers.demande;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import tn.devMinds.controllers.ClientMenuController;
import tn.devMinds.entities.Assurence;
import tn.devMinds.entities.Demande;
import tn.devMinds.iservices.ServiceDemande;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class DemandefrontListController extends ClientMenuController {
    @FXML
    public ChoiceBox filterChoiceBox;
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

    private final ServiceDemande demandeService = new ServiceDemande();

    // Add this field to your class
    private String loggedInClientName;

    // In initialize() method
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        filterChoiceBox.setValue("All");
        loggedInClientName = "abderahmen"; // Assuming "djoo" is the logged-in client's name, replace it with your method to retrieve the name
        try {
            showList(getAllList(loggedInClientName)); // Filter by logged-in client name
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setupActionColumn();
        searchTerm.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                try {
                    showList(getAllList(loggedInClientName)); // Filter by logged-in client name
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                // Implement search functionality if required
            }
        });
    }

    // In filtrerDemandes() method
    @FXML
    void filtrerDemandes(ActionEvent event) {
        String selectedEtat = (String) filterChoiceBox.getValue();
        ObservableList<Demande> filteredList = FXCollections.observableArrayList();
        try {
            if (selectedEtat.equals("All")) {
                filteredList.addAll(getAllList(loggedInClientName)); // Filter by logged-in client name
            } else {
                for (Demande demande : getAllList(loggedInClientName)) { // Filter by logged-in client name
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


    ObservableList<Demande> getAllList(String clientName) throws SQLException {
        ObservableList<Demande> allDemands = FXCollections.observableArrayList(demandeService.getAllData());
        ObservableList<Demande> filteredList = FXCollections.observableArrayList();
        for (Demande demande : allDemands) {
            if (demande.getNomClient().equals(clientName)) {
                filteredList.add(demande);
            }
        }
        return filteredList;
    }

    private void setupActionColumn() {
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteBtn = new Button("Annuler");
            private final HBox hbox = new HBox(5, deleteBtn);

            {
                deleteBtn.setOnAction(event -> {
                    Demande demande = getTableView().getItems().get(getIndex());
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Dialog");
                    alert.setHeaderText("Delete Demand");
                    alert.setContentText("Are you sure you want to delete this demand?");

                    // Add buttons to the alert
                    ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
                    ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);
                    alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

                    // Handle the user's response
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == buttonTypeYes) {
                        try {
                            demandeService.delete(demande);
                            showList(getAllList("djoo")); // Filter by client name "djoo"
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
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

    public void showList(ObservableList<Demande> observableList) {
        nomClientColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getNomClient()));
        assuranceColumn.setCellValueFactory(cellData -> {
            Assurence assurance = cellData.getValue().getA();
            if (assurance != null) {
                return new SimpleStringProperty(assurance.getNom());
            } else {
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
}
