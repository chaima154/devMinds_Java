package tn.devMinds.controllers.admin.credit;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tn.devMinds.models.Credit;
import tn.devMinds.sercices.CreditCrud;
import tn.devMinds.views.CreditCellFactory;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminIndexCreditController implements Initializable, AdminCreditCell.CreditDeletedListener  {
    private final CreditCrud creditCrud = new CreditCrud();
    public Button ajout_btn;
    @FXML
    private ListView<Credit> credit_listview;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showCredits();
    }

    private void showCredits() {
        List<Credit> credits = creditCrud.showCredit();

        credit_listview.getItems().clear();

        for (Credit credit : credits) {
            AdminCreditCell.CreditDeletedListener listener = this; // Pass the listener
            credit_listview.setCellFactory(listView -> new CreditCellFactory(listener)); // Pass the listener
            // Populate the ListView with the retrieved data
            credit_listview.getItems().add(credit);
        }
    }
    @FXML
    private void handleAddCredit() {

    }

    @Override
    public void onCreditDeleted(Credit deletedCredit) {
        showCredits();
    }
}
