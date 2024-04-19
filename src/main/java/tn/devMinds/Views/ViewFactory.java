package tn.devMinds.Views;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.devMinds.controllers.Admin.AdminController;
import tn.devMinds.controllers.Client.ClientController;

import java.io.IOException;

public class ViewFactory {

    private Role loginAccountType;
    private final StringProperty adminSelectedMenuItem;
    private AnchorPane createClientView;
    private AnchorPane clientsView;

    public ViewFactory() {
        this.loginAccountType = Role.CLIENT;
        this.adminSelectedMenuItem = new SimpleStringProperty("");
    }

    public Role getLoginAccountType() {
        return loginAccountType;
    }

    public void setLoginAccountType(Role loginAccountType) {
        this.loginAccountType = loginAccountType;
    }

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

    public StringProperty getAdminSelectedMenuItem() {
        return adminSelectedMenuItem;
    }

    public AnchorPane getCreateClientView() {
        if (createClientView == null) {
            try {
                createClientView = new FXMLLoader(getClass().getResource("/fxml/Admin/CreateClient.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return createClientView;
    }

    public AnchorPane getClientsView() {
        if (clientsView == null) {
            try {
                clientsView = new FXMLLoader(getClass().getResource("/fxml/Admin/Clients.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return clientsView;
    }

    public void showAdminWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/sidebarre_admin.fxml"));
        AdminController controller = new AdminController();
        loader.setController(controller);
        createStage(loader);
    }

    private void createStage(FXMLLoader loader) throws IOException {
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setTitle("E-frank");
        stage.setScene(scene);
        stage.show();
    }

    public void closeStage(Stage stage) {
        stage.close();
    }

    public void showCreateClientWindow() {
    }
}