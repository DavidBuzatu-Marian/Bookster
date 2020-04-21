package com.davidmarian_buzatu.bookster.model;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Client implements User {
    private static Client user = null;
    private static DocumentSnapshot userInfo;
    private FirebaseUser firebaseUser;

    private Client(FirebaseUser currentUser) {
        firebaseUser = currentUser;
    }


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

    public static Client getInstance() {
        if(user == null) {
            user = new Client(FirebaseAuth.getInstance().getCurrentUser());
        }
        return user;
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


}
