package tn.devMinds.controllers.GestionCard;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import tn.devMinds.models.TypeCard;
import tn.devMinds.services.TypeCardCrud;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class addCardNormalClientbutton implements Initializable {

    @FXML
    private GridPane gridPane; // GridPane to hold dynamically created card items

    private static final double MAX_TOTAL_HEIGHT = 438.0; // Maximum total height allowed for all cards
    private static final double MAX_TOTAL_WIDTH = 732.0; // Maximum total width allowed for all cards
    private static final double CARD_HEIGHT = 100.0; // Height of each card
    private static final double CARD_WIDTH = 300.0; // Width of each card
    private static final double ROW_SPACING = 20.0; // Vertical spacing between rows
    private static final double COLUMN_SPACING = 20.0; // Horizontal spacing between columns

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Fetch data from database using TypeCardCrud
        TypeCardCrud typeCardCrud = new TypeCardCrud();
        ArrayList<TypeCard> typeCards = null;
        try {
            typeCards = typeCardCrud.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Populate UI with data
        int columnIndex = 0; // Column index for the grid
        int rowIndex = 0; // Row index for the grid
        for (TypeCard typeCard : typeCards) {
            try {
                // Load the card_item.fxml file dynamically
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/GestionCard/card_item.fxml"));
                AnchorPane cardItem = loader.load();

                // Set the data for the card item
                card_item controller = loader.getController();
                controller.initialize(typeCard);

                // Add the card item to the GridPane
                gridPane.add(cardItem, columnIndex, rowIndex);

                // Update column and row indices
                columnIndex++;
                if (columnIndex >= 3) {
                    columnIndex = 0;
                    rowIndex++;
                }

                // Check if the maximum total height or width has been reached
                if (rowIndex * (CARD_HEIGHT + ROW_SPACING) > MAX_TOTAL_HEIGHT || columnIndex * (CARD_WIDTH + COLUMN_SPACING) > MAX_TOTAL_WIDTH) {
                    break; // Stop adding more cards if the maximum total height or width has been reached
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
