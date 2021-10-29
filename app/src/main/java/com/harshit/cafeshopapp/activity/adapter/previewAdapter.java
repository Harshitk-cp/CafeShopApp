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
import com.harshit.cafeshopapp.activity.eventbus.updatecartEvent;
import com.harshit.cafeshopapp.activity.model.CartModel;
import com.harshit.cafeshopapp.activity.model.CoffeeModel;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class previewAdapter extends RecyclerView.Adapter<previewAdapter.MyPreviewViewHolder> {

    private Context context;
    private List<CartModel> cartModelList;

    public previewAdapter(Context context, List<CartModel> cartModelList) {
        this.context = context;
        this.cartModelList = cartModelList;
    }

    @NonNull
    @Override
    public MyPreviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyPreviewViewHolder(LayoutInflater.from(context).inflate(R.layout.preview_list_item_design, parent, false));



    }



    @Override
    public void onBindViewHolder(@NonNull MyPreviewViewHolder holder, int position) {

        holder.txtCoffeeNamePreview.setText(new StringBuilder().append(cartModelList.get(position).getName()));
        holder.txtPricePreview.setText(new StringBuilder("Rs.").append(cartModelList.get(position).getPrices()));


    }



    @Override
    public int getItemCount() {
        return cartModelList.size();
    }

    public class MyPreviewViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.txtCoffeeNamePreview)
        TextView txtCoffeeNamePreview;
        @BindView(R.id.txtPricePreview)
        TextView txtPricePreview;

        Unbinder unbinder;
        public MyPreviewViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }
}
