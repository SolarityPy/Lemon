package com.lemon;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Checks {
    private Lemon lemonObj;
    private Stage stage;

    public Checks(Lemon lemonObj, Stage stage) {
        this.lemonObj = lemonObj;
        this.stage = stage;
    }

    public void handle() {
        Button add = new Button("Add Vulnerability");
        VBox root = new VBox(add);
        Scene addVulnerability = new Scene(root, 500, 500);

        EventHandler<ActionEvent> doneButtonEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                
            }
        };

        add.setOnAction(doneButtonEvent);
        stage.setScene(addVulnerability);



    }


    
}
