package com.wayruha.customWindow;


import com.wayruha.MainApp;
import com.wayruha.customForms.CustomSynonimListCell;
import com.wayruha.util.ManufacturersSynonimRow;
import com.wayruha.util.Parser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SynonimsWindow implements Initializable {

    @FXML
    ListView<String> synonimList;
    @FXML
    Button addButt, saveButt;
    ObservableList<String> dataList = FXCollections.observableArrayList();
    Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stage.getIcons().add(MainApp.getAppIcon());

        for (ManufacturersSynonimRow row : Parser.getSynonimListFromJSON())
            dataList.add(row.toString());

        synonimList.setItems(dataList);
        synonimList.setEditable(true);
        synonimList.setCellFactory(param -> new CustomSynonimListCell());
        synonimList.setOnEditCommit(new EventHandler<ListView.EditEvent<String>>() {
            @Override
            public void handle(ListView.EditEvent<String> t) {
                synonimList.getItems().set(t.getIndex(), t.getNewValue());
                System.out.println("setOnEditCommit");
            }

        });
        synonimList.setOnEditCancel(new EventHandler<ListView.EditEvent<String>>() {
            @Override
            public void handle(ListView.EditEvent<String> t) {
                System.out.println("setOnEditCancel");
            }
        });


    }

    @FXML
    public void handleAddButt() {
        dataList.add("");
    }

    @FXML
    public void handleSaveButt() {
        ArrayList<ManufacturersSynonimRow> list=new ArrayList<>();
        for (String word : dataList) {
            if (!word.trim().isEmpty()) {
                String[] arr=word.split(",");
                list.add(new ManufacturersSynonimRow(arr));
            }
        }
        Parser.writeSynonims(list);
        stage.close();

    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }


}
