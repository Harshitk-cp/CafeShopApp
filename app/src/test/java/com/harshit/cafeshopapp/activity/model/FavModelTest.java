package com.harshit.cafeshopapp.activity.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FavModelTest {
  @Test
  void constructor() {
    IllegalArgumentException e1 = assertThrows(IllegalArgumentException.class, () -> {
      new FavModel("", "Caramel Macchiato", "100", "img-url");
    });
    assertEquals("Provided key is an empty string.", e1.getMessage());

    IllegalArgumentException e2 = assertThrows(IllegalArgumentException.class, () -> {
      new FavModel("test-key", "", "100", "img-url");
    });
    assertEquals("Provided name is an empty string.", e2.getMessage());

    assertThrows(NumberFormatException.class, () -> {
      new FavModel("test-key", "Caramel Macchiato", "nan", "img-url");
    });

    assertThrows(ArithmeticException.class, () -> {
      new FavModel("test-key", "Caramel Macchiato", "-1", "img-url");
    });

    IllegalArgumentException e3 = assertThrows(IllegalArgumentException.class, () -> {
      new FavModel("test-key", "Caramel Macchiato", "100", "");
    });
    assertEquals("Provided img url is an empty string.", e3.getMessage());

    assertDoesNotThrow(() -> {
      new FavModel("test-key", "Caramel Macchiato", "100", "img-url");
    });
  }

  @Test
  void getKey() {
    FavModel model = new FavModel("test-key", "Caramel Macchiato", "100", "img-url");
    assertEquals("test-key", model.getKey());
  }

  @Test
  void setKey() {
    FavModel model = new FavModel("test-key", "Caramel Macchiato", "100", "img-url");

    model.setKey("test-key-2");
    assertEquals("test-key-2", model.getKey());

    IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
      model.setKey("");
    });
    assertEquals("Provided key is an empty string.", e.getMessage());
  }

  @Test
  void getName() {
    FavModel model = new FavModel("test-key", "Caramel Macchiato", "100", "img-url");
    assertEquals("Caramel Macchiato", model.getName());
  }

  @Test
  void setName() {
    FavModel model = new FavModel("test-key", "Caramel Macchiato", "100", "img-url");

    model.setName("test-name");
    assertEquals("test-name", model.getName());

    IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
      model.setName("");
    });
    assertEquals("Provided name is an empty string.", e.getMessage());
  }

  @Test
  void getPrices() {
    FavModel model = new FavModel("test-key", "Caramel Macchiato", "100", "img-url");
    assertEquals("100", model.getPrices());
  }

  @Test
  void setPrices() {
    FavModel model = new FavModel("test-key", "Caramel Macchiato", "100", "img-url");

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
    FavModel model = new FavModel("test-key", "Caramel Macchiato", "100", "img-url");
    assertEquals("img-url", model.getImgUrl());
  }

  @Test
  void setImgUrl() {
    FavModel model = new FavModel("test-key", "Caramel Macchiato", "100", "img-url");

    model.setImgUrl("test-img-url");
    assertEquals("test-img-url", model.getImgUrl());

    IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
      model.setImgUrl("");
    });
    assertEquals("Provided img url is an empty string.", e.getMessage());
  }
}