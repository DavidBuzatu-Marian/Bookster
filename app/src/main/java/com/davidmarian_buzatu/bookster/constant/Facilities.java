package com.davidmarian_buzatu.bookster.constant;

import androidx.annotation.NonNull;

public enum Facilities {
    BREAKFAST("Delicious breakfast"),
    GYM("Fully equipped gym"),
    INTERNET("Wi-fi"),
    POOL("Swimming pool"),
    BAR("Bar"),
    NON_SMOKING("Non-smoking rooms"),
    SPA("Spa & wellness center");

    private final String mText;

    private Facilities(String text) {
        mText = text;
    }

    @NonNull
    @Override
    public String toString() {
        return mText;
    }
}
