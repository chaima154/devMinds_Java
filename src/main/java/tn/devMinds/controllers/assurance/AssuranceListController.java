package tn.devMinds.controllers.assurance;

import javafx.beans.property.SimpleObjectProperty;
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
import tn.devMinds.iservices.AssuranceService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AssuranceListController implements Initializable {
    public SideBarre_adminController backendHome;
    @FXML
    public BorderPane borderPane;

    @FXML
    private TableView<Assurence> table;
    @FXML
    private TableColumn<Assurence, String> nomColumn;
    @FXML
    private TableColumn<Assurence, String> descriptionColumn;
    @FXML
    private TableColumn<Assurence, String> primeColumn;
    @FXML
    private TableColumn<Assurence, String> franchiseColumn;
    @FXML
    private TableColumn<Assurence, Void> actionColumn;

    @FXML
    private Button ajout;
    @FXML
    private TextField searchTerm;
    private SideBarre_adminController sidebarController;

    public void setSidebarController(SideBarre_adminController sidebarController) {
        this.sidebarController = sidebarController;
    }

    private AssuranceService assuranceService = new AssuranceService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showList(getAllList());
        setupActionColumn();
        searchTerm.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                showList(getAllList());
            } else {
                // Implement search functionality if required
            }
        });
    }

    private ObservableList<Assurence> getAllList() {
        return FXCollections.observableArrayList(assuranceService.getAllData());
    }

    @FXML
    void ajout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/ASSURANCE/AjoutAssurance.fxml"));
        Parent parent = loader.load();
        AjoutAssuranceController controller = loader.getController();
        controller.setAssuranceListController(this);
        this.borderPane.setCenter(parent);
        showList(getAllList());
    }


    private void setupActionColumn() {
        actionColumn.setCellFactory(param -> new TableCell<Assurence, Void>() {
            private final Button deleteBtn = new Button("Delete");
            private final Button updateBtn = new Button("Update");
            private final HBox hbox = new HBox(5, updateBtn, deleteBtn);

            {
                deleteBtn.setOnAction(event -> {
                    Assurence assurance = getTableView().getItems().get(getIndex());
                    assuranceService.delete(assurance);
                    showList(getAllList());
                });

                updateBtn.setOnAction(event -> {
                    Assurence assurance = getTableView().getItems().get(getIndex());
                    openUpdateFXML(assurance);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : hbox);
            }
        });
    }

    private void openUpdateFXML(Assurence assurance) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/ASSURANCE/UpdateAssurance.fxml"));
            Parent root = loader.load();

            UpdateAssuranceController controller = loader.getController();
            controller.initializeData(assurance);

            borderPane.setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void delete(ActionEvent event) {
        Assurence assurance = table.getSelectionModel().getSelectedItem();
        if (assurance != null) {
            if (assuranceService.delete(assurance)) {
                showList(getAllList());
            } else {
                System.err.println("Deletion failed.");
            }
        } else {
            System.err.println("No item selected for deletion.");
        }
    }

    public void showList(ObservableList<Assurence> observableList) {
        nomColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getNom()));
        descriptionColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getDescription()));
        primeColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getPrime()).asString());
        franchiseColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getFranchise()).asString());
        table.setItems(observableList);
    }

}
