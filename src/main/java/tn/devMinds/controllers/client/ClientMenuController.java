package tn.devMinds.controllers.client;

import tn.devMinds.models.Model;
import tn.devMinds.views.ClientMenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientMenuController implements Initializable {
    public Button dashboard_btn;
    public Button utilisateurs_btn;
    public Button comptes_btn;
    public Button transactions_btn;
    public Button cartesbancaires_btn;
    public Button credit_btn;
    public Button assurances_btn;
    public Button deconnectre_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    private void addListeners(){
        dashboard_btn.setOnAction(event -> onDashboard());
        credit_btn.setOnAction(event -> onCredit());
    }

    private void onDashboard(){
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOptions.DASHBOARD);
    }

    private void onCredit(){
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOptions.CREDIT);
    }
}
