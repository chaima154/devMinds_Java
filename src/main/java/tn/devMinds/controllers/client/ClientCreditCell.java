package tn.devMinds.controllers.client;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tn.devMinds.controllers.admin.credit.AdminUpdateCreditController;
import tn.devMinds.models.Credit;
import tn.devMinds.sercices.CreditCrud;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ClientCreditCell implements Initializable {

    public Label id;
    public Label compteId;
    public Label montantCredit;
    public Label duree;
    public Label tauxInteret;
    public Label dateObtention;
    public Label montantRestant;
    public Label statutCredit;
    public Label typeCredit;

    private Credit credit;

    private final CreditCrud creditCrud = new CreditCrud();

    public ClientCreditCell(Credit credit){
        this.credit = credit;

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCredit(credit);
    }

    public void setCredit(Credit credit) {
        this.credit = credit;

        id.setText(String.valueOf(credit.getId()));
        compteId.setText(String.valueOf(credit.getCompteId()));
        montantCredit.setText(String.valueOf(credit.getMontantCredit()));
        duree.setText(String.valueOf(credit.getDuree()));
        tauxInteret.setText(String.valueOf(credit.getTauxInteret()));
        dateObtention.setText(credit.getDateObtention().toString());
        montantRestant.setText(String.valueOf(credit.getMontantRestant()));
        statutCredit.setText(credit.getStatutCredit());
        typeCredit.setText(credit.getTypeCredit());
    }



}
