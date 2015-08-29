package com.wayruha.util;

import com.wayruha.controller.MainController;

public final class Logger {


    static MainController mainController;
    public static void write(Object text){
         if(mainController==null)
             new Thread(()->{
                 try {
                     Thread.sleep(1000);
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
                 mainController.logArea.setText(text.toString());}).start();
        else mainController.logArea.setText(text.toString());
        System.out.println(text.toString());
    }
    public static void append(Object text){
        //  MainApp main=new MainApp();
        //MainController mainController=(MainController)main.getLoader().getController();
        mainController.logArea.setText(mainController.logArea.getText()+text.toString()+"\r\n");

    }
    public static void setMainController(MainController MC) {
        mainController = MC;
    }

}
