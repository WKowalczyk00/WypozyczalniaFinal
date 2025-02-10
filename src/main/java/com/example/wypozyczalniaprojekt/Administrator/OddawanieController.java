package com.example.wypozyczalniaprojekt.Administrator;

import Wypozyczalnia.Administrator.AdministratorManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class OddawanieController {

    @FXML
    private TextField nrRejestracyjnyTextField;
    @FXML
    private Button potwierdzButton;
    @FXML
    private Button anulujButton;
    @FXML
    private Button wybierzZdjecie;

    @FXML
    private Label wybierzLabel;
    private boolean isAdded = false;
    private File zdjecie = null;


    private AdministratorManager administratorManager = new AdministratorManager();

    public void initialize() {
        potwierdzButton.setOnAction(this::potwierdzOddanie);
        anulujButton.setOnAction(this::zamknijOkno);

        wybierzZdjecie.setOnAction(event -> eWybierzZdjecie());

    }

    private void eWybierzZdjecie() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Obrazy", "*.png", "*.jpg", "*.jpeg"));
        File file = fileChooser.showOpenDialog(null);
        zdjecie = file;
        if (file != null) {
            wybierzLabel.setText("Zdjecie zostalo dodane");
            isAdded = true;
        }
    }

    private void pokazBlad(String wiadomosc) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Błąd");
        alert.setHeaderText(null);
        alert.setContentText(wiadomosc);
        alert.showAndWait();
    }
    private void pokazKomunikat(String wiadomosc) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informacja");
        alert.setHeaderText(null);
        alert.setContentText(wiadomosc);
        alert.showAndWait();
    }


    private void potwierdzOddanie(ActionEvent event) {

        String nr_rej = nrRejestracyjnyTextField.getText();

        if (!nr_rej.matches("[A-Za-z0-9]{7,10}")) {
            pokazBlad(nr_rej+" Niepoprawny numer rejestracyjny");
            return;
        }
        switch (administratorManager.returnCar(nr_rej,zdjecie)){
            case 10:
                pokazBlad("Błąd przy zapisywaniu pliku na dysku, skontaktuj się z IT");
                return;
            case 11:
                pokazBlad("Wybrany numer rejestracyjny: "+nr_rej+" nie znajduje się w bazie");
                return;
            case 12:
                pokazBlad("Wybrany numer rejestracyjny: "+nr_rej+ " jest już dostępny, sprawdź ponownie numer rejestracyjny");
                return;
            case 13:
                pokazBlad("Błąd z obsługą bazy danych, skontaktuj się z IT");
                return;
        }

        if(!isAdded){
            pokazBlad("Nie dodano zdjecia");
            return;
        }


//        AdministratorController.stworzBazeDanych();
        pokazKomunikat("Samochód o numerze rejestracyjnym: "+nr_rej+" został poprawnie oddany :))");
        handlingWindowClosing();
    }

    private void zamknijOkno(ActionEvent event) {
        handlingWindowClosing();
    }
    private void handlingWindowClosing() {
        Stage stage = (Stage) anulujButton.getScene().getWindow();
        stage.close();
    }

}
