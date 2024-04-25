package tn.devMinds.views;

import tn.devMinds.controllers.admin.AdminCreditCell;
import tn.devMinds.models.Credit;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

public class CreditCellFactory extends ListCell<Credit> {
    @Override
    protected void updateItem(Credit credit, boolean empty) {
        super.updateItem(credit, empty);
        if (empty){
            setText(null);
            setGraphic(null);
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/admin/creditCell.fxml"));
            AdminCreditCell controller = new AdminCreditCell(credit);
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
