package Wypozyczalnia;

import com.itextpdf.io.IOException;

import java.sql.Connection;
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
    public void generateInvoice(Car samochod){
        try {
            generateCarListPdf("C:\\Users\\wojci\\Desktop\\studia\\3-semestr\\po\\baza_z_pdf\\x.pdf", samochod);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            updateCarAvailability(connection,chosenSamochod.getId(),chosenSamochod.isAvaible());
        }
    }
    public List<Car> getCars(){
        return cars;
    }
}
