package com.gui.controller.user;

import com.SQLsupport.DBClass.Faq;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import static com.gui.Constants.*;
import static com.gui.LanguageSupport.*;

public class UserFAQController extends UserMenuController{

    private ObservableList<Faq> dataFromServer;

    @FXML
    private TableColumn<Faq, String> answersColumn;

    @FXML
    private TableView<Faq> faqTable;

    @FXML
    private TableColumn<Faq, String> questionColumn;

    @FXML
    private Button backButton;

    @FXML
    public void initialize(){

        this.initMainScene();
        dataFromServer= FXCollections.observableArrayList();

        questionColumn.setCellValueFactory(new PropertyValueFactory<>("question"));
        answersColumn.setCellValueFactory(new PropertyValueFactory<>("answer"));

        client.sendData("select all faq");
        client.sendData(" ");
        dataFromServer.addAll(super.client.receiveFAQ());
        faqTable.setItems(dataFromServer);

    }

    @Override
    public void initMainScene(){
        super.initMainScene();
        backButton.setOnMouseClicked(event -> {super.switchScene(event,USER_PROFILE_FXML);});
        languageButton.setOnMouseClicked(event -> {
            int language_count1=client.isRussianLanguage()?LANGUAGE_ENGLISH:LANGUAGE_RUSSIAN;
            this.switchLanguage(language_count1);
            client.switchLanguage();
        });
    }

    @Override
    protected void switchLanguage(int language_count){
        super.switchLanguage(language_count);
        headLabel.setText(LABEL_FAQ_TEXT[language_count]);
        questionColumn.setText(FAQ_QUESTION_TEXT[language_count]);
        answersColumn.setText(FAQ_ANSWER_TEXT[language_count]);
        backButton.setText(FAQ_BACK_TEXT[language_count]);
    }
}
