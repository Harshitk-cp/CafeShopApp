package com.harshit.cafeshopapp.activity.model;

public class FavModel {
  private String _key;
  private String _name;
  private String _price;

  public FavModel() {
  }

  public FavModel(String key, String name, String price) {
    this.setKey(key);
    this.setName(name);
    this.setPrices(price);
  }

  public String getKey() {
    return this._key;
  }

  public void setKey(String key) {
    if (key.isEmpty()) {
      throw new IllegalArgumentException("Provided key is an empty string.");
    }
    this._key = key;
  }

  public String getName() {
    return this._name;
  }

  public void setName(String name) {
    if (name.isEmpty()) {
      throw new IllegalArgumentException("Provided name is an empty string.");
    }
    this._name = name;
  }

  public String getPrices() {
    return this._price;
  }

  public void setPrices(String price) {
    double n = Integer.parseInt(price);
    if (n < 0) {
      throw new ArithmeticException("Price cannot be negative.");
    }
    this._price = price;
  }
}
