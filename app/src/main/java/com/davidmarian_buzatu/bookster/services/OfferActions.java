package com.davidmarian_buzatu.bookster.services;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.davidmarian_buzatu.bookster.R;
import com.davidmarian_buzatu.bookster.activity.ui.search.helper.DialogShow;
import com.davidmarian_buzatu.bookster.model.Offer;
import com.davidmarian_buzatu.bookster.model.Reservation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

    public void cancelOffer(Offer offer, Context context) {
        displayLoadingDialog(context, R.string.frag_displayOffer_dialog_delete_offer_message);
        deleteOffer(offer).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                deleteReservationsForOffer(offer, context);
            }
        });
    }

    private void deleteReservationsForOffer(Offer offer, Context context) {
        List<Reservation> newListReservation = new ArrayList<>();
        getListOfReservations().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    List<HashMap<String, Object>> reservationList = (List<HashMap<String, Object>>) task.getResult().get("reservations");
                    for(HashMap<String, Object> reservationMap: reservationList) {
                        for(Map.Entry<String, Object> entry: reservationMap.entrySet()) {
                            Reservation reservation = (Reservation) entry.getValue();
                            if(!reservation.getOfferID().equals(offer.getOfferID())) {
                                newListReservation.add(reservation);
                            }
                        }
                    }
                    saveNewReservationList(newListReservation, context);
                }
            }
        });
    }

    private void saveNewReservationList(List<Reservation> newListReservation, Context context) {
        FirebaseFirestore.getInstance().collection("reservations").document(FirebaseAuth.getInstance().getUid()).update("reservations", newListReservation).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(!task.isSuccessful()) {
                    Toast.makeText(context, "Offer and reservations deleted!", Toast.LENGTH_LONG).show();
                }
                mDialog.dismiss();
            }
        });
    }

    private Task<DocumentSnapshot> getListOfReservations() {
        return FirebaseFirestore.getInstance().collection("reservations").document(FirebaseAuth.getInstance().getUid()).get();
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
                    Reservation reservation = createReservation(offer, totalPrice);
                    saveReservationToFirebase(reservation, context);
                } else {
                    mDialog.dismiss();
                    Toast.makeText(context, "Error while making reservation", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private Reservation createReservation(Offer offer, double totalPrice) {
        Reservation reservation = new Reservation();
        reservation.setEndDate(offer.getDateEnd());
        reservation.setStartDate(offer.getDateStart());
        reservation.setLocation(offer.getCityName());
        reservation.setOfferID(offer.getOfferID());
        reservation.setPresentationURL(offer.getPresentationURL());
        reservation.setPrice(String.valueOf(totalPrice));
        return reservation;
    }

    private void displayLoadingDialog(Context context, int messageResource) {
        mDialog = DialogShow.getInstance().getDisplayDialog(context, messageResource);
        mDialog.show();
    }

    private void saveReservationToFirebase(Reservation reservation, Context context) {
        Map<String, Object> currentReservation = new HashMap<>();
        currentReservation.put(String.valueOf(UUID.randomUUID()), reservation);
        FirebaseFirestore.getInstance()
                .collection("reservations")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .update("reservations", FieldValue.arrayUnion(currentReservation))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        mDialog.dismiss();
                        if (task.isSuccessful()) {
                            Toast.makeText(context, "Reservation Made!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(context, "Reservation Error!" + task.getException().toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private Task<Void> updateOfferInFirebase(Offer offer) {
        return FirebaseFirestore.getInstance()
                .collection("offers")
                .document(offer.getOfferID())
                .set(offer);
    }

    public void cancelOfferManager(Offer mOffer, Context context) {

    }
}
