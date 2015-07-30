package com.wayruha.controller;

import com.wayruha.MainApp;
import com.wayruha.model.ConfigFile;
import com.wayruha.model.ProductNote;
import com.wayruha.util.Logger;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.net.URL;
import java.util.*;


public class MainController implements Initializable {

    @FXML
    AnchorPane top;
    @FXML
    AnchorPane patternBox;
    @FXML
    TableView<ObservableList<ProductNote>> table;
    @FXML
    Button addButt, prevButt, nextButt;
    @FXML
    public  TextArea logArea;

    private String newQuery;

    MainApp mainApp;
    Random rand = new Random();
    private int selectedRow;
    private ProductNote selectedNote;

    ArrayList<ConfigFile> filesList = new ArrayList<ConfigFile>();
    ObservableList<ObservableList<ProductNote>> dataList = FXCollections.observableArrayList();
    SimpleIntegerProperty indexProp = new SimpleIntegerProperty(1);
    ObservableList<SimpleIntegerProperty> priceLvlList = FXCollections.observableArrayList();
    ArrayList<ArrayList<ProductNote>>sortedList=new ArrayList<>();



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        BooleanBinding bindingPrevButt=new BooleanBinding() { { bind(priceLvlList); }
            @Override protected boolean computeValue() { return priceLvlList.get(selectedRow).get()<1; }
        }, bindingNextButt=new BooleanBinding() { { bind(priceLvlList); }
            @Override protected boolean computeValue() {
                return priceLvlList.get(selectedRow).get()>filesList.size()-2;
            }
        };

        mainApp = new MainApp();

        //filesList.add(new ConfigFile("TOVAR"));
        filesList.add(new ConfigFile("SKS"));
        filesList.add(new ConfigFile("Atlant"));
        filesList.add(new ConfigFile("ТехноСвіт"));

        for(int i=0;i<filesList.size();i++)
            priceLvlList.add(new SimpleIntegerProperty(0));



        prevButt.disableProperty().bind(Bindings.isEmpty(table.getSelectionModel().getSelectedCells()).or(bindingPrevButt));      //TODO! походу звичайні змінні не підуть.яхз(
        nextButt.disableProperty().bind(Bindings.isEmpty(table.getSelectionModel().getSelectedCells()).or(bindingNextButt));
        table.getSelectionModel().setCellSelectionEnabled(true);

        for (int i = 0; i < 3; i++) {
            ObservableList<ProductNote> row = FXCollections.observableArrayList();
            row.add(new ProductNote(rand.nextInt(100)));
            row.add(new ProductNote(rand.nextInt(100)));
            row.add(new ProductNote(55));
            dataList.add(row);

        }


        table.setItems(dataList);
        for (ObservableList<ProductNote> obsList:dataList)
            addSortedCopyOfARow(obsList,sortedList);



        TableColumn queryCol=new TableColumn("Шуканий товар");
        queryCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, ProductNote>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, ProductNote> param) {
                return new SimpleStringProperty(((ProductNote)param.getValue().get(0)).getQueryString());
            }
        });


        table.getColumns().add(queryCol);
        for (int i = 0; i < filesList.size(); i++) {
            final int j = i;
            String name = filesList.get(i).getName();
            TableColumn col = new TableColumn(name);
            col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, ProductNote>, SimpleDoubleProperty>() {
                public SimpleDoubleProperty call(TableColumn.CellDataFeatures<ObservableList, ProductNote> param) {
                    return new SimpleDoubleProperty(((ProductNote) param.getValue().get(j)).getPrice());
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
                                if (getItem().equals(lowestInRow(getIndex(), priceLvlList.get(getIndex()).get()).getPrice()))
                                    setTextFill(Color.RED);
                                else setTextFill(Color.BLACK);
                                setText(item.toString());
                            }
                        }
                    };

                    priceLvlList.addListener((ListChangeListener<SimpleIntegerProperty>) c -> {
                        if (cell.getItem() != null) {
                            ProductNote note=((ObservableList<ProductNote>)cell.getTableRow().getItem()).get(j);
                            if (table.getItems().get(selectedRow).contains(note))   //TODO правильно зробити Equals()
                                if (note.equals(lowestInRow(selectedRow, priceLvlList.get(selectedRow).get())))
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
            @Override public TableCell call(TableColumn param) {
                final TableCell cell=new TableCell(){
                    @Override protected void updateItem(Object item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item!=null)
                            setText(String.valueOf(lowestInRow(getIndex(), 0).getPrice() - sortedList.get(getIndex()).get(priceLvlList.get(getIndex()).get()).getPrice()));
                    }
                };
                priceLvlList.addListener((ListChangeListener<SimpleIntegerProperty>) c -> {
                    if (cell.getItem()!=null)
                        cell.setText(String.valueOf(lowestInRow(cell.getIndex(), 0).getPrice() - sortedList.get(cell.getIndex()).get(priceLvlList.get(cell.getIndex()).get()).getPrice()));

                });
                return cell;

            }
        });

        table.getColumns().addAll(differenceCol);
        Logger.setMainController(this);
    }

    @FXML
    public void handleAddButt() {

        System.out.println("ADD Button clicked");
    }

    @FXML
    public void handleTableClick() {
        try
        {
            selectedRow=table.getSelectionModel().getSelectedIndex();
            //int col = table.getSelectionModel().getSelectedCells().get(0).getColumn();
            selectedNote=(ProductNote)table.getItems().get(selectedRow).get(table.getSelectionModel().getSelectedCells().get(0).getColumn()-1);
            System.out.println(selectedNote);
            Logger.write(selectedNote);
        }  catch (Exception e){System.out.println("Error:"+e.getMessage());}

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

    private ProductNote lowestInRow(int rowIndex, int getPos) {
        return sortedList.get(rowIndex).get(getPos);
    }

    public void addQuery(String query){
       this.newQuery=query;
        doSearch(query);

    }

    public void doSearch(String query){//TODO на основі шаблонів і запросу робимо пошук. Вертати має ліст ПродактНодів
        //ПОШУК РОБИТЬ КЛАС ФАЙНДЕР

    }
    public void setNewQuery(String newQuery) {
        this.newQuery = newQuery;
    }

    public String getNewQuery() {
        return newQuery;
    }

    public void addSortedCopyOfARow(ObservableList row, ArrayList destList){
        ArrayList list=new ArrayList<>(row);
        // Collections.copy(list,obsList);
        Collections.sort(list,(Comparator) (o1, o2) -> {
            ProductNote note1 = (ProductNote) o1;
            ProductNote note2 = (ProductNote) o2;
            if (note1.getPrice() < note2.getPrice()) return -1;
            else if (note1.getPrice() > note2.getPrice()) return 1;
            else return 0;
        });
        destList.add(list);
    }

}
