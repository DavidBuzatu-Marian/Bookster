package com.davidmarian_buzatu.bookster.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.davidmarian_buzatu.bookster.R;
import com.davidmarian_buzatu.bookster.adapter.RegisterAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hbb20.CountryCodePicker;

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
                if (validFields(email, password, name) && mRegAdapter.getIsValidNumberClient()) {
                    showLoadingDialog();
                    createUser(email, password, name, mRegAdapter.getmCCPClient(), "Client");
                } else if(!mRegAdapter.getIsValidNumberClient())  {
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

                if (validFields(email, password, name) && mRegAdapter.getIsValidNumberManager() && address.getText() != null) {
                    showLoadingDialog();
                    createUser(email, password, name, address, mRegAdapter.getmCCPManager(), "Manager");
                } else if(!mRegAdapter.getIsValidNumberManager())  {
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
                            //TODO: SAVE INFO IN FIRESTORE
                            Log.d("REG_TEST", "Registered user");

                            saveUserInfo(email.getText().toString(), CCP.getFullNumberWithPlus(), name.getText().toString(), address.getText().toString(), type);
                        } else {
                            Log.d("REG_TEST", "Registered user failed");
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
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // TODO: GET USER INSTANCE
                        Log.d("REG_TEST", "SAVED USER INFO");
                        mDialog.dismiss();
                        redirectToUI("Manager");
                    }
                });
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
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("REG_TEST", "SAVED USER INFO");
                        mDialog.dismiss();
                        redirectToUI("Client");
                    }
                });
    }

    private boolean validFields(TextInputEditText email, TextInputEditText password, TextInputEditText name) {
        if (email.getText() == null || !emailIsValid(email.getText().toString(), email)) {
            return false;
        }

        if(password.getText() == null || !passwordIsValid(password.getText().toString(), password)) {
            return false;
        }

        if(name.getText() == null || !isNameValid(name.getText().toString(), name)) {
            name.setError("Name cannot be empty");
            return false;
        }

        return true;
    }

    private boolean isNameValid(String name, TextInputEditText nameET) {
        boolean hasDigit_TRUE = false;
        if(name.isEmpty()) {
            nameET.setError("Name cannot be empty");
        }
        /* it has digits */
        for (char digit : name.toCharArray()) {
            if (Character.isDigit(digit)) {
                hasDigit_TRUE = true;
                break;
            }
        }
        if (hasDigit_TRUE) {
            nameET.setError("Name must not have digits");
            return false;
        }
        nameET.setError(null);
        return true;
    }

    private boolean passwordIsValid(String password, TextInputEditText passwordET) {
        boolean hasDigit_TRUE = false;
        if (password.isEmpty()) {
            passwordET.setError("Field required!");
            return false;
        }
        /* check if length is between limits */
        if (password.length() < 8 || password.length() > 20) {
            passwordET.setError("Password must be between 8 and 20 characters");
            return false;
        }
        /* it has digits */
        for (char digit : password.toCharArray()) {
            if (Character.isDigit(digit)) {
                hasDigit_TRUE = true;
                break;
            }
        }
        if (!hasDigit_TRUE) {
            passwordET.setError("Password must have digits");
            return false;
        }
        /* it has uppercase/ lowercase letter */
        if (password.equals(password.toLowerCase()) || password.equals(password.toUpperCase())) {
            passwordET.setError("Password needs lowercase and uppercase letters");
            return false;
        }
        passwordET.setError(null);
        return true;
    }

    private boolean emailIsValid(String email, TextInputEditText emailET) {
        if (email.isEmpty()) {
            emailET.setError("Field required!");
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailET.setError("Invalid Email");
            return false;
        }
        emailET.setError(null);
        return true;
    }

    private void showLoadingDialog() {
        mDialog = new ProgressDialog(this);
        mDialog.setMessage(getString(R.string.act_register_dialog_message));
        mDialog.setTitle(getString(R.string.act_register_dialog_title));
        mDialog.setIndeterminate(false);
        mDialog.setCancelable(false);
        mDialog.show();
    }

    private void redirectToUI(String type) {
        Intent landingAct = new Intent(this, MenuActivity.class);
        landingAct.putExtra("Type", type);
        startActivity(landingAct);
    }
}
