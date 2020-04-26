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
    private FirebaseAuth mAuth;

    public UploadCities(){

    }

    public void uploadCitiesToDatabase(){
        mAuth=FirebaseAuth.getInstance();
        Map<String,String> city1=new HashMap<>();
        city1.put("Country","Italy");
        FirebaseFirestore.getInstance()
                .collection("Cities")
                .document("Rome")
                .set(city1)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("UPLD_CITY","Uploaded city successfully");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("UPLD_CITY","Failed to upload");
                    }
                });


        Map<String,String> city2=new HashMap<>();
        city2.put("Country","Spain");
        FirebaseFirestore.getInstance()
                .collection("Cities")
                .document("Madrid")
                .set(city2)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("UPLD_CITY","Uploaded city successfully");
                    }
                });

        Map<String,String> city3=new HashMap<>();
        city3.put("Country","France");
        FirebaseFirestore.getInstance()
                .collection("Cities")
                .document("Paris")
                .set(city3)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("UPLD_CITY","Uploaded city successfully");
                    }
                });

        Map<String,String> city4=new HashMap<>();
        city4.put("Country","United Kingdom");
        FirebaseFirestore.getInstance()
                .collection("Cities")
                .document("London")
                .set(city4)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("UPLD_CITY","Uploaded city successfully");
                    }
                });

        Map<String,String> city5=new HashMap<>();
        city5.put("Country","Germany");
        FirebaseFirestore.getInstance()
                .collection("Cities")
                .document("Berlin")
                .set(city5)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("UPLD_CITY","Uploaded city successfully");
                    }
                });

        Map<String,String> city6=new HashMap<>();
        city6.put("Country","Portugal");
        FirebaseFirestore.getInstance()
                .collection("Cities")
                .document("Lisbon")
                .set(city6)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("UPLD_CITY","Uploaded city successfully");
                    }
                });

        Map<String,String> city7=new HashMap<>();
        city7.put("Country","Italy");
        FirebaseFirestore.getInstance()
                .collection("Cities")
                .document("Milan")
                .set(city7)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("UPLD_CITY","Uploaded city successfully");
                    }
                });

        Map<String,String> city8=new HashMap<>();
        city8.put("Country","France");
        FirebaseFirestore.getInstance()
                .collection("Cities")
                .document("Lyon")
                .set(city8)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("UPLD_CITY","Uploaded city successfully");
                    }
                });

        Map<String,String> city9=new HashMap<>();
        city9.put("Country","Germany");
        FirebaseFirestore.getInstance()
                .collection("Cities")
                .document("Munich")
                .set(city9)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("UPLD_CITY","Uploaded city successfully");
                    }
                });

        Map<String,String> city10=new HashMap<>();
        city10.put("Country","Spain");
        FirebaseFirestore.getInstance()
                .collection("Cities")
                .document("Barcelona")
                .set(city10)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("UPLD_CITY","Uploaded city successfully");
                    }
                });

        Map<String,String> city11=new HashMap<>();
        city11.put("Country","Italy");
        FirebaseFirestore.getInstance()
                .collection("Cities")
                .document("Turin")
                .set(city11)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("UPLD_CITY","Uploaded city successfully");
                    }
                });

        Map<String,String> city12=new HashMap<>();
        city12.put("Country","France");
        FirebaseFirestore.getInstance()
                .collection("Cities")
                .document("Marseille")
                .set(city12)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("UPLD_CITY","Uploaded city successfully");
                    }
                });

        Map<String,String> city13=new HashMap<>();
        city13.put("Country","Netherlands");
        FirebaseFirestore.getInstance()
                .collection("Cities")
                .document("Amsterdam")
                .set(city13)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("UPLD_CITY","Uploaded city successfully");
                    }
                });

        Map<String,String> city14=new HashMap<>();
        city14.put("Country","Belgium");
        FirebaseFirestore.getInstance()
                .collection("Cities")
                .document("Brussels")
                .set(city14)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("UPLD_CITY","Uploaded city successfully");
                    }
                });

        Map<String,String> city15=new HashMap<>();
        city15.put("Country","United Kingdom");
        FirebaseFirestore.getInstance()
                .collection("Cities")
                .document("Manchester")
                .set(city15)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("UPLD_CITY","Uploaded city successfully");
                    }
                });

        Map<String,String> city16=new HashMap<>();
        city16.put("Country","United Kingdom");
        FirebaseFirestore.getInstance()
                .collection("Cities")
                .document("Birmingham")
                .set(city16)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("UPLD_CITY","Uploaded city successfully");
                    }
                });

        Map<String,String> city17=new HashMap<>();
        city17.put("Country","Ireland");
        FirebaseFirestore.getInstance()
                .collection("Cities")
                .document("Dublin")
                .set(city17)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("UPLD_CITY","Uploaded city successfully");
                    }
                });

        Map<String,String> city18=new HashMap<>();
        city18.put("Country","Greece");
        FirebaseFirestore.getInstance()
                .collection("Cities")
                .document("Athens")
                .set(city18)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("UPLD_CITY","Uploaded city successfully");
                    }
                });

        Map<String,String> city19=new HashMap<>();
        city19.put("Country","Romania");
        FirebaseFirestore.getInstance()
                .collection("Cities")
                .document("Bucharest")
                .set(city19)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("UPLD_CITY","Uploaded city successfully");
                    }
                });

        Map<String,String> city20=new HashMap<>();
        city20.put("Country","Hungary");
        FirebaseFirestore.getInstance()
                .collection("Cities")
                .document("Budapest")
                .set(city20)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("UPLD_CITY","Uploaded city successfully");
                    }
                });

        Map<String,String> city21=new HashMap<>();
        city21.put("Country","Romania");
        FirebaseFirestore.getInstance()
                .collection("Cities")
                .document("Timisoara")
                .set(city21)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("UPLD_CITY","Uploaded city successfully");
                    }
                });

        Map<String,String> city22=new HashMap<>();
        city22.put("Country","Czech Republic");
        FirebaseFirestore.getInstance()
                .collection("Cities")
                .document("Prague")
                .set(city22)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("UPLD_CITY","Uploaded city successfully");
                    }
                });

        Map<String,String> city23=new HashMap<>();
        city23.put("Country","Austria");
        FirebaseFirestore.getInstance()
                .collection("Cities")
                .document("Vienna")
                .set(city23)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("UPLD_CITY","Uploaded city successfully");
                    }
                });

        Map<String,String> city24=new HashMap<>();
        city24.put("Country","Germany");
        FirebaseFirestore.getInstance()
                .collection("Cities")
                .document("Cologne")
                .set(city24)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("UPLD_CITY","Uploaded city successfully");
                    }
                });

        Map<String,String> city25=new HashMap<>();
        city25.put("Country","Italy");
        FirebaseFirestore.getInstance()
                .collection("Cities")
                .document("Venice")
                .set(city25)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("UPLD_CITY","Uploaded city successfully");
                    }
                });

        Map<String,String> city26=new HashMap<>();
        city26.put("Country","Poland");
        FirebaseFirestore.getInstance()
                .collection("Cities")
                .document("Warsaw")
                .set(city26)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("UPLD_CITY","Uploaded city successfully");
                    }
                });

        Map<String,String> city27=new HashMap<>();
        city27.put("Country","Switzerland");
        FirebaseFirestore.getInstance()
                .collection("Cities")
                .document("Zurich")
                .set(city27)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("UPLD_CITY","Uploaded city successfully");
                    }
                });

        Map<String,String> city28=new HashMap<>();
        city28.put("Country","Russia");
        FirebaseFirestore.getInstance()
                .collection("Cities")
                .document("Moscow")
                .set(city28)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("UPLD_CITY","Uploaded city successfully");
                    }
                });

        Map<String,String> city29=new HashMap<>();
        city29.put("Country","Turkey");
        FirebaseFirestore.getInstance()
                .collection("Cities")
                .document("Istanbul")
                .set(city29)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("UPLD_CITY","Uploaded city successfully");
                    }
                });

        Map<String,String> city30=new HashMap<>();
        city30.put("Country","Bulgaria");
        FirebaseFirestore.getInstance()
                .collection("Cities")
                .document("Sofia")
                .set(city30)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("UPLD_CITY","Uploaded city successfully");
                    }
                });
    }


}
