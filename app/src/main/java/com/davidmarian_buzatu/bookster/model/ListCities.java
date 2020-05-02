package com.davidmarian_buzatu.bookster.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.davidmarian_buzatu.bookster.activity.ui.search.helper.SearchList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListCities {
    private static ListCities listCitiesInstance;
    private List<SearchList> mListCities;

    private ListCities() {
        mListCities = new ArrayList<>();
        getCitiesFromFirebase().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Log.d("LIST_VIEW", "Task Succesful in list view");
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        mListCities.add(new SearchList(document.getId() + ", " + document.getData().get("Country")));
                    }
                } else {
                    Log.d("LIST_FAILED", "Fail to add documents");
                }
            }
        });

    }

    private Task<QuerySnapshot> getCitiesFromFirebase() {
        return FirebaseFirestore.getInstance()
                .collection("cities")
                .get();
    }

    public static ListCities getInstance() {
        if (listCitiesInstance == null) {
            listCitiesInstance = new ListCities();
        }
        return listCitiesInstance;
    }

    public List<SearchList> getList() {
        return mListCities;
    }
}
