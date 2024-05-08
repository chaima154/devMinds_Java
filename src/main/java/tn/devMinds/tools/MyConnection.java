package tn.devMinds.tools;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/db_bank";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static HikariDataSource dataSource;
    private static final int maximumPoolSize = 10;


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

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(URL);
        config.setUsername(USERNAME);
        config.setPassword(PASSWORD);
        config.setMaximumPoolSize(maximumPoolSize);
        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
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
