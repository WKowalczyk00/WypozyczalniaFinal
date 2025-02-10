module com.example.wypozyczalniaprojekt {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires kernel;
    requires layout;
    requires io;
    requires java.desktop;

    opens com.example.wypozyczalniaprojekt to javafx.fxml;
    opens com.example.wypozyczalniaprojekt.Administrator to javafx.fxml;
    exports com.example.wypozyczalniaprojekt.Administrator;
    exports com.example.wypozyczalniaprojekt;
}