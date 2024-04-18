module devMinds.Java {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    //requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;
    opens tn.devMinds.controllers;
    exports tn.devMinds.test; // Add this line if MainFx is in this package
    exports tn.devMinds.entities;
    exports tn.devMinds.Views;
}

