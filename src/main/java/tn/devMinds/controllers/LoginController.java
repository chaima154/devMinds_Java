package tn.devMinds.controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.devMinds.entities.Model;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public ChoiceBox act_selector;
    public Label mail_lbl;
    public TextField mail_field;
    public Label password_lbl;
    public Button login_b;
    public TextField password_field;
    public Label error_lbl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        login_b.setOnAction(event -> {
            try {
                onLogin();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void onLogin() throws IOException {
        Stage stage = (Stage) error_lbl.getScene().getWindow();
        Model.getInstance().getViewFactory().closrStage(stage);
        Model.getInstance().getViewFactory().showClientWindow();
    }
}
