package com.gui.controller.admin;

import com.SQLsupport.DBClass.Manufacturer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Vector;

import static com.gui.Constants.*;
import static com.gui.LanguageSupport.*;

public class AdminManufacturerController extends AdminMenuController{
    private ObservableList<Manufacturer> list, selectableManufacturerList;

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
    private Button addManufButton;

    @FXML
    private TextField contryField;

    @FXML
    private TextField countField;;

    @FXML
    private Button deleteManufButton;

    @FXML
    private TextField emailField;

    @FXML
    private Button languageButton;

    @FXML
    private TextField nameField;

    @FXML
    private TextField ratingField;


    @FXML
    void initialize() {

        this.initMainScene();

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

    public void createManuf(){
        String country, name, email, rating, count;

        country = contryField.getText();
        name = nameField.getText();
        email = emailField.getText();
        rating = ratingField.getText();
        count = countField.getText();

        client.sendData("create manufacturer");
        client.sendData(name+"@@@"+country+"@@@"+email+"@@@"+rating+"@@@"+count);

        if(client.receiveResult()){
            this.SelectAllManufacturers();
            //messageLabel.setText("Создан");
        }

    }

    public void initMainScene(){

        super.initMainScene();

        list= FXCollections.observableArrayList();
        selectableManufacturerList=FXCollections.observableArrayList();

        addManufButton.setOnMouseClicked(event -> {createManuf();});
        deleteManufButton.setOnMouseClicked(event -> {deleteOneManuf();});

        manufacturerTable.getSelectionModel().selectedItemProperty().addListener(
                (obs,oldSelection,newSelection)->{
                    if(newSelection!=null){
                        selectableManufacturerList.clear();
                        selectableManufacturerList.add(manufacturerTable.getSelectionModel().getSelectedItem());
                    }
                }
        );

        languageButton.setOnMouseClicked(event -> {
            int language_count1=client.isRussianLanguage()?LANGUAGE_ENGLISH:LANGUAGE_RUSSIAN;
            this.switchLanguage(language_count1);
            client.switchLanguage();
        });

    }

    private void deleteOneManuf() {
        Manufacturer manufacturer=selectableManufacturerList.get(0);
        if (manufacturer==null)
            return;
        client.sendData("delete one manufacturer");
        int id=manufacturer.getId_manufacturer();
        client.sendData(Integer.toString(id));

        if(client.receiveResult()){
            this.SelectAllManufacturers();
        }
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
        nameField.setPromptText(MANUFACTURER_NAME_TEXT[language_count]);
        contryField.setPromptText(MANUFACTURER_COUNTRY_TEXT[language_count]);
        emailField.setPromptText(MANUFACTURER_EMAIL_TEXT[language_count]);
        ratingField.setPromptText(MANUFACTURER_RATING_TEXT[language_count]);
        countField.setPromptText(MANUFACTURER_RATING_COUNT_TEXT[language_count]);
        addManufButton.setText(MANUFACTURER_ADD_MANUF_TEXT[language_count]);
        deleteManufButton.setText(MANUFACTURER_DELETE_MANUF_TEXT[language_count]);
    }
}
