package com.wayruha.model;

/**
 * Created by Roman on 17.07.2015.
 */
public class ProductNote{

    ConfigFile configFile;
    double price;
    String description;
    String queryString;

    public ConfigFile getConfigFile() {
        return configFile;
    }

    public void setConfigFile(ConfigFile configFile) {
        this.configFile = configFile;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }


    public ProductNote(){}
    public ProductNote(double price){
        this.price=price;
    }

    @Override
    public boolean equals(Object obj){
        if(obj!=null){
            ProductNote newNote=(ProductNote)obj;
            return price==newNote.getPrice();
        }   else
            return this==null;

    }

/*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductNote)) return false;

        ProductNote that = (ProductNote) o;

        if (Double.compare(that.price, price) != 0) return false;
        if (!configFile.equals(that.configFile)) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (!queryString.equals(that.queryString)) return false;

        return true;
    }
*/

      @Override
    public String toString(){
        return configFile!=null?configFile.getName():"noCnfg"+":"+queryString+"--"+price;
    }

}