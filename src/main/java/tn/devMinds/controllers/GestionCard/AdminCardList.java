package tn.devMinds.controllers.GestionCard;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import tn.devMinds.models.Card;
import tn.devMinds.models.Compte;
import tn.devMinds.services.CardCrud;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
public class AdminCardList implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
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
        reload();
    }

    private void reload(){

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
            addButtonToTable();
            tableview.setItems(initialData());
            addButtonToTableprepaedcard();
            tableview1.setItems(initialData2());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void reloadnormal(){
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
    private void addButtonToTable() {
        TableColumn<Card, Void> colBtn = new TableColumn("Button Column");
        Callback<TableColumn<Card, Void>, TableCell<Card, Void>> cellFactory = new Callback<TableColumn<Card, Void>, TableCell<Card, Void>>() {
            @Override
            public TableCell<Card, Void> call(final TableColumn<Card, Void> param) {
                final TableCell<Card, Void> cell = new TableCell<Card, Void>() {
                    private final Button btn = new Button("Suprimer");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Card data = getTableView().getItems().get(getIndex());
                            System.out.println("selectedData: " + data);
                        });
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        colBtn.setCellFactory(cellFactory);
        tableview.getColumns().add(colBtn);
    }
    private void addButtonToTableprepaedcard() {
        TableColumn<Card, Void> colBtn = new TableColumn<>("Button Column");
        Callback<TableColumn<Card, Void>, TableCell<Card, Void>> cellFactory = new Callback<TableColumn<Card, Void>, TableCell<Card, Void>>() {
            @Override
            public TableCell<Card, Void> call(final TableColumn<Card, Void> param) {
                final TableCell<Card, Void> cell = new TableCell<Card, Void>() {
                    private final Button btnSupprimer = new Button("Supprimer");
                    private final Button btnEdit = new Button("Modifier statuts");

                    {
                        btnSupprimer.setOnAction((ActionEvent event) -> {
                            Card data = getTableView().getItems().get(getIndex());
                            CardCrud cc = new CardCrud();
                            try {
                                if (cc.delete(data)) {
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setContentText("Carte supprimée");
                                    alert.showAndWait();
                                    reloadnormal();
                                } else {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setContentText("Vérifiez les données");
                                    alert.showAndWait();
                                }
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            System.out.println("selectedData: " + data);
                        });
                        btnEdit.setOnAction((ActionEvent event) -> {
                            Card datam = getTableView().getItems().get(getIndex());
                            CardCrud cc = new CardCrud();
                            try {
                                if (cc.updateStat(datam.getId(),datam.getStatutCarte())) {
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setContentText("Carte modifiee");
                                    alert.showAndWait();
                                    reloadnormal();
                                } else {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setContentText("Vérifiez les données");
                                    alert.showAndWait();
                                }
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            System.out.println("selectedData: " + datam);
                            // Add your edit action here
                        });
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox buttons = new HBox(btnSupprimer, btnEdit);
                            buttons.setSpacing(5);
                            setGraphic(buttons);
                        }
                    }
                };
                return cell;
            }
        };
        colBtn.setCellFactory(cellFactory);
        tableview1.getColumns().add(colBtn);
    }

    @FXML
    void goAdd(ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/banque/GestionCard/addCardAdmin.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}


