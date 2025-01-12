package Wypozyczalnia;

import java.sql.Connection;
import java.util.List;

import static Wypozyczalnia.CarRentalDatabase.getCarsFromDatabase;
import static Wypozyczalnia.CarRentalDatabase.openDB;
import static Wypozyczalnia.PdfGenerator.generateCarListPdf;

public class Main {
    public Main(){}
    private final Connection connection = openDB();

    private final List<Car> cars = getCarsFromDatabase(connection);

    private Car wybierzSamochod(String klasa, String skrzynia){
        Car wybranySamochod = null;
        for (Car car : cars) {
            if(car.isAvaible()&&car.getCarClass().equals(klasa)&&car.getCarClass().equals(skrzynia)){
                System.out.println("Znaleziono samochód");
                wybranySamochod = car;
                return wybranySamochod;
            }
        }
        throw new WybierzSamochodException("Nie znaleziono samochodu, wybierz inny rodzaj samochodu");
    }
    public void generateInvoice(){
        generateCarListPdf("C:\\Users\\wojci\\Desktop\\studia\\3-semestr\\po\\baza_z_pdf\\x.pdf",cars);
    }
    public List<Car> getCars(){
        return cars;
    }
}
