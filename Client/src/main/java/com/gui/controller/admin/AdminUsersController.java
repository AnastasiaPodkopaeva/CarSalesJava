package com.gui.controller.admin;

import com.SQLsupport.DBClass.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import static com.gui.Constants.*;
import static com.gui.LanguageSupport.*;

public class AdminUsersController extends AdminMenuController{


    @FXML
    private TableColumn<User, String> addressColumn;

    @FXML
    private Button blockButton;

    @FXML
    private Button unblockButton;

    @FXML
    private TableColumn<User, String> fnameColumn;

    @FXML
    private TableColumn<User, String> lnameColumn;

    @FXML
    private TableColumn<User, String> loginColumn;

    @FXML
    private TableColumn<User, Integer> moneyColumn;

    @FXML
    private TableColumn<User, String> passwordColumn;

    @FXML
    private TableColumn<User, String> phoneColumn;

    @FXML
    private TableColumn<User, Integer> roleColumn;


    @FXML
    private TableView<User> usersTable;
    private ObservableList<User> dataFromServer;
    private ObservableList<User> selectableUserList;


    public AdminUsersController() {
    }

    @FXML
    void initialize() {

        dataFromServer = FXCollections.observableArrayList();
        selectableUserList = FXCollections.observableArrayList();
        loginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        fnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        moneyColumn.setCellValueFactory(new PropertyValueFactory<>("money"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        this.initMainScene();
        this.selectAllUsers();

    }

    private void selectAllUsers() {
        client.sendData("select all users");
        client.sendData("1");
        this.updateTable();
    }

    private void updateTable() {
        dataFromServer.clear();
        dataFromServer.addAll(super.client.receiveUsers());
        usersTable.setItems(dataFromServer);
    }

    public void initMainScene(){

        super.initMainScene();

        blockButton.setOnMouseClicked(event -> {blocking();});
        unblockButton.setOnMouseClicked((event -> {unblocking();}));

        languageButton.setOnMouseClicked(event -> {
            int language_count1=client.isRussianLanguage()?LANGUAGE_ENGLISH:LANGUAGE_RUSSIAN;
            this.switchLanguage(language_count1);
            client.switchLanguage();
        });

        usersTable.getSelectionModel().selectedItemProperty().addListener(
                (obs,oldSelection,newSelection)->{
                    if(newSelection!=null){
                        selectableUserList.clear();
                        selectableUserList.add(usersTable.getSelectionModel().getSelectedItem());
                    }
                }
        );


    }


    private void blocking(){
        if(selectableUserList.size()==0)
            return;
        int id=selectableUserList.get(0).getId();
        client.sendData("edit role user");
        client.sendData(id+" "+"-1");

        if(client.receiveResult())
            selectAllUsers();
    }

    private void unblocking(){
        if(selectableUserList.size()==0)
            return;
        int id=selectableUserList.get(0).getId();
        client.sendData("edit role user");
        client.sendData(id+" "+"0");

        if(client.receiveResult()){
            selectAllUsers();
        }

    }

    protected void switchLanguage(int language_count) {
        super.switchLanguage(language_count);
        headLabel.setText(LABEL_USERS_TEXT[language_count]);
        loginColumn.setText(USERS_LOGIN_TEXT[language_count]);
        passwordColumn.setText(USERS_PASSWORD_TEXT[language_count]);
        roleColumn.setText(USERS_ROLE_TEXT[language_count]);
        fnameColumn.setText(USERS_NAME_TEXT[language_count]);
        lnameColumn.setText(USERS_SURNAME_TEXT[language_count]);
        moneyColumn.setText(USERS_MONEY_TEXT[language_count]);
        addressColumn.setText(USERS_ADDRESS_TEXT[language_count]);
        phoneColumn.setText(USERS_PHONE_TEXT[language_count]);
        blockButton.setText(USERS_BLOCK_TEXT[language_count]);
        unblockButton.setText(USERS_UNBLOCK_TEXT[language_count]);
    }

}

