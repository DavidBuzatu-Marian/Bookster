package com.davidmarian_buzatu.bookster.services;

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
import static org.junit.Assert.assertFalse;

@RunWith(AndroidJUnit4.class)
public class ValidationTest {
    @Mock
    private RegisterValidationActions registerValidationActions;
    private TextInputEditText email, password, name, address;
    private RegisterAdapter registerAdapter;
    private Context mContext = ApplicationProvider.getApplicationContext();


    @Test
    public void emailValidation() {
        // Here you can try things like this
        registerValidationActions = new RegisterValidationActions();
        email = new TextInputEditText(mContext);
        assertTrue(RegisterValidationActions.isEmailValid("email@gmail.com", email));
        assertTrue(RegisterValidationActions.isEmailValid("email.david_28@gmail.com", email));
        assertFalse(RegisterValidationActions.isEmailValid("email@gmail", email));
        assertFalse(RegisterValidationActions.isEmailValid("", email));
    }

    @Test
    public void passwordValidation() {
        registerValidationActions = new RegisterValidationActions();
        password = new TextInputEditText(mContext);
        assertFalse(RegisterValidationActions.isPasswordValid("12345678", password));
        assertTrue(RegisterValidationActions.isPasswordValid("12345678Aa", password));
        assertTrue(RegisterValidationActions.isPasswordValid("A random password2",password));
        assertFalse(RegisterValidationActions.isPasswordValid("", password));
    }

    @Test
    public void nameValidation() {
        registerValidationActions = new RegisterValidationActions();
        name = new TextInputEditText(mContext);
        assertTrue(RegisterValidationActions.isNameValid("Timotei", name));
        assertTrue(RegisterValidationActions.isNameValid("Timotei Caracoancea", name));
        assertFalse(RegisterValidationActions.isNameValid("1243 test fail", name));
    }

    @Test
    public void addressValidation() {
        registerValidationActions = new RegisterValidationActions();
        address = new TextInputEditText(mContext);
        assertFalse(RegisterValidationActions.isAddressValid("str.'Closca',nr.10", address));
        assertTrue(RegisterValidationActions.isAddressValid("str.Closca,nr.10", address));
    }
}