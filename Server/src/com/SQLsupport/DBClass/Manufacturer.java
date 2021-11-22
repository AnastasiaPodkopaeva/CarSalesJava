package com.SQLsupport.DBClass;

import java.io.Serializable;

public class Manufacturer implements Serializable {
    private int id_manufacturer;
    private String name;
    private String country;
    private String email;
    private double rating;
    private int number_of_ratings;

    public Manufacturer(int id_manufacturer, String name, String country, String email, double rating, int number_of_ratings) {
        this.id_manufacturer = id_manufacturer;
        this.name = name;
        this.country = country;
        this.email = email;
        this.rating = rating;
        this.number_of_ratings = number_of_ratings;
    }

    public void setId_manufacturer(int id_manufacturer) {
        this.id_manufacturer = id_manufacturer;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setNumber_of_ratings(int number_of_ratings) {
        this.number_of_ratings = number_of_ratings;
    }

    public int getId_manufacturer() {
        return id_manufacturer;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getEmail() {
        return email;
    }

    public double getRating() {
        return rating;
    }

    public int getNumber_of_ratings() {
        return number_of_ratings;
    }
}
