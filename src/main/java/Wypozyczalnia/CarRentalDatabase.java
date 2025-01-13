package Wypozyczalnia;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//trzeba dodac paczke jdbc https://learn.microsoft.com/pl-pl/sql/connect/jdbc/download-microsoft-jdbc-driver-for-sql-server?view=sql-server-ver16
//ctrl alt shift s -> modules -> + w dependencies i dodajemy
public class CarRentalDatabase {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/car_rental";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "zaq1@WSX";


    public static Connection openDB() {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Connected to the database!");
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Car> getCarsFromDatabase(Connection connection) {
        List<Car> carList = new ArrayList<>();
        String query = "SELECT * FROM Cars";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String model = resultSet.getString("model");
                String carClass = resultSet.getString("class");
                String transmission = resultSet.getString("transmission");
                String registrationNumber = resultSet.getString("registration_number");
                int seatCount = resultSet.getInt("seat_count");
                double rentalPrice = resultSet.getDouble("rental_price");
                boolean availability = resultSet.getBoolean("availability");

                Car car = new Car(id, model,carClass, transmission, registrationNumber, seatCount, rentalPrice, availability);
                carList.add(car);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return carList;
    }
    public static void updateCarAvailability(Connection connection, int id, boolean newAvailability) {
        String updateQuery = "UPDATE Cars SET availability = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setBoolean(1, newAvailability);
            statement.setInt(2, id);

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Dostępność samochodu o ID: " + id + " została pomyślnie zaktualizowana.");
            } else {
                System.out.println("Nie znaleziono samochodu o podanym ID: " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addNewCar(Connection connection) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Dodaj nowy samochód do bazy danych:");

            System.out.print("Podaj model: ");
            String model = scanner.nextLine();

            System.out.print("Podaj klase: ");
            String carClass = scanner.nextLine();

            System.out.print("Podaj rodzaj skrzyni biegów: ");
            String transmission = scanner.nextLine();

            System.out.print("Podaj numer rejestracyjny: ");
            String registrationNumber = scanner.nextLine();

            System.out.print("Podaj liczbę miejsc: ");
            int seatCount = scanner.nextInt();

            System.out.print("Podaj cenę wynajmu: ");
            double rentalPrice = scanner.nextDouble();

            System.out.print("Czy samochód jest dostępny (true/false): ");
            boolean availability = scanner.nextBoolean();

            String insertQuery = "INSERT INTO Cars (model, transmission,class, registration_number, seat_count, rental_price, availability) VALUES (?, ?, ?, ?, ?, ?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, model);
            preparedStatement.setString(2, transmission);
            preparedStatement.setString(3, carClass);
            preparedStatement.setString(4, registrationNumber);
            preparedStatement.setInt(5, seatCount);
            preparedStatement.setDouble(6, rentalPrice);
            preparedStatement.setBoolean(7, availability);

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Samochód został pomyślnie dodany do bazy danych.");
            } else {
                System.out.println("Wystąpił problem podczas dodawania samochodu.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
}}
