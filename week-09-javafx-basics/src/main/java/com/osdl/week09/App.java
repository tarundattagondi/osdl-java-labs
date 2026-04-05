package com.osdl.week09;

import com.osdl.week09.view.HotelView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main launcher for the Week 9 Hotel Management GUI.
 * Opens the HotelView with seed data on startup.
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        HotelView hotelView = new HotelView();
        Scene scene = new Scene(hotelView.getRoot(), 900, 650);
        stage.setTitle("Hotel Management System — Week 9");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
