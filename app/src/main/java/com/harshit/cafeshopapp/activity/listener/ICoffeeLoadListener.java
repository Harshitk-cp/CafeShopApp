package com.harshit.cafeshopapp.activity.listener;

import com.harshit.cafeshopapp.activity.model.CoffeeModel;

import java.util.List;

public interface ICoffeeLoadListener {
  void onCoffeeLoadSuccess(List<CoffeeModel> coffeeModelList);

  void onCoffeeLoadFailed(String message);
}
