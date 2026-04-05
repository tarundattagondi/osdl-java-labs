package com.osdl.week09.demos;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Sample Program 4: Simple Form
 * A GridPane form with Name and Email fields, submits on button click.
 */
public class SimpleFormDemo extends Application {

    @Override
    public void start(Stage stage) {
        Label lblName = new Label("Name:");
        Label lblEmail = new Label("Email:");
        Label lblResult = new Label();

        TextField txtName = new TextField();
        TextField txtEmail = new TextField();

        Button btnSubmit = new Button("Submit");

        btnSubmit.setOnAction(e -> {
            String name = txtName.getText();
            String email = txtEmail.getText();
            lblResult.setText("Submitted:\nName: " + name + "\nEmail: " + email);
        });

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(lblName, 0, 0);
        grid.add(txtName, 1, 0);
        grid.add(lblEmail, 0, 1);
        grid.add(txtEmail, 1, 1);
        grid.add(btnSubmit, 1, 2);
        grid.add(lblResult, 1, 3);

        Scene scene = new Scene(grid, 350, 250);
        stage.setTitle("Simple Form Application");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
