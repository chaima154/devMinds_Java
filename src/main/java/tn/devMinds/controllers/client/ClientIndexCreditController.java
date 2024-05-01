package tn.devMinds.controllers.client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import tn.devMinds.models.Credit;
import tn.devMinds.sercices.CreditCrud;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ClientIndexCreditController implements Initializable{
    private final CreditCrud creditCrud = new CreditCrud();
    public Button ajout_btn;
    @FXML
    private ListView<Credit> credit_listview;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showCredits();
    }

    void showCredits() {
        int id =5;
        List<Credit> credits = creditCrud.show();
        credit_listview.getItems().clear();
        for (Credit credit : credits) {
            credit_listview.getItems().add(credit);
        }

    }

}
