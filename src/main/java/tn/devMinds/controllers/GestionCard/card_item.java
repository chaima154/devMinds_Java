package tn.devMinds.controllers.GestionCard;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import tn.devMinds.models.TypeCard;

public class card_item {

    @FXML
    private Label Title;

    @FXML
    private Button addbutton;

    @FXML
    private Label description;

    @FXML
    private Label frais;
public void setData(TypeCard type)
{
    Title.setText(type.getTypeCarte());
    description.setText(type.getDescriptionCarte());
    frais.setText(String.valueOf(type.getFrais()));
}
}
