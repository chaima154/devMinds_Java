package tn.devMinds.controllers.GestionCard;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import tn.devMinds.models.TypeCard;
public class card_item {
    @FXML
    private Label typeCardLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label priceLabel;
    public void initialize(TypeCard typeCard) {
        typeCardLabel.setText("Type: " + typeCard.getTypeCarte());
        descriptionLabel.setText("Description: " + typeCard.getDescriptionCarte());
        priceLabel.setText("Price: " + typeCard.getFrais());
    }
}