package com.harshit.cafeshopapp.activity.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.harshit.cafeshopapp.R;
import com.harshit.cafeshopapp.activity.activity.CartActivity;
import com.harshit.cafeshopapp.activity.eventbus.updatecartEvent;
import com.harshit.cafeshopapp.activity.model.CartModel;
import com.harshit.cafeshopapp.activity.model.CoffeeModel;
import com.harshit.cafeshopapp.activity.adapter.coffeeAdapter;
import com.harshit.cafeshopapp.activity.listener.ICartLoadListener;
import com.harshit.cafeshopapp.activity.listener.ICoffeeLoadListener;
import com.nex3z.notificationbadge.NotificationBadge;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class HomeFragment extends Fragment implements ICartLoadListener, ICoffeeLoadListener {


    FirebaseAuth mAuth;
    RecyclerView rvHome;
    coffeeAdapter coffeeAdapter;
    Fragment fragment;
    NotificationBadge notifBadge;
    ICartLoadListener cartLoadListener;
    ICoffeeLoadListener coffeeLoadListener;

    View itemCoffee;
    NotificationBadge badge;
    TextView txtTotalPrice;
    Button btnCart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        rvHome = (RecyclerView)view.findViewById(R.id.rvHome);
        rvHome.setLayoutManager(new LinearLayoutManager(getContext()));


        init();
        loadCoffeeFromFirebase();
        countCartItem();

        Button btnCart = (Button) view.findViewById(R.id.btnCart);
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myintent = new Intent(getActivity(), CartActivity.class);
                startActivity(myintent);
            }
        });

        return view;


    }

    private void loadCoffeeFromFirebase() {

        List<CoffeeModel> coffeeModels = new ArrayList<>();
        FirebaseDatabase.getInstance()
                .getReference("coffees")
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(snapshot.exists())
                        {
                            for(DataSnapshot coffeeSnapshot:snapshot.getChildren())
                            {
                                CoffeeModel coffeeModel = coffeeSnapshot.getValue(CoffeeModel.class);
                                coffeeModel.setKey(coffeeSnapshot.getKey());
                                coffeeModels.add(coffeeModel);
                            }
                            coffeeLoadListener.onCoffeeLoadSuccess(coffeeModels);
                        }
                        else
                            coffeeLoadListener.onCoffeeLoadFailed("Firebase load error");

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        coffeeLoadListener.onCoffeeLoadFailed(error.getMessage());
                    }
                });
    }


    private void init() {


        cartLoadListener = HomeFragment.this;
        coffeeLoadListener = HomeFragment.this;




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
        countCartItem();
    }

    @Override
    public void onCartLoadSuccess(List<CartModel> cartModelList) {

        int cartSum = 0;
        for(CartModel cartModel: cartModelList)
            cartSum += cartModel.getQuantity();

        badge = (NotificationBadge) requireActivity().findViewById(R.id.notifBadge);
        badge.setNumber(cartSum);

    }

    @Override
    public void onCartLoadFailed(String message) {
        Toast.makeText(this.getContext(), "items added to cart", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCoffeeLoadSuccess(List<CoffeeModel> coffeeModelList) {
        coffeeAdapter adapter = new coffeeAdapter(this.getContext(), coffeeModelList, cartLoadListener);
        rvHome.setAdapter(adapter);
    }

    @Override
    public void onCoffeeLoadFailed(String message) {
        Toast.makeText(this.getContext(), "coffee error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        countCartItem();
    }


    private void countCartItem() {
        List<CartModel> cartModels = new ArrayList<>();
        FirebaseDatabase
                .getInstance().getReference("cart")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot cartSnapshot: snapshot.getChildren())
                        {
                            CartModel cartmodel = cartSnapshot.getValue(CartModel.class);
                            cartmodel.setKey(cartSnapshot.getKey());
                            cartModels.add(cartmodel);
                        }
                        cartLoadListener.onCartLoadSuccess(cartModels);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}
