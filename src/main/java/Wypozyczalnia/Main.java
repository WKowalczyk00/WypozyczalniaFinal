package Wypozyczalnia;

import com.itextpdf.io.IOException;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static Wypozyczalnia.CarRentalDatabase.*;
import static Wypozyczalnia.PdfGenerator.generateCarListPdf;

public class Main {
    public Main(){}
    private final Connection connection = openDB();
    private final List<Car> cars = getCarsFromDatabase(connection);
    private Car chosenSamochod = null;

    public Car wybierzSamochod(String klasa, String skrzynia){

        for (Car car : cars) {
            if(car.isAvaible()&&car.getCarClass().equals(klasa)&&car.getTransmission().equals(skrzynia)){
                System.out.println("Znaleziono samochód");

                car.changeAvailability();
                //dopiero po generowaniu invoicea
                chosenSamochod = car;
                return car;
            }
        }
        throw new WybierzSamochodException("Nie znaleziono samochodu, wybierz inny rodzaj samochodu");
    }
    public void generateInvoice(Car samochod, String dane_klienta, int ilosc_dni,String np_jazdy){
        try {
            String formattedIloscDni = getPeriod(ilosc_dni);
            generateCarListPdf("C:\\Users\\wojci\\Desktop\\studia\\3-semestr\\po\\baza_z_pdf\\x.pdf", samochod, dane_klienta,formattedIloscDni,chosenSamochod.getRentalPrice()*ilosc_dni, np_jazdy);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            updateCarAvailability(connection,chosenSamochod.getId(),chosenSamochod.isAvaible());
        }
    }
    public boolean payment(){
        System.out.println("Przetwarzanie płatności...");
        return true;
    }
    private String getPeriod(int il_dni){
        LocalDate today = LocalDate.now();
        // Utworzenie formatera do formatu dd-MM-rrrr
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        // Formatowanie daty
        String formattedDate1 = today.format(formatter);

        // Obliczenie następnego dnia
        LocalDate nextDay = today.plusDays(il_dni);

        String formattedDate2 = nextDay.format(formatter);

        return "From "+ formattedDate1 + " to " + formattedDate2;
    }
    public List<Car> getCars(){
        return cars;
    }
}
