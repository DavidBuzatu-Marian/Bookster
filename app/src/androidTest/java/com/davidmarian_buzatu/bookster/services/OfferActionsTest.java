package com.davidmarian_buzatu.bookster.services;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.test.annotation.UiThreadTest;
import androidx.test.core.app.ApplicationProvider;

import com.davidmarian_buzatu.bookster.model.Offer;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.QuerySnapshot;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.*;

public class OfferActionsTest {
    private List<TextInputEditText> tiet;
    private CalendarActions calendarActions;
    private List<Bitmap> picturesList;
    private Context mContext = ApplicationProvider.getApplicationContext();

    @Test
    @UiThreadTest
    public void validFields() {
        TextInputEditText description = new TextInputEditText(mContext);
        TextInputEditText roomSize = new TextInputEditText(mContext);
        description.setText("central position, luxury hotel");
        roomSize.setText("16");
        tiet = new ArrayList<>();
        tiet.add(description);
        tiet.add(roomSize);
        calendarActions = new CalendarActions();
        picturesList = new ArrayList<>();
        assertFalse(OfferActions.validFields(tiet, calendarActions, picturesList, mContext));
    }

    @Test
    public void isValidOfferMap() {
        List<String> facilities = new ArrayList<>();
        List<String> pictures = new ArrayList<>();
        List<String> popularFacilities = new ArrayList<>();

        String managerID = UUID.randomUUID().toString();
        String offerID = UUID.randomUUID().toString();

        facilities.add("A facility");
        pictures.add("http://random.org");
        popularFacilities.add("BREAKFAST");
        Map<String, Object> mapOffer = new HashMap<>();

        Offer offer = new Offer();
        mapOffer.put("country", "Romania");
        mapOffer.put("cityName", "Arad");
        mapOffer.put("dateEnd", 2023421232L);
        mapOffer.put("dateStart", 1234123412L);
        mapOffer.put("description", "Lorem");
        mapOffer.put("facilities", facilities);
        mapOffer.put("name", "Hilton");
        mapOffer.put("offerID", offerID);
        mapOffer.put("managerID", managerID);
        mapOffer.put("pictures", pictures);
        mapOffer.put("popularFacilities", popularFacilities);
        mapOffer.put("price", "200");
        mapOffer.put("rating", "2");
        mapOffer.put("roomDescription", "fgsgdfsgfd");
        mapOffer.put("roomType", "King");
        mapOffer.put("roomsAvailable", "23");
        mapOffer.put("size", "100");
        mapOffer.put("latitude", "1.43434234");
        mapOffer.put("longitude", "23.434343");
        offer.setOfferFromMap(mapOffer);


        assertEquals(offer, getTestOffer(managerID, offerID, facilities, popularFacilities, pictures));
        assertFalse(offer.equals(getTestOffer(offerID, managerID, facilities, popularFacilities, pictures)));

    }

    // dummy creator used just for testing
    private Offer getTestOffer(String managerID, String offerID, List<String> facilities, List<String> popularFacilities, List<String> pictures) {
        Offer offer = new Offer();
        offer.setCountry("Romania");
        offer.setCityName("Arad");
        offer.setDateEnd(2023421232L);
        offer.setDateStart(1234123412L);
        offer.setDescription("Lorem");
        offer.setFacilities(facilities);
        offer.setName("Hilton");
        offer.setOfferID(offerID);
        offer.setManagerID(managerID);
        offer.setPictures(pictures);
        offer.setPopularFacilities(popularFacilities);
        offer.setPrice("200");
        offer.setRating("2");
        offer.setRoomDescription("fgsgdfsgfd");
        offer.setRoomType("King");
        offer.setRoomsAvailable("23");
        offer.setSize("100");
        offer.setLatitude("1.43434234");
        offer.setLongitude("23.434343");
        return offer;
    }
}