package com.davidmarian_buzatu.bookster.services;

import android.util.Patterns;

import com.davidmarian_buzatu.bookster.adapter.RegisterAdapter;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterValidationActions {

    private static RegisterValidationActions mInstance;

    private RegisterValidationActions() {

    }

    public static RegisterValidationActions getInstance() {
        if(mInstance == null) {
            mInstance = new RegisterValidationActions();
        }
        return mInstance;
    }

    //used for client
    public boolean validFields(TextInputEditText email, TextInputEditText password, TextInputEditText name, RegisterAdapter regAdapter) {
        if (email.getText() == null || !isEmailValid(email.getText().toString(), email)) {
            return false;
        }

        if(password.getText() == null || !isPasswordValid(password.getText().toString(), password)) {
            return false;
        }

        if(name.getText() == null || !isNameValid(name.getText().toString(), name)) {
            name.setError("Name cannot be empty");
            return false;
        }
        if(!regAdapter.getIsValidNumberClient()) {
            return false;
        }

        return true;
    }

    //used for manager
    public boolean validFields(TextInputEditText email, TextInputEditText password, TextInputEditText name, TextInputEditText address, RegisterAdapter regAdapter) {
        if (email.getText() == null || !isEmailValid(email.getText().toString(), email)) {
            return false;
        }

        if(password.getText() == null || !isPasswordValid(password.getText().toString(), password)) {
            return false;
        }

        if(name.getText() == null || !isNameValid(name.getText().toString(), name)) {
            name.setError("Name cannot be empty");
            return false;
        }

        if(address.getText() == null || !isAddressValid(address.getText().toString(), address)) {
            address.setError("Name cannot be empty");
            return false;
        }
        if(!regAdapter.getIsValidNumberManager()) {
            return false;
        }
        return true;
    }

    private boolean isAddressValid(String address, TextInputEditText addressET) {
        if(address.isEmpty()) {
            addressET.setError("Address cannot be empty");
            return false;
        }
        if(address.contains("\"") || address.contains("\'")) {
            addressET.setError("Address cannot contain apostrophes");
            return false;
        }
        return true;
    }


    private boolean isNameValid(String name, TextInputEditText nameET) {
        boolean hasDigit_TRUE = false;
        if(name.isEmpty()) {
            nameET.setError("Name cannot be empty");
            return false;
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

    private boolean isPasswordValid(String password, TextInputEditText passwordET) {
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

    private boolean isEmailValid(String email, TextInputEditText emailET) {
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
}
