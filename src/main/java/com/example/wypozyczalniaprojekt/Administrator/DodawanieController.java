package com.example.wypozyczalniaprojekt.Administrator;

import Wypozyczalnia.Administrator.AdministratorManager;
import Wypozyczalnia.Car;
import com.example.wypozyczalniaprojekt.FormData;
import com.example.wypozyczalniaprojekt.formValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;


import static Wypozyczalnia.Main.getCars;

public class DodawanieController {

    @FXML
    private RadioButton aRadioButton;
    @FXML
    private RadioButton bRadioButton;
    @FXML
    private RadioButton cRadioButton;
    @FXML
    private RadioButton mRadioButton;
    @FXML
    private RadioButton rRadioButton;

    @FXML
    private RadioButton manualnaRadioButton;
    @FXML
    private RadioButton automatycznaRadioButton;

    @FXML
    private RadioButton dostepneRadioButton;
    @FXML
    private RadioButton nieDostepneRadioButton;

    @FXML
    private TextField modelTextField;
    @FXML
    private TextField nrRejestracyjnyTextField;
    @FXML
    private TextField liczbaSiedzenTextField;

    @FXML
    private Button dodajButton;
    @FXML
    private Button anulujButton;

    private AdministratorManager administratorManager = new AdministratorManager();

    ToggleGroup klasaGroup = new ToggleGroup();
    ToggleGroup skrzyniaGroup = new ToggleGroup();
    ToggleGroup dostepneGroup = new ToggleGroup();
    public void initialize() {


        aRadioButton.setToggleGroup(klasaGroup);
        bRadioButton.setToggleGroup(klasaGroup);
        cRadioButton.setToggleGroup(klasaGroup);
        mRadioButton.setToggleGroup(klasaGroup);
        rRadioButton.setToggleGroup(klasaGroup);


        manualnaRadioButton.setToggleGroup(skrzyniaGroup);
        automatycznaRadioButton.setToggleGroup(skrzyniaGroup);


        dostepneRadioButton.setToggleGroup(dostepneGroup);
        nieDostepneRadioButton.setToggleGroup(dostepneGroup);

        dodajButton.setOnAction(this::dodajSamochod);
        anulujButton.setOnAction(this::zamknijOkno);
    }
    private boolean validateForm() {
        // Walidacja formularza
        boolean isValid = formValidator.validateForm(
                new TextField[]{modelTextField,nrRejestracyjnyTextField,liczbaSiedzenTextField},
                // Pola tekstowe
                new ToggleGroup[]{klasaGroup,skrzyniaGroup,dostepneGroup} // Grupy RadioButtonów
        );

        if (!isValid) {
            pokazBlad("Nie wszystkie pola zostaly wypelnione (grrr)");
            return false;
        }
        String nr_rej = nrRejestracyjnyTextField.getText();
        int liczbaSiedzen = Integer.parseInt(liczbaSiedzenTextField.getText());

        if(!(liczbaSiedzen>=1&&liczbaSiedzen<=9)){
            pokazBlad("Niepoprawna ilosc siedzeń: wybierz liczbę od 1 do 9");
            return false;
        }
        if (!nr_rej.matches("[A-Za-z0-9]{7,10}")) {
            pokazBlad(nr_rej+" Niepoprawny numer rejestracyjny");
            return false;
        }



        return true;
    }

    private Car makeCarFromValues(){
        RadioButton klasaRadio = (RadioButton) klasaGroup.getSelectedToggle();
        String carClass = klasaRadio.getText();

        RadioButton skrzyniaRadio = (RadioButton) skrzyniaGroup.getSelectedToggle();
        String carTransmittion = skrzyniaRadio.getText();

        RadioButton dostepneRadio = (RadioButton) dostepneGroup.getSelectedToggle();
        String carAvai = dostepneRadio.getText();
        boolean dostepnosc = false;

        switch (carTransmittion) {
            case "MANUALNA":
                carTransmittion = "Manual";
                break;
            case "AUTOMATYCZNA":
                carTransmittion = "Automatic";
                break;


        }
        switch (carAvai){
            case "DOSTĘPNE":
                dostepnosc = true;
                break;
            case "NIEDOSTĘPNE":
                dostepnosc = false;
                break;
        }
        String modelAuta = modelTextField.getText();
        String nr_rej = nrRejestracyjnyTextField.getText();
        int liczba = Integer.parseInt(liczbaSiedzenTextField.getText());

        Car autko = new Car(getCars().getLast().getId()+1,modelAuta,carClass,carTransmittion,nr_rej,liczba,dostepnosc );
        return autko;
    }
    private void dodajSamochod(ActionEvent event) {
        if(!validateForm()){

            return;
        }
        Car newCar = makeCarFromValues();
        if(!administratorManager.addNewCar(newCar)){
            pokazBlad("Nie udało się dodać samochodu");
        }
        pokazKomunikat("Samochod zostal poprawnie dodany do bazy danych");
        //tutaj sukces
        handlingWindowClosing();
    }
    private void zamknijOkno(ActionEvent event) {
        handlingWindowClosing();
    }

    private void handlingWindowClosing() {
        Stage stage = (Stage) anulujButton.getScene().getWindow();
        stage.close();
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
}
