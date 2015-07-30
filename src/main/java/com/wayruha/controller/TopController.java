package com.wayruha.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;


public class TopController implements Initializable{

    @FXML
    Button searchButt;
    @FXML
    TextField searchField;
    @FXML
    ImageView addImg;

    @FXML
    public void handleSearchButt(){
               System.out.println("search something:"+searchField.getText());

    }
    @FXML
    public void handleAddPattern(){
        System.out.println("add pattern");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
