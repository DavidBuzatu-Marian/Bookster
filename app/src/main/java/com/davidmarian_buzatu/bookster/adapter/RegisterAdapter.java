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
    private CountryCodePicker mCCP;
    private boolean mIsValidNumber;
    private final Context mContext;

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
        mCCP = position == 0 ? layout.findViewById(R.id.act_register_cpp) : layout.findViewById(R.id.act_register_cpp_manager);
        EditText mEditTextCarrierNumber = position == 0 ? layout.findViewById(R.id.act_register_ET_carrierNumber) : layout.findViewById(R.id.act_register_ET_carrierNumber_manager);

        mCCP.registerCarrierNumberEditText(mEditTextCarrierNumber);

        mCCP.setPhoneNumberValidityChangeListener(new CountryCodePicker.PhoneNumberValidityChangeListener() {
            @Override
            public void onValidityChanged(boolean isValidNumber) {
                mIsValidNumber = isValidNumber;
            }
        });
    }

    public CountryCodePicker getCCP() {
        return mCCP;
    }

    public boolean getIsValidNumber() {
        return mIsValidNumber;
    }
}
