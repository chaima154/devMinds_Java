package tn.devMinds.Views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tn.devMinds.controllers.Client.ClientController;

import java.io.IOException;

public class ViewFactory {
    public void showLoginWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        createStage(loader);
    }
    public void showClientWindow() throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Client/Client.fxml"));
        ClientController clientController = new ClientController();
        loader.setController(clientController);
        createStage(loader);

    }

    private void createStage(FXMLLoader loader) throws IOException {
        Scene scene = new Scene(loader.load());

        try {
            scene = new Scene(loader.load());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Stage stage = new Stage();
        stage.setTitle("E-frank");
        stage.setScene(scene);
        stage.show();
    }

    public void closrStage(Stage stage){
        stage.close();
    }
}
