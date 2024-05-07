package tn.devMinds.controllers.client.tranche;


import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import tn.devMinds.models.Credit;
import tn.devMinds.models.Tranche;
import tn.devMinds.sercices.TrancheCrud;
import tn.devMinds.views.ClientTrancheCellFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientIndexTrancheController implements Initializable{
    @FXML
    public ListView<Tranche> tranche_listview;
    public TextField trancheSearchBar;
    public Button export_btn;
    private Credit credit;

    private final TrancheCrud trancheCrud = new TrancheCrud();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        export_btn.setOnAction(e -> {
            try {
                tranchesPdf.makeTranchesPdf(credit);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public void showTranches(Credit credit) {
        this.credit = credit;
        tranche_listview.getItems().clear();
        ObservableList<Tranche> tranches = trancheCrud.readById(credit.getId());
        tranche_listview.setCellFactory(param -> new ClientTrancheCellFactory());

        tranche_listview.setItems(tranches);

        FilteredList<Tranche> filter = new FilteredList<>(tranches, e -> true);

        trancheSearchBar.textProperty().addListener((Observable, oldValue, newValue) -> filter.setPredicate(predicateTranche -> {

            if (newValue == null || newValue.isEmpty()) {
                return true;
            }

            String searchKey = newValue.toLowerCase();

            if (String.valueOf(predicateTranche.getId()).contains(searchKey)) {
                return true;
            } else if (String.valueOf(predicateTranche.getMontantPaiement()).contains(searchKey)) {
                return true;
            } else if (predicateTranche.getDateEcheance().toString().contains(searchKey)) {
                return true;
            } else if (predicateTranche.getStatutPaiement().toLowerCase().contains(searchKey)) {
                return true;
            }else return false;
        }));

        SortedList<Tranche> sortList = new SortedList<>(filter);
        tranche_listview.setItems(sortList);

    }

}
