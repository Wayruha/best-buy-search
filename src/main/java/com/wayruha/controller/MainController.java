package com.wayruha.controller;

import com.wayruha.customForms.CustomListCell;
import com.wayruha.model.ConfigFile;
import com.wayruha.model.ProductNote;
import com.wayruha.model.ProductsGroup;
import com.wayruha.util.Logger;
import com.wayruha.util.ProductsComparator;
import com.wayruha.util.XmlParser;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import static java.lang.System.out;


public class MainController implements Initializable {

    @FXML
    AnchorPane top;
    @FXML
    AnchorPane patternBox;
    @FXML
    TableView<ObservableList<ProductsGroup>> table;
    @FXML
    ListView<ProductNote> groupView;
    @FXML
    Button addButt, prevButt, nextButt;
    @FXML
    ImageView prevValueImg,nextValueImg;
    @FXML
    public  TextArea logArea;


    private int selectedRow;
    private SimpleIntegerProperty selectedRowProperty=new SimpleIntegerProperty();
    ProductsGroup selectedGroup;

    ArrayList<ConfigFile> filesList;
    ObservableList<ObservableList<ProductsGroup>> dataList = FXCollections.observableArrayList();
    SimpleIntegerProperty indexProp = new SimpleIntegerProperty(1);
    ObservableList<SimpleIntegerProperty> priceLvlList = FXCollections.observableArrayList();
    ArrayList<ArrayList<ProductNote>>sortedList=new ArrayList<>();



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        table.setItems(dataList);
        loadColumns();

        BooleanBinding bindingPrevButt=new BooleanBinding() { { bind(priceLvlList);bind(selectedRowProperty); }
            @Override protected boolean computeValue() { return priceLvlList.get(selectedRowProperty.get()).get()<1; }
        }, bindingNextButt=new BooleanBinding() { { bind(priceLvlList); bind(selectedRowProperty); }
            @Override protected boolean computeValue() { return priceLvlList.get(selectedRowProperty.get()).get()>filesList.size()-2; }
        };
        PatternBoxController.setMainController(this);
        TopController.setMainController(this);

        Logger.setMainController(this);

        priceLvlList.add(new SimpleIntegerProperty(0));

        prevButt.disableProperty().bind(Bindings.isEmpty(table.getSelectionModel().getSelectedCells()).or(bindingPrevButt));      //TODO! походу звичайні змінні не підуть.яхз(
        nextButt.disableProperty().bind(Bindings.isEmpty(table.getSelectionModel().getSelectedCells()).or(bindingNextButt));
        table.getSelectionModel().setCellSelectionEnabled(true);


