package tn.devMinds.controllers;


import javafx.scene.control.TextField;
import tn.devMinds.entities.Assurence;
import tn.devMinds.entities.Demande;
import tn.devMinds.iservices.AssuranceService;
import tn.devMinds.iservices.ServiceDemande;
import tn.devMinds.tools.MyConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class frontassurance implements Initializable {
    private Connection cnx;
    private Assurence selectedAssurence;
    @FXML
    private TextField searchTerm;

    public void setSidebarController(SideBarre_adminController sidebarController) {
    }

    private AssuranceService assuranceService = new AssuranceService();


    @FXML
    private Button choice;

    @FXML
    private ListView<Assurence> lista;

    @FXML
    void addchoice(ActionEvent event) throws IOException {
        // Get the selected assurance's name
        Assurence selectedAssurence = lista.getSelectionModel().getSelectedItem();
        String selectedAssurenceName = selectedAssurence.getNom();

        // Load the demandefront.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/demandefront.fxml"));
        Parent demandefrontParent = loader.load();

        // Pass the selected assurance's name to the demandefront controller
        Demandefront demandefrontController = loader.getController();
        demandefrontController.setSelectedAssuranceName(selectedAssurenceName);

        // Set up the scene and stage
        Scene demandefrontScene = new Scene(demandefrontParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(demandefrontScene);
        window.show();
    }


    @FXML
    void selectA(MouseEvent event) {
       Assurence SelectedAssurence = lista.getSelectionModel().getSelectedItem();


    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showass();
    }
    public void showass() {
        lista.setItems(getAssurences());
    }
    public ObservableList<Assurence> getAssurences() {
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
    }

}





