package tn.devMinds.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import tn.devMinds.controllers.client.credit.ClientCreditCell;
import tn.devMinds.models.Credit;

public class ClientCreditCellFactory extends ListCell <Credit> {
    @Override
    protected void updateItem(Credit credit, boolean empty) {
        super.updateItem(credit, empty);
        if (empty){
            setText(null);
            setGraphic(null);
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/client/Credit/ClientCreditCell.fxml"));
            ClientCreditCell controller = new ClientCreditCell(credit);
            loader.setController(controller);
            setText(null);
            try {
                setGraphic(loader.load());
            }catch (Exception e){
                e.printStackTrace(System.out);
            }
        }
    }
}
