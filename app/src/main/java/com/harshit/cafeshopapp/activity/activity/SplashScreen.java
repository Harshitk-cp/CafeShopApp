package com.harshit.cafeshopapp.activity.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.harshit.cafeshopapp.R;

public class SplashScreen extends AppCompatActivity {

    Animation topAnim;
    ImageView imgLogo;
    TextView txtLogo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);




        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        imgLogo = findViewById(R.id.imgLogo);
        txtLogo = findViewById(R.id.txtLogo);

        topAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        txtLogo.startAnimation(topAnim);
        imgLogo.startAnimation(topAnim);

    }


}