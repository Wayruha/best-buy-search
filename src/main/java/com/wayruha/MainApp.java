package com.wayruha;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {


  //  Parent root;
    static Stage stage;
    Scene scene;
    FXMLLoader loader;
    static Image appIcon;
    public  MainApp(){

    }

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage=stage;
        String fxmlFile = "/fxml/main.fxml";
        loader = new FXMLLoader(getClass().getResource(fxmlFile));
        appIcon=new Image(getClass().getClassLoader().getResource("images/glass_blue.png").toExternalForm());      //glass_blue or excelIcon.png
        stage.setWidth(1048);
        BorderPane root =  loader.load();

        scene=new Scene(root);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("styles/LightTheme.css").toExternalForm());
        scene.getStylesheets().add(getClass().getClassLoader().getResource("styles/MyStyle.css").toExternalForm());
        stage.getIcons().add(appIcon);
        stage.setTitle("Best buy search");
        stage.setScene(scene);
        stage.show();
    }

    public static Stage getStage() {
        return stage;
    }


    public static Image getAppIcon() {
        return appIcon;
    }

}
