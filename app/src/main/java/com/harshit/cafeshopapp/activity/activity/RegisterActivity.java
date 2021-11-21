package com.harshit.cafeshopapp.activity.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.harshit.cafeshopapp.R;
import com.harshit.cafeshopapp.activity.model.UserModel;

public class RegisterActivity extends AppCompatActivity {
  Button btnRegister;
  EditText etRegisterEmail;
  EditText etRegisterPassword;
  EditText etRegisterName;
  EditText etNameInfo;
  EditText etAddressInfo;
  EditText etCityInfo;
  EditText etStateInfo;
  EditText etZipInfo;

  FirebaseAuth mAuth;
  DatabaseReference userDatabase;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register);

    etNameInfo = findViewById(R.id.etRegisterName);
    etAddressInfo = findViewById(R.id.etAddressInfo);
    etCityInfo = findViewById(R.id.etCityInfo);
    etStateInfo = findViewById(R.id.etStateInfo);
    etZipInfo = findViewById(R.id.etZipInfo);
    btnRegister = findViewById(R.id.btnRegister);
    etRegisterEmail = findViewById(R.id.etRegisterEmail);
    etRegisterPassword = findViewById(R.id.etRegisterPassword);
    etRegisterName = findViewById(R.id.etRegisterName);

    mAuth = FirebaseAuth.getInstance();

    btnRegister.setOnClickListener(view -> {
      createUser();
      if (etNameInfo.getText().toString().isEmpty()) {
        etNameInfo.setError("Your name is required");
      } else if (etAddressInfo.getText().toString().isEmpty()) {
        etAddressInfo.setError("Address cannot be empty");
      } else if (etCityInfo.getText().toString().isEmpty()) {
        etCityInfo.setError("required field");
      } else if (etStateInfo.getText().toString().isEmpty()) {
        etStateInfo.setError("required field");
      } else if (etZipInfo.getText().toString().isEmpty()) {
        etZipInfo.setError("required field");
      } else {
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
          @Override
          public void run() {
            insertUserData();
          }
        }, 3000);
      }
    });
  }

  private void insertUserData() {

    userDatabase = FirebaseDatabase
      .getInstance().getReference("user")
      .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("1");

    String userName = etRegisterName.getText().toString();
    String userAddress = etAddressInfo.getText().toString();
    String userCity = etCityInfo.getText().toString();
    String userState = etStateInfo.getText().toString();
    String userZip = etZipInfo.getText().toString();

    UserModel userModels = new UserModel();
    userModels.setName(userName);
    userModels.setAddress(userAddress);
    userModels.setCity(userCity);
    userModels.setState(userState);
    userModels.setZip(userZip);

    userDatabase.push().setValue(userModels);
  }

  private void createUser() {
    String registerEmail = etRegisterEmail.getText().toString();
    String registerPassword = etRegisterPassword.getText().toString();

    if (TextUtils.isEmpty(registerEmail)) {
      etRegisterEmail.setError("Email cannot be empty..");
      etRegisterEmail.requestFocus();
    } else if (TextUtils.isEmpty(registerPassword)) {
      etRegisterPassword.setError("Password cannot be empty..");
      etRegisterPassword.requestFocus();
    } else {
      mAuth.createUserWithEmailAndPassword(registerEmail, registerPassword)
        .addOnCompleteListener(this, task -> {
          if (task.isSuccessful()) {
            Toast.makeText(RegisterActivity.this, "User registered Successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(RegisterActivity.this, DashboardActivity.class));
          } else {
            Toast.makeText(RegisterActivity.this, "Registration error: Invalid Email or Password", Toast.LENGTH_SHORT).show();
          }
        });
    }
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    finish();
  }
}