module com.example.wypozyczalniaprojekt {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.wypozyczalniaprojekt to javafx.fxml;
    exports com.example.wypozyczalniaprojekt;
}