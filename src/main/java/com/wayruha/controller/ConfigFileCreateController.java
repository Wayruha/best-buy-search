package com.wayruha.controller;

import com.wayruha.model.ConfigFile;
import com.wayruha.util.ErrorWindow;
import com.wayruha.util.XmlParser;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
    Label errorLabel;

    static PatternBoxController patternBoxController;
    Stage stage;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void handleBrowseButt(){

    }
    @FXML
    public void handleSaveButt(){
        if(!validateFields()) return;
        ConfigFile configFile=new ConfigFile(name.getText().trim(),filePath.getText().trim(),Integer.valueOf(priceCol.getText()),Integer.valueOf(modelCol.getText()),Integer.valueOf(manufacturerCol.getText()), getAppendCol());
        try
        {
            XmlParser.writeAnXml(configFile);
            patternBoxController.reload();
        } catch (Exception e){
            new ErrorWindow();
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
