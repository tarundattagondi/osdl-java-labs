package com.osdl.week09.demos;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Sample Program 3: Button Event Handling
 * Updates a label when a button is clicked.
 */
public class ButtonEventDemo extends Application {

    @Override
    public void start(Stage stage) {
        Button btnClick = new Button("Click Here");
        Label lblMessage = new Label("Waiting for button click...");

        btnClick.setOnAction(e -> {
            lblMessage.setText("Button Clicked!");
        });

        VBox root = new VBox(15);
        root.getChildren().addAll(btnClick, lblMessage);

        Scene scene = new Scene(root, 300, 200);
        stage.setTitle("Button Click Event Handling");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
