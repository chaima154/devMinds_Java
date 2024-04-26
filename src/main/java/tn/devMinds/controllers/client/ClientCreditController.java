package tn.devMinds.controllers.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tn.devMinds.models.Credit;
import tn.devMinds.sercices.CreditCrud;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientCreditController implements Initializable {
    public ListView credit_listview;
    public ChoiceBox typeCredit;
    public TextField montantCredit;
    public TextField tauxInteret;
    public TextField duree;
    public Button simuler_btn;
    public Button listeCredit_btn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void showCredits(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/client/credit.fxml"));
            Parent root = loader.load();
            ClientIndexCreditController controller = loader.getController();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
