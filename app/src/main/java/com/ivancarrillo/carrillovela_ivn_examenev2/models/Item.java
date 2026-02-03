package com.ivancarrillo.carrillovela_ivn_examenev2.models;

import io.realm.RealmObject;

public class Item extends RealmObject {

    private String name;
    private String category;
    private double price;
    private int quantity;
    private boolean purchased;

    public Item() {
    }

    public Item(String name, String category, double price, int quantity, boolean purchased) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.purchased = purchased;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }
}
