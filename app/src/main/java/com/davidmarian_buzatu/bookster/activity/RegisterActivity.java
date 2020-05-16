package com.davidmarian_buzatu.bookster.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.davidmarian_buzatu.bookster.R;
import com.davidmarian_buzatu.bookster.services.DialogShow;
import com.davidmarian_buzatu.bookster.adapter.RegisterAdapter;
import com.davidmarian_buzatu.bookster.services.RegisterValidationActions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hbb20.CountryCodePicker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private FirebaseAuth mAuth;
    private Activity mActivity = this;
    private int mPos;
    private RegisterAdapter mRegAdapter;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initializeTabs();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mPos = tab.getPosition();
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
        mRegAdapter = new RegisterAdapter(this);
        setPagerTabs(mRegAdapter);
    }

    private void setPagerTabs(RegisterAdapter regAdapter) {
        ViewPager pager = findViewById(R.id.act_register_VP);
        mTabLayout = findViewById(R.id.act_register_TL);
        mTabLayout.setupWithViewPager(pager, false);
        pager.setAdapter(regAdapter);
    }

    public void registerUser(View view) {
        TextInputEditText email, password, name, address;
        email = findViewById(R.id.act_register_TIET_email);
        password = findViewById(R.id.act_register_TIET_password);
        name = findViewById(R.id.act_register_TIET_name);
        if (mPos == 0) {
            // REGISTER CLIENT
            try {
                if (RegisterValidationActions.validFields(email, password, name, mRegAdapter)) {
                    showLoadingDialog();
                    createUser(email, password, name, mRegAdapter.getmCCPClient(), "Client");
                } else if (!mRegAdapter.getIsValidNumberClient()) {
                    Toast.makeText(this, "Invalid phone number", Toast.LENGTH_SHORT).show();
                }
            } catch (IllegalArgumentException ex) {
                mDialog.dismiss();
                email.setError("Invalid credentials");
            }
        } else {
            // REGISTER MANAGER
            try {
                address = findViewById(R.id.act_register_TIET_address_manager);
                email = findViewById(R.id.act_register_TIET_email_manager);
                password = findViewById(R.id.act_register_TIET_password_manager);
                name = findViewById(R.id.act_register_TIET_name_manager);

                if (RegisterValidationActions.validFields(email, password, name, address, mRegAdapter)) {
                    showLoadingDialog();
                    createUser(email, password, name, address, mRegAdapter.getmCCPManager(), "Manager");
                } else if (!mRegAdapter.getIsValidNumberManager()) {
                    Toast.makeText(this, "Invalid phone number", Toast.LENGTH_SHORT).show();
                }
            } catch (IllegalArgumentException ex) {
                mDialog.dismiss();
                email.setError("Invalid credentials");
            }
        }
    }

    private void createUser(TextInputEditText email, TextInputEditText password, TextInputEditText name, TextInputEditText address, CountryCodePicker CCP, String type) {
        mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(mActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Success
                        if (task.isSuccessful()) {

                            saveUserInfo(email.getText().toString(), CCP.getFullNumberWithPlus(), name.getText().toString(), address.getText().toString(), type);
                        } else {
                            Toast.makeText(RegisterActivity.this, task.getException().toString(), Toast.LENGTH_LONG).show();
                            mDialog.dismiss();
                        }
                    }
                });
    }

    private void saveUserInfo(String email, String number, String name, String address, String type) {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("Name", name);
        userInfo.put("Email", email);
        userInfo.put("PhoneNumber", number);
        userInfo.put("Address", address);
        userInfo.put("Type", type);

        FirebaseFirestore.getInstance()
                .collection("users")
                .document(mAuth.getCurrentUser().getUid())
                .set(userInfo)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            createManagerMessagesDocument().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        createUserReservationDocument("reservationsManager", "Manager");
                                    }
                                }
                            });

                        }
                        mDialog.dismiss();
                    }
                });
    }

    private Task<Void> createManagerMessagesDocument() {
        Map<String, Object> messages = new HashMap<>();
        messages.put("messages", new ArrayList<>());
        return FirebaseFirestore.getInstance()
                .collection("messages")
                .document(mAuth.getCurrentUser().getUid())
                .set(messages);
    }

    private void createUser(TextInputEditText email, TextInputEditText password, TextInputEditText name, CountryCodePicker CCP, String type) {
        mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(mActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Success
                        if (task.isSuccessful()) {
                            Log.d("REG_TEST", "Registered user");
                            saveUserInfo(email.getText().toString(), CCP.getFullNumberWithPlus(), name.getText().toString(), type);
                        } else {
                            Log.d("REG_TEST", "Registered user failed");
                            Toast.makeText(RegisterActivity.this, task.getException().toString(), Toast.LENGTH_LONG).show();
                            mDialog.dismiss();
                        }
                    }
                });
    }

    private void saveUserInfo(String email, String number, String name, String type) {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("Name", name);
        userInfo.put("Email", email);
        userInfo.put("PhoneNumber", number);
        userInfo.put("Type", type);


        FirebaseFirestore.getInstance()
                .collection("users")
                .document(mAuth.getCurrentUser().getUid())
                .set(userInfo)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            createUserReservationDocument("reservations", "Client");
                        }
                        mDialog.dismiss();
                    }
                });
    }

    private void createUserReservationDocument(String collection, String type) {
        Map<String, Object> reservations = new HashMap<>();
        reservations.put("reservations", new ArrayList<>());
        FirebaseFirestore.getInstance()
                .collection(collection)
                .document(mAuth.getCurrentUser().getUid())
                .set(reservations)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            redirectToUI(type);
                        }
                        mDialog.dismiss();
                    }
                });
    }


    private void showLoadingDialog() {
        mDialog = DialogShow.getInstance().getDisplayDialog(this, R.string.act_register_dialog_message, R.string.act_register_dialog_title);
        mDialog.show();
    }

    private void redirectToUI(String type) {
        Intent landingAct = new Intent(this, MenuActivity.class);
        landingAct.putExtra("Type", type);
        startActivity(landingAct);
    }
}
