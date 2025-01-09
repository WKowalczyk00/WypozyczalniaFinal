package Wypozyczalnia;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//trzeba dodac paczke jdbc https://learn.microsoft.com/pl-pl/sql/connect/jdbc/download-microsoft-jdbc-driver-for-sql-server?view=sql-server-ver16
//ctrl alt shift s -> modules -> + w dependencies i dodajemy
public class CarRentalDatabase {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/car_rental";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "zaq1@WSX";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            System.out.println("Connected to the database!");

            // Pobierz dane z tabeli Cars
            String query = "SELECT * FROM Cars";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("Lista samochodów:");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String model = resultSet.getString("model");
                String transmission = resultSet.getString("transmission");
                String registrationNumber = resultSet.getString("registration_number");
                int seatCount = resultSet.getInt("seat_count");
                double rentalPrice = resultSet.getDouble("rental_price");
                boolean availability = resultSet.getBoolean("availability");

                System.out.printf("ID: %d, Model: %s, Skrzynia: %s, Rejestracja: %s, Liczba siedzeń: %d, Cena: %.2f PLN, Dostępność: %s%n",
                        id, model, transmission, registrationNumber, seatCount, rentalPrice, availability ? "Dostępny" : "Niedostępny");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
