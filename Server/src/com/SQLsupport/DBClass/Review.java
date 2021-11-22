package com.SQLsupport.DBClass;

import java.io.Serializable;

public class Review implements Serializable {
    private int id_review;
    private String nameUser;
    private int id_product;
    private String nameProduct;
    private String review_text;
    private int id_user;

    public Review(String nameUser, String review_text) {
        this.nameUser = nameUser;
        this.review_text = review_text;
    }

    public Review(int id_product, String review_text, int id_user) {
        this.id_product = id_product;
        this.review_text = review_text;
        this.id_user = id_user;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_review() {
        return id_review;
    }

    public String getNameUser() {
        return nameUser;
    }

    public int getId_product() {
        return id_product;
    }

    public String getReview_text() {
        return review_text;
    }
}
