package tn.devMinds.controllers.assurance;


import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import tn.devMinds.controllers.ClientMenuController;
import tn.devMinds.controllers.SideBarre_adminController;
import tn.devMinds.controllers.demande.AjoutDemandefront;
import tn.devMinds.controllers.demande.DemandefrontListController;
import tn.devMinds.entities.Assurence;
import tn.devMinds.iservices.AssuranceService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class frontassurance extends ClientMenuController {
    @FXML
    public Button choice1;

    private ClientMenuController clientMenuController;

    @FXML
    private BorderPane borderPane;
    @FXML
    private TextField searchTerm;
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


    private final AssuranceService assuranceService = new AssuranceService();


    @FXML
    private Button choice;

    public frontassurance() throws SQLException {
    }


    @FXML
    void addchoice(ActionEvent event) throws IOException {
        // Get the selected assurance's name
        Assurence selectedAssurence = table.getSelectionModel().getSelectedItem();
        String selectedAssurenceName = selectedAssurence.getNom();

        // Load the demandefront.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/DEMANDE/demandefront.fxml"));

        // Pass the selected assurance's name to the demandefront controller
        Parent root = loader.load();
        AjoutDemandefront ajoutDemandefrontController = loader.getController();
        ajoutDemandefrontController.setSelectedAssuranceName(selectedAssurenceName);

        // Set the borderPane from ClientMenuController
        ajoutDemandefrontController.setSidebarController(clientMenuController);

        // Set up the scene and stage
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void clientMenuController(ClientMenuController clientMenuController) {
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showass(getAllList());

    }

    public ObservableList<Assurence> getAllList() {
        return FXCollections.observableArrayList(assuranceService.getAllData());
    }


    public void showass(ObservableList<Assurence> observableList) {
        nomColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getNom()));
        descriptionColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getDescription()));
        primeColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getPrime()).asString());
        franchiseColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getFranchise()).asString());
        table.setItems(observableList);
    }

    public void setSidebarController(ClientMenuController clientMenuController) {
    }

}