       /* for (ObservableList<ProductsGroup> obsList:dataList)    //Таблиця ж зразу пуста.
            addSortedCopyOfARow(obsList,sortedList);
*/


    }

    @FXML
    public void handleAddButt() {

        out.println("ADD Button clicked");
    }

    @FXML
    public void handleTableClick() {
        try
        {
            selectedRow=table.getSelectionModel().getSelectedIndex();
            selectedRowProperty.set(selectedRow);
//int col = table.getSelectionModel().getSelectedCells().get(0).getColumn();  //For debugging
           // System.out.println("col:"+col);
            selectedGroup=dataList.get(selectedRow).get(table.getSelectionModel().getSelectedCells().get(0).getColumn()-1);
            groupView.setItems(selectedGroup.getNoteList());
            Logger.write(selectedGroup.getSelectedNote());
        }  catch (IndexOutOfBoundsException e)
        {
            e.printStackTrace();
            //out.println(" Вийшли за межі таблиці.Error:" + e.getMessage());
            selectedRow = 0;
            selectedRowProperty.set(selectedRow);
        }
    }
    @FXML
    public void handleListClick(){


    }

    @FXML
    public void handlePrevButt() {
        int value=priceLvlList.get(selectedRow).get();
        //if (value > 1) ;
        priceLvlList.set(selectedRow,new SimpleIntegerProperty(value-1));
        indexProp.set(indexProp.get()-1);
      //  System.out.println(table.getItems().get(selectedRow).get(priceLvlList.get(selectedRow).get()).getPrice());
    }

    @FXML
    public void handleNextButt() {
        int value=priceLvlList.get(selectedRow).get();
      //  if (value <filesList.size()-1) ;
        priceLvlList.set(selectedRow,new SimpleIntegerProperty(value+1));
        indexProp.set(indexProp.get()+1);
     //  System.out.println(table.getItems().get(selectedRow).get(priceLvlList.get(selectedRow).get()).getPrice());

    }

    public void refreshTableView() {

        TablePosition selectedPos=table.getSelectionModel().getSelectedCells().get(0);
        ObservableList newList=FXCollections.observableArrayList(dataList);
        dataList.removeAll(dataList);
        dataList.addAll(newList);
        newList=null;
        table.getSelectionModel().select(selectedPos.getRow(),selectedPos.getTableColumn());

    }

    private ProductNote lowestInRow(int rowIndex, int getPos) {
        return sortedList.get(rowIndex).get(getPos);
    }

    public void addRowInTable(ObservableList<ProductsGroup> row){
        if(!table.getItems().isEmpty())priceLvlList.add(new SimpleIntegerProperty(0));
        ObservableList<ProductNote> tableRow=FXCollections.observableArrayList();
        for (ProductsGroup group:row) {
            group.selectedToggleProperty().addListener((ov,oldToggle,newToggle)->{
                reMakeSortedList(selectedRow);
                refreshTableView();
            });
            tableRow.add(group.getSelectedNote());
        }
        dataList.add(row);
        addSortedCopyOfARow(tableRow, sortedList);  //tableRow

    }

    public void loadColumns(){
        table.getColumns().removeAll(table.getColumns());
        filesList= XmlParser.loadAllPatterns();

        TableColumn queryCol=new TableColumn("Шуканий товар");
        queryCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, ProductNote>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, ProductNote> param) {
                return new SimpleStringProperty(((ProductsGroup)param.getValue().get(0)).getSelectedNote().getQueryString());
            }
        });
        table.getColumns().add(queryCol);
        for (int i = 0; i < filesList.size(); i++)
        {
            final int j = i;
            String name = filesList.get(i).getName();
            TableColumn col = new TableColumn(name);
            col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, ProductNote>, SimpleDoubleProperty>() {
                public SimpleDoubleProperty call(TableColumn.CellDataFeatures<ObservableList, ProductNote> param) {
                    return new SimpleDoubleProperty(((ProductsGroup)param.getValue().get(j)).getSelectedNote().getPrice());
                }
            });
            col.setCellFactory(new Callback<TableColumn, TableCell>() {
                @Override
                public TableCell call(TableColumn tableColumn) {

                    final TableCell cell = new TableCell() {
                        @Override
                        protected void updateItem(Object item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item != null) {
                                try {
                                    if (getItem().equals(lowestInRow(getIndex(), priceLvlList.get(getIndex()).get()).getPrice()))
                                        setTextFill(Color.RED);
                                    else setTextFill(Color.BLACK);
                                    if (getItem().equals(lowestInRow(getIndex(), 0).getPrice())) setUnderline(true);
                                    setText(item.toString());
                                } catch (Exception e){ out.println(e);}
                            }
                        }
                    };

                    priceLvlList.addListener((ListChangeListener<SimpleIntegerProperty>) c -> {
                        if (cell.getItem() != null) {
                            ProductsGroup group=((ObservableList<ProductsGroup>)cell.getTableRow().getItem()).get(j);
                            if (table.getItems().get(selectedRow).contains(group))   //TODO правильно зробити Equals()
                                if (group.getSelectedNote().equals(lowestInRow(selectedRow, priceLvlList.get(selectedRow).get())))
                                    cell.setTextFill(Color.RED);
                                else cell.setTextFill(Color.BLACK);
                        }
                    });
                    return cell;
                }
            });
            table.getColumns().add(col);
        }
        TableColumn differenceCol=new TableColumn("Різниця в сумі");
        differenceCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, ProductNote>, ObservableValue<Integer>>() {
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<ObservableList, ProductNote> param) {
                return new ReadOnlyObjectWrapper(param.getValue());
            }
        });
        differenceCol.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                final TableCell cell = new TableCell() {
                    @Override
                    protected void updateItem(Object item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null)
                            setText(String.valueOf(lowestInRow(getIndex(), 0).getPrice() - sortedList.get(getIndex()).get(priceLvlList.get(getIndex()).get()).getPrice()));
                    }
                };
                priceLvlList.addListener((ListChangeListener<SimpleIntegerProperty>) c -> {
                    if (cell.getItem() != null)
                        cell.setText(String.valueOf(lowestInRow(cell.getIndex(), 0).getPrice() - sortedList.get(cell.getIndex()).get(priceLvlList.get(cell.getIndex()).get()).getPrice()));

                });
                return cell;

            }
        });
         final MainController mc=this;
        groupView.setCellFactory(new Callback<ListView<ProductNote>, ListCell<ProductNote>>() {
           @Override
           public ListCell<ProductNote> call(ListView<ProductNote> param) {
               return new CustomListCell(mc,selectedRowProperty);
           }

       });
        table.getColumns().addAll(differenceCol);
    }

    public void reMakeSortedList(){
        sortedList.clear();
        ArrayList<ProductNote> sortedRow=new ArrayList();
        for(ObservableList<ProductsGroup> row:dataList) {
            for (ProductsGroup group : row)
                sortedRow.add(group.getSelectedNote());
            sortedRow.sort(new ProductsComparator());
            sortedList.add(sortedRow);
        }
    }
    public void reMakeSortedList(int index){                       //TODO метод походу не викупає шо інфа змінилася і сортує ще за старою. Чи так це?
        ArrayList<ProductNote> sortedRow=new ArrayList();
            for (ProductsGroup group : dataList.get(index))
                sortedRow.add(group.getSelectedNote());
            sortedRow.sort(new ProductsComparator());
            sortedList.set(index, sortedRow);

    }

    public  void addSortedCopyOfARow(ObservableList<ProductNote> row, ArrayList destList){
        ArrayList list=new ArrayList<>(row);
        // Collections.copy(list,obsList);
        Collections.sort(list,new ProductsComparator());
        destList.add(list);
    }



}
