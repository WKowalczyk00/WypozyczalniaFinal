package Wypozyczalnia.Administrator;

import Wypozyczalnia.Car;
import Wypozyczalnia.CarRentalDatabase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static Wypozyczalnia.CarRentalDatabase.*;
import static Wypozyczalnia.Main.*;

public class AdministratorManager {

    public List<Car> showCars() {
        return getCars();
    }
    public List<String> carInformation() {
        List<String> carInfo = new ArrayList<String>();
        for (Car car : getCars()) {
            carInfo.add(car.toString());
        }

        return carInfo;
    }
    public boolean addNewCar(Car car) {
        if(!CarRentalDatabase.addNewCar(getConnection(),car)){
            return false;
        }
        Integer newId = getCarIdByRegistrationNumber(getConnection(),car.getRegistrationNumber());
        if (newId == null) {
            return false;
        }
        car = new Car(newId, car.getModel(), car.getCarClass(), car.getTransmission(), car.getRegistrationNumber(), car.getSeatCount(),car.isAvaible());

        return getCars().add(car);
    }
    private boolean zapiszPlikNaDysku(File plikWejsciowy, String sciezkaDocelowa) {

        File plikWyjsciowy = new File(sciezkaDocelowa);

        try (FileInputStream in = new FileInputStream(plikWejsciowy);
             FileOutputStream out = new FileOutputStream(plikWyjsciowy)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }

            System.out.println("Plik zapisano pomyślnie w: " + sciezkaDocelowa);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Błąd podczas zapisywania pliku.");
            return false;
        }
        return true;
    }
    private String getFileExtension(File file) {
        String fileName = file.getName();
        int lastIndex = fileName.lastIndexOf(".");
        if (lastIndex != -1 && lastIndex < fileName.length() - 1) {
            return "."+fileName.substring(lastIndex + 1);
        }
        return ""; // Brak rozszerzenia
    }
    public static String getFormattedDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm");
        return LocalDateTime.now().format(formatter);
    }

    public byte returnCar(String numer_rej, File zdjecie) {


        Car chosenSamochod = findCarByRegistrationNumber(numer_rej);

        if (chosenSamochod == null) {
            return 11;
        }

        if (chosenSamochod.isAvaible()) {
            return 12;
        }

        if(!zapiszPlikNaDysku(zdjecie,"C:\\Users\\wojci\\Desktop\\zwroty\\"+numer_rej+"__"+getFormattedDateTime()+getFileExtension(zdjecie))){
            return 10;
        }

        if(!CarRentalDatabase.updateCarAvailabilityByRegNumber(getConnection(),numer_rej,true)){
            return 13;
        }


        changeCarInList(chosenSamochod);
        return 1;
    }
    public boolean deleteCar(int id){
        if(!deleteCarById(getConnection(),id)){
            return false;
        }
        return true;
    }
}
