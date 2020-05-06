package com.davidmarian_buzatu.bookster.services;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MessageActions {
    @SuppressLint("IntentReset")
    public void sendEmail(View root){
        Intent emailIntent=new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL,"To");
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,"");
        emailIntent.putExtra(Intent.EXTRA_TEXT,"Hello, ...");

        try{
            root.getContext().startActivity(Intent.createChooser(emailIntent,"Send mail"));

        }catch(android.content.ActivityNotFoundException e){
            Toast.makeText(root.getContext(),"There is no email client installed",Toast.LENGTH_SHORT).show();
        }
    }

}
