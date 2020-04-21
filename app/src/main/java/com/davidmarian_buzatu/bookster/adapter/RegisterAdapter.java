package com.davidmarian_buzatu.bookster.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.davidmarian_buzatu.bookster.R;

public class RegisterAdapter  extends PagerAdapter {

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
        return layout;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ConstraintLayout)object);
    }
}
