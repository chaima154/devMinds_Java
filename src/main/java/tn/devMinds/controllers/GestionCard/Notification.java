package tn.devMinds.controllers.GestionCard;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class Notification {
   public void notifier(String ch)
   {

       try {
           Image img = new Image("banque/images/notification.png");
           if (img.isError()) {
               System.out.println("Error loading image: " + img.getException().getMessage());
               return;
           }
           Notifications notificationBuilder = Notifications.create()
                   .title("E-Frank")
                   .text(ch)
                   .graphic(new ImageView(img))
                   .hideAfter(Duration.seconds(5))
                   .position(Pos.TOP_RIGHT)
                   .darkStyle()
                   .onAction(new EventHandler<ActionEvent>() {
                       @Override
                       public void handle(ActionEvent actionEvent) {
                           System.out.println("ey n3am");
                       }
                   });
           notificationBuilder.show();
       } catch (Exception e) {
           e.printStackTrace();
       }

   }
}
