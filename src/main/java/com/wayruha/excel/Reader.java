package com.wayruha.excel;

import com.wayruha.model.ConfigFile;
import com.wayruha.model.ProductNote;
import com.wayruha.model.ProductsGroup;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.Iterator;


public class Reader {
    ConfigFile configFile;
  //Строка запроса буде розділятись ':' на автора і модель
    String manufacturer;
    String[] modelArr;
    ProductsGroup group;

    public Reader(String queryString) {
       String[] queryArr=queryString.split(":",2);
       manufacturer=queryArr[0].toLowerCase();
       modelArr=queryArr[1].split(" ");
       for(String partModel:modelArr) partModel.toLowerCase();
    }

    public ProductsGroup findInDoc(ConfigFile configFile){
        this.configFile=configFile;
        try(FileInputStream fis = new FileInputStream(configFile.getFilePath())) {
            Workbook workbook = null;
            if(configFile.getFilePath().toLowerCase().endsWith("xlsx")){
                workbook = new XSSFWorkbook(fis);
            }else if(configFile.getFilePath().toLowerCase().endsWith("xls")){
                workbook = new HSSFWorkbook(fis);
            }
            int numberOfSheets = workbook.getNumberOfSheets();
            for(int i=0; i < numberOfSheets; i++){
                Sheet sheet = workbook.getSheetAt(i);

                Iterator<Row> rowIterator = sheet.iterator();
                while (rowIterator.hasNext())
                {
                    Row row = rowIterator.next();
                    if(checkRow(row))makeProductsGroup(row);

                }
            }
        }
        catch (Exception e) {System.out.println(configFile.getName()+"--"+configFile.getFilePath());e.printStackTrace();
            }
        return group;

    }
    private boolean checkRow(Row row){
         if (!checkForManufacturer(row)) return false;
        String modelColText=row.getCell(configFile.getModelCol() - 1).getStringCellValue();
        modelColText=processString(modelColText);
        for (String partModel:modelArr)
            if(modelColText.contains(partModel)) modelColText.replace(partModel,"");   //Реплейс чи РеплейсАлл?
            else return false;
        return true; //рядок підходить.
    }

    private boolean checkForManufacturer(Row row){
        Cell cell=row.getCell(configFile.getManufacturerCol()>0?configFile.getManufacturerCol()-1:configFile.getModelCol()-1);
            String text=cell.getStringCellValue();
            text=processString(text);
            //TODO вирізати пробеєли і т.д. з $text
            if(text.contains(manufacturer))  //TODO тут підгрузити масив синонімів для слова виробника
            {
                text.replaceAll(manufacturer, "");
                return true;
            }
            else return false;
    }

    private String processString(String string){
        string.toLowerCase();
        string.trim();
        string.replace(" ","");
        string.replace("-","");
        string.replace("_","");
        string.replace("№","");
        return string;
    }
     //клас має вертати елемент типу ProductsGroup;
    private void makeProductsGroup(Row row){
        group.getNoteList().add(new ProductNote(row));
    }


}
