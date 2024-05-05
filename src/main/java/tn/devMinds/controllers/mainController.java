package tn.devMinds.controllers;

import tn.devMinds.entities.user;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import tn.devMinds.iservices.userService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class mainController extends Application {
    private static Stage primaryStage;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException{
        mainController.setPrimaryStage(primaryStage);
        primaryStage.setTitle("E-frank");
        loadFXML("/banque/login.fxml");
    }



    public static FXMLLoader loadFXML(String fxmlFileName) throws IOException {
        FXMLLoader loader = new FXMLLoader(mainController.class.getResource(fxmlFileName));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        return loader;
    }



    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }



}
