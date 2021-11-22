package com.gui.controller.user;

import com.SQLsupport.DBClass.Review;
import com.gui.controller.user.UserMenuController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import static com.gui.Constants.*;
import static com.gui.Constants.LANGUAGE_RUSSIAN;
import static com.gui.LanguageSupport.*;

public class CarsReviewController extends UserMenuController {

    private ObservableList<Review> dataFromServer, selectableReviewList;
    private String carName;
    private boolean isHaveReview;

    @FXML
    private Button reviewButton;

    @FXML
    private TableView<Review> reviewsTable;

    @FXML
    private TableColumn<Review, String> reviewColumn;

    @FXML
    private TextField reviewField;

    @FXML
    private TableColumn<Review, String> userColumn;

    @FXML
    public void initialize(){
        this.initMainScene();

        isHaveReview=false;

        carName=super.client.getSelectableCarForReview().getName();

        dataFromServer = FXCollections.observableArrayList();
        selectableReviewList = FXCollections.observableArrayList();
        userColumn.setCellValueFactory(new PropertyValueFactory<>("nameUser"));
        reviewColumn.setCellValueFactory(new PropertyValueFactory<>("review_text"));

        selectAllReviews();

    }

    @Override
    protected void switchLanguage(int language_count){
        super.switchLanguage(language_count);
        headLabel.setText(LABEL_REVIEW_TEXT[language_count]+": "+carName);
        reviewButton.setText(REVIEW_BUTTON_TEXT[language_count]);
        reviewField.setPromptText(REVIEW_INPUT_TEXT[language_count]);
        userColumn.setText(REVIEW_USER_TEXT[language_count]);
        reviewColumn.setText(REVIEW_TEXT_TEXT[language_count]);
    }

    public void selectAllReviews(){

        super.client.sendData("select all reviews");
        super.client.sendData(carName);
        this.updateTable();
    }

    public void updateTable(){
        if(dataFromServer!=null){
            dataFromServer.clear();
            dataFromServer.addAll(super.client.receiveReviews());
            reviewsTable.setItems(dataFromServer);
        }
    }

    @Override
    public void initMainScene(){

        super.initMainScene();

        reviewButton.setOnMouseClicked(event -> {
            if(!isHaveReview) {
                sendReviewToServer();
                if(client.receiveResult()){
                    selectAllReviews();
                    isHaveReview=true;
                }
            }
        });

        languageButton.setOnMouseClicked(event -> {
            int language_count1=client.isRussianLanguage()?LANGUAGE_ENGLISH:LANGUAGE_RUSSIAN;
            this.switchLanguage(language_count1);
            client.switchLanguage();
        });

    }

    private void sendReviewToServer() {
        String text_review=reviewField.getText();
        if(text_review.equals(""))
            return;
        client.sendData("add review");
        client.sendData(client.getUserProfile().getId()+"@@@"
                +client.getSelectableCarForReview().getId_product()+"@@@"
                +text_review);
    }

}
