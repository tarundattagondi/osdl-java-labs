package com.osdl.hotel;

import com.osdl.hotel.dao.DatabaseManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main entry point for the Hotel Management System.
 * Initializes the SQLite database and launches the JavaFX GUI.
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Initialize database (creates hotel.db and seeds data on first run)
        DatabaseManager.getInstance();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 1000, 700);
        scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());

        stage.setTitle("Hotel Management System — OSDL Week 10");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
