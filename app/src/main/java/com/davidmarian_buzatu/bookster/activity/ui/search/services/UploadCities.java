package com.davidmarian_buzatu.bookster.activity.ui.search.services;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Map;

public class UploadCities {

    private static final String[] mCities = {"Rome", "Madrid", "Paris", "London", "Berlin", "Lisbon", "Milan", "Lyon", "Münich",
            "Barcelona", "Turin", "Marseille", "Amsterdam", "Brussels", "Manchester", "Birmingham",
            "Dublin", "Athens", "Bucharest", "Budapest", "Timisoara", "Prague", "Vienna", "Cologne", "Venice",
            "Warsaw", "Zürich", "Moscow", "Istanbul"};
    private static final String[] mCountries = {"Italy", "Spain", "France", "United Kingdom", "Germany", "Portugal", "Italy",
            "France", "Germany", "Spain", "Italy", "France", "Netherlands", "Belgium", "United Kingdom",
            "United Kingdom", "Ireland", "Greece", "Romania", "Hungary", "Romania", "Czech Republic", "Austria",
            "Germany", "Italy", "Poland", "Germany", "Russia", "Turkey"};

    public String[] getCities() {
        return mCities;
    }

    public String[] getCountries() {
        return mCountries;
    }

    public void uploadCitiesToDatabase() {
        for (int i = 0; i < mCities.length; ++i) {
            saveToFirebase(mCountries[i], mCities[i]);
        }

    }

    private void saveToFirebase(String country, String city) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        Map<String, String> cities = new HashMap<>();
        cities.put("Country", country);
        if (auth.getCurrentUser() != null) {
            FirebaseFirestore.getInstance()
                    .collection("cities")
                    .document(city)
                    .set(cities)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("UPLD_CITY", "Uploaded city successfully");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("UPLD_CITY", e.toString());
                        }
                    });
        }
    }

}
