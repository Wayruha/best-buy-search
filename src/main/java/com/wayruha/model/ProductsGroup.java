package com.wayruha.model;

import javafx.collections.ObservableList;
import javafx.scene.control.ToggleGroup;


public class ProductsGroup extends ToggleGroup {
    int selectedIndex;
    ObservableList<ProductNote> noteList;


    public ProductsGroup(ObservableList<ProductNote> noteList) {
        this.selectedIndex=0;
        this.noteList = noteList;
        for(ProductNote note:noteList)
            note.setGroup(this);

        selectedToggleProperty().addListener((ov,oldToggle,newToggle)->{
            if (getSelectedToggle() != null)
              selectedIndex=(Integer)getSelectedToggle().getUserData();

        });

    }

    public int getSelectedIndex(){
        return selectedIndex;
    }

    public ProductNote getSelectedNote(){
        return noteList.get(selectedIndex);
    }

    public ObservableList<ProductNote> getNoteList() {
        return noteList;
    }

    public void setNoteList(ObservableList<ProductNote> noteList) {
        this.noteList = noteList;
    }



}
