package tn.devMinds.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.prefs.Preferences;

public class MainFx extends Application {

    private Preferences preferences;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/banque/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Image logoImage = new Image(getClass().getResourceAsStream("/banque/images/logo.png"));

        // Set the primary stage icon
        primaryStage.getIcons().add(logoImage);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Initialize preferences
        preferences = Preferences.userRoot().node(this.getClass().getName());

        // Example of saving a variable
        preferences.put("Id_Client", "1");

        // Example of retrieving the saved variable
        String savedValue = preferences.get("Id_Client", "02");
        System.out.println("Saved Value: " + savedValue);
    }
}
