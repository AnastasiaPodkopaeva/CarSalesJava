package com.gui.controller.admin;

import com.SQLsupport.DBClass.Car;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import static com.gui.Constants.*;
import static com.gui.LanguageSupport.*;
import static com.gui.LanguageSupport.CARS_COMPARISON_SUCCESSFUL_TEXT;

public class AdminCarsController extends AdminMenuController{
    private ObservableList<Car> dataFromServer, selectableCarList;
    private String successfulAdd,successfulCompr;

    @FXML
    private Button addCountButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TextField costField;

    @FXML
    private TextField countField;

    @FXML
    private Button createCarButton;

    @FXML
    private Button deleteCountButton;

    @FXML
    private Label headLabel;

    @FXML
    private Button languageButton;

    @FXML
    private TextField manufField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField numberField;

    @FXML
    private TextField typeField;

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
    private Button searchButton;

    @FXML
    void initialize(){

        dataFromServer = FXCollections.observableArrayList();
        selectableCarList = FXCollections.observableArrayList();
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

    public void selectOneCar(){
        String selectableName= searchField.getText();
        if(selectableName.equals(""))
            return;
        super.client.sendData("select one car");
        super.client.sendData(selectableName);

    }

    public void updateTable(){
        dataFromServer.clear();
        dataFromServer.addAll(super.client.receiveCars());
        carsTable.setItems(dataFromServer);
        searchField.setText("");

        costField.setText("");
        nameField.setText("");
        numberField.setText("");
        manufField.setText("");
        typeField.setText("");

        countField.setText("");
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
                        searchField.setText(selectableName);
                        messageLabel.setText(" ");
                    }
                }
        );

        searchButton.setOnMouseClicked(event -> {
            selectOneCar();
            this.updateTable();
        });

        createCarButton.setOnMouseClicked(event -> {createCar();});
        deleteButton.setOnMouseClicked(event -> {deleteOneCar();});
        deleteCountButton.setOnMouseClicked(event -> {updateCount(-1);});
        addCountButton.setOnMouseClicked(event -> {updateCount(1);});

        languageButton.setOnMouseClicked(event -> {
            int language_count1=client.isRussianLanguage()?LANGUAGE_ENGLISH:LANGUAGE_RUSSIAN;
            this.switchLanguage(language_count1);
            client.switchLanguage();
        });
    }

    private void updateCount(int value) {
        Car car = selectableCarList.get(0);
        if (car ==null)
            return;
        int newCount=value * Integer.parseInt(countField.getText());
        int id= car.getId_product();
        client.sendData("edit car count");
        client.sendData(id+" "+newCount);

        if(client.receiveResult()){
            this.selectAllCars();
        }
    }

    private void deleteOneCar() {
        Car car = selectableCarList.get(0);
        if (car ==null)
            return;
        client.sendData("delete one car");
        int id= car.getId_product();
        client.sendData(Integer.toString(id));

        if(client.receiveResult()){
            this.selectAllCars();
        }
    }

    private void createCar() {
        String cost, name, number, manuf, type;

        cost = costField.getText();
        name = nameField.getText();
        number = numberField.getText();
        manuf = manufField.getText();
        type = typeField.getText();

        client.sendData("create car");
        client.sendData(name+"@@@"+type+"@@@"+cost+"@@@"+number+"@@@"+manuf);

        if(client.receiveResult()){
            this.selectAllCars();
            //messageLabel.setText("Создан");
        }
        else
            System.out.println("oops");
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
        searchButton.setText(CARS_SEARCH_TEXT[language_count]);
        searchField.setPromptText(CARS_CHOOSE_PR_TEXT[language_count]);
        deleteButton.setText(CARS_DELETE_TEXT[language_count]);
        nameField.setPromptText(CARS_ADD_NAME_TEXT[language_count]);
        typeField.setPromptText(CARS_ADD_TYPE_TEXT[language_count]);
        costField.setPromptText(CARS_ADD_COST_TEXT[language_count]);
        numberField.setPromptText(CARS_ADD_QUANTITY_TEXT[language_count]);
        manufField.setPromptText(CARS_ADD_MANUFACTURER_TEXT[language_count]);
        countField.setPromptText(CARS_ADD_COUNT_TEXT[language_count]);
        createCarButton.setText(CARS_ADD_TEXT[language_count]);
        addCountButton.setText(CARS_ADD_TO_STOCK_TEXT[language_count]);
        deleteCountButton.setText(CARS_DELETE_FROM_STOCK_TEXT[language_count]);
        successfulAdd=CARS_ADD_SUCCESSFUL_TEXT[language_count];
        successfulCompr=CARS_COMPARISON_SUCCESSFUL_TEXT[language_count];
    }

}
