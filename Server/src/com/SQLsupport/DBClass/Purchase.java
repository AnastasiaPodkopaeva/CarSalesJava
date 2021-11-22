package com.SQLsupport.DBClass;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

public class Purchase implements Serializable {
    private int id_purchase, car_cost;
    private String car_name, car_type, manufacturer_name;

    public Purchase(int id_purchase, String car_name, String car_type, int car_cost, String manufacturer_name) {
        this.id_purchase = id_purchase;
        this.car_name = car_name;
        this.car_type = car_type;
        this.car_cost = car_cost;
        this.manufacturer_name = manufacturer_name;
    }

    public void print(FileWriter writer){
        try{
            writer.write("id: "+id_purchase+"\nname: "+car_name+"\ntype: ");
            writer.write(car_type+"\ncost: "+car_cost+"\nmanufacturer: "+manufacturer_name+"\n\n");
        }catch (IOException e){
            System.out.printf("\ncannot write purchase on file\n");
            e.printStackTrace();
        }
    }

    public void printConsole(){
        System.out.println("\nman="+manufacturer_name+"\ncost="+car_cost);
    }

    public int getId_purchase() {
        return id_purchase;
    }

    public String getCar_name() {
        return car_name;
    }

    public String getCar_type() {
        return car_type;
    }

    public int getCar_cost() {
        return car_cost;
    }

    public String getManufacturer_name() {
        return manufacturer_name;
    }

    public void setCar_cost(int car_cost) {
        this.car_cost = car_cost;
    }
}
