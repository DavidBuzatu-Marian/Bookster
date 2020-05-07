package com.davidmarian_buzatu.bookster.services;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.w3c.dom.Document;

public class MessageActions {
    private String[] mManagerMail=new String[1];

    @SuppressLint("IntentReset")
    public void sendEmail(View root, String managerId, FragmentActivity activity) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        FirebaseFirestore.getInstance()
                .collection("users")
                .document(managerId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot doc = task.getResult();
                            if (doc != null) {
                                mManagerMail[0] = (String) doc.getData().get("Email");
                                Log.d("EMAIL","Manager mail is "+mManagerMail[0]);
                                sendEmailToManager(emailIntent, root, activity);
                            }
                        } else {
                            Log.d("EMAIL", "Failed to get manager email:" + managerId + task.getException());
                        }
                    }
                });


    }

    private void sendEmailToManager(Intent emailIntent, View root, FragmentActivity activity) {
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, mManagerMail);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Hello, ...");

        try {
            activity.startActivityForResult(emailIntent, 1);
        } catch (android.content.ActivityNotFoundException e) {
            Toast.makeText(root.getContext(), "There is no email client installed", Toast.LENGTH_SHORT).show();
        }
    }

}
