package tn.devMinds.controllers.admin.credit;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tn.devMinds.models.Credit;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import tn.devMinds.sercices.CreditCrud;
import tn.devMinds.views.ViewFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminCreditCell implements Initializable {

    private final CreditDeletedListener listener;
    public Label id;
    public Label compteId;
    public Label montantCredit;
    public Label duree;
    public Label tauxInteret;
    public Label dateObtention;
    public Label montantRestant;
    public Label statutCredit;
    public Label typeCredit;
    public Button delete_btn;
    public Button edit_btn;
    public Button tranches_btn;

    private Credit credit;

    private final CreditCrud creditCrud = new CreditCrud();

    public AdminCreditCell(Credit credit, CreditDeletedListener listener){
        this.credit = credit;
        this.listener = listener;

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCredit(credit);
    }

    public void setCredit(Credit credit) {
        this.credit = credit;

        id.setText(String.valueOf(credit.getId()));
        compteId.setText(String.valueOf(credit.getCompteId()));
        montantCredit.setText(String.valueOf(credit.getMontantCredit()));
        duree.setText(String.valueOf(credit.getDuree()));
        tauxInteret.setText(String.valueOf(credit.getTauxInteret()));
        dateObtention.setText(credit.getDateObtention().toString());
        montantRestant.setText(String.valueOf(credit.getMontantRestant()));
        statutCredit.setText(credit.getStatutCredit());
        typeCredit.setText(credit.getTypeCredit());

        delete_btn.setOnAction(event -> handleDelete(credit));
        edit_btn.setOnAction(event -> handleEdit(credit));
        tranches_btn.setOnAction(event -> handleTranches(credit.getId()));
    }

    private void handleDelete(Credit credit) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Suppression de Crédit");
        alert.setContentText("Êtes-vous sûr de vouloir supprimer ce crédit ?");

        // Display the confirmation dialog
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Delete the offer from the database
                try {
                    creditCrud.deleteCredit(credit.getId());
                    listener.onCreditDeleted(credit); // Invoke the listener
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void handleEdit(Credit credit) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/admin/credit/createCredit.fxml"));
            Parent root = loader.load();
            AdminUpdateCreditController controller = loader.getController();
            controller.setIndexCreditController(this);
            controller.setCredit(credit); // Pass the offer to update to the update controller
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleTranches(int id) {

    }

    public interface CreditDeletedListener {
        void onCreditDeleted(Credit deletedCredit);
    }


}
