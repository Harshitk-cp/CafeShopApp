package com.harshit.cafeshopapp.activity.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.harshit.cafeshopapp.R;
import com.harshit.cafeshopapp.activity.activity.CartActivity;
import com.harshit.cafeshopapp.activity.eventbus.updatecartEvent;
import com.harshit.cafeshopapp.activity.listener.IFavLoadListener;
import com.harshit.cafeshopapp.activity.model.CartModel;
import com.harshit.cafeshopapp.activity.model.CoffeeModel;
import com.harshit.cafeshopapp.activity.adapter.coffeeAdapter;
import com.harshit.cafeshopapp.activity.listener.ICartLoadListener;
import com.harshit.cafeshopapp.activity.listener.ICoffeeLoadListener;
import com.harshit.cafeshopapp.activity.model.FavModel;
import com.nex3z.notificationbadge.NotificationBadge;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

public class HomeFragment extends Fragment implements ICartLoadListener, ICoffeeLoadListener, IFavLoadListener {
  RecyclerView rvHome;
  ICartLoadListener cartLoadListener;
  ICoffeeLoadListener coffeeLoadListener;
  IFavLoadListener favLoadListener;
  NotificationBadge badge;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_home, container, false);

    rvHome = view.findViewById(R.id.rvHome);
    rvHome.setLayoutManager(new LinearLayoutManager(getContext()));

    init();
    loadCoffeeFromFirebase();
    countCartItem();

    mProgressDialog = new ProgressDialog(this.getContext());
    mProgressDialog.setCancelable(false);
    mProgressDialog.show();
    mProgressDialog.setContentView(R.layout.coffee_progress_layout);
    mProgressDialog.getWindow().setBackgroundDrawableResource(
      android.R.color.transparent
    );

    Button btnCart = view.findViewById(R.id.btnCart);
    btnCart.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent myintent = new Intent(getActivity(), CartActivity.class);
        startActivity(myintent);
      }
    });

    return view;
  }

  public static ProgressDialog mProgressDialog;

  private void loadCoffeeFromFirebase() {
    List<CoffeeModel> coffees = new ArrayList<>();

    FirebaseDatabase instance = FirebaseDatabase.getInstance();
    DatabaseReference menu = instance.getReference("menu");

    ValueEventListener listener = new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot collection) {
        if (collection.exists()) {
          for (DataSnapshot coffeeItem : collection.getChildren()) {
            String key = coffeeItem.getKey();
            String name = coffeeItem.child("name").getValue(String.class);
            String description = coffeeItem.child("description").getValue(String.class);
            Integer price = coffeeItem.child("price").getValue(Integer.class);
            String imgUrl = coffeeItem.child("imgUrl").getValue(String.class);
            CoffeeModel coffee = new CoffeeModel(key, name, description, String.valueOf(price), imgUrl);
            coffees.add(coffee);
          }
          coffeeLoadListener.onCoffeeLoadSuccess(coffees);
        } else {
          // TODO: Just directly show error toast
          coffeeLoadListener.onCoffeeLoadFailed("Error: Firebase menu list not found.");
        }
      }

      @Override
      public void onCancelled(DatabaseError error) {
        // TODO: Just directly show error toast
        coffeeLoadListener.onCoffeeLoadFailed(error.getMessage());
      }
    };

    menu.addListenerForSingleValueEvent(listener);
  }

  private void init() {

    cartLoadListener = HomeFragment.this;
    coffeeLoadListener = HomeFragment.this;
    favLoadListener = HomeFragment.this;
  }

  @Override
  public void onStart() {
    super.onStart();
    EventBus.getDefault().register(this);
  }

  @Override
  public void onStop() {
    if (EventBus.getDefault().hasSubscriberForEvent(updatecartEvent.class))
      EventBus.getDefault().removeStickyEvent(updatecartEvent.class);
    EventBus.getDefault().unregister(this);
    super.onStop();
  }

  @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
  public void onUpdateCart(updatecartEvent event) {
    countCartItem();
  }

  @Override
  public void onCartLoadSuccess(List<CartModel> cartModelList) {

    int cartSum = 0;
    for (CartModel cartModel : cartModelList)
      cartSum += cartModel.getQuantity();

    badge = (NotificationBadge) requireActivity().findViewById(R.id.notifBadge);
    badge.setNumber(cartSum);
  }

  @Override
  public void onCartLoadFailed(String message) {
    Snackbar.make(getActivity().findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
  }

  @Override
  public void onCoffeeLoadSuccess(List<CoffeeModel> coffeeModelList) {
    coffeeAdapter adapter = new coffeeAdapter(this.getContext(), coffeeModelList, cartLoadListener, favLoadListener);
    rvHome.setAdapter(adapter);
  }

  @Override
  public void onCoffeeLoadFailed(String message) {
    Snackbar.make(getActivity().findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
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
          for (DataSnapshot cartSnapshot : snapshot.getChildren()) {
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

  @Override
  public void onFavLoadSuccess(List<FavModel> favModelList) {

  }

  @Override
  public void onFavLoadFailed(String message) {
    Snackbar.make(getActivity().findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
  }
}