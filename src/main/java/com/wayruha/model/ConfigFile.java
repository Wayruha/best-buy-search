package com.wayruha.model;

/**
 * Created by Roman on 17.07.2015.
 */
public class ConfigFile {
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public ConfigFile (){}
    public ConfigFile (String name){
        this.name=name;
    }

}
