package com.wayruha.customWindow;

import com.wayruha.util.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
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
            Dialogs.create()
                    .owner(stage)
                    .title("Сталася помилка")
                    .message(customMessage != null ? customMessage : exception.getMessage())
                    .showException(exception);

            inUse = true;
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
