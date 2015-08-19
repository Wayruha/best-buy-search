package com.wayruha.model;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;

import javafx.beans.Observable;


public class ProductsGroup implements Observable {
    SimpleIntegerProperty selectedIndex;
    ObservableList<ProductNote> noteList;

    public ProductsGroup(ObservableList<ProductNote> noteList, SimpleIntegerProperty selectedItem) {
        this.noteList = noteList;
        this.selectedIndex = selectedItem;
    }

    public ProductNote getSelectedNote(){
        return noteList.get(selectedIndex.get());
    }

    public ObservableList<ProductNote> getNoteList() {
        return noteList;
    }

    public void setNoteList(ObservableList<ProductNote> noteList) {
        this.noteList = noteList;
    }

    public int getSelectedIndex() {
        return selectedIndex.get();
    }

    public SimpleIntegerProperty selectedIndexProperty() {
        return selectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex.set(selectedIndex);
    }

    @Override
    public void addListener(InvalidationListener listener) {

    }

    @Override
    public void removeListener(InvalidationListener listener) {

    }
}
