package tn.devMinds.test;

import tn.devMinds.models.Model;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    // MyConnection mc = new MyConnection();
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws IOException {
        Model.getInstance().getViewFactory().showLoginWindow();

    }
}