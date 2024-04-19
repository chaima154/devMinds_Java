package tn.devMinds.controllers.Admin;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import tn.devMinds.entities.Client;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientCellController implements Initializable {


    public Label fName_lbl;
    public Label lName_lbl;
    public Label mail_lbl;
    public Button delete_btn;
    private final Client client;

    public ClientCellController(Client client) {
        this.client = client;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
