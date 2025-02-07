package com.example.wypozyczalniaprojekt;

import Wypozyczalnia.Car;
import Wypozyczalnia.Main;
import Wypozyczalnia.WybierzSamochodException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TextField;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import org.w3c.dom.Text;

import java.util.Objects;


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
    private RadioButton manualnyRadioButton;
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
    private Button wynajmijButton2;

    private Main communication;

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
        manualnyRadioButton.setToggleGroup(silnikGroup);
        automatycznyRadioButton.setToggleGroup(silnikGroup);

        dokumentGroup = new ToggleGroup();
        paszportRadioButton.setToggleGroup(dokumentGroup);
        dowodRadioButton.setToggleGroup(dokumentGroup);


        wynajmijButton.setOnAction(this::calculatePrice);
        wynajmijButton2.setOnAction(this::rentCar);

        communication = new Main();
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

//            communication.getCennikList().get(0);

            switch (carClass) {
                case "KLASA A":
                    pricePerDay = communication.getCennikList().get(0).getPrice();
                    break;
                case "KLASA B":
                    pricePerDay = communication.getCennikList().get(1).getPrice();
                    break;
                case "KLASA C":
                    pricePerDay = communication.getCennikList().get(2).getPrice();
                    break;
                case "KLASA M":
                    pricePerDay = communication.getCennikList().get(3).getPrice();
                    break;
                case "KLASA R":
                    pricePerDay = communication.getCennikList().get(4).getPrice();
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
            // tekstowego, . - liczba dziesietna, 2 - zaokraglenie do 2 miejsc po przecin4u, f - float


        } catch (NumberFormatException e) {
            cenaTextField.setText("Błędne dni!");
        }


    }

    private void rentCar(ActionEvent event){
        FormData getInfo = validateForm();
        if (getInfo == null) {
            pokazBlad("Dane niepoprawnie wprowadzone, sproboj ponownie po naprawieniu usterki");
            return;
        }
//        communication.generateInvoice();
        Car chosenSamochod = null;
        try {
            chosenSamochod = communication.wybierzSamochod(getInfo.getKlasaAuta(), getInfo.getRodzajSilnika());
        } catch (WybierzSamochodException e) {
            // Obsługa wyjątku
            System.out.println("Przechwycono wyjątek: " + e.getMessage());
            pokazBlad(e.getMessage());
        } finally {
            if(communication.payment()&&chosenSamochod!=null){
                int start = getInfo.getStartint();
                int ilosc_dni = getInfo.getIloscDni();
                String dane_klienta = getInfo.getImie()+" "+ getInfo.getNazwisko();
                String numer_p_jazdy = String.valueOf(getInfo.getNrPrawaJazdy());
                if(communication.generateInvoice(chosenSamochod, dane_klienta, ilosc_dni, numer_p_jazdy,start)) {
                    pokazKomunikat("Pdf zostal wygenerowany poprawnie");
                    pokazKomunikat("Platnosc zostala pobrana");
                }
                }
            else{
                pokazBlad("Nie udalo sie pobrac płatności");
            }
        }
    }
    private void pokazKomunikat(String wiadomosc) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informacja");
        alert.setHeaderText(null);
        alert.setContentText(wiadomosc);
        alert.showAndWait();
    }

    private void pokazBlad(String wiadomosc) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Błąd");
        alert.setHeaderText(null);
        alert.setContentText(wiadomosc);
        alert.showAndWait();
    }

    private FormData validateForm() {
        // Walidacja formularza
        boolean isValid = formValidator.validateForm(
                new TextField[]{imieTextField, nazwiskoTextField, iloscDniTextField, nrPrawaTextField,
                        dataPrawaTextField,nrKartyTextField,dataWaznosciTextField},
                // Pola tekstowe
                new ToggleGroup[]{dataGroup, klasaGroup, dokumentGroup,silnikGroup} // Grupy RadioButtonów
        );

        if (!isValid) {
            return null;
        }

        // Pobranie danych z formularza
        FormData formData = collectFormData();

        if (!formData.getdataPrawaJazdy().matches("\\d{7}")) {
            return null ;      }
        if(!formData.getNrPrawaJazdy().matches("\\d{11}")){
            return null ;
        }
        if(!formData.getNrKarty().matches("\\d{12}")){
            return null ;
        }
        if(!formData.getDataWaznosci().matches("\\d{4}")){
            return null ;
        }
        if(!formData.getCvc().matches("\\d{3}")){
            return null ;
        }

        // Wyświetlenie danych w konsoli (lub przekazanie do faktury)
        System.out.println("Zebrane dane:");
        System.out.println(formData);

        return formData;
    }

    private FormData collectFormData() {
        // Pobierz dane z TextField
        String imie = imieTextField.getText();
        String nazwisko = nazwiskoTextField.getText();
        Integer iloscDni = Integer.valueOf(iloscDniTextField.getText());
        String nrPrawa = nrPrawaTextField.getText();
        String dataPrawa = dataPrawaTextField.getText();
        String nrKarty = nrKartyTextField.getText();
        String dataWaznosci = dataWaznosciTextField.getText();
        String cvc = cvcTextField.getText();


        String start = ((RadioButton)dataGroup.getSelectedToggle()).getText();
        int startint;
        if(Objects.equals(start,"JUTRO")){startint = 1;}
        else {
            startint = 0;
        }

        // Pobierz wartość wybraną w grupie RadioBox (klasa samochodu)
        String klasaAuta = ((RadioButton) klasaGroup.getSelectedToggle()).getText();
        if (Objects.equals(klasaAuta, "KLASA A"))
            klasaAuta = "A";
        else if (Objects.equals(klasaAuta, "KLASA B"))
            klasaAuta = "B";
        else if (Objects.equals(klasaAuta, "KLASA C"))
            klasaAuta = "C";
        else if (Objects.equals(klasaAuta, "KLASA M"))
            klasaAuta = "M";
        else if (Objects.equals(klasaAuta, "KLASA R"))
            klasaAuta = "R";

        // Pobierz wartość wybraną w grupie RadioBox (rodzaj paliwa)
        String silnikType = ((RadioButton) silnikGroup.getSelectedToggle()).getText();
        if (Objects.equals(silnikType, "SKRZYNIA MANUALNA"))
            silnikType = "Manual";
        else if (Objects.equals(silnikType, "SKRZYNIA AUTOMATYCZNA"))
            silnikType = "Automatic";

        //rodzaj dowodu
        String dokumentType = ((RadioButton) dokumentGroup.getSelectedToggle()).getText();
        if (dokumentType == "DOWÓD OSOBISTY")
            dokumentType = "id";
        else if (dokumentType == "PASZPOSRT")
            dokumentType = "passport";





        // Zwróć dane w postaci obiektu
        return new FormData(imie, nazwisko, iloscDni, nrPrawa, dataPrawa, klasaAuta, silnikType, dokumentType,nrKarty,dataWaznosci,cvc,startint);
    }


}




