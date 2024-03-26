module com.example.banque {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.example.banque to javafx.fxml;
    exports com.example.banque;
}