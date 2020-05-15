package com.davidmarian_buzatu.bookster.services;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.davidmarian_buzatu.bookster.R;
import com.davidmarian_buzatu.bookster.model.Offer;
import com.davidmarian_buzatu.bookster.model.Reservation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OfferActions {
    private static OfferActions mOfferActions;
    private ProgressDialog mDialog;

    private OfferActions() {

    }

    public static OfferActions getInstance() {
        if (mOfferActions == null) {
            mOfferActions = new OfferActions();
        }
        return mOfferActions;
    }

    public void deleteOffer(Offer offer, Context context) {
        displayLoadingDialog(context, R.string.frag_displayOffer_dialog_delete_offer_message);
        deleteOffer(offer).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                ReservationActions.getInstance().deleteAllReservationsForOffer(offer, context, mDialog);
            }
        });

    }


    private Task<Void> deleteOffer(Offer offer) {
        return FirebaseFirestore.getInstance().collection("offers").document(offer.getOfferID()).delete();
    }


    public void reserveOffer(Offer offer, Context context, double totalPrice) {
        int nrRoomsAvailable = Integer.parseInt(offer.getRoomsAvailable());
        if (nrRoomsAvailable > 0) {
            offer.setRoomsAvailable(--nrRoomsAvailable + "");
        }

        displayLoadingDialog(context, R.string.frag_displayOffer_dialog_message);
        updateOfferInFirebase(offer).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Reservation reservation = ReservationActions.getInstance().createReservation(offer, totalPrice);
                    ReservationActions.getInstance().saveReservationToFirebase(reservation, context, offer.getManagerID(), mDialog);
                } else {
                    mDialog.dismiss();
                    Toast.makeText(context, "Error while making reservation", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    private void displayLoadingDialog(Context context, int messageResource) {
        mDialog = DialogShow.getInstance().getDisplayDialog(context, messageResource);
        mDialog.show();
    }


    private Task<Void> updateOfferInFirebase(Offer offer) {
        return FirebaseFirestore.getInstance()
                .collection("offers")
                .document(offer.getOfferID())
                .set(offer);
    }

    public Task<QuerySnapshot> getOffersForField(String field, String value) {
        return FirebaseFirestore.getInstance()
                .collection("offers")
                .whereEqualTo(field, value)
                .get();
    }


    public static boolean validFields(List<TextInputEditText> TIETList, CalendarActions calendarActions, List<Bitmap> picturesList, Context context) {
        for (TextInputEditText tiet : TIETList) {
            if (tiet.getText().toString().length() == 0) {
                tiet.setError("Field required!");
                return false;
            }
        }
        if (calendarActions.getEndDate() == 0) {
            Toast.makeText(context, "A valid period is required!", Toast.LENGTH_LONG).show();
            return false;
        }
        if (calendarActions.getStartDate() == 0) {
            Toast.makeText(context, "A valid period is required!", Toast.LENGTH_LONG).show();
            return false;
        }
        if (picturesList.size() == 0) {
            Toast.makeText(context, "At least two images are required!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public List<Offer> getOffersFromQuery(Task<QuerySnapshot> task) {
        List<Offer> offers = new ArrayList<>();
        for (QueryDocumentSnapshot doc : task.getResult()) {
            Map<String, Object> mapOffer = doc.getData();
            Offer offer = new Offer();
            offer.setOfferFromMap(mapOffer);
            offer.setOfferID(doc.getId());
            offers.add(offer);
        }
        return offers;
    }

    public List<Offer> getOffersFromQueryWithinDate(Task<QuerySnapshot> task, Long startDate, Long endDate) {
        List<Offer> offers = new ArrayList<>();
        for (QueryDocumentSnapshot doc : task.getResult()) {
            Map<String, Object> mapOffer = doc.getData();
            Offer offer = new Offer();
            offer.setOfferFromMap(mapOffer);
            offer.setOfferID(doc.getId());
            if (isValid(offer, startDate, endDate)) {
                offers.add(offer);
            }
        }
        return offers;
    }

    private boolean isValid(Offer offer, Long startDate, Long endDate) {
        return (offer.getDateStart() <= startDate && offer.getDateEnd() >= endDate && Integer.parseInt(offer.getRoomsAvailable()) > 0);
    }
}
