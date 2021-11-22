package com.harshit.cafeshopapp.activity.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.harshit.cafeshopapp.R;
import com.harshit.cafeshopapp.activity.activity.CartActivity;
import com.harshit.cafeshopapp.activity.activity.DashboardActivity;
import com.harshit.cafeshopapp.activity.eventbus.updatecartEvent;
import com.harshit.cafeshopapp.activity.fragments.FavFragment;
import com.harshit.cafeshopapp.activity.fragments.HomeFragment;
import com.harshit.cafeshopapp.activity.listener.ICartLoadListener;
import com.harshit.cafeshopapp.activity.listener.IFavLoadListener;
import com.harshit.cafeshopapp.activity.model.CartModel;
import com.harshit.cafeshopapp.activity.model.FavModel;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class favAdapter extends RecyclerView.Adapter<favAdapter.MyFavViewHolder> {
  private final Context context;
  private final List<FavModel> favModelList;
  private IFavLoadListener iFavLoadListener;

  public favAdapter(Context context, List<FavModel> favModelList, IFavLoadListener iFavLoadListener) {
    this.context = context;
    this.favModelList = favModelList;
    this.iFavLoadListener = iFavLoadListener;
  }

  @NonNull
  @Override
  public MyFavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new MyFavViewHolder(LayoutInflater.from(context).inflate(R.layout.fav_list_item_design, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull MyFavViewHolder holder, int position) {

    holder.txtCoffeeNameFav.setText(new StringBuilder().append(favModelList.get(position).getName()));
    holder.txtPriceFav.setText(new StringBuilder("Rs.").append(favModelList.get(position).getPrices()));
    Glide.with(holder.imgCoffeeImageFav.getContext()).load(favModelList.get(position).getImgUrl()).override(600, 700).centerCrop().into(holder.imgCoffeeImageFav);

    holder.txtRemoveFav.setOnClickListener(v -> {
      AlertDialog dialog = new AlertDialog.Builder(context)
        .setTitle("Remove Item")
        .setMessage("Do you want to remove this item from favs?")
        .setNegativeButton("cancel", (dialog1, which) -> dialog1.dismiss())
        .setPositiveButton("Ok", (dialog12, which) -> {


          if(getItemCount() == 1){
            deleteFavFromFirebase(favModelList.get(position));
            notifyItemRemoved(position);
            notifyDataSetChanged();
            ((DashboardActivity)context).finish();
            Intent intent = new Intent(this.context, DashboardActivity.class);
            context.startActivity(intent);
          }

          deleteFavFromFirebase(favModelList.get(position));
          notifyItemRemoved(position);
          notifyDataSetChanged();
          dialog12.dismiss();
        }).create();
      dialog.show();
    });
  }

  private void deleteFavFromFirebase(FavModel favModel) {

    FirebaseDatabase.getInstance()
      .getReference("favs")
      .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
      .child(favModel.getKey())
      .removeValue()
      .addOnSuccessListener(aVoid -> EventBus.getDefault().postSticky(new updatecartEvent()));
  }

  @Override
  public int getItemCount() {
    return favModelList.size();
  }

  public class MyFavViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.txtCoffeeNameFav)
    TextView txtCoffeeNameFav;
    @BindView(R.id.txtPriceFav)
    TextView txtPriceFav;
    @BindView(R.id.txtRemoveFav)
    ImageView txtRemoveFav;
    @BindView(R.id.imgCoffeeImageFav)
      ImageView imgCoffeeImageFav;

    Unbinder unbinder;

    public MyFavViewHolder(@NonNull View itemView) {
      super(itemView);
      unbinder = ButterKnife.bind(this, itemView);
    }
  }
}
