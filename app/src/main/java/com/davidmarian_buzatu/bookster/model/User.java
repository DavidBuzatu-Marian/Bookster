package com.davidmarian_buzatu.bookster.model;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

public interface User {
    public Task<DocumentSnapshot> getUserInfo();

    public static Client getInstance() {
        return null;
    }
    public String getUserPhoneNumber();
    public String getUserName();
}
