package com.wayruha.util;


import com.wayruha.model.ConfigFile;
import com.wayruha.model.ProductNote;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Random;

public class Finder {
  //  String queryString;
    ArrayList<ConfigFile> patternList=new ArrayList<>();
    ObservableList<ProductNote> productFetch= FXCollections.observableArrayList();
    Random rand=new Random();

    public ObservableList<ProductNote> fetchFromExcel(String queryString){
        ArrayList<ConfigFile> patternsList=XmlParser.loadAllPatterns();
            ObservableList<ProductNote> row = FXCollections.observableArrayList();
        for (ConfigFile configFile:XmlParser.loadAllPatterns())
            row.add(new ProductNote(rand.nextInt(100),queryString));
        return row;
    }

    public Finder(){}

}
