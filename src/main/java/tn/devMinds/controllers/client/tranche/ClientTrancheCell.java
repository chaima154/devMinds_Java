package tn.devMinds.controllers.client.tranche;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import tn.devMinds.models.Credit;
import tn.devMinds.models.Tranche;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientTrancheCell implements Initializable {

    public Label id;
    public Label montantPaiement;
    public Label dateEcheance;
    public Label statutPaiement;
    private final Tranche tranche;


    public ClientTrancheCell(Tranche tranche){
        this.tranche = tranche;

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("hellow darkness");
        setTranche(tranche);
    }

    public void setTranche(Tranche tranche) {
        id.setText(String.valueOf(tranche.getId()));
        montantPaiement.setText(String.valueOf(tranche.getMontantPaiement()));
        dateEcheance.setText(tranche.getDateEcheance().toString());
        statutPaiement.setText(tranche.getStatutPaiement());
    }



}
