package com.wayruha.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ToggleGroup;


public class ProductsGroup extends ToggleGroup {
    int selectedIndex;
    ObservableList<ProductNote> noteList;
    String queryString;

    public ProductsGroup() {
        noteList = FXCollections.observableArrayList();
        selectedToggleProperty().addListener((ov, oldToggle, newToggle) -> {
            if (getSelectedToggle() != null)
                selectedIndex = (Integer) getSelectedToggle().getUserData();
        });
    }

    public ProductsGroup(String queryString) {
        this.queryString = queryString;
    }

    public ProductsGroup(ObservableList<ProductNote> noteList) {
        this.selectedIndex = 0;
        this.noteList = noteList;
        for (ProductNote note : noteList)
            note.setGroup(this);

        selectedToggleProperty().addListener((ov, oldToggle, newToggle) -> {
            if (getSelectedToggle() != null)
                selectedIndex = (Integer) getSelectedToggle().getUserData();
        });

    }

    public void addItem(ProductNote item) {
        item.setGroup(this);
        noteList.add(item);
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void decreaseSelectedIndex() {
        if(this.selectedIndex>0)this.selectedIndex--;
    }

    public ProductNote getSelectedNote() {
        return noteList.get(selectedIndex);
    }

    public ObservableList<ProductNote> getNoteList() {
        return noteList;
    }

    public void setNoteList(ObservableList<ProductNote> noteList) {
        this.noteList = noteList;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

}
