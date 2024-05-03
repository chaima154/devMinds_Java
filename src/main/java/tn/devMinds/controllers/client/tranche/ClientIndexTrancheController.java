package tn.devMinds.controllers.client.tranche;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import tn.devMinds.controllers.client.credit.ClientCreditCell;
import tn.devMinds.models.Credit;
import tn.devMinds.models.Tranche;
import tn.devMinds.sercices.CreditCrud;
import tn.devMinds.sercices.TrancheCrud;
import tn.devMinds.views.ClientCreditCellFactory;
import tn.devMinds.views.ClientTrancheCellFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientIndexTrancheController implements Initializable{
    public ListView<Tranche> tranche_listview;
    public TextField trancheSearchBar;
    private final TrancheCrud trancheCrud = new TrancheCrud();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void showTranches(Credit credit) {
        ObservableList<Tranche> tranches = trancheCrud.readById(credit.getId());
        System.out.println("this is itt maaaan " + tranches);
        tranche_listview.setCellFactory(param -> new ClientTrancheCellFactory());

        tranche_listview.setItems(tranches);

        FilteredList<Tranche> filter = new FilteredList<>(tranches, e -> true);

        trancheSearchBar.textProperty().addListener((Observable, oldValue, newValue) -> filter.setPredicate(predicateTranche -> {

            if (newValue == null || newValue.isEmpty()) {
                return true;
            }

            String searchKey = newValue.toLowerCase();

            if (String.valueOf(predicateTranche.getId()).contains(searchKey)) {
                System.out.println("id True");
                return true;
            } else if (String.valueOf(predicateTranche.getMontantPaiement()).contains(searchKey)) {
                System.out.println("getMontantPaiement True");
                return true;
            } else if (predicateTranche.getDateEcheance().toString().contains(searchKey)) {
                System.out.println("getDateEcheance True");
                return true;
            } else if (predicateTranche.getStatutPaiement().toLowerCase().contains(searchKey)) {
                System.out.println("getStatutPaiement True");
                return true;
            }else return false;
        }));

        SortedList<Tranche> sortList = new SortedList<>(filter);
        tranche_listview.setItems(sortList);

    }

}
