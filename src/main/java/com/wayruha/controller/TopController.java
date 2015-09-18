package com.wayruha.controller;

import com.wayruha.MainApp;
import com.wayruha.customWindow.SynonimsWindow;
import com.wayruha.excel.Finder;
import com.wayruha.model.ProductsGroup;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
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
    TextField manufacturerField,modelField;
    @FXML
    ImageView addImg;
    @FXML
    MenuBar menuBar;
    @FXML
    Label manufacturerLabel,modelLabel;
    static MainController mainController;

     public static void setMainController(MainController MC){
         mainController=MC;
     }

    @FXML
    public void handleSearchButt(){
        String queryString=manufacturerField.getText()+":"+modelField.getText();
        out.println("search something:"+queryString);

        ObservableList<ProductsGroup> foundResult=Finder.fetchFromExcel(manufacturerField.getText() + ":" + modelField.getText());
        mainController.addRowInTable(foundResult,queryString);
        modelField.setText("");
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

    @FXML
    public void openDictionary(){
        String fxmlFile = "/fxml/synonimWindow.fxml";
        Stage stage = new Stage();
        SynonimsWindow synonimWindowController=new SynonimsWindow();
        synonimWindowController.setStage(stage);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        loader.setController(synonimWindowController);
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
        manufacturerField.setOnKeyPressed(event->{ if (event.getCode().toString().equalsIgnoreCase("ENTER")) handleSearchButt(); });
        modelField.setOnKeyPressed(event->{ if (event.getCode().toString().equalsIgnoreCase("ENTER")) handleSearchButt(); });
        Stage stage=MainApp.getStage();
        menuBar.prefWidthProperty().bind(stage.widthProperty());
        manufacturerField.prefWidthProperty().bind(stage.widthProperty().multiply(162/1048d));
        modelField.prefWidthProperty().bind(stage.widthProperty().multiply(255/1048d));
        searchButt.prefWidthProperty().bind(stage.widthProperty().multiply(76/1048d));
        manufacturerLabel.prefWidthProperty().bind(stage.widthProperty().multiply(76/1048d));
        modelLabel.prefWidthProperty().bind(stage.widthProperty().multiply(60/1048d));

    }

    public TopController(){}

}
