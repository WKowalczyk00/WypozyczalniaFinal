package Wypozyczalnia.Administrator;

import Wypozyczalnia.Car;
import Wypozyczalnia.CarRentalDatabase;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static Wypozyczalnia.CarRentalDatabase.getCarsFromDatabase;
import static Wypozyczalnia.CarRentalDatabase.openDB;
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
        return getCars().add(car);
    }
    public boolean returnCar(String numer_rej) {
        if(!CarRentalDatabase.updateCarAvailabilityByRegNumber(getConnection(),numer_rej,true)){
            return false;
        }
        Car chosenSamochod = findCarByRegistrationNumber(numer_rej);
        if(chosenSamochod==null){
            return false;
        }
        changeCarInList(chosenSamochod);
        return true;
    }
}
