package tn.devMinds.controllers.GestionCard;
import com.google.protobuf.StringValue;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import tn.devMinds.models.Card;
import tn.devMinds.models.Compte;
import tn.devMinds.models.TypeCard;
import tn.devMinds.services.CardCrud;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

public class carousel implements Initializable {
   @FXML
    int idtoopencompte;
    @FXML
    private StackPane Stackpane1;
    @FXML
    private StackPane Stackpane2;
    @FXML
    private StackPane Stackpane3;
    private int show = 0;
    public int getIdtoopencompte() {
        return idtoopencompte;
    }
    public void setIdtoopencompte(int idtoopencompte) {
        this.idtoopencompte = idtoopencompte;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<Card> cardData = initialData();
            int dataSize = cardData.size();
            System.out.println(dataSize);
            // Dynamically load cards into panes
            for (int i = 0; i < dataSize && i < 3; i++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/GestionCard/cardBank.fxml"));
                Parent cardPage = loader.load();
                StackPane currentPane = null;
                switch (i) {
                    case 0:
                        cardBank cb=loader.getController();
                        cb.getLabelCardnumber().setText(cardData.get(i).getNumero());
                        cb.getLabelDate().setText(cardData.get(i).getDateExpiration().toString());
                        cb.setStatuts(cardData.get(i).getStatutCarte());
                       // cb.setHyperlinkDate(cardData.get(i).getDateExpiration());
                        LocalDate today = LocalDate.now();
                        // Calculate the expiration date with at least 3 months added
                        LocalDate expirationPlusThreeMonths = today.plusMonths(3);
                        // Display the expiration date if it's not empty
                        // Create a clickable text field
                        String action;
                        if (cardData.get(i).getDateExpiration().isBefore(today)) {
                            // Expired date
                            action = " Expired "+cardData.get(i).getDateExpiration()+" Renew Now!!";
                            cb.getHyperlinkDate().setTextFill(Color.RED);
                        }
                        else if (cardData.get(i).getDateExpiration().isBefore(expirationPlusThreeMonths))
                        {
                            // Date is within the next 3 months
                            action = " Expire soon on "+cardData.get(i).getDateExpiration()+" Renew?";
                            cb.getHyperlinkDate().setTextFill(Color.GREEN);
                        } else {
                            // Date is more than 3 months in the future
                            action = " Expire on "+cardData.get(i).getDateExpiration()+" Renew?";
                            cb.getHyperlinkDate().setTextFill(Color.ORANGE);
                        }
                        cb.getHyperlinkDate().setText(action);
                        int finalI1 = i;
                        cb.getHyperlinkDate().setOnAction(event -> {
                            Card data = cardData.get(finalI1);
                            // Define the action to be performed when clicked
                            System.out.println("Clicked on the expiration date: " + data.getDateExpiration());
                            System.out.println(data.getCsv());
                            if (data.getDateExpiration().isBefore(today)) {
                                // Expired date
                                renewcard( data);
                            }
                            else if (data.getDateExpiration().isBefore(expirationPlusThreeMonths))
                            {
                                renewcard( data);
                            } else
                            {
                                // Date is more than 3 months in the future
                                renewcard( data);
                            }
                        });
                        if(Objects.equals(cardData.get(i).getStatutCarte(), "active"))
                        {
                            cb.getBtn().setText("active");
                        }
                        else
                        {
                            cb.getBtn().setText("inactive");
                        }
                        int finalI = i;
                        cb.getBtn().setOnMouseClicked(event -> {
                            // Add your event handling code here
                            CardCrud cc=new CardCrud();
                            try {
                                cc.updateStat(cardData.get(finalI).getId(),cardData.get(finalI).getStatutCarte());
                                if(Objects.equals(cardData.get(finalI).getStatutCarte(), "active"))
                                {cb.getBtn().setText("inactive");
                                    cardData.get(finalI).setStatutCarte("inactive");
                                }
                                else
                                {cb.getBtn().setText("active");
                                    cardData.get(finalI).setStatutCarte("active");
                                }

                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        });
                        currentPane = Stackpane1;
                        break;
                    case 1:
                        cardBank cbb=loader.getController();
                        cbb.getLabelCardnumber().setText(cardData.get(i).getNumero());
                            cbb.getLabelDate().setText(cardData.get(i).getDateExpiration().toString());
                        cbb.setStatuts(cardData.get(i).getStatutCarte());
                        LocalDate today1 = LocalDate.now();
                        // Calculate the expiration date with at least 3 months added
                        LocalDate expirationPlusThreeMonths1 = today1.plusMonths(3);
                        // Display the expiration date if it's not empty
                        // Create a clickable text field
                        String actionn;
                        if (cardData.get(i).getDateExpiration().isBefore(today1)) {
                            // Expired date
                            actionn = " Expired "+cardData.get(i).getDateExpiration()+" Renew Now!!";
                            cbb.getHyperlinkDate().setTextFill(Color.RED);
                        }
                        else if (cardData.get(i).getDateExpiration().isBefore(expirationPlusThreeMonths1))
                        {
                            // Date is within the next 3 months
                            actionn = " Expire soon on "+cardData.get(i).getDateExpiration()+" Renew?";
                            cbb.getHyperlinkDate().setTextFill(Color.GREEN);
                        } else {
                            // Date is more than 3 months in the future
                            actionn = " Expire on "+cardData.get(i).getDateExpiration()+" Renew?";
                            cbb.getHyperlinkDate().setTextFill(Color.ORANGE);
                        }
                        cbb.getHyperlinkDate().setText(actionn);
                        int finalII1 = i;
                        cbb.getHyperlinkDate().setOnAction(event -> {
                            Card data = cardData.get(finalII1);

                            // Define the action to be performed when clicked
                            System.out.println("Clicked on the expiration date: " + data.getDateExpiration());
                            System.out.println(data.getCsv());
                            if (data.getDateExpiration().isBefore(today1)) {
                                // Expired date
                                renewcard( data);
                            }
                            else if (data.getDateExpiration().isBefore(expirationPlusThreeMonths1))
                            {
                                renewcard( data);
                            } else
                            {
                                // Date is more than 3 months in the future
                                renewcard( data);
                            }
                        });
                        if(Objects.equals(cardData.get(i).getStatutCarte(), "active"))
                        {
                            cbb.getBtn().setText("active");
                        }
                        else
                        {
                            cbb.getBtn().setText("inactive");
                        }
                        int finalII = i;
                        cbb.getBtn().setOnMouseClicked(event -> {
                            // Add your event handling code here
                            CardCrud cc=new CardCrud();
                            try {
                                cc.updateStat(cardData.get(finalII).getId(),cardData.get(finalII).getStatutCarte());

                                if(Objects.equals(cardData.get(finalII).getStatutCarte(), "active"))
                                {cbb.getBtn().setText("inactive");
                                    cardData.get(finalII).setStatutCarte("inactive");
                                }
                                else
                                {cbb.getBtn().setText("active");
                                    cardData.get(finalII).setStatutCarte("active");
                                }
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        });
                        currentPane = Stackpane2;
                        break;
                    case 2:
                        cardBank cbbb=loader.getController();
                        cbbb.getLabelCardnumber().setText(cardData.get(i).getNumero());
                        cbbb.getLabelDate().setText(cardData.get(i).getDateExpiration().toString());
                        cbbb.setStatuts(cardData.get(i).getStatutCarte());
                        LocalDate today2 = LocalDate.now();
                        // Calculate the expiration date with at least 3 months added
                        LocalDate expirationPlusThreeMonths2 = today2.plusMonths(3);
                        // Display the expiration date if it's not empty
                        // Create a clickable text field
                        String actionnn;
                        if (cardData.get(i).getDateExpiration().isBefore(today2)) {
                            // Expired date
                            actionnn = " Expired "+cardData.get(i).getDateExpiration()+" Renew Now!!";
                            cbbb.getHyperlinkDate().setTextFill(Color.RED);
                        }
                        else if (cardData.get(i).getDateExpiration().isBefore(expirationPlusThreeMonths2))
                        {
                            // Date is within the next 3 months
                            actionnn = " Expire soon on "+cardData.get(i).getDateExpiration()+" Renew?";
                            cbbb.getHyperlinkDate().setTextFill(Color.GREEN);
                        } else {
                            // Date is more than 3 months in the future
                            actionnn = " Expire on "+cardData.get(i).getDateExpiration()+" Renew?";
                            cbbb.getHyperlinkDate().setTextFill(Color.ORANGE);
                        }
                        cbbb.getHyperlinkDate().setText(actionnn);
                        int finalIII1 = i;
                        cbbb.getHyperlinkDate().setOnAction(event -> {
                            Card data = cardData.get(finalIII1);
                            // Define the action to be performed when clicked
                            System.out.println("Clicked on the expiration date: " + data.getDateExpiration());
                            System.out.println(data.getCsv());
                            if (data.getDateExpiration().isBefore(today2)) {
                                // Expired date
                                renewcard( data);
                            }
                            else if (data.getDateExpiration().isBefore(expirationPlusThreeMonths2))
                            {
                                renewcard( data);
                            } else
                            {
                                // Date is more than 3 months in the future
                                renewcard( data);
                            }
                        });
                        if(Objects.equals(cardData.get(i).getStatutCarte(), "active"))
                        {
                            cbbb.getBtn().setText("active");
                        }
                        else
                        {
                            cbbb.getBtn().setText("inactive");
                        }
                        int finalIII = i;
                        cbbb.getBtn().setOnMouseClicked(event -> {
                            // Add your event handling code here
                            CardCrud cc=new CardCrud();
                            try {
                                cc.updateStat(cardData.get(finalIII).getId(),cardData.get(finalIII).getStatutCarte());
                                if(Objects.equals(cardData.get(finalIII).getStatutCarte(), "active"))
                                {cbbb.getBtn().setText("inactive");
                                    cardData.get(finalIII).setStatutCarte("inactive");
                                }
                                else
                                {cbbb.getBtn().setText("active");
                                    cardData.get(finalIII).setStatutCarte("active");
                                }
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        });
                        currentPane = Stackpane3;
                        break;
                    default:
                        break;
                }
                if (currentPane != null) {
                    // Set initial opacity for the card
                    cardPage.setOpacity(1.0);
                    currentPane.getChildren().add(cardPage);
                }
            }
            // If data is less than 3, add a button in the datasize+1 pane
            if (dataSize < 3) {
                try {
                    FXMLLoader buttonLoader = new FXMLLoader(getClass().getResource("/banque/GestionCard/addCardNormalClientbutton.fxml"));
                    GridPane page = buttonLoader.load();
//                    Scene scene = new Scene((Parent) page);
//                    // Bind the prefHeight and prefWidth of the page to the height and width of its scene
//                    page.prefHeightProperty().bind(scene.heightProperty());
//                    page.prefWidthProperty().bind(scene.widthProperty());
                    StackPane nextPane = null;
                    // Determine the next available StackPane based on the dataSize
                    switch (dataSize) {
                        case 0:
                            nextPane = Stackpane1;
                            break;
                        case 1:
                            nextPane = Stackpane2;
                            break;
                        case 2:
                            nextPane = Stackpane3;
                            break;
                    }

                    if (nextPane != null) {
                        page.setOpacity(1.0);
                        nextPane.getChildren().add(page); // Add the page to the next available StackPane
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Stackpane1.setVisible(true);
        Stackpane2.setVisible(false);
        Stackpane3.setVisible(false);
    }
    private ObservableList<Card> initialData() throws SQLException {
        CardCrud ps = new CardCrud();
        //idtoopencompte;
        return FXCollections.observableArrayList(ps.getAllNormlaCardByCompteid(2));
    }
    private void slideOut(StackPane pane, double width) {
        TranslateTransition slideOutTransition = new TranslateTransition(Duration.seconds(0.5), pane);
        slideOutTransition.setToX(-width); // Slide out to the left
        slideOutTransition.play();
    }
    private void slideInAndFade(StackPane pane, double width) {
        // Fade in transition
        FadeTransition fadeInTransition = new FadeTransition(Duration.seconds(0.5), pane);
        fadeInTransition.setToValue(1.0); // Fade in to full opacity
        // Slide in transition
        pane.setTranslateX(width); // Move the pane to the right before sliding in
        pane.setVisible(true); // Make sure the pane is visible
        TranslateTransition slideInTransition = new TranslateTransition(Duration.seconds(0.5), pane);
        slideInTransition.setToX(0); // Slide in from the right
        fadeInTransition.play();
        slideInTransition.play();
    }
    private void slideOutAndFade(StackPane pane, double width) {
        // Fade out transition
        FadeTransition fadeOutTransition = new FadeTransition(Duration.seconds(0.5), pane);
        fadeOutTransition.setToValue(0.0); // Fade out to 0 opacity
        // Slide out transition
        TranslateTransition slideOutTransition = new TranslateTransition(Duration.seconds(0.5), pane);
        slideOutTransition.setToX(width); // Slide out to the right
        fadeOutTransition.play();
        slideOutTransition.play();
    }
    @FXML
    public void next(javafx.scene.input.MouseEvent mouseEvent) {
        StackPane currentPane = null;
        StackPane nextPane = null;
        // Determine the current and next panes based on the value of 'show'
        switch (show) {
            case 0:
                currentPane = Stackpane1;
                if (!Stackpane2.getChildren().isEmpty()) {
                    nextPane = Stackpane2;
                }
                break;
            case 1:
                currentPane = Stackpane2;
                if (!Stackpane3.getChildren().isEmpty()) {
                    nextPane = Stackpane3;
                }
                break;
            case 2:
                currentPane = Stackpane3;
                if (!Stackpane1.getChildren().isEmpty()) {
                    nextPane = Stackpane1;
                }
                break;
        }
        // If there is a next pane, perform sliding animation
        if (nextPane != null) {
            // Slide out and fade out the current pane
            slideOutAndFade(currentPane, currentPane.getWidth());

            // Slide in and fade in the next pane after a short delay
            slideInAndFade(nextPane, nextPane.getWidth());

            // Update the 'show' variable for the next iteration
            show = (show + 1) % 3;
        }
    }
    @FXML
    public void back(javafx.scene.input.MouseEvent mouseEvent) {
        StackPane currentPane = null;
        StackPane prevPane = null;

        // Determine the current and previous panes based on the value of 'show'
        switch (show) {
            case 0:
                currentPane = Stackpane1;
                if (!Stackpane3.getChildren().isEmpty()) {
                    prevPane = Stackpane3;
                }
                break;
            case 1:
                currentPane = Stackpane2;
                if (!Stackpane1.getChildren().isEmpty()) {
                    prevPane = Stackpane1;
                }
                break;
            case 2:
                currentPane = Stackpane3;
                if (!Stackpane2.getChildren().isEmpty()) {
                    prevPane = Stackpane2;
                }
                break;
        }

        // If there is a previous pane, perform sliding animation
        if (prevPane != null) {
            // Slide out and fade out the current pane
            slideOutAndFade(currentPane, currentPane.getWidth());
            // Slide in and fade in the previous pane after a short delay
            slideInAndFade(prevPane, prevPane.getWidth());
            // Update the 'show' variable for the next iteration
            show = (show + 2) % 3; // Go back two steps (equivalent to subtracting 1 modulo 3)
        }
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
        renewCard.setSolde(0.0);
        System.out.println(renewCard);
        newcard.add(renewCard);
        try {
            newcard.delete(data);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
