package com.wayruha.controller;


import com.wayruha.customForms.PatternGroupLayout;
import com.wayruha.model.ConfigFile;
import com.wayruha.util.Parser;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class PatternBoxController implements Initializable {

    @FXML
    VBox patternBox;

    static MainController mainController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Parser.loadAllPatterns();
        reload();
        ConfigFileCreateController.setPatternBoxController(this);
    }

    public void reload(){
        patternBox.getChildren().clear();

       ArrayList<ConfigFile> patternsList = Parser.getConfigList();
       for(ConfigFile configFile:patternsList){
           PatternGroupLayout patternGroup=new PatternGroupLayout(configFile,true);
           patternBox.getChildren().add(patternGroup.createLayout());
       }
        for(ConfigFile configFile: Parser.getNotLoadedconfigList()){
            PatternGroupLayout patternGroup=new PatternGroupLayout(configFile,false);
            patternBox.getChildren().add(patternGroup.createLayout());
        }
        if(mainController!=null) mainController.loadColumns();
       System.out.println("reload in PatternBox");
    } //заново загрузити списки файлів.

    public static void setMainController(MainController mainController) {
        PatternBoxController.mainController = mainController;
    }


}
