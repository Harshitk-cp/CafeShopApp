package com.harshit.cafeshopapp.activity.listener;

import com.harshit.cafeshopapp.activity.model.CartModel;

import java.util.List;

public interface ICartLoadListener {

    void onCartLoadSuccess(List<CartModel> cartModelList);
    void onCartLoadFailed(String message);

}
