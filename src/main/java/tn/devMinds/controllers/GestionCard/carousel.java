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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import tn.devMinds.models.Card;
import tn.devMinds.services.CardCrud;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class carousel implements Initializable {
    @FXML
    private StackPane Stackpane1;
    @FXML
    private StackPane Stackpane2;
    @FXML
    private StackPane Stackpane3;
    private int show = 0;
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
                FXMLLoader buttonLoader = new FXMLLoader(getClass().getResource("/banque/GestionCard/addCardNormalClientbutton.fxml"));
                Parent buttonPage = buttonLoader.load();
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

                    buttonPage.setOpacity(1.0);
                    nextPane.getChildren().add(buttonPage); // Add the button to the next available StackPane
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

}
