package com.davidmarian_buzatu.bookster.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.davidmarian_buzatu.bookster.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

        // TODO: REDIRECT ON SUCCESS
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
               showLoadingDialog();
               signInUser(email, password);

           } catch (IllegalArgumentException ex) {
               email.setError("Invalid Credentials");
           }
        } else {
            email.setError("Invalid Credentials");
        }
    }

    private void signInUser(TextInputEditText email, TextInputEditText password) {
        mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Success
                    Log.d("Login", "SignInWithEmail: Success");
                    // Redirect
                    getUserTypeAndRedirect();
                } else {
                    // Fail
                    Log.d("Login", "SignInWithEmail: Fail");
                    Toast.makeText(MainActivity.this, "Check your internet connection!", Toast.LENGTH_LONG).show();
                    mDialog.dismiss();
                }
            }
        });
    }

    private void showLoadingDialog() {
        mDialog = new ProgressDialog(this);
        mDialog.setMessage(getString(R.string.act_main_dialog_message));
        mDialog.setTitle(getString(R.string.act_main_dialog_title));
        mDialog.setIndeterminate(false);
        mDialog.setCancelable(false);
        mDialog.show();
    }

    private void getUserTypeAndRedirect() {
        FirebaseFirestore.getInstance().collection("users").document(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    mDialog.dismiss();
                    redirectToUI(documentSnapshot.get("Type").toString());
                }
            }
        });
    }

    private void redirectToUI(String type) {
        Intent landingAct = new Intent(this, MenuActivity.class);
        landingAct.putExtra("Type", type);
        startActivity(landingAct);
    }

    public void startRegisterActivity(View view) {
        Intent regAct = new Intent(this, RegisterActivity.class);
        startActivity(regAct);
    }
}
