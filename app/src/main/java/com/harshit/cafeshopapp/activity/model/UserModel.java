package com.harshit.cafeshopapp.activity.model;

public class UserModel {
  private String _key;
  private String _address;
  private String _city;
  private String _name;
  private String _state;
  private String _zip;

  public UserModel() {
  }

  public UserModel(String key, String name, String address, String city, String state, String zip) {
    this.setKey(key);
    this.setName(name);
    this.setAddress(address);
    this.setCity(city);
    this.setState(state);
    this.setZip(zip);
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

  public String getAddress() {
    return this._address;
  }

  public void setAddress(String address) {
    if (address.isEmpty()) {
      throw new IllegalArgumentException("Provided address is an empty string.");
    }
    this._address = address;
  }

  public String getCity() {
    return this._city;
  }

  public void setCity(String city) {
    if (city.isEmpty()) {
      throw new IllegalArgumentException("Provided city is an empty string.");
    }
    this._city = city;
  }

  public String getState() {
    return this._state;
  }

  public void setState(String state) {
    if (state.isEmpty()) {
      throw new IllegalArgumentException("Provided state is an empty string.");
    }
    this._state = state;
  }

  public String getZip() {
    return this._zip;
  }

  public void setZip(String zip) {
    if (zip.isEmpty()) {
      throw new IllegalArgumentException("Provided zip is an empty string.");
    }
    this._zip = zip;
  }
}
