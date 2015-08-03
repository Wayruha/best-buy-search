package com.wayruha.customForms;

import javafx.geometry.Insets;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class PatternGroupLayout {
    HBox hBox;
    String patternName;
    final int imgWidth=21,imgHeight=28;

    public PatternGroupLayout(String name){
        this.patternName=name;
    }

    public HBox createLayout(){
        hBox=new HBox();
        hBox.setId("patternHBox");


        Label label=new Label(patternName);
        label.setContentDisplay(ContentDisplay.CENTER);
        label.setId("patternLabel");
        ImageView img=new ImageView("/images/editPattern.png");
        img.setFitHeight(imgHeight);
        img.setFitWidth(imgWidth);
        img.setPickOnBounds(true);
        img.setPreserveRatio(true);
        img.setId("patternImg");

        Insets insets=new Insets(10,0,0,0);
        hBox.setPadding(insets);
        hBox.getChildren().add(label);
        hBox.getChildren().add(img);
        return hBox;
    }
}
