package com.osdl.hotel.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/** Convenience methods for JavaFX Alert dialogs. */
public class AlertHelper {

    public static void info(String title, String message) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        a.setTitle(title);
        a.setHeaderText(null);
        a.showAndWait();
    }

    public static void error(String message) {
        Alert a = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        a.setTitle("Error");
        a.setHeaderText(null);
        a.showAndWait();
    }

    /** Returns true if the user clicks OK. */
    public static boolean confirm(String title, String message) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.OK, ButtonType.CANCEL);
        a.setTitle(title);
        a.setHeaderText(null);
        Optional<ButtonType> result = a.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
}
