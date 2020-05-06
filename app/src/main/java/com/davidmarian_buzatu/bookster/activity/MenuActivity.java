package com.davidmarian_buzatu.bookster.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


import com.davidmarian_buzatu.bookster.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import androidx.navigation.ui.NavigationUI;


public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        Bundle extras = getIntent().getExtras();
        navController.navigate(R.id.navigation_home, extras);
        navController.navigate(R.id.navigation_messages, extras);
        navController.navigate(R.id.navigation_profile, extras);
        navController.navigate(R.id.navigation_search, extras);

    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("TEST", "Called");
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        fragment.onActivityResult(requestCode, resultCode, data);
    }


}
