package com.harshit.cafeshopapp.activity.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.harshit.cafeshopapp.R;

public class RegisterActivity extends AppCompatActivity {

    Button btnRegister;
    EditText etRegisterEmail;
    EditText etRegisterPassword;

     FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnRegister = findViewById(R.id.btnRegister);
        etRegisterEmail = findViewById(R.id.etRegisterEmail);
        etRegisterPassword = findViewById(R.id.etRegisterPassword);

        mAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(view -> {
            createUser();
        });

    }

    private void createUser(){
        String registerEmail = etRegisterEmail.getText().toString();
        String registerPassword = etRegisterPassword.getText().toString();

        if(TextUtils.isEmpty(registerEmail)){
            etRegisterEmail.setError("Email cannot be empty..");
            etRegisterEmail.requestFocus();
        }else if(TextUtils.isEmpty(registerPassword)){
            etRegisterPassword.setError("Password cannot be empty..");
            etRegisterPassword.requestFocus();
        }else{
            mAuth.createUserWithEmailAndPassword(registerEmail, registerPassword).addOnCompleteListener(this, task -> {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "User registered Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, DashboardActivity.class));
                }else{
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