package Wypozyczalnia;

public class Car {
    private int id;
    private String model;
    private String carClass;
    private String transmission;
    private String registrationNumber;
    private int seatCount;
    private double rentalPrice;
    private boolean availability;

    public Car(int id, String model, String carClass, String transmission, String registrationNumber, int seatCount, boolean availability) {
        this.id = id;
        this.model = model;
        this.carClass = carClass;
        this.transmission = transmission;
        this.registrationNumber = registrationNumber;
        this.seatCount = seatCount;
        this.availability = availability;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Model: %s, Klasa: %s, Skrzynia: %s, Rejestracja: %s, Liczba siedzeń: %d, Dostępność: %s",
                id, model,carClass, transmission, registrationNumber, seatCount, availability ? "Dostępny" : "Niedostępny");
    }

    public int getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public String getTransmission() {
        return transmission;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public double getRentalPrice() {
        return rentalPrice;
    }

    public boolean isAvaible() {
        return availability;
    }

    public String getCarClass() {
        return carClass;
    }


    public void changeAvailability() {
        this.availability = !this.availability;
    }
}