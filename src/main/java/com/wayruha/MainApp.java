package com.wayruha;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {


  //  Parent root;
    Stage stage;
    FXMLLoader loader;

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


        BorderPane root =  loader.load();
      //  Parent root=FXMLLoader.load(getClass().getResource(fxmlFile));


        Scene scene=new Scene(root);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("styles/LightTheme.css").toExternalForm());
        stage.setTitle("JavaFX and Maven");

        stage.setScene(scene);
        stage.show();
    }

    public Stage getStage() {
        return stage;
    }

    public  FXMLLoader getLoader() { return loader; }

}
