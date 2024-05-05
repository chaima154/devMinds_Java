package tn.devMinds.controllers.client;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import tn.devMinds.models.Credit;
import tn.devMinds.models.Model;
import tn.devMinds.models.Tranche;
import tn.devMinds.sercices.CreditCrud;
import tn.devMinds.sercices.TrancheCrud;

import java.net.URL;
import java.sql.Date;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ClientController implements Initializable {
    public static final String ACCOUNT_SID = "ACc3bc97fbd7d7fdc0e57c7a5e6e6be5f4";
    public static final String AUTH_TOKEN = "283c8bb0a5cce6f84d26a7ab0504ad76";
    public BorderPane client_parent;
    private final CreditCrud creditCrud = new CreditCrud();
    private final TrancheCrud trancheCrud = new TrancheCrud();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().addListener(((observableValue, oldVal, newVal) -> {
            switch (newVal){
                case CREDIT-> client_parent.setCenter(Model.getInstance().getViewFactory().getClientCreditView());
                default -> client_parent.setCenter(Model.getInstance().getViewFactory().getDashboardView());
            }
        } ));
        List<Credit> credits = creditCrud.readById(5);
        for(Credit credit: credits){
            if (credit.getStatutCredit().equals("Approuvé")){
                sendCreditMessages(credit);
            }
        }

    }
    void sendCreditMessages(Credit credit){
        System.out.println("the message will be sent !!!!");
        LocalDate date = credit.getDateObtention().minusDays(10); // Start one month from now
        List<Tranche> tranches = trancheCrud.readById(credit.getId());
        for(Tranche tranche : tranches){
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

            // Define the start date and time for the first execution
            date = date.plusMonths(1); // Start one month from now
            LocalTime time = LocalTime.of(19, 5);
            LocalDateTime dateTime = date.atTime(time);
            // Define the time delay between each execution (in milliseconds)
            long delay = TimeUnit.DAYS.toMillis(30); // 30 days in milliseconds

            // Schedule the task to execute repeatedly with a fixed delay
            scheduler.scheduleAtFixedRate(() -> {
                // Your function to execute
                sendSMS(tranche, credit);
                System.out.println("Message sent on " + LocalDateTime.now());
            }, getDelay(dateTime), delay, TimeUnit.MILLISECONDS);
        }
    }

    double calculateMensualite(Credit credit){
        double pay = ((((credit.getMontantCredit() * credit.getTauxInteret()) / 100) + credit.getMontantCredit()) / credit.getDuree());
        final DecimalFormat decimalForm = new DecimalFormat("0.00");
        return (Double.parseDouble(decimalForm.format(pay)));
    }

    void sendSMS(Tranche tranche, Credit credit) {
        System.out.println("hello");
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber("+21654025328"),
                        new com.twilio.type.PhoneNumber("+15054665064"), "Votre Tranche de Crédit #" + tranche.getCreditId() + " de type " + credit.getTypeCredit() + " est dû le " + tranche.getDateEcheance() + ". Veuillez payer la mensualité de somme: " + calculateMensualite(credit))
                .create();
        System.out.println(message.getSid());
    }

    private static long getDelay(LocalDateTime startDate) {
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(startDate)) {
            startDate = startDate.plusMonths(1); // If the start date has passed, move to the next month

        }
        return Date.from(startDate.atZone(ZoneId.systemDefault()).toInstant()).getTime() - System.currentTimeMillis();
    }

}
