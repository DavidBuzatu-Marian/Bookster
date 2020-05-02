package com.davidmarian_buzatu.bookster.model;

import java.util.List;

public class Offer {

    private String mName, mDescription, mPresentationURL, mRoomDescription, mRoomType, mManagerID, mRating, mLongitude, mLatitude, mID;
    private List<String> mFacilities, mPictures, mPopularFacilities;
    private String mNrPersons, mPrice, mRoomsAvailable,  mSize;
    private Long mDateStart, mDateEnd;
    private City mCity;

    public void setCity(City city) {
        mCity = city;
    }

    public City getCity() {
        return mCity;
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

    public List<String> getFacilities() {
        return mFacilities;
    }

    public String getPresentaion() {
        return mPresentationURL;
    }

    public void setPrice(String mPrice) {
        this.mPrice = mPrice;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public void setRating(String mRating) {
        this.mRating = mRating;
    }

    public void setPresentationURL(String mPresentationURL) {
        this.mPresentationURL = mPresentationURL;
    }

    public void setFacilities(List<String>  mFacilities) {
        this.mFacilities = mFacilities;
    }

    public Long getDateStart() {
        return mDateStart;
    }

    public void setDateStart(Long mDateStart) {
        this.mDateStart = mDateStart;
    }

    public Long getDateEnd() {
        return mDateEnd;
    }

    public void setDateEnd(Long mDateEnd) {
        this.mDateEnd = mDateEnd;
    }

    public String getRoomDescription() {
        return mRoomDescription;
    }

    public void setRoomDescription(String mRoomDescription) {
        this.mRoomDescription = mRoomDescription;
    }

    public String getRoomType() {
        return mRoomType;
    }

    public void setRoomType(String mRoomType) {
        this.mRoomType = mRoomType;
    }

    public String getRoomsAvailable() {
        return mRoomsAvailable;
    }

    public void setRoomsAvailable(String mRoomsAvailable) {
        this.mRoomsAvailable = mRoomsAvailable;
    }

    public String getSize() {
        return mSize;
    }

    public void setSize(String mSize) {
        this.mSize = mSize;
    }

    public List<String>  getPictures() {
        return mPictures;
    }

    public void setPictures(List<String>  mPictures) {
        this.mPictures = mPictures;
    }

    public List<String>  getPopularFacilities() {
        return mPopularFacilities;
    }

    public void setPopularFacilities(List<String>  mPopularFacilities) {
        this.mPopularFacilities = mPopularFacilities;
    }

    public void setManagerID(String value) {
        this.mManagerID = value;
    }
    public String getManagerID() {
        return mManagerID;
    }

    public void setNrPersons(String value) {
        this.mNrPersons = value;
    }

    public String getNrPersons() {
        return mNrPersons;
    }

    public String getLongitude() {
        return mLongitude;
    }

    public void setLongitude(String mLongitude) {
        this.mLongitude = mLongitude;
    }

    public String getLatitude() {
        return mLatitude;
    }

    public void setLatitude(String mLatitude) {
        this.mLatitude = mLatitude;
    }

    public void setOfferID(String id) {
        mID = id;
    }
    public String getOfferID() {
        return mID;
    }
}
