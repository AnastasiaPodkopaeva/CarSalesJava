package com.SQLsupport.DBClass;

import java.io.Serializable;

public class InformationForPieChart implements Serializable {
    private String manufName;
    private int manufCount;

    public InformationForPieChart(String manufName, int manufCount) {
        this.manufName = manufName;
        this.manufCount = manufCount;
    }

    public String getManufName() {
        return manufName;
    }

    public int getManufCount() {
        return manufCount;
    }
}
