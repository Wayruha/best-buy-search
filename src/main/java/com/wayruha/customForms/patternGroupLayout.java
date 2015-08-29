package com.wayruha.customForms;

import com.wayruha.controller.ConfigFileCreateController;
import com.wayruha.model.ConfigFile;
import com.wayruha.util.Parser;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class PatternGroupLayout {
    HBox hBox;
    String patternName;
    final int imgWidth=21,imgHeight=28;
    boolean isLoaded;


    public PatternGroupLayout(ConfigFile file,boolean isLoaded){
        this.patternName=file.getName();
        this.isLoaded=isLoaded;
    }

    public HBox createLayout(){
        hBox=new HBox();
        hBox.setId(isLoaded?"patternHBox":"notLoadedPatternHBox");

        Label label=new Label(patternName);
        label.setContentDisplay(ContentDisplay.CENTER);
        label.setId("patternLabel");
        ImageView img=new ImageView("/images/editPattern.png");
        img.setFitHeight(imgHeight);
        img.setFitWidth(imgWidth);
        img.setPickOnBounds(true);
        img.setPreserveRatio(true);
        img.setId("patternImg");
        img.setOnMouseClicked(this::loadFileSettings);
        Insets insets=new Insets(10,0,0,0);
        hBox.setPadding(insets);
        hBox.getChildren().add(label);
        hBox.getChildren().add(img);
        return hBox;
    }

    public void loadFileSettings(MouseEvent event){
        String fxmlFile = "/fxml/configFileCreate.fxml";
        Stage stage = new Stage();
        ConfigFileCreateController fileCreateController=new ConfigFileCreateController(Parser.readSettingsFromName(patternName));
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
}
