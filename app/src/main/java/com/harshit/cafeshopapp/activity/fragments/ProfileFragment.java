package com.harshit.cafeshopapp.activity.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.harshit.cafeshopapp.R;

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

    return view;

  }
}
