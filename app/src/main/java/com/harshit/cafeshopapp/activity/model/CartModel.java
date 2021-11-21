package com.harshit.cafeshopapp.activity.model;

public class CartModel {
  private String _key;
  private String _name;
  private String _price;
  private int _quantity;

  public CartModel() {
  }

  public CartModel(String key, String name, String price, int qualtity) {
    this.setKey(key);
    this.setName(name);
    this.setPrices(price);
    this.setQuantity(qualtity);
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

  public int getQuantity() {
    return this._quantity;
  }

  public void setQuantity(int quantity) {
    if (quantity < 0) {
      throw new ArithmeticException("Quantity cannot be negative.");
    }
    this._quantity = quantity;
  }

  public float getTotalPrice() {
    return Float.parseFloat(this._price) * this._quantity;
  }
}
