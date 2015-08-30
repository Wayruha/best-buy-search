package com.wayruha.customWindow;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ConfirmWindow implements Initializable {
    String message;


    public ConfirmWindow() {
        try {
            String fxmlFile = "/fxml/errorWindow.fxml";
            Stage stage = new Stage();

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            loader.setController(this);
            AnchorPane root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getClassLoader().getResource("styles/MyStyle.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Error");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
