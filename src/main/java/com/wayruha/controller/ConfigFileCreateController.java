package com.wayruha.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ConfigFileCreateController implements Initializable {
    @FXML
    TextField name,filePath;
    @FXML
    TextField priceCol,modelCol,manufacturerCol,appendCol;
    @FXML
    Button browseButt,saveButt;
    @FXML
    CheckBox checkAppend;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void handleBrowseButt(){

    }
    @FXML
    public void handleSaveButt(){

    }
}
