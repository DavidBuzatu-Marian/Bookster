package com.davidmarian_buzatu.bookster.services;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
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
    public static final int LAUNCH_MAIL_ACTIVITY=1;

    @SuppressLint("IntentReset")
    public void sendEmail(Context context, String managerId, FragmentActivity activity,String subject) {
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
                                sendEmailToManager(emailIntent, context, activity,subject);
                            }
                        } else {
                            Log.d("EMAIL", "Failed to get manager email:" + managerId + task.getException());
                        }
                    }
                });


    }

    private void sendEmailToManager(Intent emailIntent, Context context, FragmentActivity activity, String subject) {
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, mManagerMail);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Hello, ...");

        try {
            activity.startActivityForResult(emailIntent, LAUNCH_MAIL_ACTIVITY);
        } catch (android.content.ActivityNotFoundException e) {
            Toast.makeText(context, "There is no email client installed", Toast.LENGTH_SHORT).show();
        }
    }


}
