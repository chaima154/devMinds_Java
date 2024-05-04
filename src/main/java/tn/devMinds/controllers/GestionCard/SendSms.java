package tn.devMinds.controllers.GestionCard;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

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
}
