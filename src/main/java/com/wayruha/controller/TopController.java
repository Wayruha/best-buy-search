package com.wayruha.controller;

import com.wayruha.model.ProductsGroup;
import com.wayruha.util.Finder;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.System.out;


public class TopController implements Initializable{

    @FXML
    Button searchButt;
    @FXML
    TextField searchField;
    @FXML
    ImageView addImg;
    static MainController mainController;

     public static void setMainController(MainController MC){
         mainController=MC;
     }

    @FXML
    public void handleSearchButt(){
        out.println("search something:"+searchField.getText());
        Finder finder=new Finder();
        ObservableList<ProductsGroup> row=finder.fetchFromExcel(searchField.getText());
        mainController.addRowInTable(row);
        searchField.setText(null);
    }
    @FXML
    public void handleAddPattern(){

        String fxmlFile = "/fxml/configFileCreate.fxml";
        Stage stage = new Stage();
        ConfigFileCreateController fileCreateController=new ConfigFileCreateController();
        fileCreateController.setStage(stage);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        loader.setController(fileCreateController);
        AnchorPane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene=new Scene(root);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("styles/MyStyle.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchField.setOnKeyPressed(event->{ if (event.getCode().toString().equalsIgnoreCase("ENTER")) handleSearchButt(); });
    }

    public TopController(){}

}
