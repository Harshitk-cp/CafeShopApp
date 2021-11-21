package com.harshit.cafeshopapp.activity.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.harshit.cafeshopapp.R;
import com.harshit.cafeshopapp.activity.adapter.previewAdapter;
import com.harshit.cafeshopapp.activity.eventbus.updatecartEvent;
import com.harshit.cafeshopapp.activity.listener.ICartLoadListener;
import com.harshit.cafeshopapp.activity.model.CartModel;
import com.harshit.cafeshopapp.activity.model.UserModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class PreviewActivity extends AppCompatActivity implements ICartLoadListener {
  ICartLoadListener cartLoadListener;
  RecyclerView rvPreview;
  EditText etNameInfo;
  EditText etAddressInfo;
  EditText etCityInfo;
  EditText etStateInfo;
  EditText etZipInfo;
  Button btnPlaceOrder;
  DatabaseReference userDatabase;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_place_order);

    rvPreview = findViewById(R.id.rvPreview);
    etNameInfo = findViewById(R.id.etRegisterName);
    etAddressInfo = findViewById(R.id.etAddressInfo);
    etCityInfo = findViewById(R.id.etCityInfo);
    etStateInfo = findViewById(R.id.etStateInfo);
    etZipInfo = findViewById(R.id.etZipInfo);
    btnPlaceOrder = findViewById(R.id.btnPlaceOrder);

    Toolbar actionBarCart = findViewById(R.id.actionBarInfo);
    setSupportActionBar(actionBarCart);
    ActionBar actionBar = getSupportActionBar();
    getSupportActionBar().setTitle("Place Order");
    actionBar.setDisplayHomeAsUpEnabled(true);

    init();
    loadPreviewFromFirebase();

    userDatabase = FirebaseDatabase
      .getInstance().getReference("user")
      .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
      .child("2");

    btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {



          Toast.makeText(PreviewActivity.this, "your order was placed succesfully!!", Toast.LENGTH_SHORT).show();
          Intent intent = new Intent(PreviewActivity.this, DashboardActivity.class);
          startActivity(intent);
          finish();

      }
    });
  }



  private void loadPreviewFromFirebase() {
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
    rvPreview.setLayoutManager(layoutManager);
    rvPreview.addItemDecoration(new DividerItemDecoration(this, layoutManager.getOrientation()));
  }

  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      onBackPressed();
      return true;
    }
    return super.onOptionsItemSelected(item);
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
    loadPreviewFromFirebase();
  }

  @Override
  public void onCartLoadSuccess(List<CartModel> cartModelList) {

    previewAdapter previewAdapter = new previewAdapter(this, cartModelList);

    rvPreview.setAdapter(previewAdapter);
  }

  @Override
  public void onCartLoadFailed(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }
}