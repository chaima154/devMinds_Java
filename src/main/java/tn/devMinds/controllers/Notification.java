package tn.devMinds.controllers;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Notification {

    public static void display(String message, String complexity) {
        Stage window = new Stage();

        // Block interactions with other windows until this one is closed
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Notification");
        window.setMinWidth(400);
        window.setMinHeight(200);

        Label messageLabel = new Label(message);
        messageLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        Label complexityLabel = new Label(complexity);
        complexityLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        VBox layout = new VBox(10);
        layout.getChildren().addAll(messageLabel, complexityLabel);
        layout.setAlignment(Pos.CENTER);

        // Set text color based on complexity level
        switch (complexity.toLowerCase()) {
            case "faible":
                complexityLabel.setTextFill(Color.RED);
                break;
            case "moyenne":
                complexityLabel.setTextFill(Color.YELLOW);
                break;
            case "bonne":
                complexityLabel.setTextFill(Color.ORANGE);
                break;
            case "très bonne":
            case "excellente":
                complexityLabel.setTextFill(Color.GREEN);
                break;
            default:
                complexityLabel.setTextFill(Color.BLACK);
                break;
        }
        // Create and display the scene
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}
