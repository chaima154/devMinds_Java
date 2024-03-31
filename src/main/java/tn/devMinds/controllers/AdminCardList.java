package tn.devMinds.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import tn.devMinds.models.Card;
import tn.devMinds.models.Compte;
import tn.devMinds.services.CardCrud;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminCardList implements Initializable {

        @FXML
        private TableColumn<Card, String> compteCol;

        @FXML
        private TableColumn<Card, Integer> csvCol;

        @FXML
        private TableColumn<Card, LocalDate> dateExpirationCol;

        @FXML
        private TableColumn<Card, Integer> idCol;

        @FXML
        private TableColumn<Card, String> mdpCol;

        @FXML
        private TableColumn<Card, String> numeroCol;

        @FXML
        private TableColumn<Card, Double> soldeCol;

        @FXML
        private TableColumn<Card, String> statutCarteCol;

        @FXML
        private TableColumn<Card, String> typeCol;
    @FXML
    private TableView<Card> tableview;


    ObservableList<Card>initialData() throws SQLException
    {
        CardCrud ps=new CardCrud();
        return FXCollections.observableArrayList(ps.getAll());
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        numeroCol.setCellValueFactory(new PropertyValueFactory<Card, String>("numero"));
        compteCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Card, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Card, String> param) {
                // Get the Compte object from the Card object
                Compte compte = param.getValue().getCompte();
                // Return the rib property as the value for compteCol
                return new SimpleStringProperty(((Compte) compte).getRib());
            }
        });

        try {
            tableview.setItems(initialData());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}


