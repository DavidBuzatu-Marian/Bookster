package com.davidmarian_buzatu.bookster.model;

import java.util.Map;

public class Reservation {
    private String mLocation, mOfferID, mPrice, mPresentationURL;
    private Long mStartDate, mEndDate;

//    public Reservation(Map<String, Object> values) {
//        for (Map.Entry<String, Object> entry : values.entrySet()) {
//            switch (entry.getKey()) {
//                case "endDate":
//                    mEndDate = (Long) entry.getValue();
//                    break;
//                case "startDate":
//                    mStartDate = (Long) entry.getValue();
//                    break;
//                case "location":
//                    mLocation = (String) entry.getValue();
//                    break;
//                case "offerID":
//                    mOfferID = (String) entry.getValue();
//                    break;
//                case "price":
//                    mPrice = (String) entry.getValue();
//                    break;
//                case "presentationURL":
//                    mPresentationURL = (String) entry.getValue();
//                    break;
//                default:
//                    break;
//            }
//
//        }
//    }

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

    public String getPresentationURL() {
        return mPresentationURL;
    }

    public void setPresentationURL(String presentationURL) {
        mPresentationURL = presentationURL;
    }

    public void setReservationFromMap(Map.Entry<String, Object> entry) {
        Map<String, Object> mapResevation = (Map<String, Object>) entry.getValue();
        for (Map.Entry<String, Object> reservationEntry : mapResevation.entrySet()) {
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
                default:
                    break;
            }

        }
    }

}