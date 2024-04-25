package tn.devMinds.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/db_bank";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private static MyConnection instance;
    private Connection cnx;

    private MyConnection() {
        try {
            cnx = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connection established....");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Could not connect to the database");
        }
    }

    public static MyConnection getInstance() {
        if (instance == null) {
            instance = new MyConnection();
        }
        return instance;
    }

    public Connection getCnx() {
        return cnx;
    }

    public void close() {
        try {
            if (cnx != null) {
                cnx.close();
                System.out.println("Connection closed....");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
