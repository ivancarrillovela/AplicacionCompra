package com.ivancarrillo.carrillovela_ivn_examenev2.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Item extends RealmObject {

    @PrimaryKey
    private int id;
    private String name;
    private String category;
    private double price;
    private int quantity;
    private boolean purchased;

    public Item() {
    }

    public Item(String name, String category, double price, int quantity, boolean purchased) {
        // Asignamos ID atómico en el constructor para persistencia correcta
        this.id = com.ivancarrillo.carrillovela_ivn_examenev2.app.MyApp.ItemID.incrementAndGet();
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.purchased = purchased;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
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
