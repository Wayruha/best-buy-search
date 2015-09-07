package com.wayruha.excel;


import com.wayruha.model.ConfigFile;
import com.wayruha.model.ProductsGroup;
import com.wayruha.util.Parser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Finder {
  //  String queryString;


    public static ObservableList<ProductsGroup> fetchFromExcel(String queryString){
        Reader reader=new Reader(queryString);
        ObservableList<ProductsGroup> row = FXCollections.observableArrayList();
        ProductsGroup group;
        for (ConfigFile configFile: Parser.getConfigList()) {
             group=reader.findInDoc(configFile);
             row.add(group);
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
