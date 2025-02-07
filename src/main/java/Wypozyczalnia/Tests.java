package Wypozyczalnia;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.util.List;

public class Tests {
    public static void main(String[] args){
        Main main = new Main();
        Car chosenSamochod = main.wybierzSamochod("M","Manual");
        int ilosc_dni = 3;
        String dane_klienta = "Fredi Krzemionka";
        String numer_p_jazdy = String.valueOf(293021930);
        main.generateInvoice(chosenSamochod,dane_klienta,ilosc_dni,numer_p_jazdy,1);
//        System.out.println(main.getCars().getLast().getModel());
    }

    private Main operacje = new Main();
    public void poKliku(ActionEvent actionEvent) {
        Car chosenSamochod = null;
        try {
            chosenSamochod = operacje.wybierzSamochod("C", "Mau");
        } catch (WybierzSamochodException e) {
            // Obsługa wyjątku
            System.out.println("Przechwycono wyjątek: " + e.getMessage());
            pokazBlad(e.getMessage());
        } finally {
            if(operacje.payment()){
                int ilosc_dni = 3;
                String dane_klienta = "Fredi Krzemionka";
                String numer_p_jazdy = String.valueOf(293021930);
                operacje.generateInvoice(chosenSamochod, dane_klienta, ilosc_dni, numer_p_jazdy,1);
            }
            else{
                pokazBlad("Nie udalo sie odebrać płatności");
            }
        }

    }
    public void pokazBlad(String wiadomosc) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Błąd");
        alert.setHeaderText(null);
        alert.setContentText(wiadomosc);
        alert.showAndWait();
    }
}
