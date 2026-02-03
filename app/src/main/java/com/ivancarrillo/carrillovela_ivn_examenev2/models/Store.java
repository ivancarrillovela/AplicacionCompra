package com.ivancarrillo.carrillovela_ivn_examenev2.models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Store extends RealmObject {

    @PrimaryKey
    private int id;
    private String name;
    private String address;
    private Double lat;
    private Double lon;
    private boolean isActive;
    private RealmList<Item> items;

    public Store() {
    }

    public Store(String name, String address, Double lat, Double lon, boolean isActive) {
        // Generamos el ID aquí usando AtomicInteger de MyApp para simular Autoincrement
        // y cumplir la restricción de NO modificar Utils.java.
        this.id = com.ivancarrillo.carrillovela_ivn_examenev2.app.MyApp.StoreID.incrementAndGet();
        this.name = name;
        this.address = address;
        this.lat = lat;
        this.lon = lon;
        this.isActive = isActive;
        this.items = new RealmList<>();
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

    public String getAddress() {
        return address;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public RealmList<Item> getItems() {
        return items;
    }
}
