package com.lemon;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        Button createButton = new Button("Create New Config");
        Button openButton = new Button("Open Existing Config");

        VBox vbox = new VBox(10); // 10px spacing between buttons
        vbox.getChildren().addAll(createButton, openButton);

        Scene sc = new Scene(vbox, 200, 200);

        primaryStage.setScene(sc);
        primaryStage.setTitle("Lemon");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}