package tn.devMinds.controllers.GestionCard;
import javafx.beans.property.SimpleIntegerProperty;
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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import tn.devMinds.controllers.SideBarre_adminController;
import tn.devMinds.models.Card;
import tn.devMinds.models.Compte;
import tn.devMinds.services.CardCrud;
import javafx.event.ActionEvent;
import tn.devMinds.tools.MyConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
public class AdminCardList implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Button btnpenadd;
    @FXML
    private TableView<Map.Entry<String, Integer>> demande;

    @FXML
    private TableColumn<Map.Entry<String, Integer>, String> colrib;

    @FXML
    private TableColumn<Map.Entry<String, Integer>, Integer> colnum;

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

    private SideBarre_adminController sidebarController;

    public void setSidebarController(SideBarre_adminController sideBarreAdminController) {
        this.sidebarController = sidebarController;
    }
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

    private void fillDemandeTable() {
        colrib.setCellValueFactory(cellData -> {
            String rib = cellData.getValue().getKey();
            return new SimpleStringProperty(rib);
        });

        colnum.setCellValueFactory(cellData -> {
            Integer numberOfWaitingCards = cellData.getValue().getValue();
            return new SimpleIntegerProperty(numberOfWaitingCards).asObject();
        });
        // Create cell factory for the new column
        Callback<TableColumn<Map.Entry<String, Integer>, Void>, TableCell<Map.Entry<String, Integer>, Void>> cellFactory =
                new Callback<TableColumn<Map.Entry<String, Integer>, Void>, TableCell<Map.Entry<String, Integer>, Void>>() {
                    @Override
                    public TableCell<Map.Entry<String, Integer>, Void> call(final TableColumn<Map.Entry<String, Integer>, Void> param) {
                        return new TableCell<Map.Entry<String, Integer>, Void>() {
                            private final Button acceptButton = new Button("Accept");
                            private final Button refuseButton = new Button("Refuse");
                            private String currentRib; // Store the RIB for the current row
                            {
                                acceptButton.setOnAction(event -> handleAccept(currentRib));
                                refuseButton.setOnAction(event -> handleRefuse(currentRib));
                            }
                            @Override
                            public void updateItem(Void item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                } else {
                                    // Set the current RIB for this row
                                    currentRib = getTableView().getItems().get(getIndex()).getKey();
                                    setGraphic(new HBox(acceptButton, refuseButton));
                                }
                            }
                        };
                    }
                };
        TableColumn<Map.Entry<String, Integer>, Void> colActions = new TableColumn<>("Actions");
        colActions.setCellFactory(cellFactory);
        demande.getColumns().add(colActions);
        try {
            String query = "SELECT cm.rib AS rib, COUNT(*) AS numberOfWaitingCards FROM carte c JOIN compte cm ON c.compte_id = cm.id WHERE c.statut_carte = 'Waiting' GROUP BY cm.rib";
            PreparedStatement statement = MyConnection.getConnection().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            ObservableList<Map.Entry<String, Integer>> data = FXCollections.observableArrayList();
            while (resultSet.next()) {
                String rib = resultSet.getString("rib");
                int numberOfWaitingCards = resultSet.getInt("numberOfWaitingCards");
                System.out.println(numberOfWaitingCards);
                Map.Entry<String, Integer> entry = new HashMap.SimpleEntry<>(rib, numberOfWaitingCards);
                data.add(entry);
            }
            demande.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void handleAccept(String rib) {
        System.out.println("Accept button clicked for RIB: " + rib);
        updateCardStatus(rib, "Accepted");
        fillDemandeTable();

    }

    private void handleRefuse(String rib) {
        System.out.println("Refuse button clicked for RIB: " + rib);
        updateCardStatus(rib, "Refused");
        fillDemandeTable();
    }

    private void updateCardStatus(String rib, String newStatus) {
        try {
            Connection connection = MyConnection.getConnection();
            String query = "UPDATE carte SET statut_carte = ? WHERE compte_id = (SELECT id FROM compte WHERE rib = ?) AND statut_carte = 'Waiting'";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newStatus);
            statement.setString(2, rib);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Card status updated successfully.");
                fillDemandeTable();
                reload();
                reloadnormal();
            } else {
                System.out.println("No cards found with the given RIB and waiting status.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillDemandeTable();reload();

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

            tableview.setItems(initialData());

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
    private void addButtonToTablemaincard() {
        TableColumn<Card, Void> colBtn = new TableColumn<>("Actions");
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
        tableview.getColumns().add(colBtn);
    }
    private void addButtonToTableprepaedcard() {
        TableColumn<Card, Void> colBtn = new TableColumn<>("Actions");
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
        FXMLLoader loader =new FXMLLoader(getClass().getResource("/banque/GestionCard/addCardAdmin.fxml"));
        try{
            Parent root = loader.load();
            // Get the current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            // Set the scene to the stage
            stage.setScene(scene);
            stage.show();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}


