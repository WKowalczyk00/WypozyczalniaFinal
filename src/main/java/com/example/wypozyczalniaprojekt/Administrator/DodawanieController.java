package com.example.wypozyczalniaprojekt.Administrator;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class DodawanieController {

    @FXML
    private RadioButton aRadioButton;
    @FXML
    private RadioButton bRadioButton;
    @FXML
    private RadioButton cRadioButton;
    @FXML
    private RadioButton mRadioButton;
    @FXML
    private RadioButton rRadioButton;

    @FXML
    private RadioButton manualnaRadioButton;
    @FXML
    private RadioButton automatycznaRadioButton;

    @FXML
    private RadioButton dostepneRadioButton;
    @FXML
    private RadioButton nieDostepneRadioButton;

    @FXML
    private TextField modelAutaTextField;
    @FXML
    private TextField nrRejestracyjnyTextField;
    @FXML
    private TextField liczbaSiedzenTextField;

    @FXML
    private Button dodajButton;
    @FXML
    private Button anulujButton;



    public void initialize() {

        ToggleGroup klasaGroup = new ToggleGroup();
        aRadioButton.setToggleGroup(klasaGroup);
        bRadioButton.setToggleGroup(klasaGroup);
        cRadioButton.setToggleGroup(klasaGroup);
        mRadioButton.setToggleGroup(klasaGroup);
        rRadioButton.setToggleGroup(klasaGroup);

        ToggleGroup skrzyniaGroup = new ToggleGroup();
        manualnaRadioButton.setToggleGroup(skrzyniaGroup);
        automatycznaRadioButton.setToggleGroup(skrzyniaGroup);

        ToggleGroup dostepneGroup = new ToggleGroup();
        dostepneRadioButton.setToggleGroup(dostepneGroup);
        nieDostepneRadioButton.setToggleGroup(dostepneGroup);
    }
}
