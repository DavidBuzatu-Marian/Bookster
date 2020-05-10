package com.davidmarian_buzatu.bookster.services;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.davidmarian_buzatu.bookster.R;
import com.davidmarian_buzatu.bookster.fragment.DisplayOfferFragment;
import com.davidmarian_buzatu.bookster.model.Offer;
import com.google.gson.GsonBuilder;

public class FragmentActions {

    public static void startDisplayOfferFragment(Offer offer, FragmentActivity activity, String displayType) {
        DisplayOfferFragment nextFragment = new DisplayOfferFragment();
        Bundle bundle = new Bundle();

        String offerStringified = new GsonBuilder().create().toJson(offer);
        bundle.putString("Offer", offerStringified);
        bundle.putString("displayOfferType", displayType);
        nextFragment.setArguments(bundle);
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment, nextFragment)
                .addToBackStack(null)
                .commit();
    }
}
