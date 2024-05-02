package tn.devMinds.test;
        import javafx.application.Application;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Scene;
        import javafx.stage.Stage;
        import tn.devMinds.entities.User;
        import tn.devMinds.iservices.UserService;
        import tn.devMinds.tools.MyConnection;
        import java.io.IOException;
        import java.util.ArrayList;

public class MainFx extends Application {
   // MyConnection mc = new MyConnection();
   public static void main(String[] args) {
       UserService userService = new UserService();

       // Récupérer toutes les données des utilisateurs depuis la base de données
       ArrayList<User> users = userService.getAllData();

       // Afficher les utilisateurs récupérés
       for (User user : users) {
           System.out.println(user);
       }
       launch(args);
   }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/banque/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());


        primaryStage.setTitle("E-frank");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}