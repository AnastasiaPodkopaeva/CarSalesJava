package com.SQLsupport.DBClass;

import java.io.Serializable;

public class Rebate implements Serializable {
    private int id, percent;
    private String manufacturer;

    public Rebate(int id, int percent, String manufacturer) {
        this.id = id;
        this.percent = percent;
        this.manufacturer = manufacturer;
    }

    public int getId() {
        return id;
    }

    public int getPercent() {
        return percent;
    }

    public String getManufacturer() {
        return manufacturer;
    }
}
