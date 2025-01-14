package com.example.wypozyczalniaprojekt;

import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class formValidator {
        /**
         * Sprawdza, czy wszystkie TextFieldy są wypełnione oraz czy w każdej grupie RadioButton wybrano jedną opcję.
         *
         * @param textFields tablica TextFieldów do sprawdzenia
         * @param toggleGroups tablica grup ToggleGroup dla RadioButtonów
         * @return true, jeśli wszystkie warunki są spełnione; false w przeciwnym razie
         */
        public static boolean validateForm(TextField[] textFields, ToggleGroup[] toggleGroups) {
            // Sprawdzanie, czy każde pole TextField jest wypełnione
            for (TextField textField : textFields) {
                if (textField.getText() == null || textField.getText().trim().isEmpty()) {
                    return false; // Puste pole tekstowe
                }
            }

            // Sprawdzanie, czy w każdej grupie RadioButton wybrano jedną opcję
            for (ToggleGroup group : toggleGroups) {
                if (group.getSelectedToggle() == null) {
                    return false; // Brak wybranego RadioButtona w grupie
                }
            }

            // Wszystkie warunki spełnione
            return true;
        }


}

