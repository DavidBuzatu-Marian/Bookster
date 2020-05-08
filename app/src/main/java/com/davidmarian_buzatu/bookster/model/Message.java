package com.davidmarian_buzatu.bookster.model;

public class Message {
    private String mOfferID;

    public Message(String mOfferID) {
        this.mOfferID = mOfferID;
    }

    public String getOfferID() {
        return mOfferID;
    }

    public void setOfferID(String mOfferID) {
        this.mOfferID = mOfferID;
    }
}
