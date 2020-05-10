package com.davidmarian_buzatu.bookster.constant;

import androidx.annotation.NonNull;

public enum DisplayOfferTypes {
    OFFER_CLIENT("ViewOfferClient"),
    OFFER_MANAGER("ViewOfferManager"),
    OFFER_RESERVATION("ViewOfferReservation");

    private final String mText;

    private DisplayOfferTypes(String text) {
        mText = text;
    }

    @NonNull
    @Override
    public String toString() {
        return mText;
    }
}
