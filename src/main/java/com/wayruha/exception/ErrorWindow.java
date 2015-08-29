package com.wayruha.exception;

import com.wayruha.util.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ErrorWindow implements Initializable {
    static boolean inUse=false;
    @FXML
    Label errorLabel;
    Exception exception;
    String customMessage=null;

    public ErrorWindow(Exception givenException) {
        this.exception=givenException;
        throwWindow();
    }

    public ErrorWindow(Exception exception,String message) {
        this.customMessage = message;
        this.exception = exception;
        throwWindow();
    }

    private void throwWindow(){
        if(!inUse)
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
                inUse=true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        Logger.write("Сталася помилка:" + customMessage!=null?customMessage:exception.getMessage()+ ". ");
        exception.printStackTrace();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errorLabel.setText(exception.getMessage());
    }
}
