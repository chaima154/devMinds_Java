package tn.devMinds.controllers.GestionCard;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import tn.devMinds.models.TypeCard;
import tn.devMinds.services.TypeCardCrud;
import javafx.scene.layout.HBox;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
public class addCardNormalClientbutton implements Initializable {
    @FXML
    private GridPane typeCardGrid;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TypeCardCrud tcc = new TypeCardCrud();
        TypeCard tc = new TypeCard();
        ArrayList<TypeCard> list = new ArrayList<>();
        try {
            list = new ArrayList<>(tcc.getAllNormlaCard());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
int columns=0;
        int row=1;
        // Iterate over the list using foreach loop
        for (TypeCard typeCard : list) {
            FXMLLoader fxmlLoader=new FXMLLoader();
         fxmlLoader.setLocation(getClass().getResource("/banque/GestionCard/card_item.fxml"));
            HBox hboxtypecarte = null;
            try {
                hboxtypecarte = fxmlLoader.load();
                card_item ci=fxmlLoader.getController();
                ci.setData(typeCard);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if(columns==3)
            {
               columns=0;
               ++row;
            }
            typeCardGrid.add(hboxtypecarte,columns++,row);
            GridPane.setMargin(hboxtypecarte,new Insets(10));
        }
    }
}