package tn.devMinds.controllers.GestionCard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import tn.devMinds.models.Card;
import tn.devMinds.models.TypeCard;
import tn.devMinds.services.CardCrud;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class cardBank implements Initializable {

    @FXML
    private Button btn;

    @FXML
    private AnchorPane card;

    @FXML
    private ImageView chipImage;

    @FXML
    private ImageView contactlessImage;

    @FXML
    private Label labelCardnumber;

    @FXML
    private Label labelDate;


@FXML
private String statuts;

    public String getStatuts() {
        return statuts;
    }

    public void setStatuts(String statuts) {
        this.statuts = statuts;
    }

    @FXML
    private ImageView visaImage;
    public Button getBtn() {
        return btn;
    }
    public void setBtn(Button btn) {
        this.btn = btn;
    }
    public Label getLabelCardnumber() {
        return labelCardnumber;
    }
    public void setLabelCardnumber(Label labelCardnumber) {
        this.labelCardnumber = labelCardnumber;
    }
    public Label getLabelDate() {
        return labelDate;
    }
    public void setLabelDate(Label labelDate) {
        this.labelDate = labelDate;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LocalDate today = LocalDate.now();
        // Calculate the expiration date with at least 3 months added
        LocalDate expirationPlusThreeMonths = today.plusMonths(3);

    }
}