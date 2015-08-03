package com.wayruha.controller;


import com.wayruha.customForms.PatternGroupLayout;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;



public class PatternBoxController {

    @FXML
    VBox patternBox;



   @FXML
    public void initialize(){
       PatternGroupLayout patternGroup=new PatternGroupLayout("Атлант");
       patternBox.getChildren().add(patternGroup.createLayout());
       ConfigFileCreateController.setPatternBoxController(this);
   }

   public void reload(){
       System.out.println("reload in PatternBox");
   } //заново загрузити списки файлів.
}
