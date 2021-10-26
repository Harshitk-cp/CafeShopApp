package com.harshit.cafeshopapp.activity.listener;


import com.harshit.cafeshopapp.activity.model.FavModel;

import java.util.List;

public interface IFavLoadListener {

    void onFavLoadSuccess(List<FavModel> favModelList);
    void onFavLoadFailed(String message);

}
