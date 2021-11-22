package com.harshit.cafeshopapp.activity.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CoffeeModelTest {
  @Test
  void constructor() {
    IllegalArgumentException e1 = assertThrows(IllegalArgumentException.class, () -> {
      new CoffeeModel("", "Affogato", "Affogato Coffee", "100", "img-url");
    });
    assertEquals("Provided key is an empty string.", e1.getMessage());

    IllegalArgumentException e2 = assertThrows(IllegalArgumentException.class, () -> {
      new CoffeeModel("test-key", "", "Affogato Coffee", "100", "img-url");
    });
    assertEquals("Provided name is an empty string.", e2.getMessage());

    IllegalArgumentException e3 = assertThrows(IllegalArgumentException.class, () -> {
      new CoffeeModel("test-key", "Affogato", "", "100", "img-url");
    });
    assertEquals("Provided description is an empty string.", e3.getMessage());

    assertThrows(NumberFormatException.class, () -> {
      new CoffeeModel("test-key", "Affogato", "Affogato Coffee", "nan", "img-url");
    });

    assertThrows(ArithmeticException.class, () -> {
      new CoffeeModel("test-key", "Affogato", "Affogato Coffee", "-1", "img-url");
    });

    IllegalArgumentException e4 = assertThrows(IllegalArgumentException.class, () -> {
      new CoffeeModel("test-key", "Affogato", "Affogato Coffee", "100", "");
    });
    assertEquals("Provided img url is an empty string.", e4.getMessage());

    assertDoesNotThrow( () -> {
      new CoffeeModel("test-key", "Affogato", "Affogato Coffee", "100", "img-url");
    });
  }

  @Test
  void getKey() {
    CoffeeModel model = new CoffeeModel("test-key", "Affogato", "Affogato Coffee", "100", "img-url");
    assertEquals("test-key", model.getKey());
  }

  @Test
  void setKey() {
    CoffeeModel model = new CoffeeModel("test-key", "Affogato", "Affogato Coffee", "100", "img-url");

    model.setKey("test-key-2");
    assertEquals("test-key-2", model.getKey());

    IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
      model.setKey("");
    });
    assertEquals("Provided key is an empty string.", e.getMessage());
  }

  @Test
  void getName() {
    CoffeeModel model = new CoffeeModel("test-key", "Affogato", "Affogato Coffee", "100", "img-url");
    assertEquals("Affogato", model.getName());
  }

  @Test
  void setName() {
    CoffeeModel model = new CoffeeModel("test-key", "Affogato", "Affogato Coffee", "100", "img-url");

    model.setName("test-name");
    assertEquals("test-name", model.getName());

    IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
      model.setName("");
    });
    assertEquals("Provided name is an empty string.", e.getMessage());
  }

  @Test
  void getDescription() {
    CoffeeModel model = new CoffeeModel("test-key", "Affogato", "Affogato Coffee", "100", "img-url");
    assertEquals("Affogato Coffee", model.getDescription());
  }

  @Test
  void setDescription() {
    CoffeeModel model = new CoffeeModel("test-key", "Affogato", "Affogato Coffee", "100", "img-url");

    model.setDescription("test-description");
    assertEquals("test-description", model.getDescription());

    IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
      model.setDescription("");
    });
    assertEquals("Provided description is an empty string.", e.getMessage());
  }

  @Test
  void getPrices() {
    CoffeeModel model = new CoffeeModel("test-key", "Affogato", "Affogato Coffee", "100", "img-url");
    assertEquals("100", model.getPrices());
  }

  @Test
  void setPrices() {
    CoffeeModel model = new CoffeeModel("test-key", "Affogato", "Affogato Coffee", "100", "img-url");

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

  @Test
  void getImgUrl() {
    CoffeeModel model = new CoffeeModel("test-key", "Affogato", "Affogato Coffee", "100", "img-url");
    assertEquals("img-url", model.getImgUrl());
  }

  @Test
  void setImgUrl() {
    CoffeeModel model = new CoffeeModel("test-key", "Affogato", "Affogato Coffee", "100", "img-url");

    model.setImgUrl("test-img-url");
    assertEquals("test-img-url", model.getImgUrl());

    IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
      model.setImgUrl("");
    });
    assertEquals("Provided img url is an empty string.", e.getMessage());
  }
}