package tn.devMinds.controllers.GestionCard;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import tn.devMinds.models.Card;
import tn.devMinds.models.Compte;
import tn.devMinds.models.TypeCard;
import tn.devMinds.services.CardCrud;

import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;

public class card_item {

    @FXML
    private Label Title;

    @FXML
    private Button addbutton;
@FXML
private int id;
    @FXML
    private Label frais;
    @FXML
    private Text description;

    private TypeCard typec;
    private int idcompte;

    public void setData(TypeCard type)
{
    Title.setText(type.getTypeCarte());
    description.setText(type.getDescriptionCarte());
    frais.setText(String.valueOf(type.getFrais()));
    typec=type;
    // idcompte=id;
}


    LocalDate getDate() {
        LocalDate today = LocalDate.now();
        LocalDate futureDate = today.plusYears(2);
        return futureDate;
    }

    public void addrequest(javafx.scene.input.MouseEvent mouseEvent) {
        CardCrud cc=new CardCrud();
        if(cc.containstypeValueWaiting(3)) {
            Notification not=new Notification();
            not.notifier("maktaarefha s3iba");
        }
        else{Card newCard=new Card();
        Compte compte=new Compte();
        TypeCard typeCard=new TypeCard();
        newCard.setNumero(cc.generateUniqueNumero(16));
        newCard.setCsv(cc.generateRandomNumberString(3));
        newCard.setDateExpiration(getDate());
        newCard.setMdp(cc.generateUniqueNumero(4));
        newCard.setStatutCarte("Waiting");
        compte.setId(3);
        typeCard.setId(typec.getId());
        newCard.setCompte(compte);
        newCard.setTypeCarte(typeCard);
        newCard.setSolde(0.0);
        cc.add(newCard);}
    }
}
