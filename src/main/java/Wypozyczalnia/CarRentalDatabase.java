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
                boolean availability = resultSet.getBoolean("availability");

                Car car = new Car(id, model, carClass, transmission, registrationNumber, seatCount, availability);
                carList.add(car);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return carList;
    }
    public static List<Cennik> readCennikFromDatabase(Connection connection) {
        String query = "SELECT * FROM cennik";

        List<Cennik> cennikList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            System.out.println("Model | Cena");
            System.out.println("---------------");

            while (resultSet.next()) {
                String model = resultSet.getString("model");
                double cena = resultSet.getDouble("cena");
                cennikList.add(new Cennik(model, cena));

                System.out.println("  " + model + "    | " + cena);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return cennikList;
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
    public static Integer getCarIdByRegistrationNumber(Connection connection, String registrationNumber) {
        String query = "SELECT id FROM Cars WHERE registration_number = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, registrationNumber);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // Zwraca null, jeśli samochód nie został znaleziony
    }

    public static boolean updateCarAvailabilityByRegNumber(Connection connection, String registrationNumber, boolean newAvailability) {
        String updateQuery = "UPDATE Cars SET availability = ? WHERE registration_number = ?";

        try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setBoolean(1, newAvailability);
            statement.setString(2, registrationNumber);

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Dostępność samochodu o numerze rejestracyjnym: " + registrationNumber + " została pomyślnie zaktualizowana.");
                return true;
            } else {
                System.out.println("Nie znaleziono samochodu o podanym numerze rejestracyjnym: " + registrationNumber);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }




    public static boolean addNewCar(Connection connection,Car car) {
//        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Dodaj nowy samochód do bazy danych:");

            System.out.print("Podaj model: ");
            String model = car.getModel();


            System.out.print("Podaj klase: ");
            String carClass = car.getCarClass();

            System.out.print("Podaj rodzaj skrzyni biegów: ");
            String transmission = car.getTransmission();

            System.out.print("Podaj numer rejestracyjny: ");
            String registrationNumber = car.getRegistrationNumber();

            System.out.print("Podaj liczbę miejsc: ");
            int seatCount = car.getSeatCount();

            System.out.print("Czy samochód jest dostępny (true/false): ");
            boolean availability = car.isAvaible();

            // Sprawdzenie, czy samochód już istnieje
            String checkQuery = "SELECT COUNT(*) FROM Cars WHERE registration_number = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setString(1, registrationNumber);
            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) > 0) {
                System.out.println("Samochód z tym numerem rejestracyjnym już istnieje.");
                return false;
            }

            // Dodanie nowego samochodu
            String insertQuery = "INSERT INTO Cars (model, transmission, class, registration_number, seat_count, availability) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setString(1, model);
            insertStatement.setString(2, transmission);
            insertStatement.setString(3, carClass);
            insertStatement.setString(4, registrationNumber);
            insertStatement.setInt(5, seatCount);
            insertStatement.setBoolean(6, availability);

            int rowsInserted = insertStatement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Samochód został pomyślnie dodany do bazy danych.");
                return true;
            } else {
                System.out.println("Wystąpił problem podczas dodawania samochodu.");
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
//            scanner.close();
        }
    }
    public static boolean deleteCarById(Connection connection, int id) {
        String deleteQuery = "DELETE FROM Cars WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            statement.setInt(1, id);

            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Samochód o ID: " + id + " został usunięty.");
                return true;
            } else {
                System.out.println("Nie znaleziono samochodu o ID: " + id);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}