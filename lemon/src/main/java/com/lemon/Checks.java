package com.lemon;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.LinkedHashMap;

public class Checks {
    private Lemon lemonObj;
    private Stage stage;

    public Checks(Lemon lemonObj, Stage stage) {
        this.lemonObj = lemonObj;
        this.stage = stage;
    }

    public void handle() {
        Button addVuln = new Button("Add Vulnerability");
        VBox root = new VBox(addVuln);
        Scene addVulnerability = new Scene(root, 500, 500);

        EventHandler<ActionEvent> doneButtonEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                handleCheck();
            }
        };

        addVuln.setOnAction(doneButtonEvent);
        stage.setScene(addVulnerability);
    }

    public void handleCheck() {
        LinkedHashMap<String, String[]> checksMap = new LinkedHashMap<>();
        checksMap.put("CommandContains", new String[]{"cmd", "value"});
        checksMap.put("CommandOutput", new String[]{"cmd", "value"});
        checksMap.put("DirContains", new String[]{"path", "value"});
        checksMap.put("FileContains", new String[]{"path", "value"});
        checksMap.put("FileEquals", new String[]{"path", "value"});
        checksMap.put("FileOwner", new String[]{"path", "name"});
        checksMap.put("FirewallUp", new String[]{});
        checksMap.put("PathExists", new String[]{"path"});
        checksMap.put("ProgramInstalled", new String[]{"name"});
        checksMap.put("ProgramVersion", new String[]{"name", "value"});
        checksMap.put("ServiceUp", new String[]{"name"});
        checksMap.put("UserExists", new String[]{"name"});
        checksMap.put("UserInGroup", new String[]{"user", "group"});
        //I'm not sure if this implementation will work because people may name wrong but idk
        //also idk if we need the linux checks but like who cares (just gunna assume windows unless linux in title)
        if (lemonObj.getOs().contains("Linux") || lemonObj.getOs().contains("linux")) {
            checksMap.put("PasswordChanged", new String[]{"user", "value"});
            checksMap.put("PermissionIs", new String[]{"path", "value"});
            checksMap.put("AutoCheckUpdatesEnabled", new String[]{});
            checksMap.put("Command", new String[]{"cmd"});
            checksMap.put("GuestDisabledLDM", new String[]{});
            checksMap.put("KernelVersion", new String[]{"value"});
        } else {
            checksMap.put("PasswordChanged", new String[]{"user", "after"});
            checksMap.put("PermissionIs", new String[]{"path", "name", "value"});
            checksMap.put("BitlockerEnabled", new String[]{});
            checksMap.put("FirewallDefaultBehavior", new String[]{"name", "value", "key"});
            checksMap.put("GuestDisabledLDM", new String[]{"key", "value"});
            checksMap.put("RegistryKeyExists", new String[]{"key"});
            checksMap.put("ScheduledTaskExists", new String[]{"name"});
            checksMap.put("SecurityPolicy", new String[]{"key", "value"});
            checksMap.put("ServiceStartup", new String[]{"name", "value"});
            checksMap.put("ShareExists", new String[]{"name"});
            checksMap.put("UserDetail", new String[]{"user","key", "value"});
            checksMap.put("UserRights", new String[]{"name", "value"});
            checksMap.put("WindowsFeature", new String[]{"name"});
        }
        VBox allChecksBox = new VBox();
        addCheckPass(checksMap, allChecksBox);

        Button add = new Button("+");
        EventHandler<ActionEvent> addButtonEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                addCheckPass(checksMap, allChecksBox);
            }
        };
        add.setOnAction(addButtonEvent);

        VBox checkRoot = new VBox(allChecksBox, add);
        Scene sc = new Scene(checkRoot, 500, 500);
        stage.setScene(sc);
    }


    public void addCheckPass(LinkedHashMap<String, String[]> checksMap, VBox box) {
        String[] checkKinds = {"[[check.pass]]", "[[check.fail]]", "[[check.passoverride]]"};
        final ComboBox<String> checkKind = new ComboBox<>();
        for (String kind : checkKinds) {
            checkKind.getItems().add(kind);
        }
        final ComboBox<String> checkTypes = new ComboBox<>();
        for (String type : checksMap.keySet()) {
            checkTypes.getItems().add(type);
        }

        VBox paramsBox = new VBox();

        EventHandler<ActionEvent> dropTypesEvent = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e) {
            paramsBox.getChildren().clear();
            String[] params = checksMap.get(checkTypes.getValue());
            if (params != null) {
                for (String poramValue : params) {
                    Label poram = new Label(poramValue + ":");
                    TextField poramField = new TextField();
                    HBox hbporam = new HBox();
                    hbporam.getChildren().addAll(poram, poramField);
                    hbporam.setSpacing(10);
                    paramsBox.getChildren().add(hbporam);
                }
            }
        }
    };
    checkTypes.setOnAction(dropTypesEvent);
    VBox checkContainer = new VBox(checkKind, checkTypes, paramsBox);
    box.getChildren().add(checkContainer);
    }
}