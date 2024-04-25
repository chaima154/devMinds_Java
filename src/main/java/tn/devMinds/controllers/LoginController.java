package tn.devMinds.controllers;

import tn.devMinds.models.Model;
import tn.devMinds.views.AccountType;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public ChoiceBox<AccountType> typeCompte;
    public TextField email;
    public TextField mdp;
    public Button login_btn;
    public Label error_lbl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        typeCompte.setItems(FXCollections.observableArrayList(AccountType.CLIENT, AccountType.ADMIN));
        typeCompte.setValue(Model.getInstance().getViewFactory().getLoginAccountType());
        typeCompte.valueProperty().addListener(observable -> Model.getInstance().getViewFactory().setLoginAccountType(typeCompte.getValue()));
        login_btn.setOnAction(event -> onLogin());
    }

    public void onLogin() {
        Stage stage = (Stage) error_lbl.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        if (Model.getInstance().getViewFactory().getLoginAccountType() == AccountType.CLIENT){
            Model.getInstance().getViewFactory().showClientWindow();
        }else {
            Model.getInstance().getViewFactory().showAdminWindow();
        }
    }
}
