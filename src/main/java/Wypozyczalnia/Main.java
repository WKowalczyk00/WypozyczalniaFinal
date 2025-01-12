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

    public Car wybierzSamochod(String klasa, String skrzynia){

        for (Car car : cars) {
            if(car.isAvaible()&&car.getCarClass().equals(klasa)&&car.getTransmission().equals(skrzynia)){
                System.out.println("Znaleziono samochód");

                car.changeAvailability();
                return car;
            }
        }
        throw new WybierzSamochodException("Nie znaleziono samochodu, wybierz inny rodzaj samochodu");
    }
    public void generateInvoice(Car samochod){
        generateCarListPdf("C:\\Users\\wojci\\Desktop\\studia\\3-semestr\\po\\baza_z_pdf\\x.pdf",samochod);
    }
    public List<Car> getCars(){
        return cars;
    }
}
