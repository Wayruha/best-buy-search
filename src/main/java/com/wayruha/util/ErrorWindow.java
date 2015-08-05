package com.wayruha.util;

import com.wayruha.controller.ConfigFileCreateController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Roman on 05.08.2015.
 */
public class ErrorWindow {
    public ErrorWindow(){
        try { String fxmlFile = "/fxml/login.fxml";
        Stage stage = new Stage();
        ConfigFileCreateController fileCreateController=new ConfigFileCreateController();
        fileCreateController.setStage(stage);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            loader.setController(this);
        BorderPane root = loader.load();

        Scene scene=new Scene(root);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("styles/MyStyle.css").toExternalForm());
        stage.setScene(scene);
            stage.setTitle("Error");
        stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
