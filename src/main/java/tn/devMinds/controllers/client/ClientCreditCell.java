package tn.devMinds.controllers.client;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import tn.devMinds.models.Credit;
import tn.devMinds.sercices.CreditCrud;

import java.net.URL;
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
