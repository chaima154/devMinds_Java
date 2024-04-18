package tn.devMinds.test;
        import javafx.application.Application;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Scene;
        import javafx.stage.Stage;
        import tn.devMinds.tools.MyConnection;
        import java.io.IOException;

public class MainFx extends Application {
   // MyConnection mc = new MyConnection();
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Hello!");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}