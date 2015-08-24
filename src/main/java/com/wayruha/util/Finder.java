package com.wayruha.util;


import com.wayruha.excel.Reader;
import com.wayruha.model.ConfigFile;
import com.wayruha.model.ProductsGroup;
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
        Reader reader=new Reader(queryString);
        ObservableList<ProductsGroup> row = FXCollections.observableArrayList();
        for (ConfigFile configFile:XmlParser.loadAllPatterns()) {
            row.add(reader.findInDoc(configFile));
        }
        return row;
    }

    /*public ObservableList<ProductsGroup> fetchFromExcel(String queryString){
        ArrayList<ConfigFile> patternsList=XmlParser.loadAllPatterns();
        ObservableList<ProductsGroup> row = FXCollections.observableArrayList();
        ProductsGroup group;
        for (ConfigFile configFile:XmlParser.loadAllPatterns()) {
            ObservableList<ProductNote> list = FXCollections.observableArrayList();
            list.add(new ProductNote(rand.nextInt(100), queryString));
            list.add(new ProductNote(rand.nextInt(100), queryString));
            list.sort(new ProductsComparator());
            group = new ProductsGroup(list);
            row.add(group);
        }
        row.remove(row.size()-1);
        row.add(null);
        return row;
    }   */
    public Finder(){}

}
