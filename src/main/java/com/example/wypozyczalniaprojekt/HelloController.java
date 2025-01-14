package com.example.wypozyczalniaprojekt;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;


public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    private RadioButton dzisRadioButton;
    private RadioButton jutroRadioButton;

    private RadioButton aRadioButton;
    private RadioButton bRadioButton;
    private RadioButton cRadioButton;
    private RadioButton mRadioButton;
    private RadioButton rRadioButton;

    private RadioButton manulanyRadioButton;
    private RadioButton automatycznyRadioButton;

    private RadioButton paszportRadioButton;
    private RadioButton dowodRadioButton


    private ToggleGroup dataGroup;
    private ToggleGroup klasaGroup;
    private ToggleGroup silnikGroup;
    private ToggleGroup dokumentGroup;

    @FXML
    public void initialize() {
        // Inicjalizacja ToggleGroup i przypisanie do RadioButtonów
        dataGroup = new ToggleGroup();
        dzisRadioButton.setToggleGroup(dataGroup);
        jutroRadioButton.setToggleGroup(dataGroup);

        klasaGroup = new ToggleGroup();
        aRadioButton.setToggleGroup(klasaGroup);
        bRadioButton.setToggleGroup(klasaGroup);
        cRadioButton.setToggleGroup(klasaGroup);
        mRadioButton.setToggleGroup(klasaGroup);
        rRadioButton.setToggleGroup(klasaGroup);

        silnikGroup = new ToggleGroup();
        manulanyRadioButton.setToggleGroup(silnikGroup);
        automatycznyRadioButton.setToggleGroup(silnikGroup);

        dokumentGroup = new ToggleGroup();
        paszportRadioButton.setToggleGroup(dokumentGroup);
        dowodRadioButton.setToggleGroup(dokumentGroup);




}