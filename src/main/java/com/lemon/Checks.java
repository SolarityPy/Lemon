package com.lemon;

import java.rmi.NotBoundException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.LinkedHashMap;

import javafx.collections.*;

import java.util.ArrayList;
import java.util.Map;

public class Checks {
    private Lemon lemonObj;
    private Stage stage;

    private ComboBox<String> checkKind;
    private ComboBox<String> checkTypes;

    private ArrayList<Check> checks;

    public Checks(Lemon lemonObj, Stage stage) {
        this.lemonObj = lemonObj;
        this.stage = stage;
    }


    public void handle() {
        checks = new ArrayList<>();
        Button addVuln = new Button("Add Vulnerability");
        VBox root = new VBox(addVuln);
        Scene addVulnerability = new Scene(root, 500, 500);

        EventHandler<ActionEvent> addButtonEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                //On add vulnerability button pressed calls handleCheck method
                handleCheck();
            }
        };

        addVuln.setOnAction(addButtonEvent);
        stage.setScene(addVulnerability);
    }

    public void handleCheck() {
        LinkedHashMap<String, String[]> checksMap = new LinkedHashMap<>(); // Adds all the possible types of checks
        checksMap.put("CommandContains", new String[]{"cmd", "value"});// based off of OS
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
        String os = lemonObj.getOs().toLowerCase();

        if (!(os.contains("windows"))) { // Checks if current OS is Linux 
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
            checksMap.put("RegistryKeyExists", new String[]{"key"});
            checksMap.put("ScheduledTaskExists", new String[]{"name"});
            checksMap.put("SecurityPolicy", new String[]{"key", "value"});
            checksMap.put("ServiceStartup", new String[]{"name", "value"});
            checksMap.put("ShareExists", new String[]{"name"});
            checksMap.put("UserDetail", new String[]{"user","key", "value"});
            checksMap.put("UserRights", new String[]{"name", "value"});
            checksMap.put("WindowsFeature", new String[]{"name"});
        }
    
        String[] checkKinds = {"[[check.pass]]", "[[check.fail]]", "[[check.passoverride]]"};

        Label message = new Label("message:");
        TextField messageField = new TextField();
        HBox hbMessage = new HBox();
        hbMessage.getChildren().addAll(message, messageField);
        hbMessage.setSpacing(10);

        Label points = new Label("points:");
        TextField pointsField = new TextField();
        HBox hbPoints = new HBox();
        hbPoints.getChildren().addAll(points, pointsField);
        hbPoints.setSpacing(10);

        Button add = new Button("+");
        Button done = new Button("Done!");

        VBox child = new VBox();
        child.setSpacing(10);

        EventHandler<ActionEvent> doneButtonEvent = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e)
            {
                createChecks(messageField, pointsField);
                Checks.this.handle();
            }
        };

        done.setOnAction(doneButtonEvent);
        
        EventHandler<ActionEvent> addButtonEvent = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e)
            {
                addChunk(checkKinds, checksMap, child);
            }
        };

        add.setOnAction(addButtonEvent);
        done.setOnAction(doneButtonEvent);

        VBox parent = new VBox(done, hbMessage, hbPoints, child, add);
        parent.setSpacing(10);
        Scene sc = new Scene(parent, 500, 500);
        stage.setScene(sc);
    } 

    public void addChunk(String[] checkKinds, LinkedHashMap<String, String[]> checksMap, VBox parent) {
        final boolean[] addedAlready = {false};
        VBox childVbox = new VBox();
        VBox childParamsVBox = new VBox();
        final ComboBox kindList = new ComboBox(FXCollections.observableArrayList(checkKinds));
        final ComboBox<String> checkTypes = new ComboBox<>();

        for (String type : checksMap.keySet()) {
            checkTypes.getItems().add(type);
        }

        CheckBox notCheck = new CheckBox("Not");
        
        EventHandler<ActionEvent> dropTypesEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            childParamsVBox.getChildren().clear();
            String[] params = checksMap.get(checkTypes.getValue()); // fetches string array of args for the check type
            for (String paramValue : params) {
                Label param = new Label(paramValue + ":");
                TextField paramField = new TextField();
                HBox hbparam = new HBox();
                hbparam.getChildren().addAll(param, paramField);
                hbparam.setSpacing(10);
                childParamsVBox.getChildren().add(hbparam);
            }
            if (!addedAlready[0]) {
                Check checkObj = new Check(kindList, checkTypes, notCheck, childParamsVBox);
                checks.add(checkObj);
                addedAlready[0] = true;
            }
        }
    };
    checkTypes.setOnAction(dropTypesEvent);
    HBox notTypes = new HBox(checkTypes, notCheck);
    notTypes.setSpacing(5);
    VBox dropBoxes = new VBox(kindList, notTypes);
    childVbox.getChildren().addAll(dropBoxes, childParamsVBox);
    childVbox.setSpacing(7.5);
    parent.getChildren().add(childVbox); 
    }

    public void createChecks(TextField message, TextField points) {
        ArrayList<String> kindList = new ArrayList<String>();
        ArrayList<String> typeList = new ArrayList<String>();
        ArrayList<Map<String, String>> checkMap = new ArrayList<>();
        ArrayList<Boolean> notList = new ArrayList<>();
        for (Check objKindGetter : checks) {
            kindList.add(objKindGetter.kindBox.getValue());
        }

        for (Check objTypeGetter : checks) {
            typeList.add(objTypeGetter.typeBox.getValue());
        }

        for (Check objBoxGetter : checks) {
            ObservableList x = objBoxGetter.paramsBox.getChildren();
            Map<String, String> paramsMap = new LinkedHashMap<>();
            for (int i = 0; i < x.size(); i++) {
                HBox paramHBox = (HBox) x.get(i);
                Label label = (Label) paramHBox.getChildren().get(0);
                TextField text = (TextField) paramHBox.getChildren().get(1);
                paramsMap.put(label.getText(), text.getText());
            }
            checkMap.add(paramsMap);
        }
        for (Check objNotBox : checks) {
            notList.add(objNotBox.notBox.isSelected());
        }
        lemonObj.addCheck(message.getText(), points.getText(), kindList, typeList, checkMap, notList);
        
    }

}
