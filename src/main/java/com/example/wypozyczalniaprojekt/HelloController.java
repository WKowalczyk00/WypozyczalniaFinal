package com.example.wypozyczalniaprojekt;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TextField;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import org.w3c.dom.Text;


public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    private RadioButton dzisRadioButton;
    @FXML
    private RadioButton jutroRadioButton;

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
    private RadioButton manulanyRadioButton;
    @FXML
    private RadioButton automatycznyRadioButton;

    @FXML
    private RadioButton paszportRadioButton;
    @FXML
    private RadioButton dowodRadioButton;


    private ToggleGroup dataGroup;
    private ToggleGroup klasaGroup;
    private ToggleGroup silnikGroup;
    private ToggleGroup dokumentGroup;

    @FXML
    private TextField imieTextField;
    @FXML
    private TextField nazwiskoTextField;
    @FXML
    private TextField iloscDniTextField;
    @FXML
    private TextField nrPrawaTextField;
    @FXML
    private TextField dataPrawaTextField;
    @FXML
    private TextField nrKartyTextField;
    @FXML
    private TextField dataWaznosciTextField;
    @FXML
    private TextField cvcTextField;
    @FXML
    private TextField cenaTextField;

    @FXML
    private Button wynajmijButton;


    @FXML
    public void initialize() {
        // Inicjalizacja ToggleGroup aby można bylo wybrać tylko jeden i przypisanie do RadioButtonów
        dataGroup = new ToggleGroup();
        dzisRadioButton.setToggleGroup(dataGroup);
        jutroRadioButton.setToggleGroup(dataGroup);

        klasaGroup = new ToggleGroup();
        aRadioButton.setToggleGroup(klasaGroup);
        bRadioButton.setToggleGroup(klasaGroup);
        cRadioButton.setToggleGroup(klasaGroup);
        mRadioButton.setToggleGroup(klasaGroup);
        rRadioButton.setToggleGroup(klasaGroup);

        silnikGroup = new ToggleGroup();
        manulanyRadioButton.setToggleGroup(silnikGroup);
        automatycznyRadioButton.setToggleGroup(silnikGroup);

        dokumentGroup = new ToggleGroup();
        paszportRadioButton.setToggleGroup(dokumentGroup);
        dowodRadioButton.setToggleGroup(dokumentGroup);

        onlyNumbers();

        wynajmijButton.setOnAction(this::calculatePrice);
    }

    @FXML
    public void onlyNumbers() {
        iloscDniTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) { // Akceptuje tylko cyfry
                Platform.runLater(() ->
                        iloscDniTextField.setText(newValue.replaceAll("[^\\d]", ""))
                );
            }
        });

        nrKartyTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) { // Akceptuje tylko cyfry
                Platform.runLater(() ->
                        nrKartyTextField.setText(newValue.replaceAll("[^\\d]", ""))
                );
            }
        });

        nrPrawaTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) { // Akceptuje tylko cyfry
                Platform.runLater(() ->
                        nrPrawaTextField.setText(newValue.replaceAll("[^\\d]", ""))
                );
            }
        });

        cvcTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) { // Akceptuje tylko cyfry
                Platform.runLater(() ->
                        cvcTextField.setText(newValue.replaceAll("[^\\d]", ""))
                );
            }
        });

    }

    @FXML
    public void calculatePrice(ActionEvent event) {
        RadioButton selectedRadio;
        int days;
        try {

            days = Integer.parseInt(iloscDniTextField.getText());
            selectedRadio = (RadioButton) klasaGroup.getSelectedToggle();


            // Pobierz tekst z zaznaczonego przycisku
            String carClass = selectedRadio.getText();

            // Ustal cenę na podstawie klasy
            double pricePerDay = 0.0;

            switch (carClass) {
                case "KLASA A":
                    pricePerDay = 50.0;
                    break;
                case "KLASA B":
                    pricePerDay = 100.0;
                    break;
                case "KLASA C":
                    pricePerDay = 200.0;
                    break;
                case "KLASA M":
                    pricePerDay = 300.0;
                    break;
                case "KLASA R":
                    pricePerDay = 400.0;
                    break;
                default:
                    cenaTextField.setText("Niwybrano klasy!");
                    return;
            }

            // Oblicz całkowitą cenę
            double totalPrice;
            totalPrice = days * pricePerDay;

            // Wyświetl cenę w TextField
            cenaTextField.setText(String.format("%.2f PLN", totalPrice)); // formatowanie tekstu: % - poczatek ciagu
            // tekstowego, . - liczba dziesietna, 2 - zaokraglenie do 2 miejsc po przecinku, f - float
        } catch (NumberFormatException e) {
            cenaTextField.setText("Błędne dni!");
        }
    }


    private void validateForm() {
        // Walidacja formularza
        boolean isValid = formValidator.validateForm(
                new TextField[]{imieTextField, nazwiskoTextField, iloscDniTextField, nrPrawaTextField,
                        dataPrawaTextField, nrKartyTextField, dataWaznosciTextField, cvcTextField, cenaTextField},
                // Pola tekstowe
                new ToggleGroup[]{dataGroup, klasaGroup, dokumentGroup} // Grupy RadioButtonów
        );

        if (!isValid) {
            System.out.println("Formularz niepoprawny! Wypełnij wszystkie pola.");
            return;
        }

        // Pobranie danych z formularza
        FormData formData = collectFormData();

        // Wyświetlenie danych w konsoli (lub przekazanie do faktury)
        System.out.println("Zebrane dane:");
        System.out.println(formData);
    }

    private FormData collectFormData() {
        // Pobierz dane z TextField
        String imie = imieTextField.getText();
        String nazwisko = nazwiskoTextField.getText();
        Integer iloscDni = Integer.valueOf(iloscDniTextField.getText());
        Integer nrPrawa = Integer.valueOf(nrPrawaTextField.getText());
        String dataPrawa = dataPrawaTextField.getText();



        // Pobierz wartość wybraną w grupie RadioBox (klasa samochodu)
        String klasaAuta = ((RadioButton) klasaGroup.getSelectedToggle()).getText();
        if (klasaAuta == "KLASA A")
            klasaAuta = "a";
        else if (klasaAuta == "KLASA B")
            klasaAuta = "b";
        else if (klasaAuta == "KLASA C")
            klasaAuta = "c";
        else if (klasaAuta == "KLASA M")
            klasaAuta = "m";
        else if (klasaAuta == "KLASA R")
            klasaAuta = "r";

        // Pobierz wartość wybraną w grupie RadioBox (rodzaj paliwa)
        String silnikType = ((RadioButton) silnikGroup.getSelectedToggle()).getText();
        if (silnikType == "SILNIK MANUALNY")
            silnikType = "manual";
        else if (silnikType == "SILNIK AUTOMATYCZNY")
            silnikType = "automatic";

        //rodzaj dowodu
        String dokumentType = ((RadioButton) dokumentGroup.getSelectedToggle()).getText();
        if (dokumentType == "DOWÓD OSOBISTY")
            dokumentType = "id";
        else if (dokumentType == "PASZPOSRT")
            dokumentType = "passport";

        // Zwróć dane w postaci obiektu
        return new FormData(imie, nazwisko, iloscDni, nrPrawa, dataPrawa, klasaAuta, silnikType, dokumentType);
    }


}




