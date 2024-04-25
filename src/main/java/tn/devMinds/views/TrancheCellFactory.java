package tn.devMinds.views;

import tn.devMinds.controllers.admin.AdminTrancheCell;
import tn.devMinds.models.Tranche;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

public class TrancheCellFactory extends ListCell<Tranche> {
    @Override
    protected void updateItem(Tranche tranche, boolean empty) {
        super.updateItem(tranche, empty);
        if (empty){
            setText(null);
            setGraphic(null);
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/admin/trancheCell.fxml"));
            AdminTrancheCell controller = new AdminTrancheCell(tranche);
            loader.setController(controller);
            setText(null);
            try {
                setGraphic(loader.load());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
