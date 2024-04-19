package tn.devMinds.entities;

import tn.devMinds.Views.Role;
import tn.devMinds.Views.ViewFactory;
import tn.devMinds.tools.MyConnection;

public class Model {
    private final ViewFactory viewFactory;
    private final MyConnection cnx;
    private static Model model;
    private final Client client;
    private boolean clientLoginSuccessFlag;
    private Role loginAccountType= Role.CLIENT;

    private Model() {
        this.viewFactory = new ViewFactory();
        this.cnx = MyConnection.getInstance();
        //client
        this.clientLoginSuccessFlag = false;
        this.client=new Client("","","","");


        //admin
    }

    public static synchronized Model getInstance() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }

    public ViewFactory getViewFactory() {
        return viewFactory;
    }

    public MyConnection getCnx() {
        return cnx;
    }

    public Role getLoginAccountType() {
        return loginAccountType;
    }

    public void setLoginAccountType(Role loginAccountType) {
        this.loginAccountType = loginAccountType;
    }

    public boolean isClientLoginSuccessFlag() {
        return this.clientLoginSuccessFlag;
    }

    public void setClientLoginSuccessFlag(boolean flag) {
        this.clientLoginSuccessFlag = flag;
    }

    public Client getClient() {
        return client;
    }

}
