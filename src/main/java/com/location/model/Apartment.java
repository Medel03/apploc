package com.location.model;

public class Apartment {
    private String type;
    private String quartier;
    private int price;

    public Apartment(String type, String quartier, int price) {
        this.type = type;
        this.quartier = quartier;
        this.price = price;
    }

    // Getters et Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQuartier() {
        return quartier;
    }

    public void setQuartier(String quartier) {
        this.quartier = quartier;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}


