package com.harshit.cafeshopapp.activity.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.harshit.cafeshopapp.R;
import com.harshit.cafeshopapp.activity.activity.CartActivity;
import com.harshit.cafeshopapp.activity.eventbus.updatecartEvent;
import com.harshit.cafeshopapp.activity.model.CartModel;
import com.harshit.cafeshopapp.activity.model.CoffeeModel;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class cartAdapter extends RecyclerView.Adapter<cartAdapter.MyCartViewHolder> {

    private Context context;
    private List<CartModel> cartModelList;

    public cartAdapter(Context context, List<CartModel> cartModelList) {
        this.context = context;
        this.cartModelList = cartModelList;
    }

    @NonNull
    @Override
    public MyCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyCartViewHolder(LayoutInflater.from(context).inflate(R.layout.cart_list_item_design, parent, false));



    }



    @Override
    public void onBindViewHolder(@NonNull MyCartViewHolder holder, int position) {

        holder.txtCoffeeNameCart.setText(new StringBuilder().append(cartModelList.get(position).getName()));
        holder.txtPriceCart.setText(new StringBuilder("Rs.").append(cartModelList.get(position).getPrices()));
        holder.txtQuantityCart.setText(new StringBuilder().append(cartModelList.get(position).getQuantity()));

        holder.btnMinusQuantity.setOnClickListener(v -> {
            minusCartItem(holder, cartModelList.get(position));
        });

        holder.btnPlusQuantity.setOnClickListener(v -> {
            plusCartItem(holder, cartModelList.get(position));
        });

        holder.btnDeleteItem.setOnClickListener(v -> {
            AlertDialog dialog = new AlertDialog.Builder(context)
                    .setTitle("Delete Item")
                    .setMessage("Do you really want to delete this Item")
                    .setNegativeButton("cancel", (dialog1, which) -> dialog1.dismiss())
                    .setPositiveButton("Ok", (dialog12, which) -> {

                        notifyItemRemoved(position);

                        deleteFromFirebase(cartModelList.get(position));
                        dialog12.dismiss();




                    }).create();
            dialog.show();
        });
        
    }



    private void deleteFromFirebase(CartModel cartModel) {

        FirebaseDatabase.getInstance()
                .getReference("cart")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(cartModel.getKey())
                .removeValue()
                .addOnSuccessListener(aVoid -> EventBus.getDefault().postSticky(new updatecartEvent()));


    }

    private void plusCartItem(MyCartViewHolder holder, CartModel cartModel) {


        float totalPrice = Float.parseFloat(cartModel.getPrices());
        cartModel.setQuantity(cartModel.getQuantity()+1);
        cartModel.setTotalPrice(cartModel.getQuantity()*totalPrice);
        holder.txtQuantityCart.setText(new StringBuilder().append(cartModel.getQuantity()));
        updateFirebase(cartModel);
    }


    private void minusCartItem(MyCartViewHolder holder, CartModel cartModel) {

        if(cartModel.getQuantity() > 1)
        {

            cartModel.setQuantity(cartModel.getQuantity() - 1);
            cartModel.setTotalPrice(cartModel.getQuantity()*Float.parseFloat(cartModel.getPrices()));

            holder.txtQuantityCart.setText(new StringBuilder().append(cartModel.getQuantity()));
            updateFirebase(cartModel);

        }else {
            Toast.makeText(context, "cannot be less than 1", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateFirebase(CartModel cartModel) {

        FirebaseDatabase.getInstance()
                .getReference("cart")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(cartModel.getKey())
                .setValue(cartModel)
                .addOnSuccessListener(aVoide -> EventBus.getDefault().postSticky(new updatecartEvent()));

    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
    }

    public class MyCartViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.btnMinusQuantity)
        ImageView btnMinusQuantity;
        @BindView(R.id.btnPlusQuantity)
        ImageView btnPlusQuantity;
        @BindView(R.id.btnDeleteItem)
        ImageView btnDeleteItem;
        @BindView(R.id.txtCoffeeNameCart)
        TextView txtCoffeeNameCart;
        @BindView(R.id.txtPriceCart)
        TextView txtPriceCart;
        @BindView(R.id.txtQuantityCart)
        TextView txtQuantityCart;

        Unbinder unbinder;
        public MyCartViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }
}
