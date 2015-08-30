package com.wayruha.model;

public class ConfigFile {
    String name, filePath;
    int priceCol,modelCol,manufacturerCol,appendCol;
    double discount;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ConfigFile(String name, String filePath, int priceCol, int modelCol, int manufacturerCol, int appendCol, double discount) {
        this.name = name;
        this.filePath = filePath;
        this.priceCol = priceCol;
        this.modelCol = modelCol;
        this.manufacturerCol = manufacturerCol;
        this.appendCol = appendCol;
        this.discount=discount;
    }

    public ConfigFile(String name) {
        this.name = name;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getPriceCol() {
        return priceCol;
    }

    public void setPriceCol(int priceCol) {
        this.priceCol = priceCol;
    }

    public int getModelCol() {
        return modelCol;
    }

    public void setModelCol(int modelCol) {
        this.modelCol = modelCol;
    }

    public int getManufacturerCol() {
        return manufacturerCol;
    }

    public void setManufacturerCol(int manufacturerCol) {
        this.manufacturerCol = manufacturerCol;
    }

    public int getAppendCol() {
        return appendCol;
    }

    public void setAppendCol(int appendCol) {
        this.appendCol = appendCol;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
