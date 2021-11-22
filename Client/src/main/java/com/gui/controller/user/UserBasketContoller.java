package com.gui.controller.user;

import com.SQLsupport.DBClass.Purchase;
import com.SQLsupport.DBClass.Rebate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import static com.gui.Constants.*;
import static com.gui.LanguageSupport.*;

public class UserBasketContoller extends UserMenuController{

    private ObservableList<Purchase> dataAboutPurchases, selectablePurchasesList;
    private ObservableList<Rebate> dataAboutRebates, selectableRebatesList;

    private String oneCostText,allCostText,yourMoneyText;
    private String deleted,oneSuccessful,allSuccessful, unsuccessful, fileText;

    @FXML
    private Label allCostLabel;

    @FXML
    private Label messageLabel;

    @FXML
    private Button buyAllButton;

    @FXML
    private Button buyOneButton;

    @FXML
    private Label currentAccountLabel;

    @FXML
    private Button deleteButton;

    @FXML
    private Button useRebateButton;

    @FXML
    private Button printButton;

    @FXML
    private Label oneCostLabel;

    @FXML
    private TableColumn<Rebate, String> rebateManufColumn;

    @FXML
    private TableColumn<Rebate, Integer> rebatePercentColumn;

    @FXML
    private TableView<Rebate> rebateTable;

    @FXML
    private TextField searchField;

    @FXML
    private TableColumn<Purchase, Integer> costColumn;

    @FXML
    private TableColumn<Purchase, Integer> idColumn;

    @FXML
    private TableColumn<Purchase, String> manufacturerColumn;

    @FXML
    private TableView<Purchase> purchaseTable;

    @FXML
    private TableColumn<Purchase, String> nameColumn;

    @FXML
    private TableColumn<Purchase, String> typeColumn;

    @FXML
    void initialize(){

        this.initMainScene();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id_purchase"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("car_name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("car_type"));
        costColumn.setCellValueFactory(new PropertyValueFactory<>("car_cost"));
        manufacturerColumn.setCellValueFactory(new PropertyValueFactory<>("manufacturer_name"));
        this.selectAllCarsFromBasket();

        dataAboutRebates=FXCollections.observableArrayList();
        selectableRebatesList=FXCollections.observableArrayList();
        rebateManufColumn.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        rebatePercentColumn.setCellValueFactory(new PropertyValueFactory<>("percent"));
        this.selectAllRebates();


