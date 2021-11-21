package com.harshit.cafeshopapp.activity.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.harshit.cafeshopapp.R;
import com.harshit.cafeshopapp.activity.activity.DashboardActivity;
import com.harshit.cafeshopapp.activity.model.FavModel;
import com.harshit.cafeshopapp.activity.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

  TextView txtProfileName;
  TextView txtProfileAddress;
  TextView txtProfileCity;
  TextView txtProfileState;
  TextView txtProfileZip;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view =  inflater.inflate(R.layout.fragment_profile, container, false);

    txtProfileName = view.findViewById(R.id.txtProfileName);
    txtProfileAddress = view.findViewById(R.id.txtProfileAddress);
    txtProfileCity = view.findViewById(R.id.txtProfileCity);
    txtProfileState = view.findViewById(R.id.txtProfileState);
    txtProfileZip = view.findViewById(R.id.txtProfileZip);

    loadUserDataFromFirebase();
    
    return view;

  }

  private void loadUserDataFromFirebase() {

    FirebaseDatabase.getInstance().getReference("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
      .child("1")
      .addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {

          if (snapshot.exists()) {
            for (DataSnapshot userSnapshot : snapshot.getChildren()) {
              UserModel userModel = userSnapshot.getValue(UserModel.class);
              txtProfileName.setText(new StringBuilder().append(userModel.getName()));
              txtProfileAddress.setText(new StringBuilder().append(userModel.getAddress()));
              txtProfileCity.setText(new StringBuilder().append(userModel.getCity()));
              txtProfileState.setText(new StringBuilder().append(userModel.getState()));
              txtProfileZip.setText(new StringBuilder().append(userModel.getZip()));
            }
          } else {
            //Toast.makeText(DashboardActivity.this, "hah error", Toast.LENGTH_SHORT).show();
          }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
          System.out.println("hah error");
        }
      });


  }
}
