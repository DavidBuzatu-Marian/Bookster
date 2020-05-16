package com.davidmarian_buzatu.bookster.model;

import java.util.Map;
import java.util.Objects;

public class Reservation {
    private String mLocation, mOfferID, mPrice, mPresentationURL, mClientID, mID;
    private Long mStartDate, mEndDate;

    public Long getStartDate() {
        return mStartDate;
    }

    public void setStartDate(long startDate) {
        mStartDate = startDate;
    }

    public Long getEndDate() {
        return mEndDate;
    }

    public void setEndDate(long endDate) {
        mEndDate = endDate;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public String getOfferID() {
        return mOfferID;
    }

    public void setOfferID(String offerID) {
        mOfferID = offerID;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public void setClientID(String clientID) {
        this.mClientID = clientID;
    }

    public String getClientID() {
        return mClientID;
    }

    public void setID(String id) {
        mID = id;
    }

    public String getID() {
        return mID;
    }

    public String getPresentationURL() {
        return mPresentationURL;
    }

    public void setPresentationURL(String presentationURL) {
        mPresentationURL = presentationURL;
    }

    public void setReservationFromMap(Map<String, Object> mapReservation) {
        for (Map.Entry<String, Object> reservationEntry : mapReservation.entrySet()) {
            switch (reservationEntry.getKey()) {
                case "endDate":
                    setEndDate((Long) reservationEntry.getValue());
                    break;
                case "startDate":
                    setStartDate((Long) reservationEntry.getValue());
                    break;
                case "location":
                    setLocation((String) reservationEntry.getValue());
                    break;
                case "price":
                    setPrice((String) reservationEntry.getValue());
                    break;
                case "presentationURL":
                    setPresentationURL((String) reservationEntry.getValue());
                    break;
                case "offerID":
                    setOfferID((String) reservationEntry.getValue());
                    break;
                case "clientID":
                    setClientID((String) reservationEntry.getValue());
                    break;
                case "id":
                    setID((String) reservationEntry.getValue());
                default:
                    break;
            }

        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(mLocation, that.mLocation) &&
                Objects.equals(mOfferID, that.mOfferID) &&
                Objects.equals(mPrice, that.mPrice) &&
                Objects.equals(mPresentationURL, that.mPresentationURL) &&
                Objects.equals(mClientID, that.mClientID) &&
                Objects.equals(mID, that.mID) &&
                Objects.equals(mStartDate, that.mStartDate) &&
                Objects.equals(mEndDate, that.mEndDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mLocation, mOfferID, mPrice, mPresentationURL, mClientID, mID, mStartDate, mEndDate);
    }
}