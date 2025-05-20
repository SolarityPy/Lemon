package com.lemon;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;

public class Check {
    public ComboBox<String> kindBox; // stores the check.pass, check.passoverride, etc.
    public ComboBox<String> typeBox; // stores different check types
    public CheckBox notBox; // stores the box
    public VBox paramsBox; // stores the c


    public Check(ComboBox<String> kindBox, ComboBox<String> typeBox, CheckBox notBox, VBox paramsBox) {
        this.kindBox = kindBox;
        this.typeBox = typeBox;
        this.notBox = notBox;
        this.paramsBox = paramsBox;
    }
}