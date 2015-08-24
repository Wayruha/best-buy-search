package com.wayruha.controller;

import com.wayruha.model.ConfigFile;
import com.wayruha.util.ErrorWindow;
import com.wayruha.util.XmlParser;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ConfigFileCreateController implements Initializable {
    @FXML
    TextField name,filePath;
    @FXML
    TextField priceCol,modelCol,manufacturerCol,appendCol;
    @FXML
    Button browseButt,saveButt;
    @FXML
    CheckBox checkAppend;
    @FXML
    Label errorLabel,appendLabel;

    static PatternBoxController patternBoxController;
    ConfigFile configFile;
    Stage stage;
    boolean editing=false;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        BooleanBinding appendDisableBinding=new BooleanBinding() {
            {bind(checkAppend.selectedProperty());}
            @Override
            protected boolean computeValue() {
                return !checkAppend.isSelected();
            }
        };
        appendCol.disableProperty().bind(appendDisableBinding);
        appendLabel.disableProperty().bind(appendDisableBinding);

        if(configFile!=null){
            name.setText(configFile.getName());
            filePath.setText(configFile.getFilePath());
            priceCol.setText(String.valueOf(configFile.getPriceCol()));
            manufacturerCol.setText(String.valueOf(configFile.getManufacturerCol()));
            modelCol.setText(String.valueOf(configFile.getModelCol()));
            if(configFile.getAppendCol()>0){
                checkAppend.setSelected(true);
                appendCol.setText(String.valueOf(configFile.getAppendCol()));
            }
        }

    }

    public ConfigFileCreateController() {
        configFile=null;
    }

    public ConfigFileCreateController(ConfigFile configFile){
        this.configFile=configFile;
        editing=true;


    }

    @FXML
    public void handleBrowseButt(){
        FileChooser fileChooser=new FileChooser();
        File selectedFile=fileChooser.showOpenDialog(stage);
       if (selectedFile!=null) filePath.setText(selectedFile.getAbsolutePath());

    }
    @FXML
    public void handleSaveButt(){
        if(!validateFields()) return;
        ConfigFile newConfigFile=new ConfigFile(name.getText().trim(),filePath.getText().trim(),Integer.valueOf(priceCol.getText()),Integer.valueOf(modelCol.getText()),Integer.valueOf(manufacturerCol.getText()), getAppendCol());
        try
        {
            XmlParser.writeAnXml(newConfigFile,editing?configFile.getName():null);
            patternBoxController.reload();
        } catch (Exception e){
            e.printStackTrace();
            new ErrorWindow(e);
        }
        stage.close();
    }


  public int getAppendCol(){
      return checkAppend.isSelected()?Integer.valueOf(appendCol.getText().trim()):-1;
  }

    public boolean validateFields() {
        boolean flag=true;
        if(name.getText().trim().isEmpty() | !Character.isLetter(name.getText().trim().toCharArray()[0])) {name.setId("errorField");flag=false;}else name.setId("");
        if(filePath.getText().isEmpty())    {filePath.setId("errorField");flag=false;} else filePath.setId("");
        if(priceCol.getText().isEmpty())    {priceCol.setId("errorField"); flag=false;} else priceCol.setId("");
        if(modelCol.getText().isEmpty())    {modelCol.setId("errorField"); flag=false;}  else modelCol.setId("");
        if(checkAppend.isSelected()) if(appendCol.getText().isEmpty()) {appendCol.setId("errorField");flag=false;} else checkAppend.setId("");
        if(!flag) {
            errorLabel.setText("Невірно заповнено поля");
            errorLabel.setVisible(true);
        }
        return flag;
    }
    public static void setPatternBoxController(PatternBoxController patternBxController) {
        patternBoxController = patternBxController;
    }
    public void setStage(Stage thisStage) {
        this.stage = thisStage;
    }

}
