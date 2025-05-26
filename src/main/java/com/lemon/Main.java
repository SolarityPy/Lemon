package com.lemon;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
    // Start of the program's GUI window
    @Override
    public void start(Stage primaryStage) {
        EventHandler<ActionEvent> createButtonEvent = e -> createButtonPressed(primaryStage);
        EventHandler<ActionEvent> openButtonEvent = e -> {
            OpenCheck open = new OpenCheck();
            open.readConfig(primaryStage);
        };
        
        Text title = new Text("Welcome to Lemon!");
        Button createButton = new Button("Create New Config");
        Button openButton = new Button("Open Existing Config");

        createButton.setOnAction(createButtonEvent);
        openButton.setOnAction(openButtonEvent);

        VBox vbox = new VBox(20);
        vbox.getChildren().addAll(title, createButton, openButton);
        vbox.setAlignment(Pos.CENTER);
        StackPane root = new StackPane(vbox);
        StackPane.setAlignment(vbox, Pos.CENTER);

        Scene sc = new Scene(root, 200, 150);

        primaryStage.setScene(sc);
        primaryStage.setTitle("Lemon"); 
        primaryStage.show();
    }

    //adds text boxes that we need to create a Lemon object
    public void createButtonPressed(Stage stage) {
        Label title = new Label("Image Title:");
        TextField imageTextField = new TextField();
        HBox hbTitle = new HBox();
        hbTitle.getChildren().addAll(title, imageTextField);
        hbTitle.setSpacing(10);
        hbTitle.setAlignment(Pos.CENTER);
        StackPane rootTitle = new StackPane(hbTitle);
        StackPane.setAlignment(hbTitle, Pos.CENTER);

        Label os = new Label("OS Name:");
        TextField osTextField = new TextField();
        HBox hbOs = new HBox();
        hbOs.getChildren().addAll(os, osTextField);
        hbOs.setSpacing(10);
        hbOs.setAlignment(Pos.CENTER);
        StackPane rootOs = new StackPane(hbOs);
        StackPane.setAlignment(hbOs, Pos.CENTER);

        Label user = new Label("Signed-in User:");
        TextField userTextField = new TextField();
        HBox hbUser = new HBox();
        hbUser.getChildren().addAll(user, userTextField);
        hbUser.setSpacing(10);   
        hbUser.setAlignment(Pos.CENTER);
        StackPane rootUser = new StackPane(hbUser);
        StackPane.setAlignment(hbUser, Pos.CENTER);
        
        Label remote = new Label("Remote:");
        TextField remoteTextField = new TextField();
        HBox hbRemote = new HBox();
        hbRemote.getChildren().addAll(remote, remoteTextField);
        hbRemote.setSpacing(10);  
        hbRemote.setAlignment(Pos.CENTER);
        StackPane rootRemote = new StackPane(hbRemote);
        StackPane.setAlignment(hbRemote, Pos.CENTER);

        Label password = new Label("Remote Password");
        TextField passwordTextField = new TextField();
        HBox hbPassword = new HBox();
        hbPassword.getChildren().addAll(password, passwordTextField);
        hbPassword.setSpacing(10); 
        hbPassword.setAlignment(Pos.CENTER);
        StackPane rootPassword = new StackPane(hbPassword);
        StackPane.setAlignment(hbPassword, Pos.CENTER);

        Button doneButton = new Button("Done");
        EventHandler<ActionEvent> doneButtonEvent = (ActionEvent e) -> {
            String title1 = imageTextField.getText();
            String os1 = osTextField.getText();
            String user1 = userTextField.getText();
            String remote1 = remoteTextField.getText();
            String password1 = passwordTextField.getText();
            // Check for if boxes are empty
            if (title1.equals("") || os1.equals("") || user1.equals("")) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setHeaderText(null);
                alert.setContentText("All fields must be filled out!");
                alert.showAndWait();
                return;
            }
            Lemon config;
            if (password1.equals("") || remote1.equals("")) {
                config = new Lemon(title1, os1, user1, false);
                
            } else {
                config = new Lemon(title1, os1, user1, remote1, password1, false);
            }
            Checks checkObj = new Checks(config, stage);
            checkObj.handle();
            // Instanstiates a new Checks object with Lemon object and stage as params
        };

        doneButton.setOnAction(doneButtonEvent);

        VBox vbox = new VBox(15);
        //displays text boxes
        Text text = new Text("Leave remote or password blank for local scoring");
        vbox.getChildren().addAll(rootTitle, rootOs, rootUser, text, rootRemote, rootPassword, doneButton);
        vbox.setAlignment(Pos.CENTER);
        StackPane root = new StackPane(vbox);
        StackPane.setAlignment(vbox, Pos.CENTER);

        Scene createWindow = new Scene(root, 300, 300);
        stage.setScene(createWindow);

    }
    public static void main(String[] args) {
        launch(args);
    }
}