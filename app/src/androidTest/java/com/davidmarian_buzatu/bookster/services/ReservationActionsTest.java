package com.davidmarian_buzatu.bookster.services;

import com.davidmarian_buzatu.bookster.model.Offer;
import com.davidmarian_buzatu.bookster.model.Reservation;

import org.junit.Test;

import static org.junit.Assert.*;

public class ReservationActionsTest {
    private ReservationActions reservationActions;
    private Offer offer;
    private Reservation reservation;
    private double price;

    @Test
    public void createReservation() {
        reservationActions=ReservationActions.getInstance();
        offer=new Offer();
        long date1=1234123412;
        long date2=2023421232;
        price=500;
        offer.setDateEnd(date2);
        offer.setDateStart(date1);
        offer.setCityName("Rome,Italy");
        offer.setOfferID("1223-2322-2423");
        offer.setPresentationURL("presentation");
        reservation=new Reservation();
        reservation.setEndDate(date2);
        reservation.setStartDate(date1);
        reservation.setPresentationURL("presentation");
        reservation.setLocation("Rome,Italy");
        reservation.setOfferID("1223-2322-2423");
        reservation.setPrice(String.valueOf(price));
        assertEquals(reservation,reservationActions.createReservation(offer,price));
    }
}