package com.davidmarian_buzatu.bookster.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.davidmarian_buzatu.bookster.R;
import com.davidmarian_buzatu.bookster.adapter.RegisterAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private FirebaseAuth mAuth;
    private Activity mActivity = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initializeTabs();
    }

    @Override
    protected void onStart() {
        super.onStart();

        TextInputEditText email, password, name;
        EditText phoneNumber;

        email = findViewById(R.id.act_register_TIET_email);
        password = findViewById(R.id.act_register_TIET_password);
        name = findViewById(R.id.act_register_TIET_name);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                if(pos == 0) {
                    // TODO: REGISTER CLIENT

                    try {
                        mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                                .addOnCompleteListener(mActivity, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //Success
                                if(task.isSuccessful()) {
                                    //TODO: SAVE INFO IN FIRESTORE
                                    Log.d("Register", "Registered user");
                                } else {
                                    Log.d("Register", "Registered user failed");
                                    email.setError(task.getException().toString());
                                }
                            }
                        });
                    }catch(IllegalArgumentException ex) {
                        email.setError("Invalid credentials");
                    }
                } else {
                    // TODO: REGISTER MANAGER
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initializeTabs() {
        RegisterAdapter regAdapter = new RegisterAdapter(this);
        setPagerTabs(regAdapter);
    }

    private void setPagerTabs(RegisterAdapter regAdapter) {
        ViewPager pager = findViewById(R.id.act_register_VP);
        mTabLayout = findViewById(R.id.act_register_TL);
        mTabLayout.setupWithViewPager(pager, false);
        pager.setAdapter(regAdapter);
    }


}