        currentAccountLabel.setText(yourMoneyText+client.getUserProfile().getMoney());
        allCostLabel.setText(allCostText+calculateSumInBasket());
        oneCostLabel.setText(oneCostText+"0");
        messageLabel.setText(" ");

    }

    private void deleteOneRebate(int id_rebate){
        super.client.sendData("delete one rebate");
        super.client.sendData(Integer.toString(id_rebate));
        if(client.receiveResult())
            selectAllRebates();
    }

    private void selectAllRebates() {
        super.client.sendData("select all rebates");
        super.client.sendData(Integer.toString(super.client.getUserProfile().getId()));
        this.updateRebatesTable();
    }

    private void updateRebatesTable() {
        dataAboutRebates.clear();
        dataAboutRebates.addAll(super.client.receiveRebates());
        rebateTable.setItems(dataAboutRebates);
    }


    public void selectAllCarsFromBasket(){
        super.client.sendData("select all purchases");
        super.client.sendData(Integer.toString(super.client.getUserProfile().getId()));
        this.updatePurchasesTable();
    }

    public void initMainScene(){
        super.initMainScene();

        dataAboutPurchases = FXCollections.observableArrayList();
        selectablePurchasesList = FXCollections.observableArrayList();

        deleteButton.setOnMouseClicked(event -> {deleteOnePurchase();});
        buyOneButton.setOnMouseClicked(event -> {buyOneCar();});
        buyAllButton.setOnMouseClicked(event -> {buyAllCars();});
        printButton.setOnMouseClicked( event -> {printBasket();});
        useRebateButton.setOnMouseClicked(event -> {activateRebate();});

        languageButton.setOnMouseClicked(event -> {
            int language_count1=client.isRussianLanguage()?LANGUAGE_ENGLISH:LANGUAGE_RUSSIAN;
            this.switchLanguage(language_count1);
            client.switchLanguage();
        });

        purchaseTable.getSelectionModel().selectedItemProperty().addListener(
                (obs,oldSelection,newSelection)->{
                    if(newSelection!=null){
                        selectablePurchasesList.clear();
                        selectablePurchasesList.add(purchaseTable.getSelectionModel().getSelectedItem());
                        String selectableName = selectablePurchasesList.get(0).getCar_name();
                        searchField.setText(selectableName);
                        oneCostLabel.setText(oneCostText+ selectablePurchasesList.get(0).getCar_cost());
                        messageLabel.setText(" ");
                    }
                }
        );
    }

    @Override
    protected void switchLanguage(int language_count){
        super.switchLanguage(language_count);
        headLabel.setText(LABEL_BASKET_TEXT[language_count]);
        yourMoneyText=BASKET_CURR_MONEY_TEXT[language_count];
        currentAccountLabel.setText(yourMoneyText);
        allCostText=BASKET_ALL_CAR_MONEY_TEXT[language_count];
        allCostLabel.setText(allCostText);
        oneCostText=BASKET_SELECT_CAR_MONEY_TEXT[language_count];
        oneCostLabel.setText(oneCostText);
        searchField.setPromptText(BASKET_CHOOSE_CAR_TEXT[language_count]);
        buyOneButton.setText(BASKET_BUY_SELECTED_TEXT[language_count]);
        deleteButton.setText(BASKET_REMOVE_TEXT[language_count]);
        idColumn.setText(BASKET_NUMBER_TEXT[language_count]);
        nameColumn.setText(BASKET_NAME_TEXT[language_count]);
        typeColumn.setText(BASKET_TYPE_TEXT[language_count]);
        costColumn.setText(BASKET_COST_TEXT[language_count]);
        manufacturerColumn.setText(BASKET_MANUF_TEXT[language_count]);
        rebateManufColumn.setText(BASKET_MANUF_TEXT[language_count]);
        rebatePercentColumn.setText(BASKET_PERCENT_TEXT[language_count]);
        printButton.setText(BASKET_PRINT_TEXT[language_count]);
        buyAllButton.setText(BASKET_BUY_ALL_TEXT[language_count]);
        useRebateButton.setText(BASKET_USE_REBATE_TEXT[language_count]);
        unsuccessful=BASKET_UNSUCCESSFUL_TEXT[language_count];
        oneSuccessful=BASKET_SUCCESSFUL_ONE_TEXT[language_count];
        allSuccessful=BASKET_SUCCESSFUL_ALL_TEXT[language_count];
        deleted=BASKET_DELETED_TEXT[language_count];
        fileText=BASKET_FILE_TEXT[language_count];
    }

    private void activateRebate() {
        selectableRebatesList.clear();
        selectableRebatesList.add(rebateTable.getSelectionModel().getSelectedItem());
        if (selectableRebatesList.size()==0)
            return;
        Rebate rebate = selectableRebatesList.get(0);
        client.getClientsRebates().add(rebate);
        int id_rebate=rebate.getId();
        checkAllCosts();
        deleteOneRebate(id_rebate);

        purchaseTable.refresh();
        allCostLabel.setText(allCostText+calculateSumInBasket());
    }

    private void printBasket() {
        super.client.sendData("print basket");
        super.client.sendData(Integer.toString(super.client.getUserProfile().getId()));
        String filePath = super.client.receiveFilePath();
        messageLabel.setText(fileText+filePath);
    }

    private void buyAllCars(){
        int money=client.getUserProfile().getMoney();
        int car_cost=calculateSumInBasket();
        super.client.sendData("buy all cars");
        super.client.sendData(super.client.getUserProfile().getId()+" "
                +money+" "
                +(-1*car_cost));

        if(super.client.receiveResult()){
            deleteAllPurchase();
            client.getUserProfile().setMoney(money-car_cost);
            ///////////////////////////////////
            currentAccountLabel.setText(yourMoneyText+client.getUserProfile().getMoney());
            messageLabel.setText(allSuccessful);
        }
        else
            messageLabel.setText(unsuccessful);
    }

    private void deleteAllPurchase() {
        super.client.sendData("delete all purchases");
        super.client.sendData(Integer.toString(super.client.getUserProfile().getId()));

        if(super.client.receiveResult()){
            selectAllCarsFromBasket();
            allCostLabel.setText(allCostText+calculateSumInBasket());
        }
    }

    private void buyOneCar() {
        if(selectablePurchasesList.size()==0)
            return;
        int money=client.getUserProfile().getMoney();
        int car_cost=selectablePurchasesList.get(0).getCar_cost();
        super.client.sendData("buy one car");
        super.client.sendData(super.client.getUserProfile().getId()+" "
                +money+" "
                +(-1*car_cost));

        if(super.client.receiveResult()){
            deleteOnePurchase();
            client.getUserProfile().setMoney(money-car_cost);
            ///////////////////////////////////
            currentAccountLabel.setText(yourMoneyText+client.getUserProfile().getMoney());
            messageLabel.setText(oneSuccessful);
        }
        else
            messageLabel.setText(unsuccessful);
    }

    private void deleteOnePurchase() {
        if(selectablePurchasesList.size()==0)
            return;
        super.client.sendData("delete one purchase");
        super.client.sendData(Integer.toString(selectablePurchasesList.get(0).getId_purchase()));

        if(super.client.receiveResult()){
            selectAllCarsFromBasket();
            allCostLabel.setText(allCostText+calculateSumInBasket());
            messageLabel.setText(deleted);
        }
    }

    private int calculateSumInBasket() {
        int sum=0;
        if(dataAboutPurchases.size()>0)
            for(Purchase purchase: dataAboutPurchases)
                sum+=purchase.getCar_cost();
        return sum;
    }

    public void updatePurchasesTable(){
        dataAboutPurchases.clear();
        var purchases=super.client.receivePurchases();
        dataAboutPurchases.addAll(purchases);
        checkAllCosts();
        purchaseTable.setItems(dataAboutPurchases);
        searchField.setText("");
        oneCostLabel.setText(oneCostText+"0");
    }

    private void checkAllCosts(){
        for(var purchase:dataAboutPurchases)
            for(var reb:client.getClientsRebates())
                if (purchase.getManufacturer_name().equals(reb.getManufacturer())) {
                    double new_cost=purchase.getCar_cost() * (1 - reb.getPercent() / 100.0);
                    purchase.setCar_cost((int)(new_cost));
                }

    }
}


