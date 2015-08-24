package com.wayruha.customForms;

import com.wayruha.controller.MainController;
import com.wayruha.model.ProductNote;
import com.wayruha.model.ProductsGroup;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public final class CustomListCell extends ListCell {
    Label label;
    Pane pane;
    RadioButton radio;
    Button butt;
    HBox box;
    MainController mainController;
    ProductsGroup group;

    public CustomListCell(MainController mainController,SimpleIntegerProperty row) {
        super();

        label=new Label();
        pane=new Pane();
        radio=new RadioButton();
        butt=new Button();
        box=new HBox();
        this.mainController=mainController;
        butt.setOnAction(event->{
            if(getIndex()<=group.getSelectedIndex()) group.decreaseSelectedIndex();
            this.getListView().getItems().remove(getIndex());
            mainController.reMakeSortedList(row.get());
            mainController.refreshTableView();
        });
        box.getChildren().addAll(label,pane,radio,butt);
        HBox.setHgrow(pane, Priority.ALWAYS);
    }

    @Override
    protected void updateItem(Object item, boolean empty) {
        super.updateItem(item, empty);
        setText(null);
        if(!empty){
             group=((ProductNote)this.getListView().getItems().get(getIndex())).getGroup();
            radio.setSelected(false);
            ProductNote note=(ProductNote)item;

            radio.setToggleGroup(note.getGroup());
            radio.setUserData(note.getGroup().getNoteList().indexOf(item));
            if(item.equals(note.getGroup().getSelectedNote())) radio.setSelected(true);
            label.setText(note.getConfigFile() + ":" + note.getQueryString() + ". Price: " + note.getPrice());
            setGraphic(box);
        }
        else{
            setGraphic(null);
            label.setText(null);
        }
    }
}