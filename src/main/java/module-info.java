module com.example.wypozyczalniaprojekt {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires kernel;
    requires layout;
    requires io;


    opens com.example.wypozyczalniaprojekt to javafx.fxml;
    exports com.example.wypozyczalniaprojekt;
}