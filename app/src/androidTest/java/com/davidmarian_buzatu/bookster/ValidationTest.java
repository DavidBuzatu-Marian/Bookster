package com.davidmarian_buzatu.bookster;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.davidmarian_buzatu.bookster.adapter.RegisterAdapter;
import com.davidmarian_buzatu.bookster.services.RegisterValidationActions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static junit.framework.TestCase.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ValidationTest {
    @Mock private RegisterValidationActions registerValidationActions;
    private TextInputEditText email, password, name,address;
    private RegisterAdapter registerAdapter;
    private Context mContext = ApplicationProvider.getApplicationContext();

    @Mock
    //FirebaseApp firebaseApp;
    private FirebaseFirestore mFirebaseFirestore;

    @Before
    public void init(){
        //MockitoAnnotations.initMocks(this);
        //firebaseApp = Mockito.mock(FirebaseApp.class);
        //Mockito.when(firebaseApp.initializeApp(mContext)).thenReturn(firebaseApp);
        FirebaseApp.initializeApp(mContext);
    }

    @Before
    //public void before() {
      //  registerValidationActions = Mockito.mock(RegisterValidationActions.class);
    //}

    @Test
    public void emailValidation() {
        // Here you can try things like this
        registerValidationActions = new RegisterValidationActions();
        email = new TextInputEditText(mContext);
        assertTrue(RegisterValidationActions.isEmailValid("email@gmail.com", email));
    }



    /*public void deleteOffer() {
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mFirebaseFirestore.collection("offers").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                //Do something
            }
        });
    }*/

    @Test
    public void passwordValidation(){
        registerValidationActions=new RegisterValidationActions();
        password = new TextInputEditText(mContext);
        assertTrue(RegisterValidationActions.isPasswordValid("12345678",password));
    }

    @Test
    public void nameValidation(){
        registerValidationActions=new RegisterValidationActions();
        name=new TextInputEditText(mContext);
        assertTrue(RegisterValidationActions.isNameValid("Timotei",name));
    }

    @Test
    public void addressValidation(){
        registerValidationActions=new RegisterValidationActions();
        address=new TextInputEditText(mContext);
        assertTrue(RegisterValidationActions.isAddressValid("str.'Closca',nr.10",address));
    }
}