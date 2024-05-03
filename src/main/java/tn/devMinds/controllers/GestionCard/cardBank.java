package tn.devMinds.controllers.GestionCard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import tn.devMinds.models.Card;
import tn.devMinds.models.TypeCard;
import tn.devMinds.services.CardCrud;

import java.sql.SQLException;
import java.util.ArrayList;

public class cardBank {

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

}