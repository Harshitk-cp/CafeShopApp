package com.harshit.cafeshopapp.activity.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.harshit.cafeshopapp.R;
import com.harshit.cafeshopapp.activity.fragments.FavFragment;
import com.harshit.cafeshopapp.activity.fragments.HomeFragment;
import com.harshit.cafeshopapp.activity.fragments.ProfileFragment;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawer;
    FirebaseAuth mAuth;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mAuth = FirebaseAuth.getInstance();
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null) {
            openHome();
        }

    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                openHome();
                    break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileFragment()).commit();
                getSupportActionBar().setTitle("Profile");
                break;
            case R.id.nav_FAQs:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FavFragment()).commit();
                getSupportActionBar().setTitle("FAQs");
                break;
            case R.id.nav_logout:
                new AlertDialog.Builder(DashboardActivity.this)
                    .setTitle("Logout!")
                    .setMessage("Are you sure you want to Logout?")
                    .setCancelable(true)
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
                            finish();
                        }
                    }).show();

                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void openHome(){
        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();
        getSupportActionBar().setTitle("Home");


    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            Fragment fragment = this.getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            if (fragment instanceof HomeFragment) {
                finish();
            }else{
                openHome();
            }


        }

    }



    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            new AlertDialog.Builder(DashboardActivity.this)
                    .setTitle("No Internet Connection!")
                    .setMessage("Please Connect to Internet..")
                    .setCancelable(true)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DashboardActivity.this.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                        }
                    }).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onResume() {
        isOnline();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user == null){
            startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
        }
        super.onResume();

    }


}