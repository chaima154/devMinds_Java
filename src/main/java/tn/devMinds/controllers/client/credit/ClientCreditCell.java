package tn.devMinds.controllers.client.credit;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tn.devMinds.controllers.client.tranche.ClientIndexTrancheController;
import tn.devMinds.models.Credit;
import tn.devMinds.sercices.TrancheCrud;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientCreditCell implements Initializable {

    public Label id;
    public Label compteId;
    public Label montantCredit;
    public Label duree;
    public Label tauxInteret;
    public Label dateObtention;
    public Label montantRestant;
    public Label statutCredit;
    public Label typeCredit;
    @FXML
    public Button tranches_btn;


    private final Credit credit;

    private final TrancheCrud trancheCrud = new TrancheCrud();


    public ClientCreditCell(Credit credit){
        this.credit = credit;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCredit(credit);
        tranches_btn.setOnAction(e -> handleTranche());
    }

    public void setCredit(Credit credit) {
        id.setText(String.valueOf(credit.getId()));
        compteId.setText(String.valueOf(credit.getCompteId()));
        montantCredit.setText(String.valueOf(credit.getMontantCredit()));
        duree.setText(String.valueOf(credit.getDuree()));
        tauxInteret.setText(String.valueOf(credit.getTauxInteret()));
        dateObtention.setText(credit.getDateObtention().toString());
        montantRestant.setText(String.valueOf(credit.getMontantRestant()));
        statutCredit.setText(credit.getStatutCredit());
        typeCredit.setText(credit.getTypeCredit());
    }
    @FXML
    private void handleTranche (){
        if (trancheCrud.readById(credit.getId()).isEmpty()) {
            Alert alert;
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message d'information");
            alert.setHeaderText(null);
            alert.setContentText("Ce Crédit n'est pas approuvé");
            alert.showAndWait();
        }else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/client/Tranche/tranche.fxml"));
                Parent root = loader.load();
                ClientIndexTrancheController controller = loader.getController();
                controller.showTranches(credit);
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
