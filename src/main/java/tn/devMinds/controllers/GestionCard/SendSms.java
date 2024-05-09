package tn.devMinds.controllers.GestionCard;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import tn.devMinds.entities.Credit;

public class SendSms {
    private final String ACCOUNT_SID;
    private final String AUTH_TOKEN;
    public SendSms(String accountSid, String authToken) {
        this.ACCOUNT_SID = accountSid;
        this.AUTH_TOKEN = authToken;
    }

    public void send(String fromNumber, String toNumber, String name, String text) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message
                .creator(
                        new PhoneNumber(toNumber),
                        new PhoneNumber(fromNumber),
                        "E-Frank Banque: " + name + " voici votre mots de passe: " + text
                )
                .create();
        System.out.println(message.getSid());
    }
    public void sendCredit(String fromNumber, String toNumber, Credit credit) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message
                .creator(
                        new PhoneNumber(toNumber),
                        new PhoneNumber(fromNumber),
                        "Félicitation !!! Votre Crédit #" + credit.getId() + " de type " + credit.getTypeCredit() + " a été approuvé. Veuillez consulter l'application pour accéder l'emploi des tranches."
                )

                .create();
        System.out.println(message.getSid());
    }
}
