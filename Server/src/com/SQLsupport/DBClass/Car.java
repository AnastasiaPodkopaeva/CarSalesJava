package com.SQLsupport.DBClass;

import java.io.Serializable;

public class Car implements Serializable {
    private int id_car;
    private String name;
    private String type;
    private int cost;
    private int count;
    private String nameManufacturer;

    public Car(int id_car, String name, String type, int cost, int count, String nameManufacturer) {
        this.id_car = id_car;
        this.name = name;
        this.type = type;
        this.cost = cost;
        this.count = count;
        this.nameManufacturer = nameManufacturer;
    }

    public int getId_car() {
        return id_car;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getCost() {
        return cost;
    }

    public int getCount() {
        return count;
    }

    public String getNameManufacturer() {
        return nameManufacturer;
    }
}
