package com.osdl.week09.demos;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Sample Program 2: TextField and Label
 * Reads user input from a TextField and displays a greeting in a Label.
 */
public class TextFieldLabelDemo extends Application {

    @Override
    public void start(Stage stage) {
        Label lblMessage = new Label("Enter your name:");
        TextField txtName = new TextField();
        Button btnShow = new Button("Display");
        Label lblResult = new Label();

        btnShow.setOnAction(e -> {
            String name = txtName.getText();
            lblResult.setText("Hello, " + name);
        });

        VBox root = new VBox(10);
        root.getChildren().addAll(lblMessage, txtName, btnShow, lblResult);

        Scene scene = new Scene(root, 300, 200);
        stage.setTitle("TextField and Label Example");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
