package tn.devMinds.controllers.GestionCard;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
public class SendSms {


    // Find your Account Sid and Token at console.twilio.com

    public  void send(String number,String name,String text) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message
                .creator(
                        new PhoneNumber("+21693860151"),
                        new PhoneNumber("+15073646864"),
                        "E-Frank Banque: "+name+" voici votre mots de passe: "+text
                )
                .create();

        System.out.println(message.getSid());
    }

}
