package tn.devMinds.controllers.client.credit;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tn.devMinds.sercices.CreditCrud;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientCreditController implements Initializable {

    public ChoiceBox <String> typeCredit;
    public TextField montantCredit;
    public TextField tauxInteret;

    public TextField duree;
    public Button simuler_btn;
    public Button listeCredit_btn;
    private final CreditCrud creditCrud = new CreditCrud();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    private void showCreditInterface() {
        int id =5;
        if (creditCrud.readById(id).isEmpty()) {
            Alert alert;
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message d'information");
            alert.setHeaderText(null);
            alert.setContentText("Vous n'avez pas de demandes crédits");
            alert.showAndWait();
        }else{
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/client/Credit/Credit.fxml"));
                Parent root = loader.load();
                ClientIndexCreditController controller = loader.getController();
                controller.setAdminIndexCreditController(this);
                controller.showCredits(id);
                Stage stage = new Stage();
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(new Scene(root));
                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace(System.out);
            }
        }
    }
}
