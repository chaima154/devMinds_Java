package tn.devMinds.controllers.GestionCard;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.scene.control.SelectionMode;


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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import tn.devMinds.controllers.ClientMenuController;
import tn.devMinds.controllers.LoginController;
import tn.devMinds.models.Card;
import tn.devMinds.models.Compte;
import tn.devMinds.models.TypeCard;
import tn.devMinds.services.CardCrud;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import org.controlsfx.control.Notifications;
import javafx.scene.image.Image;
import tn.devMinds.tools.MyConnection;


public class ShowCardClient implements Initializable {
    @FXML
    private ImageView notif;
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
    
    public Preferences preferences;

    private ClientMenuController clientMenuController;


    @FXML
    private Button requestmorepc;
    public int getIdtoopencompte() {
        return idtoopencompte;
    }
    public void setIdtoopencompte(int idtoopencompte) {
        this.idtoopencompte = idtoopencompte;
    }
    @FXML
    private int idtoopencompte;
    public void clientMenuController(ClientMenuController clientMenuController) {
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        reload();
        addButtonStatusToTableprepaedcard();
        addButtonLostToTableprepaedcard();
        addButtonToTableprepaedcard();
        loadcard();

    }
    private void loadcard()
    {
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
        //idtoopencompte;
        return FXCollections.observableArrayList(ps.getAllPrepaedCardById(getClientIdFromPreferences()));
    }
    private void reload(){
        numero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        csv.setCellValueFactory(new PropertyValueFactory<>("csv"));
        datexp.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getDateExpiration()));
        datexp.setCellFactory(column -> {
            return new TableCell<Card, LocalDate>() {
                @Override
                protected void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    // Clear any previous styling
                    setStyle("");
                    // Get today's date
                    LocalDate today = LocalDate.now();
                    // Calculate the expiration date with at least 3 months added
                    LocalDate expirationPlusThreeMonths = today.plusMonths(3);
                    // Display the expiration date if it's not empty
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        // Create a clickable text field
                        String action;
                        if (item.isBefore(today)) {
                            // Expired date
                            action = " Expired "+item+" Renew Now!!";
                        }
                        else if (item.isBefore(expirationPlusThreeMonths))
                        {
                            // Date is within the next 3 months
                            action = " Expire soon on "+item+" Renew?";
                        } else {
                            // Date is more than 3 months in the future
                            action = " Expire on "+item+" Renew?";
                        }
                        Hyperlink clickableText = new Hyperlink(action);
                        clickableText.setOnAction(event -> {
                            Card data = getTableView().getItems().get(getIndex());
                            // Define the action to be performed when clicked
                            System.out.println("Clicked on the expiration date: " + item);
                            System.out.println(data.getCsv());
                            if (item.isBefore(today)) {
                                // Expired date
                                renewcard( data);
                            }
                            else if (item.isBefore(expirationPlusThreeMonths))
                            {
                                renewcard( data);
                            } else
                            {
                                // Date is more than 3 months in the future
                                renewcard( data);
                            }
                        });
                        // Apply styling based on conditions
                        if (item.isBefore(today)) {
                            // Expired date, set text color to red
                            clickableText.setTextFill(Color.RED);
                        } else if (item.isAfter(expirationPlusThreeMonths)) {
                            // Date is more than 3 months in the future, set text color to green
                            clickableText.setTextFill(Color.GREEN);
                        } else {
                            // Date is within the next 3 months, set text color to orange
                            clickableText.setTextFill(Color.ORANGE);
                        }
                        // Set the cell content to the clickable text
                        setGraphic(clickableText);
                    }
                }
            };
        });
        solde.setCellValueFactory(new PropertyValueFactory<>("solde"));
  try {
            tableView.setItems(initialData());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void addButtonToTableprepaedcard() {
        TableColumn<Card, Void> colBtn = new TableColumn<>("Actions");
        Callback<TableColumn<Card, Void>, TableCell<Card, Void>> cellFactory = new Callback<TableColumn<Card, Void>, TableCell<Card, Void>>() {
            @Override
            public TableCell<Card, Void> call(final TableColumn<Card, Void> param) {
                final TableCell<Card, Void> cell = new TableCell<Card, Void>() {
                    private final Button btnpassword = new Button("mot de passe oublié");
                    private final Button btnrecharger = new Button("recharger");
                    private final Button btnSupprimer = new Button("Supprimer"); // Renamed button
                    {
                        btnpassword.setOnAction((ActionEvent event) -> {
                            Card data = getTableView().getItems().get(getIndex());
                            CardCrud cc = new CardCrud();
                            String res = cc.generateRandomNumberString(4);
                            SendSms smsSender = new SendSms("AC768c8015cd745adfc30f7e60c629f310", "5683c0963f22d94c228b440d58c6f39c");
                            smsSender.send("+15073646864", "+21693860151", "La7zim", res);
                            try {
                                cc.updatepassword(data.getId(), res);
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
                            GridPane rechargeForm = (GridPane) createRechargeForm(data.getId(), data.getCompte().getId(), data.getCompte().getSolde());

                            // Set the content of the dialog to the recharge form
                            dialog.getDialogPane().setContent(rechargeForm);

                            dialog.showAndWait();
                        });
                        // Event handler for the renamed button
                        btnSupprimer.setOnAction((ActionEvent event) -> {
                            // Define the action to be performed when the renamed button is clicked
                            System.out.println("Supprimer button clicked");
                            Card data = getTableView().getItems().get(getIndex());
                            CardCrud dc=new CardCrud();
                            try {
                                dc.delete(data);
                                reload();
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        });
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox buttons = new HBox(btnpassword, btnrecharger, btnSupprimer); // Add the renamed button to the HBox
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
    //idtoopencompte;
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
        TableColumn<Card, Void> colBtn = new TableColumn<>();
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
        TableColumn<Card, Void> colBtnLost = new TableColumn<>();
        colBtnLost.setCellFactory(param -> {
            TableCell<Card, Void> celll = new TableCell<>() {
                private final Button btnEtat = new Button();
                {
                        btnEtat.setOnAction((ActionEvent event) -> {
                            Card data = getTableView().getItems().get(getIndex());
                            CardCrud cc = new CardCrud();
                            try {
                                if (Objects.equals(data.getStatutCarte(), "Lost")) {
                                    cc.updateStat(data.getId(),"active");
                                    btnEtat.setText("Lost");
                                    reload();
                                } else if((Objects.equals(data.getStatutCarte(), "active"))||(Objects.equals(data.getStatutCarte(), "inactive")))
                                {
                                    cc.updateStat(data.getId(), "Lost");
                                    btnEtat.setText("Found");
                                    reload();
                                }
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        });
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            // Access the current item in the row
                            Card rowData = getTableView().getItems().get(getIndex());
                            // Set the button text based on the desired property of the Card object
                           if((Objects.equals(rowData.getStatutCarte(), "active"))||(Objects.equals(rowData.getStatutCarte(), "inactive")))
                            btnEtat.setText("Lost");
                           else if (Objects.equals(rowData.getStatutCarte(), "Lost"))
                               btnEtat.setText("Found");
                           setGraphic(btnEtat);
                        }
                    }
            };
            return celll;
        });
        tableView.getColumns().add(colBtnLost);
    }
    LocalDate getDate() {
        LocalDate today = LocalDate.now();
        LocalDate futureDate = today.plusYears(2);
return futureDate;
    }
    private void renewcard(Card data)
    {
        CardCrud newcard=new CardCrud();
        Card renewCard=new Card();
        Compte newcompte=new Compte();
        TypeCard newtypecard=new TypeCard();
        renewCard.setNumero(newcard.generateUniqueNumero(16));
        renewCard.setCsv(newcard.generateRandomNumberString(3));
        renewCard.setDateExpiration(getDate());
        renewCard.setMdp(newcard.generateUniqueNumero(4));
        if(!Objects.equals(data.getStatutCarte(), "Lost"))
        {renewCard.setStatutCarte(data.getStatutCarte());}
        else
        {renewCard.setStatutCarte("active");}
        newcompte.setId(data.getCompte().getId());
        newtypecard.setId(data.getTypeCarte().getId());
        renewCard.setCompte(newcompte);
        renewCard.setTypeCarte(newtypecard);
        renewCard.setSolde(data.getSolde());
        System.out.println(renewCard);
        newcard.add(renewCard);
        try {
            newcard.delete(data);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        reload();
    }
    @FXML
    void notifi(MouseEvent event ) {
        }
    private void updateCardStatusAndDelete(int compteId) {
        try {
            Connection connection = MyConnection.getConnection();
            // Select distinct statuses for the given compte_id
            String selectQuery = "SELECT statut_carte FROM carte WHERE compte_id = ?";
            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
            selectStatement.setInt(1, compteId);
            ResultSet resultSet = selectStatement.executeQuery();
            while (resultSet.next()) {
                String status = resultSet.getString("statut_carte");
                if (status.equals("Accepted")) {
                    // Update status to "Active" for cards with status "Accepted"
                    String updateQuery = "UPDATE carte SET statut_carte = 'active' WHERE compte_id = ? AND statut_carte = 'Accepted' ";
                    PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                    updateStatement.setInt(1, compteId);
                    updateStatement.executeUpdate();
                } else if (status.equals("Refused")) {
                    // Delete cards with status "Refused"
                    String deleteQuery = "DELETE FROM carte WHERE compte_id = ? AND statut_carte = 'Refused'";
                    PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
                    deleteStatement.setInt(1, compteId);
                    deleteStatement.executeUpdate();
                }
            }
            System.out.println("Card status updated and deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void notifi(javafx.scene.input.MouseEvent mouseEvent) {
       CardCrud cc=new CardCrud();
        Notification notification = new Notification();


       updateCardStatusAndDelete(getClientIdFromPreferences());
       loadcard();reload();



    }
    private int getClientIdFromPreferences() {
        preferences = Preferences.userRoot().node(LoginController.class.getName());
        String savedValue = preferences.get("Id_Client", "0");
        System.out.println(savedValue);
        return Integer.parseInt(savedValue); // Default value "0" if not found
    }
    public boolean requestmorepcbtn(ActionEvent actionEvent) {
        CardCrud cc = new CardCrud();
        
        //idtoopencompte;
        idtoopencompte = getClientIdFromPreferences();
        int cardsToCreate = 5; // Desired number of cards to create
        int createdCards = cc.containsWaitingcardprpaed(idtoopencompte);
        if (createdCards == cardsToCreate) {
            Notification nottif = new Notification();
            nottif.notifier("There are already enough waiting cards.");
            return false;
        }
        while (createdCards < cardsToCreate) {
            Card newcard = new Card();
            newcard.setNumero(cc.generateUniqueNumero(16));
            newcard.setDateExpiration(getDate());
            newcard.setCsv(cc.generateUniqueNumero(3));
            newcard.setMdp(cc.generateUniqueNumero(4));
            newcard.setStatutCarte("Waiting");
            Compte c = new Compte();
            c.setId(idtoopencompte);
            newcard.setCompte(c);
            TypeCard tcc = new TypeCard();
            tcc.setId(cc.containstypeCardpreapaed());
            newcard.setTypeCarte(tcc);
            newcard.setSolde(0.0);
            cc.add(newcard);
            createdCards++; // Increment the counter for created cards
        }
            Notification nottif = new Notification();
            nottif.notifier("la demande a été envoyée");
            loadcard();
            reload();
        return true;
    }

}