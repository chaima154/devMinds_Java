module devMinds.Java {
    requires java.sql;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    exports tn.devMinds.controllers;
    opens tn.devMinds.controllers to javafx.fxml;

    exports tn.devMinds.test;
    opens tn.devMinds.test to javafx.fxml;

    exports tn.devMinds.entities;
    opens tn.devMinds.entities to javafx.base;
}
