package com.davidmarian_buzatu.bookster.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


import com.davidmarian_buzatu.bookster.R;
import com.davidmarian_buzatu.bookster.model.Client;
import com.davidmarian_buzatu.bookster.model.Manager;
import com.davidmarian_buzatu.bookster.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import androidx.navigation.ui.NavigationUI;


public class MenuActivity extends AppCompatActivity {
    private User mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        getUserInstance(extras.getString("Type"));
        setContentViewOnType(mCurrentUser.getClass().getName(), extras);
    }

    private void getUserInstance(String type) {
        switch (type) {
            case "Client":
                mCurrentUser = Client.getInstance();
                break;
            case "Manager":
                mCurrentUser = Manager.getInstance();
                break;
            default:
                mCurrentUser = null;
        }
    }

    private void setContentViewOnType(String type, Bundle extras) {
        if (type.contains("Client")) {
            setContentView(R.layout.activity_menu_user);
            BottomNavigationView navView = findViewById(R.id.nav_view);
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            NavigationUI.setupWithNavController(navView, navController);
            navController.navigate(R.id.navigation_home_user, extras);
            navController.navigate(R.id.navigation_profile, extras);
            navController.navigate(R.id.navigation_search, extras);
        } else {
            setContentView(R.layout.activity_menu);
            BottomNavigationView navView = findViewById(R.id.nav_view);

            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            NavigationUI.setupWithNavController(navView, navController);

            navController.navigate(R.id.navigation_home, extras, new NavOptions.Builder().setPopUpTo(R.id.navigation_home, false).build());
            navController.navigate(R.id.navigation_messages, extras, new NavOptions.Builder().setPopUpTo(R.id.navigation_messages, false).build());
            navController.navigate(R.id.navigation_profile, extras, new NavOptions.Builder().setPopUpTo(R.id.navigation_profile, false).build());
            navController.navigate(R.id.navigation_offers, extras, new NavOptions.Builder().setPopUpTo(R.id.navigation_offers, false).build());
        }
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
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        fragment.onActivityResult(requestCode, resultCode, data);
    }


}
