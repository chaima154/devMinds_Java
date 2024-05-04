package tn.devMinds.controllers.GestionCard;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import tn.devMinds.models.Card;
import tn.devMinds.models.Compte;
import tn.devMinds.services.CardCrud;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;
import org.controlsfx.control.Notifications;
import javafx.scene.image.Image;


public class ShowCardClient implements Initializable {

    @FXML
    private TableColumn<Card, Integer> csv;

    @FXML
    private TableColumn<Card, LocalDate> datexp;

    @FXML
    private TableColumn<Card, String> numero;

    @FXML
    private Pane paneCardPrinciapl;

    @FXML
    private TableColumn<Card, Double> solde;

    @FXML
    private TableView<Card> tableView;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reload();
        addButtonStatusToTableprepaedcard();
        addButtonLostToTableprepaedcard();
        addButtonToTableprepaedcard();
        try {
            Pane page = FXMLLoader.load(getClass().getResource("/banque/GestionCard/carouselCard.fxml"));
            paneCardPrinciapl.getChildren().setAll(page);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    ObservableList<Card>initialData() throws SQLException
    {
        CardCrud ps=new CardCrud();
        return FXCollections.observableArrayList(ps.getAllPrepaedCardById(2));
    }


    private void reload(){
        numero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        csv.setCellValueFactory(new PropertyValueFactory<>("csv"));
        datexp.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getDateExpiration()));
        solde.setCellValueFactory(new PropertyValueFactory<>("solde"));
  try {
            tableView.setItems(initialData());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }






    private void addButtonToTableprepaedcard() {
        TableColumn<Card, Void> colBtn = new TableColumn<>("Button Column");
        Callback<TableColumn<Card, Void>, TableCell<Card, Void>> cellFactory = new Callback<TableColumn<Card, Void>, TableCell<Card, Void>>() {
            @Override
            public TableCell<Card, Void> call(final TableColumn<Card, Void> param) {
                final TableCell<Card, Void> cell = new TableCell<Card, Void>() {
                    private final Button btnpassword = new Button("mot de passe oublié");
                    private final Button btnrecharger = new Button("recharger");

                    {
                        btnpassword.setOnAction((ActionEvent event) -> {
                            Card data = getTableView().getItems().get(getIndex());
                            CardCrud cc = new CardCrud();
                            String res=cc.generateRandomNumberString(4);
                            SendSms smsSender = new SendSms("YourAccountSID", "YourAuthToken");
                            smsSender.send("YourTwilioPhoneNumber", "+21693860151", "RecipientName", "MessageText");

                            try {
                                cc.updatepassword(data.getId(),res);
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            System.out.println("selectedData: " + data);
                        });
                        btnrecharger.setOnAction((ActionEvent event) -> {
                            Card data = getTableView().getItems().get(getIndex());

                            Dialog<Void> dialog = new Dialog<>();
                            dialog.setTitle("Recharger");

                            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
                            // Create the recharge form
                            GridPane rechargeForm = (GridPane) createRechargeForm(data.getId(),data.getCompte().getId(),data.getCompte().getSolde());

                            // Set the content of the dialog to the recharge form
                            dialog.getDialogPane().setContent(rechargeForm);

                            dialog.showAndWait();
                        });
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox buttons = new HBox(btnpassword, btnrecharger);
                            buttons.setSpacing(5);
                            setGraphic(buttons);
                        }
                    }
                };
                return cell;
            }
        };
        colBtn.setCellFactory(cellFactory);
        tableView.getColumns().add(colBtn);
    }
    private Node createRechargeForm(int id, int idcompte,double totale) {
        GridPane gridPane = new GridPane();
        gridPane.add(new Label("Amount"), 0, 0);
        TextField amountTextField = new TextField(); // Create TextField for amount input
        gridPane.add(amountTextField, 1, 0);

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(event -> {
            // Retrieve the amount entered by the user
            String amountText = amountTextField.getText();
            double amount = Double.valueOf(amountText); // Convert to double

            // Update the balance of the card
            CardCrud cc = new CardCrud();
            try {
                Double res=totale-amount;
                if (res>0)
                { cc.updateSolde(id, amount);
                cc.updateSoldeCompte(idcompte,amount);
                    // Close the dialog
                    Node source = (Node) event.getSource();
                    Stage stage = (Stage) source.getScene().getWindow();
                    stage.close();
                }
            else
                {
                    System.out.println("solde insufisante");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Reload the data in the table view
            reload();

            System.out.println("Submit button clicked");
        });
        gridPane.add(submitButton, 0, 1);

        return gridPane;
    }
    private void addButtonStatusToTableprepaedcard() {
        TableColumn<Card, Void> colBtn = new TableColumn<>("Status");
        colBtn.setCellFactory(param -> {
            TableCell<Card, Void> cell = new TableCell<>() {
                private final Button btn = new Button();

                {
                    btn.setOnAction(event -> {
                        Card data = getTableView().getItems().get(getIndex());
                        CardCrud cc = new CardCrud();
                        try {

                            if (Objects.equals(data.getStatutCarte(), "active")) {
                                cc.updateStat(data.getId(), data.getStatutCarte());
                                btn.setText("inactive");
                                data.setStatutCarte("inactive");
                            } else if (Objects.equals(data.getStatutCarte(), "inactive")){
                                cc.updateStat(data.getId(), data.getStatutCarte());
                                btn.setText("active");
                                data.setStatutCarte("active");
                            }

                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        // Access the current item in the row
                        Card rowData = getTableView().getItems().get(getIndex());
                        // Set the button text based on the desired property of the Card object
                        btn.setText(rowData.getStatutCarte());
                        if(Objects.equals(getTableView().getItems().get(getIndex()).getStatutCarte(), "Lost"))
                           {btn.setVisible(false);}
                        setGraphic(btn);
                    }
                }
            };
            return cell;
        });
        tableView.getColumns().add(colBtn);
    }






    private void addButtonLostToTableprepaedcard() {
        TableColumn<Card, Void> colBtn = new TableColumn<>("Button Column");
        Callback<TableColumn<Card, Void>, TableCell<Card, Void>> cellFactory = new Callback<TableColumn<Card, Void>, TableCell<Card, Void>>() {
            @Override
            public TableCell<Card, Void> call(final TableColumn<Card, Void> param) {
                final TableCell<Card, Void> cell = new TableCell<Card, Void>() {
                    private final Button btnEtat = new Button("Lost!");
                    {
                        btnEtat.setOnAction((ActionEvent event) -> {
                            Card data = getTableView().getItems().get(getIndex());
                            CardCrud cc = new CardCrud();
                                if(!Objects.equals(data.getStatutCarte(), "Lost"))
                                {
                                    data.setStatutCarte("Lost");
                                }
                            try {
                                cc.update(data);
                                reload();
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            System.out.println("selectedData: " + data);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btnEtat);
                        }
                    }
                };
                return cell;
            }
        };
        colBtn.setCellFactory(cellFactory);
        tableView.getColumns().add(colBtn);
    }





    @FXML
    private Button notif;
    @FXML
    void notifi(ActionEvent event) {
        Notification not=new Notification();
        not.notifier();
        }

}
