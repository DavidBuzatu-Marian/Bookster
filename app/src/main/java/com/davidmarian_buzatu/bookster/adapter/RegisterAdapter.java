package com.davidmarian_buzatu.bookster.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.davidmarian_buzatu.bookster.R;
import com.hbb20.CountryCodePicker;

public class RegisterAdapter  extends PagerAdapter {
    private CountryCodePicker mCCPClient, mCCPManager;
    private boolean mIsValidNumberClient = false,  mIsValidNumberManager = false;
    private final Context mContext;
    private EditText mEditTextCarrierNumberClient, mEditTextCarrierNumberManager;

    public RegisterAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        int resID = 0;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        switch (position) {
            case 0:
                resID = R.layout.register_client;
                break;
            case 1:
                resID = R.layout.register_manager;
                break;
        }
        ViewGroup layout = (ViewGroup) inflater.inflate(resID, collection, false);
        collection.addView(layout);
        // Set card corners
        setCardViewCorners(layout);
        setCCP(layout, position);
        return layout;
    }

    private void setCardViewCorners(ViewGroup layout) {
        CardView cv = layout.findViewById(R.id.act_register_CV_FormContainer);
        cv.setRadius(32);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ConstraintLayout)object);
    }

    private void setCCP(ViewGroup layout, int position) {
        if(position == 0) {
            mCCPClient = layout.findViewById(R.id.act_register_cpp);
            mEditTextCarrierNumberClient = layout.findViewById(R.id.act_register_ET_carrierNumber);

            mCCPClient.registerCarrierNumberEditText(mEditTextCarrierNumberClient);
            mCCPClient.setPhoneNumberValidityChangeListener(new CountryCodePicker.PhoneNumberValidityChangeListener() {
                @Override
                public void onValidityChanged(boolean isValidNumber) {
                    mIsValidNumberClient = isValidNumber;
                }
            });
        }else {
            mCCPManager = layout.findViewById(R.id.act_register_cpp_manager);
            mEditTextCarrierNumberManager = layout.findViewById(R.id.act_register_ET_carrierNumber_manager);
            mCCPManager.registerCarrierNumberEditText(mEditTextCarrierNumberManager);

            mCCPManager.setPhoneNumberValidityChangeListener(new CountryCodePicker.PhoneNumberValidityChangeListener() {
                @Override
                public void onValidityChanged(boolean isValidNumber) {
                    mIsValidNumberManager = isValidNumber;
                }
            });
        }
    }

    public CountryCodePicker getmCCPClient() {
        return mCCPClient;
    }

    public CountryCodePicker getmCCPManager() {
        return mCCPManager;
    }

    public boolean getIsValidNumberClient() {
        return mIsValidNumberClient;
    }
    public boolean getIsValidNumberManager() {
        return mIsValidNumberManager;
    }
}
