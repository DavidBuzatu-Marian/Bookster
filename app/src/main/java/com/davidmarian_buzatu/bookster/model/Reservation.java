package com.davidmarian_buzatu.bookster.model;

import java.util.Map;

public class Reservation {
    private String mLocation, mOfferID, mPrice, mPresentationURL;
    private Long mStartDate, mEndDate;

    public Reservation(Map<String, Object> values) {
        for(Map.Entry<String, Object> entry: values.entrySet()) {
            switch(entry.getKey()) {
                case "endDate":
                    mEndDate = (Long) entry.getValue();
                    break;
                case "startDate":
                    mStartDate = (Long) entry.getValue();
                    break;
                case "location":
                    mLocation = (String) entry.getValue();
                    break;
                case "offerID":
                    mOfferID = (String) entry.getValue();
                    break;
                case "price":
                    mPrice = (String) entry.getValue();
                    break;
                case "presentationURL":
                    mPresentationURL = (String) entry.getValue();
                    break;
                default:
                    break;
            }

        }
    }

    public Reservation(Long dateStart, Long dateEnd, String price, String cityName, String offerID, String presentationURL) {
        mStartDate = dateStart;
        mEndDate = dateEnd;
        mPrice = price;
        mLocation = cityName;
        mOfferID = offerID;
        mPresentationURL = presentationURL;
    }

    public Long getStartDate() {
        return mStartDate;
    }

    public Long getEndDate() {
        return mEndDate;
    }

    public String getLocation() {
        return mLocation;
    }

    public String getOfferID() {
        return mOfferID;
    }

    public String getPrice() {
        return mPrice;
    }

    public String getPresentationURL() {
        return mPresentationURL;
    }
}