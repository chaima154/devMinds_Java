package tn.devMinds.controllers.assurance;


import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.*;
import tn.devMinds.controllers.demande.AjoutDemandefront;
import tn.devMinds.controllers.SideBarre_adminController;
import tn.devMinds.entities.Assurence;
import tn.devMinds.iservices.AssuranceService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class frontassurance implements Initializable {
    private Connection cnx;
    private Assurence selectedAssurence;
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

    public void setSidebarController(SideBarre_adminController sidebarController) {
    }

    private AssuranceService assuranceService = new AssuranceService();


    @FXML
    private Button choice;

    @FXML
    void addchoice(ActionEvent event) throws IOException {
        // Get the selected assurance's name
        Assurence selectedAssurence = table.getSelectionModel().getSelectedItem();
        String selectedAssurenceName = selectedAssurence.getNom();

        // Load the demandefront.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/DEMANDE/demandefront.fxml"));
        Parent demandefrontParent = loader.load();

        // Pass the selected assurance's name to the demandefront controller
        AjoutDemandefront ajoutDemandefrontController = loader.getController();
        ajoutDemandefrontController.setSelectedAssuranceName(selectedAssurenceName);


        // Set up the scene and stage
        Scene demandefrontScene = new Scene(demandefrontParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(demandefrontScene);
        window.show();
    }


    @FXML
    void selectA(MouseEvent event) {
       Assurence SelectedAssurence = table.getSelectionModel().getSelectedItem();


    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showass(getAllList());

    }

    private ObservableList<Assurence> getAllList() {
        return FXCollections.observableArrayList(assuranceService.getAllData());
    }


    public void showass(ObservableList<Assurence> observableList) {
        nomColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getNom()));
        descriptionColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getDescription()));
        primeColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getPrime()).asString());
        franchiseColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getFranchise()).asString());
        table.setItems(observableList);
    }
    /*public ObservableList<Assurence> getAssurences() {
        ObservableList<Assurence> assurances = FXCollections.observableArrayList();
        String query = "SELECT * FROM assurance";
        cnx = MyConnection.getInstance().getCnx();
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Assurence a = new Assurence();
                a.setNom(rs.getString("nom"));
                a.setDescription(rs.getString("description"));
                a.setFranchise(rs.getInt("franchise"));
                a.setPrime(rs.getInt("prime"));
                assurances.add(a);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return assurances;
    }*/

}





