package com.harshit.cafeshopapp.activity.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.harshit.cafeshopapp.R;
import com.harshit.cafeshopapp.activity.eventbus.updatecartEvent;
import com.harshit.cafeshopapp.activity.fragments.FavFragment;
import com.harshit.cafeshopapp.activity.fragments.HomeFragment;
import com.harshit.cafeshopapp.activity.listener.IFavLoadListener;
import com.harshit.cafeshopapp.activity.model.CartModel;
import com.harshit.cafeshopapp.activity.model.CoffeeModel;
import com.harshit.cafeshopapp.activity.listener.ICartLoadListener;
import com.harshit.cafeshopapp.activity.listener.IRecyclerViewClickListener;
import com.harshit.cafeshopapp.activity.model.FavModel;

import org.greenrobot.eventbus.EventBus;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class coffeeAdapter extends RecyclerView.Adapter<coffeeAdapter.MyCoffeeViewHolder> {

    private Context context;
    private List<CoffeeModel> coffeeModelList;
    private ICartLoadListener iCartLoadListener;
    private IFavLoadListener iFavLoadListener;
    private TextView txtFavourite;

    public coffeeAdapter(Context context, List<CoffeeModel> coffeeModelList, ICartLoadListener iCartLoadListener, IFavLoadListener iFavLoadListener) {
        this.context = context;
        this.coffeeModelList = coffeeModelList;
        this.iCartLoadListener = iCartLoadListener;
        this.iFavLoadListener = iFavLoadListener;
    }

    @NonNull
    @Override
    public MyCoffeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyCoffeeViewHolder(LayoutInflater.from(context).inflate(R.layout.coffee_list_item_design, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull MyCoffeeViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.txtCoffeeName.setText(new StringBuilder().append(coffeeModelList.get(position).getName()));
        holder.txtPrice.setText(new StringBuilder("Rs.").append(coffeeModelList.get(position).getPrices()));

        holder.setListener(((view, adapterPosition) ->
        {
            addToCart(coffeeModelList.get(position));
        }));

        holder.itemCoffee.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                final Dialog dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.custom_dialog_coffee);
                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                TextView txtDialogCoffeeDesc;
                txtDialogCoffeeDesc = dialog.findViewById(R.id.txtDialogCoffeeDesc);
                txtDialogCoffeeDesc.setText(new StringBuilder().append(coffeeModelList.get(position).getName()));
                return true;
            }
        });

        holder.txtFavorite.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View view) {

                addToFavs(coffeeModelList.get(position));

            }
        });



        HomeFragment.mProgressDialog.dismiss();

    }


    private void deleteFromFavs(FavModel favModel) {

        FirebaseDatabase.getInstance()
                .getReference("favs")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(favModel.getKey())
                .removeValue()
                .addOnSuccessListener(aVoid -> EventBus.getDefault().postSticky(new updatecartEvent()));


    }

    private void addToFavs(CoffeeModel coffeeModel) {



        DatabaseReference userFavs = FirebaseDatabase
                .getInstance()
                .getReference("favs")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        userFavs.child(coffeeModel.getKey())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){


                            FavModel favModel = snapshot.getValue(FavModel.class);
                            deleteFromFavs(favModel);
                            iFavLoadListener.onFavLoadFailed("Item removed from Favourites");



                        }
                        else{

                            FavModel favModel = new FavModel();
                            favModel.setKey(coffeeModel.getKey());
                            favModel.setName(coffeeModel.getName());
                            favModel.setPrices(coffeeModel.getPrices());






                            userFavs.child(coffeeModel.getKey())
                                    .setValue(favModel)
                                    .addOnSuccessListener(aVoid -> {
                                        iFavLoadListener.onFavLoadFailed("Item added to Favourites");
                                    })
                                    .addOnFailureListener(e -> iFavLoadListener.onFavLoadFailed(e.getMessage()));

                        }
                        EventBus.getDefault().postSticky(new updatecartEvent());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        iFavLoadListener.onFavLoadFailed(error.getMessage());
                    }
                });

    }

    private void addToCart(CoffeeModel coffeeModel) {

        DatabaseReference userCart = FirebaseDatabase
                .getInstance()
                .getReference("cart")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        userCart.child(coffeeModel.getKey())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){

                            CartModel cartModel = snapshot.getValue(CartModel.class);
                            cartModel.setQuantity(cartModel.getQuantity()+1);
                            Map<String,Object> updateData = new HashMap<>();
                            updateData.put("quantity", cartModel.getQuantity());
                            updateData.put("totalPrice", cartModel.getQuantity()*Float.parseFloat(coffeeModel.getPrices()));

                            userCart.child(coffeeModel.getKey())
                                    .updateChildren(updateData)
                                    .addOnSuccessListener(aVoid -> {
                                        iCartLoadListener.onCartLoadFailed("Add to cart Success");
                                    })
                                    .addOnFailureListener(e -> iCartLoadListener.onCartLoadFailed(e.getMessage()));

                        }
                        else{

                            CartModel cartModel = new CartModel();
                            cartModel.setKey(coffeeModel.getKey());
                            cartModel.setName(coffeeModel.getName());
                            cartModel.setPrices(coffeeModel.getPrices());
                            cartModel.setQuantity(1);
                            cartModel.setTotalPrice(Float.parseFloat(coffeeModel.getPrices()));


                            userCart.child(coffeeModel.getKey())
                                    .setValue(cartModel)
                                    .addOnSuccessListener(aVoid -> {
                                        iCartLoadListener.onCartLoadFailed("Item added to cart");
                                    })
                                    .addOnFailureListener(e -> iCartLoadListener.onCartLoadFailed(e.getMessage()));

                        }
                        EventBus.getDefault().postSticky(new updatecartEvent());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        iCartLoadListener.onCartLoadFailed(error.getMessage());
                    }
                });

    }

    @Override
    public int getItemCount() {
        return coffeeModelList.size();
    }

    public class MyCoffeeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.txtCoffeeName)
        TextView txtCoffeeName;
        @BindView(R.id.txtPrice)
        TextView txtPrice;
        @BindView(R.id.itemCoffee)
        View itemCoffee;
        @BindView(R.id.txtFavorite)
        TextView txtFavorite;



        IRecyclerViewClickListener listener;




        public void setListener(IRecyclerViewClickListener listener) {
            this.listener = listener;
        }


        private Unbinder unbinder;
        public MyCoffeeViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);



        }


        @Override
        public void onClick(View view) {
            listener.onRecyclerClick(view, getAdapterPosition());


        }
    }





}
