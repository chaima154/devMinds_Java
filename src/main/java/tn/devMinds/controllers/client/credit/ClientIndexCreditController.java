package tn.devMinds.controllers.client.credit;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import tn.devMinds.models.Credit;
import tn.devMinds.sercices.CreditCrud;
import tn.devMinds.views.ClientCreditCellFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientIndexCreditController implements Initializable{
    private final CreditCrud creditCrud = new CreditCrud();
    public ListView<Credit> credit_listview;
    public TextField creditSearchBar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    void showCredits(int id) {
        ObservableList<Credit> credits = creditCrud.readById(id);
        System.out.println("credit list here" + credits);
        credit_listview.setCellFactory(param -> new ClientCreditCellFactory());

        credit_listview.setItems(credits);

        FilteredList<Credit> filter = new FilteredList<>(credits, e -> true);

        creditSearchBar.textProperty().addListener((Observable, oldValue, newValue) -> filter.setPredicate(predicateCredit -> {

            if (newValue == null || newValue.isEmpty()) {
                return true;
            }

            String searchKey = newValue.toLowerCase();

            if (String.valueOf(predicateCredit.getId()).contains(searchKey)) {
                System.out.println("id True");
                return true;
            } else if (String.valueOf(predicateCredit.getCompteId()).contains(searchKey)) {
                System.out.println("getCompteId True");
                return true;
            } else if (String.valueOf(predicateCredit.getMontantCredit()).contains(searchKey)) {
                System.out.println("getMontantCredit True");
                return true;
            } else if (String.valueOf(predicateCredit.getDuree()).contains(searchKey)) {
                System.out.println("getDuree True");
                return true;
            } else if (String.valueOf(predicateCredit.getTauxInteret()).contains(searchKey)) {
                System.out.println("getTauxInteret True");
                return true;
            } else if (predicateCredit.getDateObtention().toString().contains(searchKey)) {
                System.out.println("getDateObtention True");
                return true;
            } else if (predicateCredit.getStatutCredit().toLowerCase().contains(searchKey)) {
                System.out.println("getStatutCredit True");
                return true;
            } else if (predicateCredit.getTypeCredit().toLowerCase().contains(searchKey)){
                System.out.println("getTypeCredit True");
                return true;
            }else return false;
        }));

        SortedList<Credit> sortList = new SortedList<>(filter);
        credit_listview.setItems(sortList);

    }

}
