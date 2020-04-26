package com.davidmarian_buzatu.bookster.model;

public class Offer {

    private String mLocation, mPrice, mName, mDescription, mRating;
    private String[] mFacilities;

    public Offer(String location, String price, String name, String description, String rating, String[] facilities) {
        mLocation = location;
        mPrice = price;
        mName = name;
        mDescription = description;
        mRating = rating;
        mFacilities = facilities;
    }

    public String getLocation() {
        return mLocation;
    }

    public String getPrice() {
        return mPrice;
    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getRating() {
        return mRating;
    }

    public String[] getFacilities() {
        return mFacilities;
    }
}