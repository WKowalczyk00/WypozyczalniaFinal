package Wypozyczalnia;

import com.itextpdf.io.IOException;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

import static Wypozyczalnia.CarRentalDatabase.*;
import static Wypozyczalnia.PdfGenerator.generateCarListPdf;

public class Main {
    public Main(){}
    private final static Connection connection = openDB();
    private final static List<Car> cars = getCarsFromDatabase(connection);
    private final List<Cennik> cennikList = readCennikFromDatabase(connection);
    private Car chosenSamochod = null;

    public static Connection getConnection() {
        return connection;
    }
    public static List<Car> getCars(){
        return cars;
    }
    public static Car findCarByRegistrationNumber(String registrationNumber){
        for (Car car : cars) {
            if(car.getRegistrationNumber().equals(registrationNumber)){
                return car;
            }
        }
        return null;
    }
    public static void changeCarInList(Car car) {
        for (int i = 0; i < cars.size(); i++) {
            if (cars.get(i).getRegistrationNumber().equals(car.getRegistrationNumber())) {
                cars.set(i, car);
                return;
            }
        }
    }

    public static void addCar (Car car){
        cars.add(car);
    }
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
    public List<Cennik> getCennikList() {
        return cennikList;
    }


    public boolean generateInvoice(Car samochod, String dane_klienta, int ilosc_dni, String np_jazdy,int start){
        try {
            // Pobranie aktualnej daty
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, start); // Ustawienie dnia startowego

            // Formatowanie okresu wynajmu
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String startDate = dateFormat.format(calendar.getTime());

            calendar.add(Calendar.DAY_OF_YEAR, ilosc_dni); // Dodanie ilości dni
            String endDate = dateFormat.format(calendar.getTime());

            // Formatowany okres wynajmu
            String formattedIloscDni = startDate + " do " + endDate;
            double pricePerDay = 0.0;
            switch (samochod.getCarClass()) {
                case "A":
                    pricePerDay = this.getCennikList().get(0).getPrice();
                    break;
                case "B":
                    pricePerDay = this.getCennikList().get(1).getPrice();
                    break;
                case "C":
                    pricePerDay = this.getCennikList().get(2).getPrice();
                    break;
                case "M":
                    pricePerDay = this.getCennikList().get(3).getPrice();
                    break;
                case "R":
                    pricePerDay = this.getCennikList().get(4).getPrice();
                    break;
                default:
                    System.out.println("blad");
                    return false;
            }
            generateCarListPdf("C:\\Users\\wojci\\Desktop\\faktury\\", samochod, dane_klienta,formattedIloscDni,pricePerDay*ilosc_dni, np_jazdy,start);
            return true;
        }
        catch (IOException e){
            e.printStackTrace();
            return false;
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
//    public List<Car> getCars(){
//        return cars;
//    }
}
