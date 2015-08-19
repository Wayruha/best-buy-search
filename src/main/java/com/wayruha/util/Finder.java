package com.wayruha.util;


import com.wayruha.model.ConfigFile;
import com.wayruha.model.ProductNote;
import com.wayruha.model.ProductsGroup;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Random;

public class Finder {
  //  String queryString;
    ArrayList<ConfigFile> patternList=new ArrayList<>();
    ObservableList<ProductsGroup> productFetch= FXCollections.observableArrayList();
    Random rand=new Random();

    public ObservableList<ProductsGroup> fetchFromExcel(String queryString){
        ArrayList<ConfigFile> patternsList=XmlParser.loadAllPatterns();
            ObservableList<ProductsGroup> row = FXCollections.observableArrayList();
            ProductsGroup group;
        for (ConfigFile configFile:XmlParser.loadAllPatterns()) {
            ObservableList<ProductNote> list = FXCollections.observableArrayList();
            list.add(new ProductNote(rand.nextInt(100), queryString));
            list.add(new ProductNote(rand.nextInt(100), queryString));
            list.sort(new ProductsComparator());
            group = new ProductsGroup(list, new SimpleIntegerProperty(0));
            row.add(group);
        }
        return row;
    }
    public Finder(){}

}
