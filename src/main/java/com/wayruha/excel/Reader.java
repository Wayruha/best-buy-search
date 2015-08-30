package com.wayruha.excel;

import com.wayruha.model.ConfigFile;
import com.wayruha.model.ProductNote;
import com.wayruha.model.ProductsGroup;
import com.wayruha.util.ManufacturersSynonimRow;
import com.wayruha.util.Parser;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;


public class Reader {
    ConfigFile configFile;
    //Строка запроса буде розділятись ':' на автора і модель
    String manufacturer;
    String[] modelArr;
    String queryString;
    ProductsGroup group;

    public Reader(String queryString) {
        this.queryString = queryString;
        String[] queryArr = queryString.split(":", 2);
        manufacturer = queryArr[0].toLowerCase().trim();
        modelArr = queryArr[1].split(" ");
        for (int i=0;i<modelArr.length;i++) modelArr[i]=modelArr[i].toLowerCase().trim();
    }

    public ProductsGroup findInDoc(ConfigFile configFile) {
        this.configFile = configFile;
        this.group = null;
        try (FileInputStream fis = new FileInputStream(configFile.getFilePath())) {
            Workbook workbook = null;
            if (configFile.getFilePath().toLowerCase().endsWith("xlsx")) {
                workbook = new XSSFWorkbook(fis);
            } else if (configFile.getFilePath().toLowerCase().endsWith("xls")) {
                workbook = new HSSFWorkbook(fis);
            }

            int numberOfSheets = workbook.getNumberOfSheets();
            for (int i = 0; i < numberOfSheets; i++) {
                Sheet sheet = workbook.getSheetAt(i);

                Iterator<Row> rowIterator = sheet.iterator();
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    if (row == null) continue;
                    if (checkRow(row)) makeProductsGroup(row);
                }
            }
        } catch (Exception e) {
            System.out.println("Error in " + configFile.getName() + "--" + configFile.getFilePath());
            e.printStackTrace();
        }
        if (group != null) group.setQueryString(queryString);
        return group;

    }

    private boolean checkRow(Row row) {
        if (!checkForManufacturer(row)) return false;
        Cell cell = row.getCell(configFile.getModelCol() - 1);
        if (cell != null) {
            String modelColText = cell.getStringCellValue();
            modelColText = processString(modelColText,true);
            for (String partModel : modelArr)
                if (modelColText.contains(partModel)) modelColText.replace(partModel, "");   //Реплейс(+) чи РеплейсАлл?
                else return false;
            return true;//рядок підходить.
        }
        return false; //cell пуста
    }

    private boolean checkForManufacturer(Row row) {
        ArrayList<ManufacturersSynonimRow> synonimList= Parser.getSynonimListFromJSON();
        boolean flag=false;
        for (ManufacturersSynonimRow synonimRow:synonimList){
            if(synonimRow.getSynonimsForWord(manufacturer)!=null)
                for (String word:synonimRow.getSynonimsForWord(manufacturer))
                    flag=checkForManufacturer2(row,word);
            if (flag) return flag;
        }
         return checkForManufacturer2(row,manufacturer);
    }

    private boolean checkForManufacturer2(Row row,String manufacturer){
        Cell cell = row.getCell(configFile.getManufacturerCol() > 0 ? configFile.getManufacturerCol() - 1 : configFile.getModelCol() - 1);
        if (cell != null) {
            String text = cell.getStringCellValue();
            text = processString(text,false);
            if (text.contains(" "+manufacturer+" "))
            {
                text.replaceAll(manufacturer, "");
                return true;
            } else return false;
        } else return false;

    }
    private String processString(String string,boolean isModel) {
        string = string.toLowerCase();
        string = string.trim();
        string=string.replaceAll("\'"," ");
        string=string.replaceAll("\""," ");
        if(isModel)string = string.replaceAll(" ", "");
        string = string.replaceAll("-", "");
        string = string.replaceAll("_", "");
        string = string.replaceAll("№", "");
        return string;
    }

    //клас має вертати елемент типу ProductsGroup;
    private void makeProductsGroup(Row row) {
        Cell cell = null;
        try {
            cell = row.getCell(configFile.getPriceCol() - 1);
            double price = 0;
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    price = convertTextPriceToDouble(cell.getStringCellValue());
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    price = cell.getNumericCellValue();
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    if(cell.getCachedFormulaResultType() == Cell.CELL_TYPE_NUMERIC){
                        price=cell.getNumericCellValue();
                    } else if(cell.getCachedFormulaResultType() == Cell.CELL_TYPE_STRING)
                        price=convertTextPriceToDouble(cell.getStringCellValue());
                    break;
            }
            if(configFile.getDiscount()>0) price-=price*configFile.getDiscount()/100;
            price=Double.valueOf(String.format("%.1f", price).replace(",", "."));
            if (price == 0) throw new Exception();
            ProductNote note = new ProductNote(row);
            note.setPrice(price);
            if(group==null) group=new ProductsGroup();
            group.addItem(note);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("не змогли загрузити товар" + cell);
            //new ErrorWindow(e);
        }

    }


    private double convertTextPriceToDouble(String text) {
        text = text.replaceAll(" ", "").replaceAll(",", ".");
        StringBuilder result = new StringBuilder();
        char[] arr = text.toCharArray();
        for (char chr : arr) {
            if (chr < 58 & chr > 32) result.append(chr);
            if (chr == 46) break;
        }
        return Double.valueOf(result.toString());
    }

}
