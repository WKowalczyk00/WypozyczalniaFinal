package Wypozyczalnia;

public class Cennik {
    private String model;
    private double price;

    public Cennik(String model, double price) {
        this.model = model;
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("Model: %s, Cena: %.2f", model, price);
    }

    public String getModel() {
        return model;
    }

    public double getPrice() {
        return price;
    }
}
