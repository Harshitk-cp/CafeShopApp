package com.harshit.cafeshopapp.activity.model;

public class CoffeeModel {

    public String key;
    public String description;
    public String name;
    public String prices;

    public CoffeeModel(){

    }

    public CoffeeModel(String description, String name, String prices) {
        this.description = description;
        this.name = name;
        this.prices = prices;
    }

    public String getKey() { return key; }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getPrices() {
        return prices;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrices(String price) {
        this.prices = price;
    }
}
