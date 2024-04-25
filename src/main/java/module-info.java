module com.example.projet {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.sql;


    exports tn.devMinds.models;
    exports tn.devMinds.views;
    exports tn.devMinds.controllers;
    exports tn.devMinds.controllers.admin;
    exports tn.devMinds.controllers.client;


    opens tn.devMinds.controllers to javafx.fxml;
    opens tn.devMinds.controllers.admin to javafx.fxml;
    opens tn.devMinds.controllers.client to javafx.fxml;
}