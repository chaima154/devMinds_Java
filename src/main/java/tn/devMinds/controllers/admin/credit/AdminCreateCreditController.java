package tn.devMinds.controllers.admin.credit;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminCreateCreditController implements Initializable {
    public TextField CompteId;
    public TextField montantCredit;
    public TextField duree;
    public TextField tauxInteret;
    public DatePicker dateObtention;
    public ChoiceBox statutCredit;
    public ChoiceBox typeCredit;
    public ChoiceBox categorieProfessionelle;
    public TextField salaire;
    public ChoiceBox typeSecteur;
    public ChoiceBox secteurActivite;
    public Button upload_btn;
    public Button create_btn;
    public Button simuler_btn;
    public Button listeCredit_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
