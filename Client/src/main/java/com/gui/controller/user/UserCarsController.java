package com.gui.controller.user;

import com.SQLsupport.DBClass.Car;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Vector;

import static com.gui.Constants.*;
import static com.gui.LanguageSupport.*;

public class UserCarsController extends UserMenuController{

    private ObservableList<Car> dataFromServer, selectableCarList, comparisonList;
    private String successfulAdd,successfulCompr;

    @FXML
    private TableColumn<Car, Integer> costColumn;

    @FXML
    private Label messageLabel;

    @FXML
    private TableColumn<Car, Integer> countColumn;

    @FXML
    private TableColumn<Car, Integer> idColumn;

    @FXML
    private TableColumn<Car, String> manufacturerColumn;

    @FXML
    private TableView<Car> carsTable;

    @FXML
    private TableColumn<Car, String> nameColumn;

    @FXML
    private TableColumn<Car, String> typeColumn;

    @FXML
    private TextField searchField;

    @FXML
    private Button addToBasketButton;

    @FXML
    private Button addToComparisonButton;

    @FXML
    private Button reviewButton;

    @FXML
    private TextField filterField;

    @FXML
    private Button filterButton;

    @FXML
    private Button searchButton;

    @FXML
    void initialize(){

        dataFromServer = FXCollections.observableArrayList();
        selectableCarList = FXCollections.observableArrayList();
        comparisonList =FXCollections.observableArrayList();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id_product"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));
        countColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
        manufacturerColumn.setCellValueFactory(new PropertyValueFactory<>("nameManufacturer"));

        this.initMainScene();

        this.selectAllCars();
    }

    public void selectAllCars(){
        super.client.sendData("select all cars");
        super.client.sendData(" ");
        this.updateTable();
    }

    private void selectOneCar(){
        String selectableName= searchField.getText();
        if(selectableName.equals(""))
            return;
        super.client.sendData("select one car");
        super.client.sendData(selectableName);
    }

    public void selectCarByManufacturer(){
        String selectableManufacturerName= filterField.getText();
        if(selectableManufacturerName.equals(""))
            return;
        super.client.sendData("select by manufacturer");
        super.client.sendData(selectableManufacturerName);
        this.updateTable();
    }

    public void updateTable(){
        dataFromServer.clear();
        dataFromServer.addAll(super.client.receiveCars());
        carsTable.setItems(dataFromServer);
        filterField.setText("");
        searchField.setText("");
    }

    @Override
    public void initMainScene(){

        super.initMainScene();

        messageLabel.setText(" ");

        carsTable.getSelectionModel().selectedItemProperty().addListener(
                (obs,oldSelection,newSelection)->{
                    if(newSelection!=null){
                        selectableCarList.clear();
                        selectableCarList.add(carsTable.getSelectionModel().getSelectedItem());
                        String selectableName = selectableCarList.get(0).getName();
                        String selectableManuf = selectableCarList.get(0).getNameManufacturer();
                        filterField.setText(selectableManuf);
                        searchField.setText(selectableName);
                        messageLabel.setText(" ");
                    }
                }
        );

        searchButton.setOnMouseClicked(event -> {
            selectOneCar();
            this.updateTable();
        });

        filterButton.setOnMouseClicked(event -> {selectCarByManufacturer();});
        reviewButton.setOnMouseClicked(event -> {
            if(selectableCarList.size()!=0){
                super.client.setSelectableCarForReview(selectableCarList.get(0));
                super.switchScene(event,CARS_REVIEW_FXML);
            }
        });

        addToBasketButton.setOnMouseClicked(event -> {
            selectOneCar();
            Vector<Car> cars = super.client.receiveCars();
            if(cars !=null){
                super.client.sendData("add to basket");
                super.client.sendData(super.client.getUserProfile().getId()+" "+
                        cars.elementAt(0).getId_product());

                if(super.client.receiveResult()){
                    messageLabel.setText(successfulAdd);
                }
            }
        });

        addToComparisonButton.setOnMouseClicked(event -> {
            if(selectableCarList.size()==0)
                return;
            if(comparisonList.size()<2){
                comparisonList.add(selectableCarList.get(0));
                messageLabel.setText(successfulCompr);
            }
            if(comparisonList.size()==2) {
                dataFromServer.clear();
                dataFromServer.addAll(comparisonList);
                carsTable.setItems(dataFromServer);
                comparisonList.clear();

            }
        });

        languageButton.setOnMouseClicked(event -> {
            int language_count1=client.isRussianLanguage()?LANGUAGE_ENGLISH:LANGUAGE_RUSSIAN;
            this.switchLanguage(language_count1);
            client.switchLanguage();
        });
    }

    @Override
    protected void switchLanguage(int language_count){
        super.switchLanguage(language_count);
        headLabel.setText(LABEL_CARS_TEXT[language_count]);
        idColumn.setText(CARS_NUMBER_TEXT[language_count]);
        nameColumn.setText(CARS_NAME_TEXT[language_count]);
        typeColumn.setText(CARS_TYPE_TEXT[language_count]);
        costColumn.setText(CARS_COST_TEXT[language_count]);
        countColumn.setText(CARS_COUNT_TEXT[language_count]);
        manufacturerColumn.setText(CARS_MANUF_TEXT[language_count]);
        reviewButton.setText(CARS_REVIEW_TEXT[language_count]);
        addToBasketButton.setText(CARS_BASKET_TEXT[language_count]);
        searchButton.setText(CARS_SEARCH_TEXT[language_count]);
        addToComparisonButton.setText(CARS_COMPARISON_TEXT[language_count]);
        filterButton.setText(CARS_FILTER_TEXT[language_count]);
        searchField.setPromptText(CARS_CHOOSE_PR_TEXT[language_count]);
        filterField.setPromptText(CARS_CHOOSE_MANUF_TEXT[language_count]);
        successfulAdd=CARS_ADD_SUCCESSFUL_TEXT[language_count];
        successfulCompr=CARS_COMPARISON_SUCCESSFUL_TEXT[language_count];
    }

}
