package com.davidmarian_buzatu.bookster.activity.ui.search.helper;

import android.annotation.SuppressLint;
import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

@SuppressLint("ParcelCreator")
public class SearchList implements SearchSuggestion {
    private final String value;

    public SearchList(String value) {
        this.value = value;
    }
    @Override
    public String getBody() {
        return this.value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
