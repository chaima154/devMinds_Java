package tn.devMinds.Views;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import tn.devMinds.entities.Client;

public class ClientCellFactory extends ListCell<Client> {
    public ClientCellFactory(Client client) {

    }

    @Override
    protected void updateItem(Client client, boolean empty) {
        super.updateItem(client, empty);
        if(empty){
            setText(null);
            setGraphic(null);
        }else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Admin/ClientCell.fxml"));
            ClientCellFactory controller= new ClientCellFactory(client);
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
