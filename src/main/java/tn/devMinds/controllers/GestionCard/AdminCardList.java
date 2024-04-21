package tn.devMinds.controllers.GestionCard;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import tn.devMinds.models.Card;
import tn.devMinds.models.Compte;
import tn.devMinds.services.CardCrud;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AdminCardList implements Initializable {

    @FXML
    private Button btnpenadd;

        @FXML
        private TableColumn<Card, String> compteCol;
        @FXML
        private TableColumn<Card, Integer> csvCol;
        @FXML
        private TableColumn<Card, LocalDate> dateExpirationCol;
        @FXML
        private TableColumn<Card, String> numeroCol;
        @FXML
        private TableColumn<Card, String> statutCarteCol;

    @FXML
    private TableColumn<Card, String> compteCol1;
    @FXML
    private TableColumn<Card, Integer> csvCol1;
    @FXML
    private TableColumn<Card, LocalDate>dateExpirationCol1;
    @FXML
    private TableColumn<Card, String> numeroCol1;
    @FXML
    private TableColumn<Card, String> statutCarteCol1;
    @FXML
    private TableView<Card>  tableview1;


    @FXML
    private TableView<Card> tableview;




    ObservableList<Card>initialData() throws SQLException
    {
        CardCrud ps=new CardCrud();
        return FXCollections.observableArrayList(ps.getAllNormlaCard());
    }
    ObservableList<Card>initialData2() throws SQLException
    {
        CardCrud ps=new CardCrud();
        return FXCollections.observableArrayList(ps.getAllPrepaedCard());
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        numeroCol.setCellValueFactory(new PropertyValueFactory<>("numero"));
       csvCol.setCellValueFactory(new PropertyValueFactory<>("csv"));
       statutCarteCol.setCellValueFactory(new PropertyValueFactory<>("statutCarte"));
       dateExpirationCol.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getDateExpiration()));
       compteCol.setCellValueFactory(param -> {
           // Get the Compte object from the Card object
           Compte compte = param.getValue().getCompte();
           // Return the rib property as the value for compteCol
           return new SimpleStringProperty(((Compte) compte).getRib());
       });
        numeroCol1.setCellValueFactory(new PropertyValueFactory<>("numero"));
        csvCol1.setCellValueFactory(new PropertyValueFactory<>("csv"));
        statutCarteCol1.setCellValueFactory(new PropertyValueFactory<>("statutCarte"));
        dateExpirationCol1.setCellValueFactory(new PropertyValueFactory<>("dateExpiration"));
        compteCol1.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getCompte().getRib()));
        try {
            tableview.setItems(initialData());
            tableview1.setItems(initialData2());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}


