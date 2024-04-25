package tn.devMinds.controllers.admin;

import tn.devMinds.models.Credit;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminCreditCell implements Initializable {
    public Label id;
    public Label compteId;
    public Label montantCredit;
    public Label duree;
    public Label tauxInteret;
    public Label dateObtention;
    public Label montantRestant;
    public Label statutCredit;
    public Label typeCredit;
    public Button delete_btn;
    public Button edit_btn;
    public Button tranches_btn;

    private final Credit credit;

    public AdminCreditCell(Credit credit){
        this.credit = credit;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
