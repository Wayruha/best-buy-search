package com.wayruha.util;

import com.wayruha.controller.MainController;

public  class Logger {


    static MainController mainController;
    public static void write(Object text){
      //  MainApp main=new MainApp();
       //MainController mainController=(MainController)main.getLoader().getController();
         mainController.logArea.setText(text.toString());

    }
    public static void setMainController(MainController MC) {
        mainController = MC;
    }

}
