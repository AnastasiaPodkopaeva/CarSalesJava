package com.gui.controller.user;

import java.util.Vector;

import com.SQLsupport.DBClass.Manufacturer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import static com.gui.Constants.*;
import static com.gui.LanguageSupport.*;

public class UserManufacturerController extends UserMenuController{

    private ObservableList<Manufacturer> list, selectableManufacturerList;
    boolean isHaveMark;

    @FXML
    private Button addMarkButton;

    @FXML
    private Button pieChartButton;

    @FXML
    private TextField markField;

    @FXML
    private TextField manufacturerField;

    @FXML
    private TableColumn<Manufacturer, String> countryColumn;

    @FXML
    private TableColumn<Manufacturer, String> emailColumn;

    @FXML
    private TableColumn<Manufacturer, Integer> idColumn;

    @FXML
    private TableView<Manufacturer> manufacturerTable;

    @FXML
    private TableColumn<Manufacturer, String> nameColumn;

    @FXML
    private TableColumn<Manufacturer, Double> ratingColumn;

    @FXML
    void initialize() {

        this.initMainScene();
        isHaveMark=false;

        idColumn.setCellValueFactory(new PropertyValueFactory<Manufacturer,Integer>("id_manufacturer"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Manufacturer,String>("name"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<Manufacturer,String>("country"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<Manufacturer,String>("email"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<Manufacturer,Double>("rating"));


        SelectAllManufacturers();

    }

    public void SelectAllManufacturers(){
        super.client.sendData("select all manufacturer");
        super.client.sendData(" ");
        Vector<Manufacturer> manufacturers = super.client.receiveManufacturers();
        list.clear();
        list.addAll(manufacturers);
        manufacturerTable.setItems(list);
    }

    public void initMainScene(){

        super.initMainScene();

        list= FXCollections.observableArrayList();
        selectableManufacturerList=FXCollections.observableArrayList();

        manufacturerTable.getSelectionModel().selectedItemProperty().addListener(
                (obs,oldSelection,newSelection)->{
                    if(newSelection!=null){
                        selectableManufacturerList.clear();
                        selectableManufacturerList.add(manufacturerTable.getSelectionModel().getSelectedItem());
                        String selectableName = selectableManufacturerList.get(0).getName();
                        manufacturerField.setText(selectableName);
                    }
                }
        );

        languageButton.setOnMouseClicked(event -> {
            int language_count1=client.isRussianLanguage()?LANGUAGE_ENGLISH:LANGUAGE_RUSSIAN;
            this.switchLanguage(language_count1);
            client.switchLanguage();
        });

        pieChartButton.setOnMouseClicked(event -> {switchScene(event,PIE_CHART_FXML); });

        addMarkButton.setOnMouseClicked(event -> {
            if(isHaveMark)
               return;
            int mark=0;
            try {
                mark=Integer.parseInt(markField.getText());
            }catch (NumberFormatException e){
                System.out.println(e.getMessage());
            }
            if(mark>10||mark<0){
                markField.setText("Некорректно");
                return;
            }

            super.client.sendData("add mark to manufacturer");
            super.client.sendData(manufacturerField.getText()+"@@@"+markField.getText());
            if(super.client.receiveResult()){
                SelectAllManufacturers();
                isHaveMark=true;
            }


        });
    }

    @Override
    protected void switchLanguage(int language_count){
        super.switchLanguage(language_count);
        headLabel.setText(LABEL_MANUFACTURER_TEXT[language_count]);
        idColumn.setText(MANUFACTURER_NUMBER_TEXT[language_count]);
        nameColumn.setText(MANUFACTURER_NAME_TEXT[language_count]);
        countryColumn.setText(MANUFACTURER_COUNTRY_TEXT[language_count]);
        emailColumn.setText(MANUFACTURER_EMAIL_TEXT[language_count]);
        ratingColumn.setText(MANUFACTURER_RATING_TEXT[language_count]);
        markField.setPromptText(MANUFACTURER_MARK_TEXT[language_count]);
        manufacturerField.setPromptText(MANUFACTURER_MANUF_TEXT[language_count]);
        addMarkButton.setText(MANUFACTURER_ADD_MARK_TEXT[language_count]);
        pieChartButton.setText(MANUFACTURER_CHART_TEXT[language_count]);
    }
}
