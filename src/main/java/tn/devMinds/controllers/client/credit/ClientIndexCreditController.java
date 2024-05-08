package tn.devMinds.controllers.client.credit;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import tn.devMinds.entities.Credit;
import tn.devMinds.iservices.CreditCrud;
import tn.devMinds.views.ClientCreditCellFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ClientIndexCreditController implements Initializable{
    private final CreditCrud creditCrud = new CreditCrud();
    public ListView<Credit> credit_listview;
    public TextField creditSearchBar;

    public ClientIndexCreditController() throws SQLException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    void showCredits(int id) {
        ObservableList<Credit> credits = creditCrud.readById(id);
        credit_listview.setCellFactory(param -> new ClientCreditCellFactory());

        credit_listview.setItems(credits);

        FilteredList<Credit> filter = new FilteredList<>(credits, e -> true);

        creditSearchBar.textProperty().addListener((Observable, oldValue, newValue) -> filter.setPredicate(predicateCredit -> {

            if (newValue == null || newValue.isEmpty()) {
                return true;
            }

            String searchKey = newValue.toLowerCase();

            if (String.valueOf(predicateCredit.getId()).contains(searchKey)) {
                return true;
            } else if (String.valueOf(predicateCredit.getCompteId()).contains(searchKey)) {
                return true;
            } else if (String.valueOf(predicateCredit.getMontantCredit()).contains(searchKey)) {
                return true;
            } else if (String.valueOf(predicateCredit.getDuree()).contains(searchKey)) {
                return true;
            } else if (String.valueOf(predicateCredit.getTauxInteret()).contains(searchKey)) {
                return true;
            } else if (predicateCredit.getDateObtention().toString().contains(searchKey)) {
                return true;
            } else if (predicateCredit.getStatutCredit().toLowerCase().contains(searchKey)) {
                return true;
            } else if (predicateCredit.getTypeCredit().toLowerCase().contains(searchKey)){
                return true;
            }else return false;
        }));

        SortedList<Credit> sortList = new SortedList<>(filter);
        credit_listview.setItems(sortList);

    }

}
