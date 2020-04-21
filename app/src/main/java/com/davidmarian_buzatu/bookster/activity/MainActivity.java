package com.davidmarian_buzatu.bookster.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.davidmarian_buzatu.bookster.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        // redirect if currentUser != null;
    }

    public void formSubmit(View view) {
        TextInputEditText email, password;
        email = findViewById(R.id.act_main_TIET_email);
        password = findViewById(R.id.act_main_TIET_password);

        if(email.getText() != null && password.getText() != null) {
            // Login user
           try {
               mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful()) {
                           // Success
                           Log.d("Login", "SignInWithEmail: Success");

                           // TODO: GET USER TYPE AND GET INSTANCE OF IT

                           // Redirect
                       } else {
                           // Fail
                           Log.d("Login", "SignInWithEmail: Fail");
                           email.setError("Invalid Credentials");
                       }
                   }
               });
           } catch (IllegalArgumentException ex) {
               email.setError("Invalid Credentials");
           }
        } else {
            email.setError("Invalid Credentials");
        }
    }
}
