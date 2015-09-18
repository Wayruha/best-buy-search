package com.wayruha.customWindow;

import com.wayruha.MainApp;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

import java.net.URL;
import java.util.ResourceBundle;

public class ConfirmWindow implements Initializable {
    Action response;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    ConfirmWindow(){
        Stage stage=new Stage();
        stage.getIcons().add(MainApp.getAppIcon());
         response = Dialogs.create()
                .owner(stage)
                .title("Діалог підтвердження")
                .masthead("Підтвердіть ваш вибір")
                .message("Ви дійсно хочете видалити цей шаблон?")
                .actions(Dialog.ACTION_OK, Dialog.ACTION_CANCEL)
                .showConfirm();
    }
    public Action getResponse() {
        return response;
    }
}
