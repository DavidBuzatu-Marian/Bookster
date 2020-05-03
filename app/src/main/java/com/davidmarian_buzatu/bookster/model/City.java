package com.davidmarian_buzatu.bookster.model;

import androidx.annotation.Nullable;

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

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        City objCity = (City) obj;
        return (objCity.getCityName().equals(getCityName()) && objCity.getCountry().equals(getCountry()));
    }
}
