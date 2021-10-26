package com.harshit.cafeshopapp.activity.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.harshit.cafeshopapp.R;
import com.harshit.cafeshopapp.activity.adapter.favAdapter;
import com.harshit.cafeshopapp.activity.eventbus.updatecartEvent;
import com.harshit.cafeshopapp.activity.listener.IFavLoadListener;
import com.harshit.cafeshopapp.activity.model.FavModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class FavFragment extends Fragment implements IFavLoadListener {

    RecyclerView rvFav;
    IFavLoadListener favLoadListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fav, container, false);

        rvFav = view.findViewById(R.id.rvFav);
        rvFav.setLayoutManager(new LinearLayoutManager(getContext()));

        init();
        loadFavsFromFirebase();

        mProgressDialog = new ProgressDialog(this.getContext());
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        mProgressDialog.setContentView(R.layout.coffee_progress_layout);
        mProgressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );

        return view;

    }

    public static ProgressDialog mProgressDialog;

    private void loadFavsFromFirebase() {

        List<FavModel> favModels = new ArrayList<>();
        FirebaseDatabase.getInstance()
                .getReference("favs")
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(snapshot.exists())
                        {
                            for(DataSnapshot favSnapshot:snapshot.getChildren())
                            {
                                FavModel favModel = favSnapshot.getValue(FavModel.class);
                                favModel.setKey(favSnapshot.getKey());
                                favModels.add(favModel);
                            }
                            favLoadListener.onFavLoadSuccess(favModels);
                        }
                        else
                            favLoadListener.onFavLoadFailed("Firebase load error");

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        favLoadListener.onFavLoadFailed(error.getMessage());
                    }
                });
    }

    private void init() {


        favLoadListener = FavFragment.this;

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }


    @Override
    public void onStop() {
        if(EventBus.getDefault().hasSubscriberForEvent(updatecartEvent.class))
            EventBus.getDefault().removeStickyEvent(updatecartEvent.class);
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onUpdateCart(updatecartEvent event)
    {
        loadFavsFromFirebase();
    }


    @Override
    public void onFavLoadSuccess(List<FavModel> favModelList) {
        favAdapter adapter = new favAdapter(this.getContext(), favModelList, favLoadListener);
        rvFav.setAdapter(adapter);
    }

    @Override
    public void onFavLoadFailed(String message) {
        Toast.makeText(this.getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
