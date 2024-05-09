package tn.devMinds.controllers.GestionCard;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import tn.devMinds.controllers.LoginController;
import tn.devMinds.models.Card;
import tn.devMinds.models.Compte;
import tn.devMinds.models.TypeCard;
import tn.devMinds.services.CardCrud;

import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.prefs.Preferences;

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

    private Preferences preferences;

    private int idtoopencompte;

    public int getIdtoopencompte() {
        return idtoopencompte;
    }
    public void setIdtoopencompte(int idtoopencompte) {
        this.idtoopencompte = idtoopencompte;
    }
    public void setData(TypeCard type)
{
    Title.setText(type.getTypeCarte());
    description.setText(type.getDescriptionCarte());
    frais.setText(String.valueOf(type.getFrais()));
    typec=type;
    // idcompte=id;
}

    private int getClientIdFromPreferences() {
        preferences = Preferences.userRoot().node(LoginController.class.getName());
        String savedValue = preferences.get("Id_Client", "0");
        System.out.println(savedValue);
        return Integer.parseInt(savedValue); // Default value "0" if not found
    }
    LocalDate getDate()
    {
        LocalDate today = LocalDate.now();
        LocalDate futureDate = today.plusYears(2);
        return futureDate;
    }
    public void addrequest(javafx.scene.input.MouseEvent mouseEvent) {
        Notification not=new Notification();
        CardCrud cc=new CardCrud();
        //idtoopencompte;
        if(cc.containstypeValueNormalWaiting(getClientIdFromPreferences())) {
            not.notifier("Il y a déjà une demande en cours de traitement!");
             }
        else{Card newCard=new Card();
        Compte compte=new Compte();
        TypeCard typeCard=new TypeCard();
        newCard.setNumero(cc.generateUniqueNumero(16));
        newCard.setCsv(cc.generateRandomNumberString(3));
        newCard.setDateExpiration(getDate());
        newCard.setMdp(cc.generateUniqueNumero(4));
        newCard.setStatutCarte("Waiting");
            //idtoopencompte;
        compte.setId(getClientIdFromPreferences());
        typeCard.setId(typec.getId());
        newCard.setCompte(compte);
        newCard.setTypeCarte(typeCard);
        newCard.setSolde(0.0);
        cc.add(newCard);
            not.notifier("Votre demande a été envoyée");
        }
    }
}
