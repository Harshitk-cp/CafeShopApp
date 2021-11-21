package com.harshit.cafeshopapp.activity.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CoffeeModelTest {
  @Test
  void constructor() {
    IllegalArgumentException e1 = assertThrows(IllegalArgumentException.class, () -> {
      new CoffeeModel("", "Affogato", "Affogato Coffee", "100");
    });
    assertEquals("Provided key is an empty string.", e1.getMessage());

    IllegalArgumentException e2 = assertThrows(IllegalArgumentException.class, () -> {
      new CoffeeModel("test-key", "", "Affogato Coffee", "100");
    });
    assertEquals("Provided name is an empty string.", e2.getMessage());

    IllegalArgumentException e3 = assertThrows(IllegalArgumentException.class, () -> {
      new CoffeeModel("test-key", "Affogato", "", "100");
    });
    assertEquals("Provided description is an empty string.", e3.getMessage());

    assertThrows(NumberFormatException.class, () -> {
      new CoffeeModel("test-key", "Affogato", "Affogato Coffee", "nan");
    });

    assertThrows(ArithmeticException.class, () -> {
      new CoffeeModel("test-key", "Affogato", "Affogato Coffee", "-1");
    });

    assertDoesNotThrow( () -> {
      new CoffeeModel("test-key", "Affogato", "Affogato Coffee", "100");
    });
  }

  @Test
  void getKey() {
    CoffeeModel model = new CoffeeModel("test-key", "Affogato", "Affogato Coffee", "100");
    assertEquals("test-key", model.getKey());
  }

  @Test
  void setKey() {
    CoffeeModel model = new CoffeeModel("test-key", "Affogato", "Affogato Coffee", "100");

    model.setKey("test-key-2");
    assertEquals("test-key-2", model.getKey());

    IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
      model.setKey("");
    });
    assertEquals("Provided key is an empty string.", e.getMessage());
  }

  @Test
  void getName() {
    CoffeeModel model = new CoffeeModel("test-key", "Affogato", "Affogato Coffee", "100");
    assertEquals("Affogato", model.getName());
  }

  @Test
  void setName() {
    CoffeeModel model = new CoffeeModel("test-key", "Affogato", "Affogato Coffee", "100");

    model.setName("test-name");
    assertEquals("test-name", model.getName());

    IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
      model.setName("");
    });
    assertEquals("Provided name is an empty string.", e.getMessage());
  }

  @Test
  void getDescription() {
    CoffeeModel model = new CoffeeModel("test-key", "Affogato", "Affogato Coffee", "100");
    assertEquals("Affogato Coffee", model.getDescription());
  }

  @Test
  void setDescription() {
    CoffeeModel model = new CoffeeModel("test-key", "Affogato", "Affogato Coffee", "100");

    model.setDescription("test-description");
    assertEquals("test-description", model.getDescription());

    IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
      model.setDescription("");
    });
    assertEquals("Provided description is an empty string.", e.getMessage());
  }

  @Test
  void getPrices() {
    CoffeeModel model = new CoffeeModel("test-key", "Affogato", "Affogato Coffee", "100");
    assertEquals("100", model.getPrices());
  }

  @Test
  void setPrices() {
    CoffeeModel model = new CoffeeModel("test-key", "Affogato", "Affogato Coffee", "100");

    model.setPrices("101");
    assertEquals("101", model.getPrices());

    assertThrows(NumberFormatException.class, () -> {
      model.setPrices("");
    });

    assertThrows(NumberFormatException.class, () -> {
      model.setPrices("nan");
    });

    assertThrows(ArithmeticException.class, () -> {
      model.setPrices("-1");
    });
  }
}