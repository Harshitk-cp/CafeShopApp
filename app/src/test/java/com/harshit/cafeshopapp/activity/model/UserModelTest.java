package com.harshit.cafeshopapp.activity.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UserModelTest {
  @Test
  void constructor() {
    IllegalArgumentException e1 = assertThrows(IllegalArgumentException.class, () -> {
      new UserModel("", "name", "address", "city", "state", "zip");
    });
    assertEquals("Provided key is an empty string.", e1.getMessage());

    IllegalArgumentException e2 = assertThrows(IllegalArgumentException.class, () -> {
      new UserModel("key", "", "address", "city", "state", "zip");
    });
    assertEquals("Provided name is an empty string.", e2.getMessage());

    IllegalArgumentException e3 = assertThrows(IllegalArgumentException.class, () -> {
      new UserModel("key", "name", "", "city", "state", "zip");
    });
    assertEquals("Provided address is an empty string.", e3.getMessage());

    IllegalArgumentException e4 = assertThrows(IllegalArgumentException.class, () -> {
      new UserModel("key", "name", "address", "", "state", "zip");
    });
    assertEquals("Provided city is an empty string.", e4.getMessage());

    IllegalArgumentException e5 = assertThrows(IllegalArgumentException.class, () -> {
      new UserModel("key", "name", "address", "city", "", "zip");
    });
    assertEquals("Provided state is an empty string.", e5.getMessage());

    IllegalArgumentException e6 = assertThrows(IllegalArgumentException.class, () -> {
      new UserModel("key", "name", "address", "city", "state", "");
    });
    assertEquals("Provided zip is an empty string.", e6.getMessage());

    assertDoesNotThrow(() -> {
      new UserModel("key", "name", "address", "city", "state", "zip");
    });
  }

  @Test
  void getKey() {
    UserModel model = new UserModel("key", "name", "address", "city", "state", "zip");
    assertEquals("key", model.getKey());
  }

  @Test
  void setKey() {
    UserModel model = new UserModel("key", "name", "address", "city", "state", "zip");

    model.setKey("key-2");
    assertEquals("key-2", model.getKey());

    IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
      model.setKey("");
    });
    assertEquals("Provided key is an empty string.", e.getMessage());
  }

  @Test
  void getName() {
    UserModel model = new UserModel("key", "name", "address", "city", "state", "zip");
    assertEquals("name", model.getName());
  }

  @Test
  void setName() {
    UserModel model = new UserModel("key", "name", "address", "city", "state", "zip");

    model.setName("name-2");
    assertEquals("name-2", model.getName());

    IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
      model.setName("");
    });
    assertEquals("Provided name is an empty string.", e.getMessage());
  }

  @Test
  void getAddress() {
    UserModel model = new UserModel("key", "name", "address", "city", "state", "zip");
    assertEquals("address", model.getAddress());
  }

  @Test
  void setAddress() {
    UserModel model = new UserModel("key", "name", "address", "city", "state", "zip");

    model.setAddress("address-2");
    assertEquals("address-2", model.getAddress());

    IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
      model.setAddress("");
    });
    assertEquals("Provided address is an empty string.", e.getMessage());
  }

  @Test
  void getCity() {
    UserModel model = new UserModel("key", "name", "address", "city", "state", "zip");
    assertEquals("city", model.getCity());
  }

  @Test
  void setCity() {
    UserModel model = new UserModel("key", "name", "address", "city", "state", "zip");

    model.setCity("city-2");
    assertEquals("city-2", model.getCity());

    IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
      model.setCity("");
    });
    assertEquals("Provided city is an empty string.", e.getMessage());
  }

  @Test
  void getState() {
    UserModel model = new UserModel("key", "name", "address", "city", "state", "zip");
    assertEquals("state", model.getState());
  }

  @Test
  void setState() {
    UserModel model = new UserModel("key", "name", "address", "city", "state", "zip");

    model.setState("state-2");
    assertEquals("state-2", model.getState());

    IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
      model.setState("");
    });
    assertEquals("Provided state is an empty string.", e.getMessage());
  }

  @Test
  void getZip() {
    UserModel model = new UserModel("key", "name", "address", "city", "state", "zip");
    assertEquals("zip", model.getZip());
  }

  @Test
  void setZip() {
    UserModel model = new UserModel("key", "name", "address", "city", "state", "zip");

    model.setZip("zip-2");
    assertEquals("zip-2", model.getZip());

    IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
      model.setZip("");
    });
    assertEquals("Provided zip is an empty string.", e.getMessage());
  }
}