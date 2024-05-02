package tn.devMinds.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import tn.devMinds.controllers.client.tranche.ClientTrancheCell;
import tn.devMinds.models.Tranche;

public class ClientTrancheCellFactory extends ListCell <Tranche> {
    @Override
    protected void updateItem(Tranche tranche, boolean empty) {
        super.updateItem(tranche, empty);
        if (empty){
            setText(null);
            setGraphic(null);
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/client/Tranche/ClientTrancheCell.fxml"));
            ClientTrancheCell controller = new ClientTrancheCell(tranche);
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
