package tn.devMinds.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
    public static MyConnection instance;
    Connection cnx;
    public MyConnection(){
        try {
            String url = "jdbc:mysql://localhost:3306/db_bank";
            String login = "root";
            String pwd = "";
            cnx = DriverManager.getConnection(url, login, pwd);
            System.out.println(("Connexion établie...."));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(("could not connect to database"));
        }
    }

    public Connection getCnx() {
        return cnx;
    }

    public static MyConnection getInstance() {
        if(instance == null){
            instance = new MyConnection();
        }
        return instance;
    }
}
