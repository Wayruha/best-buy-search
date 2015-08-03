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
            ObservableList<ProductNote> row = FXCollections.observableArrayList();
            row.add(new ProductNote(rand.nextInt(100),queryString));
            row.add(new ProductNote(rand.nextInt(100),queryString));
            row.add(new ProductNote(55,queryString));

        return row;
    }

    public Finder(){}

}
