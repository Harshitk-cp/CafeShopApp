package com.harshit.cafeshopapp.activity.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FavModelTest {
  @Test
  void constructor() {
    IllegalArgumentException e1 = assertThrows(IllegalArgumentException.class, () -> {
      new FavModel("", "Caramel Macchiato", "100");
    });
    assertEquals("Provided key is an empty string.", e1.getMessage());

    IllegalArgumentException e2 = assertThrows(IllegalArgumentException.class, () -> {
      new FavModel("test-key", "", "100");
    });
    assertEquals("Provided name is an empty string.", e2.getMessage());

    assertThrows(NumberFormatException.class, () -> {
      new FavModel("test-key", "Caramel Macchiato", "nan");
    });

    assertThrows(ArithmeticException.class, () -> {
      new FavModel("test-key", "Caramel Macchiato", "-1");
    });

    assertDoesNotThrow(() -> {
      new FavModel("test-key", "Caramel Macchiato", "100");
    });
  }

  @Test
  void getKey() {
    FavModel model = new FavModel("test-key", "Caramel Macchiato", "100");
    assertEquals("test-key", model.getKey());
  }

  @Test
  void setKey() {
    FavModel model = new FavModel("test-key", "Caramel Macchiato", "100");

    model.setKey("test-key-2");
    assertEquals("test-key-2", model.getKey());

    IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
      model.setKey("");
    });
    assertEquals("Provided key is an empty string.", e.getMessage());
  }

  @Test
  void getName() {
    FavModel model = new FavModel("test-key", "Caramel Macchiato", "100");
    assertEquals("Caramel Macchiato", model.getName());
  }

  @Test
  void setName() {
    FavModel model = new FavModel("test-key", "Caramel Macchiato", "100");

    model.setName("test-name");
    assertEquals("test-name", model.getName());

    IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
      model.setName("");
    });
    assertEquals("Provided name is an empty string.", e.getMessage());
  }

  @Test
  void getPrices() {
    FavModel model = new FavModel("test-key", "Caramel Macchiato", "100");
    assertEquals("100", model.getPrices());
  }

  @Test
  void setPrices() {
    FavModel model = new FavModel("test-key", "Caramel Macchiato", "100");

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