module com.example.projet {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires twilio;
    requires kernel;
    requires layout;
    requires javax.mail;
    requires jbcrypt;
    requires org.apache.poi.ooxml;
    requires googleauth;
    requires com.zaxxer.hikari;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;
    requires java.prefs;
    requires jdk.jdi;

    exports tn.devMinds.entities;
    exports tn.devMinds.views;
    exports tn.devMinds.controllers;
    exports tn.devMinds.controllers.client.tranche;

    opens tn.devMinds.test to javafx.graphics;
    opens tn.devMinds.controllers to javafx.fxml;
    opens tn.devMinds.entities to javafx.base;
    opens tn.devMinds.models to javafx.base;


    opens tn.devMinds.controllers.admin.credit to javafx.fxml;
    opens tn.devMinds.controllers.admin.tranche to javafx.fxml;
    opens tn.devMinds.controllers.comptecontroller to javafx.fxml; // Add this line

    opens tn.devMinds.controllers.client.credit to javafx.fxml;
}
