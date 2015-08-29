package com.wayruha.model;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;

import java.util.Iterator;

public class ProductNote{

    double price;
    ProductsGroup group;
    Row row;

    public ProductsGroup getGroup() {
        return group;
    }

    public void setGroup(ProductsGroup group) {
        this.group = group;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ProductNote(){}
    public ProductNote(Row row){
        this.row=row;
    }

    @Override
    public boolean equals(Object obj){
        if(obj!=null){
            ProductNote that = (ProductNote) obj;
            if (Double.compare(that.price, price) != 0) return false;
            return price==that.getPrice();

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
          StringBuilder builder=new StringBuilder();
          Iterator<Cell> cellIterator = row.iterator();
          while (cellIterator.hasNext()) {
              Cell cell = cellIterator.next();
              switch (cell.getCellType()) {
                  case Cell.CELL_TYPE_STRING:
                     builder.append(cell.getRichStringCellValue().getString());
                      break;
                  case Cell.CELL_TYPE_NUMERIC:
                      if (DateUtil.isCellDateFormatted(cell)) {
                          builder.append(cell.getDateCellValue());
                      } else
                          builder.append(cell.getNumericCellValue());
                      break;
                  case Cell.CELL_TYPE_BOOLEAN:
                      builder.append(cell.getBooleanCellValue());
                      break;
                  case Cell.CELL_TYPE_FORMULA:
                      if(cell.getCachedFormulaResultType() == Cell.CELL_TYPE_NUMERIC){
                          builder.append(cell.getNumericCellValue());
                      } else if(cell.getCachedFormulaResultType() == Cell.CELL_TYPE_STRING)
                          builder.append(cell.getStringCellValue());
                      break;
              }
              builder.append(" ");
          }
          return builder.toString();
    }

}
