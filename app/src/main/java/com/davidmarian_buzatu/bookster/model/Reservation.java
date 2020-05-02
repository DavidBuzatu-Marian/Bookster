package com.davidmarian_buzatu.bookster.model;

public class Reservation {
    private Long mStartDate, mEndDate;
    private String mPrice, mLocation, mOfferID;

    public Reservation(Long startDate, Long endDate, String price, String location, String offerID) {
        mStartDate = startDate;
        mEndDate = endDate;
        mPrice = price;
        mLocation = location;
        mOfferID = offerID;
    }

    public void setStartDate(Long date) {
        mStartDate = date;
    }

    public Long getStartDate() {
        return mStartDate;
    }

    public void setEndDate(Long date) {
        mEndDate = date;
    }

    public Long getEndDate() {
        return mEndDate;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String mPrice) {
        this.mPrice = mPrice;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String mLocation) {
        this.mLocation = mLocation;
    }

    public String getOfferID() {
        return mOfferID;
    }

    public void setmOfferID(String mOfferID) {
        this.mOfferID = mOfferID;
    }
}
