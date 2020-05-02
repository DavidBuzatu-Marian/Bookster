package com.davidmarian_buzatu.bookster.activity.ui.search.helper;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Map;

public class UploadCities {
    public UploadCities() {

    }

    public void uploadCitiesToDatabase() {
        String[] cities = {"Rome", "Madrid", "Paris", "London", "Berlin", "Lisbon", "Milan", "Lyon", "Münich",
                "Barcelona", "Turin", "Marseille", "Amsterdam", "Brussels", "Manchester", "Birmingham",
                "Dublin", "Athens", "Bucharest", "Budapest", "Timisoara", "Prague", "Vienna", "Cologne", "Venice",
                "Warsaw", "Zürich", "Moscow", "Istanbul"};
        String[] countries = {"Italy", "Spain", "France", "United Kingdom", "Germany", "Portugal", "Italy",
                "France", "Germany", "Spain", "Italy", "France", "Netherlands", "Belgium", "United Kingdom",
                "United Kingdom", "Ireland", "Greece", "Romania", "Hungary", "Romania", "Czech Republic", "Austria",
                "Germany", "Italy", "Poland", "Germany", "Russia", "Turkey"};

        for(int i = 0; i < cities.length; ++i) {
            saveToFirebase(countries[i], cities[i]);
        }

    }

    private void saveToFirebase(String country, String city) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        Map<String, String> city30 = new HashMap<>();
        city30.put("Country", country);
        if(auth.getCurrentUser() != null) {
            FirebaseFirestore.getInstance()
                    .collection("cities")
                    .document(city)
                    .set(city30)
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
