package com.davidmarian_buzatu.bookster.model;

import java.util.List;

public class City {
    private String mCityName, mCountry;

    public City(String cityName, String country) {
        mCityName = cityName;
        mCountry = country;
    }

    public String getCityName() {
        return mCityName;
    }

    public String getCountry() {
        return mCountry;
    }
}
