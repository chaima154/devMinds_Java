package tn.devMinds.controllers.admin.tranche;

import tn.devMinds.models.Tranche;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminTrancheCell implements Initializable {
    public Label id;
    public Label creditId;
    public Label dateEcheance;
    public Label montantPaiment;
    public Label statutPaiment;
    public Button delete_btn;
    public Button edit_btn;

    private final Tranche tranche;

    public AdminTrancheCell(Tranche tranche){
        this.tranche=tranche;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
