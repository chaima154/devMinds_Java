package tn.devMinds.views;

import tn.devMinds.controllers.admin.AdminController;
import tn.devMinds.controllers.client.ClientController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ViewFactory {

    private AccountType loginAccountType;

    //ClientViews
    private final ObjectProperty<ClientMenuOptions> clientSelectedMenuItem;
    private AnchorPane dashboardView;
    private AnchorPane creditView;

    //AdminViews
    private final ObjectProperty<AdminMenuOptions> adminSelectedMenuItem;
    private AnchorPane AdminDashboardView;


    /*
    * /////////////////////////////////Client Views Section//////////////////////////////////////////
     */

    public ViewFactory(){
        this.loginAccountType = AccountType.CLIENT;
        this.clientSelectedMenuItem = new SimpleObjectProperty<>();
        this.adminSelectedMenuItem = new SimpleObjectProperty<>();
    }

    public ObjectProperty<ClientMenuOptions> getClientSelectedMenuItem() {
        return clientSelectedMenuItem;
    }

    public AnchorPane getDashboardView() {
        if (dashboardView == null){
            try{
                dashboardView = new FXMLLoader(getClass().getResource("/banque/client/dashboard.fxml")).load();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return dashboardView;
    }

    public AnchorPane getClientCreditView() {
        if (creditView == null){
            try{
                creditView = new FXMLLoader(getClass().getResource("/banque/client/createcredit.fxml")).load();
            }catch(Exception e){
                e.printStackTrace();
            }
        }return creditView;
    }


    public void showClientWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/client/client.fxml"));
        ClientController clientController = new ClientController();
        loader.setController (clientController);
        createStage(loader);
    }

    public AccountType getLoginAccountType() {
        return loginAccountType;
    }

    public void setLoginAccountType(AccountType loginAccountType) {
        this.loginAccountType = loginAccountType;
    }

    /*
     * /////////////////////////////////Admin Views Section//////////////////////////////////////////
     */

    public ObjectProperty<AdminMenuOptions> getAdminSelectedMenuItem() {
        return adminSelectedMenuItem;
    }

    public AnchorPane getAdminDashboardView() {
        if (AdminDashboardView == null){
            try{
                AdminDashboardView = new FXMLLoader(getClass().getResource("/banque/admin/dashboard.fxml")).load();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return AdminDashboardView;
    }

    public AnchorPane getAdminCreditView() {
        if (creditView == null){
            try{
                creditView = new FXMLLoader(getClass().getResource("/banque/admin/credit/credit.fxml")).load();
            }catch(Exception e){
                e.printStackTrace();
            }
        }return creditView;
    }

    public AnchorPane getAdminCreditForm() {
        if (creditView == null){
            try{
                creditView = new FXMLLoader(getClass().getResource("/banque/admin/credit/createCredit.fxml")).load();
            }catch(Exception e){
                e.printStackTrace();
            }
        }return creditView;
    }


    public void showAdminWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/admin/admin.fxml"));
        AdminController adminController = new AdminController();
        loader.setController (adminController);
        createStage(loader);
    }

    /*
     * /////////////////////////////////Login Views Section//////////////////////////////////////////
     */

    public void showLoginWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/login.fxml"));
        createStage(loader);
    }

    private void createStage (FXMLLoader loader) {
        Scene scene = null;
        try {
            scene = new Scene (loader.load());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene (scene);
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/banque/images/logo.png"))));
        stage.setResizable(false);
        stage.setTitle("£Frank");
        stage.show();
    }

    public void closeStage(Stage stage){
        stage.close();
    }
}
