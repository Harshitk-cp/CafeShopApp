package com.harshit.cafeshopapp.activity.model;

public class CoffeeModel {
  private String _key;
  private String _name;
  private String _description;
  private String _price;

  public CoffeeModel() {
  }

  public CoffeeModel(String key, String name, String description, String price) {
    this._key = key;
    this._name = name;
    this._description = description;
    this._price = price;
  }

  public String getKey() {
    return this._key;
  }

  public void setKey(String key) {
    if (!key.isEmpty()) {
      this._key = key;
    }
  }

  public String getName() {
    return this._name;
  }

  public void setName(String name) {
    if (!name.isEmpty()) {
      this._name = name;
    }
  }

  public String getDescription() {
    return this._description;
  }

  public void setDescription(String description) {
    if (!description.isEmpty()) {
      this._description = description;
    }
  }

  public String getPrices() {
    return this._price;
  }

  public void setPrices(String price) {
    double n = Double.parseDouble(price);
    if (n < 0.0) {
      throw new ArithmeticException("Price cannot be negative.");
    }
    this._price = price;
  }
}
