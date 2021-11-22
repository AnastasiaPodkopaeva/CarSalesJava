package com.gui.controller.user;

import com.SQLsupport.DBClass.InformationForPieChart;
import com.gui.controller.user.UserMenuController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;

import java.util.Vector;

import static com.gui.Constants.*;
import static com.gui.LanguageSupport.*;

public class PieChartController extends UserMenuController {

    ObservableList<PieChart.Data> dataList;

    @FXML
    private PieChart myPieChart;

    @FXML
    public void initialize(){
        dataList= FXCollections.observableArrayList();

        super.initMainScene();

        client.sendData("select data for pie chart");
        client.sendData(" ");
        Vector<InformationForPieChart> informationForPieCharts = client.receiveDataForPieChart();

        for(var data:informationForPieCharts)
            dataList.add(new PieChart.Data(data.getManufName(),data.getManufCount()));

        myPieChart.setData(dataList);

        languageButton.setOnMouseClicked(event -> {
            int language_count1=client.isRussianLanguage()?LANGUAGE_ENGLISH:LANGUAGE_RUSSIAN;
            this.switchLanguage(language_count1);
            client.switchLanguage();
        });
    }

    @Override
    protected void switchLanguage(int language_count){
        super.switchLanguage(language_count);
        headLabel.setText(LABEL_PIE_CHART_TEXT[language_count]);
    }

}
