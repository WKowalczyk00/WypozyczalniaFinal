package com.example.wypozyczalniaprojekt.Administrator;

import Wypozyczalnia.Administrator.AdministratorManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.*;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class AdministratorController {

    @FXML
    private Button oddajButton;
    @FXML
    private Button dodajButton;

    @FXML
    private HBox bazaDanychHBox;

    AdministratorManager administratorManager = new AdministratorManager();

    @FXML
    public void initialize() {
        dodajButton.setOnAction(this::openDodawanieAuta);
        oddajButton.setOnAction(this::openOddawanieAuta);
        stworzBazeDanych();
    }

    private void stworzBazeDanych() {
        bazaDanychHBox.getChildren().clear();
        List<String> carInformation = administratorManager.carInformation();

        VBox vbox = new VBox(10); // VBox dla pionowego ułożenia, odstęp 10px
        vbox.setAlignment(Pos.CENTER); // Centrowanie VBox

        int i = 0;
        for (String info : carInformation) {
            HBox hbox = new HBox(20); // HBox dla pojedynczego wiersza, większy odstęp
            hbox.setAlignment(Pos.CENTER); // Centrowanie wiersza

            TextField textField = new TextField(info);
            textField.setPrefWidth(700); // Szerokość pola tekstowego
            HBox.setHgrow(textField, Priority.ALWAYS); // Rozciąganie pola

            int numer_ID = administratorManager.showCars().get(i).getId();
            String numer_rej = administratorManager.showCars().get(i).getRegistrationNumber();
            Button deleteButton = new Button("Usuń");
            deleteButton.setId("deleteButton" + numer_ID);
            deleteButton.setPrefWidth(100); // Szerokość przycisku

            deleteButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if(administratorManager.deleteCar(numer_ID)){
                        pokazKomunikat("Samochód o numerze rejestracyjnym: "+numer_rej+" zostal poprawnie usunięty z bazy");
                        vbox.getChildren().remove(hbox);
                    }


                }
            });



            hbox.getChildren().addAll(textField, deleteButton);
            vbox.getChildren().add(hbox);
            i++;
        }

        bazaDanychHBox.setAlignment(Pos.CENTER); // Centrowanie HBox
        bazaDanychHBox.getChildren().add(vbox);
    }



    private void pokazBlad(String wiadomosc) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Błąd");
        alert.setHeaderText(null);
        alert.setContentText(wiadomosc);
        alert.showAndWait();
    }
    private void pokazKomunikat(String wiadomosc) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informacja");
        alert.setHeaderText(null);
        alert.setContentText(wiadomosc);
        alert.showAndWait();
    }
    @FXML
    public void openDodawanieAuta(ActionEvent actionEvent) {
        try {
            initialize();
            // Ładowanie pliku FXML dla nowego okna
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dodawanie_auta.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            // Tworzenie nowego Stage (okna)
            Stage newWindow = new Stage();
            newWindow.setTitle("Dodawanie auta");
            newWindow.setScene(scene);

            newWindow.setOnHidden(event -> stworzBazeDanych());
            // Pokazanie nowego okna
            newWindow.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void openOddawanieAuta(ActionEvent actionEvent) {
        try {
            initialize();
            // Ładowanie pliku FXML dla nowego okna
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("oddanie_auta.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            // Tworzenie nowego Stage (okna)
            Stage newWindow = new Stage();
            newWindow.setTitle("Oddawanie Auta");
            newWindow.setScene(scene);

            newWindow.setOnHiding(event -> stworzBazeDanych());
            // Pokazanie nowego okna
            newWindow.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
