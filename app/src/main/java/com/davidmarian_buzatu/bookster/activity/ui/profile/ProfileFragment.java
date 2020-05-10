package com.davidmarian_buzatu.bookster.activity.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.davidmarian_buzatu.bookster.R;
import com.davidmarian_buzatu.bookster.activity.MainActivity;
import com.davidmarian_buzatu.bookster.model.User;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        
        setPreferencesForUser();
    }

    private void setPreferencesForUser() {
        Preference logOutPreference = findPreference("LogOut");
        logOutPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

            @Override
            public boolean onPreferenceClick(Preference preference) {
                FirebaseAuth.getInstance().signOut();
                // redirect user back to login
                Intent loginIntent = new Intent(getContext(), MainActivity.class);
                loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(loginIntent);
                return false;
            }
        });
    }
}
