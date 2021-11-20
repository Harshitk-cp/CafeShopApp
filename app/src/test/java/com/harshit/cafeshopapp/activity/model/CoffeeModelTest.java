package com.harshit.cafeshopapp.activity.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CoffeeModelTest {
  @Test
  void getKey() {
    CoffeeModel model = new CoffeeModel("test-key", "Affogato", "Affogato Coffee", "100.0");
    assertEquals("test-key", model.getKey());
  }

  @Test
  void setKey() {
    CoffeeModel model = new CoffeeModel("test-key", "Affogato", "Affogato Coffee", "100.0");

    model.setKey("test-key-2");
    assertEquals("test-key-2", model.getKey());

    model.setKey("");
    assertNotEquals("", model.getKey());
  }

  @Test
  void getName() {
    CoffeeModel model = new CoffeeModel("test-key", "Affogato", "Affogato Coffee", "100.0");
    assertEquals("Affogato", model.getName());
  }

  @Test
  void setName() {
    CoffeeModel model = new CoffeeModel("test-key", "Affogato", "Affogato Coffee", "100.0");

    model.setName("test-name");
    assertEquals("test-name", model.getName());

    model.setName("");
    assertNotEquals("", model.getName());
  }

  @Test
  void getDescription() {
    CoffeeModel model = new CoffeeModel("test-key", "Affogato", "Affogato Coffee", "100.0");
    assertEquals("Affogato Coffee", model.getDescription());
  }

  @Test
  void setDescription() {
    CoffeeModel model = new CoffeeModel("test-key", "Affogato", "Affogato Coffee", "100.0");

    model.setDescription("test-description");
    assertEquals("test-description", model.getDescription());

    model.setDescription("");
    assertNotEquals("", model.getDescription());
  }

  @Test
  void getPrices() {
    CoffeeModel model = new CoffeeModel("test-key", "Affogato", "Affogato Coffee", "100.0");
    assertEquals("100.0", model.getPrices());
  }

  @Test
  void setPrices() {
    CoffeeModel model = new CoffeeModel("test-key", "Affogato", "Affogato Coffee", "100.0");

    model.setPrices("101.0");
    assertEquals("101.0", model.getPrices());

    assertThrows(NumberFormatException.class, () -> model.setPrices("not-a-number"));
    assertThrows(NumberFormatException.class, () -> model.setPrices(""));
    assertThrows(ArithmeticException.class, () -> model.setPrices("-1.0"));
  }
}