package com.harshit.cafeshopapp.activity.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.harshit.cafeshopapp.R;
import com.harshit.cafeshopapp.activity.adapter.cartAdapter;
import com.harshit.cafeshopapp.activity.eventbus.updatecartEvent;
import com.harshit.cafeshopapp.activity.listener.ICartLoadListener;
import com.harshit.cafeshopapp.activity.model.CartModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartActivity extends AppCompatActivity implements ICartLoadListener {
  @BindView(R.id.txtTextTotalPrice)
  TextView txtTextTotalPrice;

  ICartLoadListener cartLoadListener;
  RecyclerView rvCart;
  TextView txtProceedToPay;
  ImageView imgEmptyCartIcon;
  TextView txtEmptyCartText;

  ConstraintLayout totalPriceBar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_cart);

    rvCart = findViewById(R.id.rvCart);
    imgEmptyCartIcon = findViewById(R.id.imgEmptyCartIcon);
    txtEmptyCartText = findViewById(R.id.txtEmptyCartText);
    totalPriceBar = findViewById(R.id.totalPriceBar);

    Toolbar actionBarCart = findViewById(R.id.actionBarCart);
    setSupportActionBar(actionBarCart);
    ActionBar actionBar = getSupportActionBar();
    getSupportActionBar().setTitle("Cart");
    actionBar.setDisplayHomeAsUpEnabled(true);

    txtProceedToPay = findViewById(R.id.txtProceedToPay);
    txtProceedToPay.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent myintent = new Intent(CartActivity.this, PreviewActivity.class);
        startActivity(myintent);
      }
    });

    init();
    loadCartFromFirebase();
  }

  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      onBackPressed();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private void loadCartFromFirebase() {

    List<CartModel> cartModels = new ArrayList<>();

    FirebaseDatabase.getInstance()
      .getReference("cart")
      .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
      .addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
          if (snapshot.exists()) {
            for (DataSnapshot cartSnapshot : snapshot.getChildren()) {
              CartModel cartModel = cartSnapshot.getValue(CartModel.class);
              cartModel.setKey(cartSnapshot.getKey());
              cartModels.add(cartModel);
            }
            cartLoadListener.onCartLoadSuccess(cartModels);
          } else
            System.out.println("Looks like cart is empty");
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
          cartLoadListener.onCartLoadFailed(error.getMessage());
        }
      });
  }

  private void init() {
    ButterKnife.bind(this);
    cartLoadListener = this;
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    rvCart.setLayoutManager(layoutManager);

    rvCart.addItemDecoration(new DividerItemDecoration(this, layoutManager.getOrientation()));
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
    loadCartFromFirebase();
  }

  @Override
  public void onCartLoadSuccess(List<CartModel> cartModelList) {
    double sum = 0;

    for (CartModel cartModel : cartModelList) {
      sum += cartModel.getTotalPrice();

    }

    txtTextTotalPrice.setText(new StringBuilder("Total Price-> Rs. ").append(sum));
    cartAdapter cartAdapter = new cartAdapter(this, cartModelList);

    if (cartAdapter == null) {
      txtEmptyCartText.setVisibility(View.VISIBLE);
      imgEmptyCartIcon.setVisibility(View.VISIBLE);
      totalPriceBar.setVisibility(View.GONE);
    } else {
      totalPriceBar.setVisibility(View.VISIBLE);
      txtEmptyCartText.setVisibility(View.GONE);
      imgEmptyCartIcon.setVisibility(View.GONE);
    }

    rvCart.setAdapter(cartAdapter);
  }

  @Override
  public void onCartLoadFailed(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }
}
