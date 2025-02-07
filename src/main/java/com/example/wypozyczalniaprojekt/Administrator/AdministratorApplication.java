package com.example.wypozyczalniaprojekt.Administrator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AdministratorApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AdministratorApplication.class.getResource("admin_main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 900);
        stage.setTitle("Strona dla administratora");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}