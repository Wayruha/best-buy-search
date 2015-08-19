package com.wayruha.customForms;

import com.wayruha.controller.MainController;
import com.wayruha.model.ProductNote;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public final class CustomListCell extends ListCell {
    Label label;
    Pane pane;
    Button butt;
    HBox box;

    public CustomListCell(MainController mainController,SimpleIntegerProperty row) {
        super();
        label=new Label();
        pane=new Pane();
        butt=new Button();
        box=new HBox();
        butt.setOnAction(event->{
            this.getListView().getItems().remove(getIndex());
            mainController.reMakeSortedList(row.get());
            mainController.refreshTableView();
        });
        box.getChildren().addAll(label,pane,butt);
        HBox.setHgrow(pane, Priority.ALWAYS);
    }

    @Override
    protected void updateItem(Object item, boolean empty) {
        super.updateItem(item, empty);
        setText(null);
        if(!empty){
            ProductNote note=(ProductNote)item;
            label.setText(note.getConfigFile()+":"+note.getQueryString()+". Price: "+note.getPrice());
            setGraphic(box);
        }
        else{
            setGraphic(null);
            label.setText(null);
        }
    }
}