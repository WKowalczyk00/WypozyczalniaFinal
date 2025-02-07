package com.example.wypozyczalniaprojekt.Administrator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AdministratorController {

    @FXML
    private Button oddajButton;
    @FXML
    private Button dodajButton;

    @FXML
    public void initialize() {
        dodajButton.setOnAction(this::openDodawanieAuta);
        oddajButton.setOnAction(this::openOddawanieAuta);
    }


    @FXML
    public void openDodawanieAuta(ActionEvent actionEvent) {
        try {
            initialize();
            // Ładowanie pliku FXML dla nowego okna
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dodawanie_auta.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            // Tworzenie nowego Stage (okna)
            Stage newWindow = new Stage();
            newWindow.setTitle("Nowe Okno");
            newWindow.setScene(scene);

            // Pokazanie nowego okna
            newWindow.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void openOddawanieAuta(ActionEvent actionEvent) {
        try {
            initialize();
            // Ładowanie pliku FXML dla nowego okna
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("oddanie_auta.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            // Tworzenie nowego Stage (okna)
            Stage newWindow = new Stage();
            newWindow.setTitle("Nowe Okno");
            newWindow.setScene(scene);

            // Pokazanie nowego okna
            newWindow.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
