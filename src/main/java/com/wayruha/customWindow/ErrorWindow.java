package com.wayruha.customWindow;

import com.wayruha.util.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

import java.net.URL;
import java.util.ResourceBundle;

public class ErrorWindow implements Initializable {
    static boolean inUse = false;
    @FXML
    Label errorLabel;
    Exception exception;
    String customMessage;


    public ErrorWindow(Exception givenException) {
        this.exception = givenException;
        this.customMessage = null;
        throwWindow();
    }

    public ErrorWindow(Exception exception, String message) {
        this.customMessage = message;
        this.exception = exception;
        throwWindow();
    }

    private void throwWindow() {
        if (!inUse) {
            Stage stage = new Stage();
            stage.getIcons().add(new Image(getClass().getClassLoader().getResource("images/glass_blue.png").toExternalForm()));

            Action response= Dialogs.create()
                    .owner(stage)
                    .title("Сталася помилка") .actions(Dialog.ACTION_OK)
                    .message(customMessage != null ? customMessage : exception.getMessage())
                    .showException(exception);
            inUse = true;

            if (response==Dialog.ACTION_CANCEL||response==Dialog.ACTION_OK)
                inUse=false;
        }

        StringBuilder sb = new StringBuilder("Сталася помилка: ");
        sb.append(customMessage != null ? customMessage : exception.getMessage());
        sb.append(". ");
        Logger.write(sb);
        exception.printStackTrace();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errorLabel.setText(customMessage != null ? customMessage : exception.getMessage());
    }
}
