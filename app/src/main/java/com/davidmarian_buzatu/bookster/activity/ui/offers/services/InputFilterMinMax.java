package com.davidmarian_buzatu.bookster.activity.ui.offers.services;

import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;

public class InputFilterMinMax implements InputFilter {
    private double mMin, mMax;

    public InputFilterMinMax(double input1, double input2) {
        if(input1 <= input2) {
            mMin = input1;
            mMax = input2;
        } else {
            mMin = input1;
            mMax = input2;
        }
    }

    @Override
    public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
        try{
            double input = Double.parseDouble(spanned.toString() + charSequence.toString());
            if(inputInRange(mMin, mMax, input)) {
                return null;
            }
        } catch (NumberFormatException nfe) {
            Log.d("Exception", nfe.toString());
        }
        return "";
    }

    private boolean inputInRange(double min, double max, double input) {
        return input >= min && input <= max;
    }
}
