package com.lemon;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    // Start of the program's GUI window
    @Override
    public void start(Stage primaryStage) {
        EventHandler<ActionEvent> createButtonEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                createButtonPressed(primaryStage); // Links button press to method
            }
        };
        
        EventHandler<ActionEvent> openButtonEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                System.out.println("open button pressed"); // to be implemplemented
            }
        };
        
        Button createButton = new Button("Create New Config");
        Button openButton = new Button("Open Existing Config");

        createButton.setOnAction(createButtonEvent);
        openButton.setOnAction(openButtonEvent);

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(createButton, openButton);

        Scene sc = new Scene(vbox, 200, 200);

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

        Label os = new Label("OS Name:");
        TextField osTextField = new TextField();
        HBox hbOs = new HBox();
        hbOs.getChildren().addAll(os, osTextField);
        hbOs.setSpacing(10);

        Label user = new Label("Signed-in User:");
        TextField userTextField = new TextField();
        HBox hbUser = new HBox();
        hbUser.getChildren().addAll(user, userTextField);
        hbUser.setSpacing(10);   
        
        Label remote = new Label("Remote:");
        TextField remoteTextField = new TextField();
        HBox hbRemote = new HBox();
        hbRemote.getChildren().addAll(remote, remoteTextField);
        hbRemote.setSpacing(10);  

        Label password = new Label("Remote Password");
        TextField passwordTextField = new TextField();
        HBox hbPassword = new HBox();
        hbPassword.getChildren().addAll(password, passwordTextField);
        hbPassword.setSpacing(10); 

        Button doneButton = new Button("Done");
        EventHandler<ActionEvent> doneButtonEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                // Grabs all the text from the boxes and turns into String variables
                String title = imageTextField.getText();
                String os = osTextField.getText();
                String user = userTextField.getText();
                String remote = remoteTextField.getText();
                String password = passwordTextField.getText();

                // Check for if boxes are empty
                if (title.equals("") || os.equals("") || user.equals("") || remote.equals("") || password.equals("")) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Input Error");
                    alert.setHeaderText(null);
                    alert.setContentText("All fields must be filled out!");
                    alert.showAndWait();
                    return;
                }

                // Instanstiates a new Lemon object
                Lemon config = new Lemon(title, os, user, remote, password);

                // Instanstiates a new Checks object with Lemon object and stage as params
                Checks checkObj = new Checks(config, stage);
                
                checkObj.handle();

            }
        };

        doneButton.setOnAction(doneButtonEvent);

        VBox vbox = new VBox(15);
        //displays text boxes
        vbox.getChildren().addAll(hbTitle, hbOs, hbUser, hbRemote, hbPassword, doneButton);

        Scene createWindow = new Scene(vbox, 300, 300);
        stage.setScene(createWindow);

    }
    public static void main(String[] args) {
        launch(args);
    }
}