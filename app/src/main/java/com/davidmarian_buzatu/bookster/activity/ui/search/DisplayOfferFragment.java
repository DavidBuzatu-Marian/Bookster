package com.davidmarian_buzatu.bookster.activity.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.davidmarian_buzatu.bookster.R;
import com.davidmarian_buzatu.bookster.adapter.ViewPagerImagesAdapter;

import java.util.ArrayList;
import java.util.List;

public class DisplayOfferFragment extends Fragment {

    private ViewPager2 mViewPager2;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_display_offer, container, false);

        setUpViewPager(root);

        return root;
    }

    private void setUpViewPager(View root) {
        mViewPager2 = root.findViewById(R.id.frag_displayOffer_VP_images);
        List<String> list = new ArrayList<>();
        list.add("https://firebasestorage.googleapis.com/v0/b/bookster-9e512.appspot.com/o/properties_ref%2F5ea7ba25-596db591.jpg?alt=media&token=d24b8ca2-57c1-428b-96a8-6d20f55f6327");
        list.add("https://firebasestorage.googleapis.com/v0/b/bookster-9e512.appspot.com/o/properties_ref%2Fhotelreview1a.jpg?alt=media&token=028052e8-cdb0-4ca8-8d5e-848a23f8dc54");
        mViewPager2.setAdapter(new ViewPagerImagesAdapter(getContext(), list));
    }
}
