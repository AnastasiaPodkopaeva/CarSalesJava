package com.gui.controller.user;

import com.SQLsupport.DBClass.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import static com.gui.Constants.*;
import static com.gui.LanguageSupport.*;

public class UserAddMoneyController extends UserMenuController{

    private String SUCCESSFUL, UNSUCCESSFUL;

    @FXML
    private Label messageLabel;

    @FXML
    private Label TimeLabel;

    @FXML
    private Label moneyLabel;

    @FXML
    private TextField moneyField;

    @FXML
    private Button backButton;

    @FXML
    private Button addMoneyButton;

    @FXML
    private TextField cvField;

    @FXML
    private Label cvLabel;

    @FXML
    private TextField firstNumbers;

    @FXML
    private TextField firstTime;

    @FXML
    private TextField fourthNumbers;

    @FXML
    private Label numberCardLabel;

    @FXML
    private TextField secondNumbers;

    @FXML
    private TextField secondTime;

    @FXML
    private TextField thirdNumbers;

    @FXML
    public void initialize(){

        messageLabel.setText(" ");

        this.initMainScene();
    }

    @Override
    public void initMainScene(){
        super.initMainScene();
        backButton.setOnMouseClicked(event -> {switchScene(event,USER_PROFILE_FXML);});
        addMoneyButton.setOnMouseClicked(event -> {addMoney();});
        super.mainPane.setOnMouseClicked(event -> {messageLabel.setText("");});

        languageButton.setOnMouseClicked(event -> {
            int language_count1=client.isRussianLanguage()?LANGUAGE_ENGLISH:LANGUAGE_RUSSIAN;
            this.switchLanguage(language_count1);
            client.switchLanguage();
        });
    }

    @Override
    protected void switchLanguage(int language_count){
        super.switchLanguage(language_count);
        headLabel.setText(LABEL_ADD_MONEY_TEXT[language_count]);
        numberCardLabel.setText(ADD_MONEY_CARD_NUMBER_TEXT[language_count]);
        TimeLabel.setText(ADD_MONEY_TIME_TEXT[language_count]);
        cvLabel.setText(ADD_MONEY_CV_TEXT[language_count]);
        moneyLabel.setText(ADD_MONEY_SUM_TEXT[language_count]);
        addMoneyButton.setText(ADD_MONEY_TEXT[language_count]);
        backButton.setText(ADD_MONEY_BACK_TEXT[language_count]);
        SUCCESSFUL=ADD_MONEY_SUCCESSFUL_TEXT[language_count];
        UNSUCCESSFUL=ADD_MONEY_UNSUCCESSFUL_TEXT[language_count];
    }

    private void addMoney() {

        User user=client.getUserProfile();
        int money=user.getMoney(), add_money=Integer.parseInt(moneyField.getText());

        if(checkAllFields()){
            client.sendData("add money");
            client.sendData(user.getId()+" "+money+" "+add_money);
            if(client.receiveResult()){
                user.setMoney(money+add_money);
                messageLabel.setText(SUCCESSFUL);
            }
        }
        else
            messageLabel.setText(UNSUCCESSFUL);


    }

    private boolean checkAllFields(){
        boolean res=true;
        int value;
        value = Integer.parseInt(firstNumbers.getText());
        if(!check(value,1000,9999)) return false;
        res &= check(value,1000,9999);

        value = Integer.parseInt(secondNumbers.getText());
        if(!check(value,1000,9999)) return false;
        res &=check(value,1000,9999);

        value = Integer.parseInt(thirdNumbers.getText());
        if(!check(value,1000,9999)) return false;

        value = Integer.parseInt(fourthNumbers.getText());
        if(!check(value,1000,9999)) return false;

        value = Integer.parseInt(firstTime.getText());
        if(!check(value,1,12)) return false;

        value = Integer.parseInt(secondTime.getText());
        if(!check(value,21,99)) return false;

        value = Integer.parseInt(cvField.getText());
        if(!check(value,100,999)) return false;

        value = Integer.parseInt(moneyField.getText());
        if(!check(value,1,1000000)) return false;

        return true;
    }

    private boolean check(double value,double min, double max){
        return (value<=max&&value>=min);
    }
}
