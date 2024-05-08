module com.example.projet {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires twilio;
    requires kernel;
    requires layout;

    requires jbcrypt;

    requires googleauth;
    requires com.zaxxer.hikari;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;
    requires java.prefs;
    requires jdk.jdi;
    requires itextpdf;
    requires com.google.api.client.auth;
    requires com.google.api.client.extensions.java6.auth;
    requires com.google.api.client.extensions.jetty.auth;
    requires google.api.client;
    requires com.google.api.client;
    requires com.google.api.client.json.gson;
    requires com.google.api.services.gmail;
    requires org.apache.commons.codec;
    requires java.mail;
    requires activation;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    exports tn.devMinds.controllers.assurance;
    requires jdk.httpserver; // Add this line


    exports tn.devMinds.entities;
    exports tn.devMinds.views;
    exports tn.devMinds.controllers;
    exports tn.devMinds.controllers.client.tranche;
    exports tn.devMinds.controllers.demande to javafx.fxml;

    opens tn.devMinds.controllers.assurance to javafx.fxml;
    opens tn.devMinds.controllers.demande to javafx.fxml;

    opens tn.devMinds.test to javafx.graphics;
    opens tn.devMinds.controllers to javafx.fxml;
    opens tn.devMinds.entities to javafx.base;
    opens tn.devMinds.models to javafx.base;

    opens tn.devMinds.controllers.admin.credit to javafx.fxml;
    opens tn.devMinds.controllers.admin.tranche to javafx.fxml;
    opens tn.devMinds.controllers.comptecontroller to javafx.fxml;
    opens tn.devMinds.controllers.client.credit to javafx.fxml;
}
