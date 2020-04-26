package com.davidmarian_buzatu.bookster.activity.ui.search;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.davidmarian_buzatu.bookster.R;

public class ListOffersFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_list_offers, container, false);
        Log.d("LIST_OFFER", "LISTED");
        return root;
    }
}
