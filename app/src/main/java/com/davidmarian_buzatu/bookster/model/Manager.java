package com.davidmarian_buzatu.bookster.model;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Manager implements User {
    private static Manager user = null;
    private static DocumentSnapshot userInfo;
    private FirebaseUser firebaseUser;

    private Manager(FirebaseUser currentUser) {
        firebaseUser = currentUser;
    }

    public static Manager getInstance() {
        if(user == null) {
            user = new Manager(FirebaseAuth.getInstance().getCurrentUser());
        }
        return user;
    }
    @Override
    public Task<DocumentSnapshot> getUserInfo() {
        Task<DocumentSnapshot> task = FirebaseFirestore.getInstance().collection("users").document(firebaseUser.getUid()).get();
        task.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                userInfo = task.getResult();
            }
        });

        return task;
    }

    public String getUserPhoneNumber() {
        String phoneNumber = null;
        if(userInfo != null) {
            phoneNumber = (String) userInfo.get("phoneNumber");
        }
        return phoneNumber;
    }

    public String getUserName() {
        String name = null;
        if(userInfo != null) {
            name = (String) userInfo.get("name");
        }
        return name;
    }

    public String getUserAddress() {
        String address = null;
        if(userInfo != null) {
            address = (String) userInfo.get("address");
        }
        return address;
    }

    public String getUserBusinessName() {
        String bName = null;
        if(userInfo != null) {
            bName = (String) userInfo.get("business");
        }
        return bName;
    }
}
