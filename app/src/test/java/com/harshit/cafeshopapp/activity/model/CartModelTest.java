package com.harshit.cafeshopapp.activity.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CartModelTest {
  @Test
  void constructor() {
    IllegalArgumentException e1 = assertThrows(IllegalArgumentException.class, () -> {
      new CartModel("", "Affogato", "100", 1, "img-url");
    });
    assertEquals("Provided key is an empty string.", e1.getMessage());

    IllegalArgumentException e2 = assertThrows(IllegalArgumentException.class, () -> {
      new CartModel("test-key", "", "100", 1, "img-url");
    });
    assertEquals("Provided name is an empty string.", e2.getMessage());

    assertThrows(NumberFormatException.class, () -> {
      new CartModel("test-key", "Affogato", "", 1, "img-url");
    });

    assertThrows(ArithmeticException.class, () -> {
      new CartModel("test-key", "Affogato", "-1", 1, "img-url");
    });

    assertThrows(ArithmeticException.class, () -> {
      new CartModel("test-key", "Affogato", "100", -1, "img-url");
    });

    IllegalArgumentException e3 = assertThrows(IllegalArgumentException.class, () -> {
      new CartModel("test-key", "Affogato", "100", 1, "");
    });
    assertEquals("Provided img url is an empty string.", e3.getMessage());

    assertDoesNotThrow(() -> {
      new CartModel("test-key", "Affogato", "100", 1, "img-url");
    });
  }

  @Test
  void getKey() {
    CartModel model = new CartModel("test-key", "Affogato", "100", 1, "img-url");
    assertEquals("test-key", model.getKey());
  }

  @Test
  void setKey() {
    CartModel model = new CartModel("test-key", "Affogato", "100", 1, "img-url");

    model.setKey("test-key-2");
    assertEquals("test-key-2", model.getKey());

    IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
      model.setKey("");
    });
    assertEquals("Provided key is an empty string.", e.getMessage());
  }

  @Test
  void getName() {
    CartModel model = new CartModel("test-key", "Affogato", "100", 1, "img-url");
    assertEquals("Affogato", model.getName());
  }

  @Test
  void setName() {
    CartModel model = new CartModel("test-key", "Affogato", "100", 1, "img-url");

    model.setName("test-name");
    assertEquals("test-name", model.getName());

    IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
      model.setName("");
    });
    assertEquals("Provided name is an empty string.", e.getMessage());
  }

  @Test
  void getPrices() {
    CartModel model = new CartModel("test-key", "Affogato", "100", 1, "img-url");
    assertEquals("100", model.getPrices());
  }

  @Test
  void setPrices() {
    CartModel model = new CartModel("test-key", "Affogato", "100", 1, "img-url");

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
  void getQuantity() {
    CartModel model = new CartModel("test-key", "Affogato", "100", 1, "img-url");
    assertEquals(1, model.getQuantity());
  }

  @Test
  void setQuantity() {
    CartModel model = new CartModel("test-key", "Affogato", "100", 1, "img-url");

    model.setQuantity(2);
    assertEquals(2, model.getQuantity());

    assertThrows(ArithmeticException.class, () -> {
      model.setQuantity(-1);
    });
  }

  @Test
  void getTotalPrice() {
    CartModel model = new CartModel("test-key", "Affogato", "100", 2, "img-url");
    assertEquals(200.0, model.getTotalPrice());
  }

  @Test
  void getImgUrl() {
    CartModel model = new CartModel("test-key", "Affogato", "100", 2, "img-url");
    assertEquals("img-url", model.getImgUrl());
  }

  @Test
  void setImgUrl() {
    CartModel model = new CartModel("test-key", "Affogato", "100", 2, "img-url");

    model.setImgUrl("test-img-url");
    assertEquals("test-img-url", model.getImgUrl());

    IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
      model.setImgUrl("");
    });
    assertEquals("Provided img url is an empty string.", e.getMessage());
  }
}