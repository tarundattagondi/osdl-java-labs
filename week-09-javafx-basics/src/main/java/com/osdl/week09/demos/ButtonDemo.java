package com.osdl.week09.demos;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Sample Program 1: JavaFX Button
 * Displays a single button in a StackPane layout.
 */
public class ButtonDemo extends Application {

    @Override
    public void start(Stage stage) {
        Button btn = new Button("Click Me");

        StackPane root = new StackPane();
        root.getChildren().add(btn);

        Scene scene = new Scene(root, 300, 200);
        stage.setTitle("JavaFX Button Example");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
