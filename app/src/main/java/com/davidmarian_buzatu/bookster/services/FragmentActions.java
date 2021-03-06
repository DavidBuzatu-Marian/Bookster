package com.davidmarian_buzatu.bookster.services;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.davidmarian_buzatu.bookster.R;
import com.davidmarian_buzatu.bookster.fragment.DisplayOfferFragment;
import com.davidmarian_buzatu.bookster.model.Offer;
import com.davidmarian_buzatu.bookster.model.Reservation;
import com.google.gson.GsonBuilder;

public class FragmentActions {

    public static void startDisplayOfferFragment(Offer offer, FragmentActivity activity, String displayType, Long startDate, Long endDate) {
        DisplayOfferFragment nextFragment = new DisplayOfferFragment();
        Bundle bundle = new Bundle();
        String offerStringified = new GsonBuilder().create().toJson(offer);
        bundle.putString("Offer", offerStringified);
        bundle.putString("displayOfferType", displayType);
        if(startDate != null && endDate != null) {
            bundle.putLong("startDate", startDate);
            bundle.putLong("endDate", endDate);
        }
        nextFragment.setArguments(bundle);
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment, nextFragment)
                .addToBackStack(nextFragment.getClass().getName())
                .commit();
    }

    public static void startDisplayOfferFragment(Offer offer, FragmentActivity activity, String displayType, Reservation reservation) {
        DisplayOfferFragment nextFragment = new DisplayOfferFragment();
        Bundle bundle = new Bundle();

        String offerStringified = new GsonBuilder().create().toJson(offer);
        String reservationStringified = new GsonBuilder().create().toJson(reservation);
        bundle.putString("Offer", offerStringified);
        bundle.putString("displayOfferType", displayType);
        bundle.putString("Reservation", reservationStringified);
        nextFragment.setArguments(bundle);
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment, nextFragment)
                .addToBackStack(nextFragment.getClass().getName())
                .commit();
    }
}
