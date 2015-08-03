package com.wayruha.model;

/**
 * Created by Roman on 17.07.2015.
 */
public class ConfigFile {


    String name,filePath;
    int priceCol,modelCol,manufacturerCol,appendCol;
    boolean isAppend;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ConfigFile(String name, String filePath, int priceCol, int modelCol, int manufacturerCol, int appendCol, boolean isAppend) {
        this.name = name;
        this.filePath = filePath;
        this.priceCol = priceCol;
        this.modelCol = modelCol;
        this.manufacturerCol = manufacturerCol;
        this.appendCol = appendCol;
        this.isAppend = isAppend;
    }
    public ConfigFile(String name) {
        this.name = name;
    }


}
