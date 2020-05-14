package com.davidmarian_buzatu.bookster.services;

import android.app.ProgressDialog;
import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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

    public void deleteOffer(Offer offer, Context context) {
        displayLoadingDialog(context, R.string.frag_displayOffer_dialog_delete_offer_message);
        deleteOffer(offer).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                deleteAllReservationsForOffer(offer, context);
            }
        });

    }

    private void deleteAllReservationsForOffer(Offer offer, Context context) {
        List<Reservation> newReservationList = new ArrayList<>();
        getAllReservations().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        // we get a map which has a list of reservations
                        Map<String, Object> reservationMap = doc.getData();
                        List<HashMap<String, Object>> reservationMapList = (List<HashMap<String, Object>>) reservationMap.get("reservations");
                        for (Map<String, Object> mapReservation : reservationMapList) {
                            // Its a map in the database
                            Map.Entry<String, Object> entry = mapReservation.entrySet().iterator().next();
                            Reservation reservation = new Reservation();
                            reservation.setReservationFromMap(entry);
                            if (!reservation.getOfferID().equals(offer.getOfferID())) {
                                newReservationList.add(reservation);
                            }
                        }
                        saveNewReservationList(newReservationList, "reservation", doc.getId()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(context, "Reservation(s) deleted!", Toast.LENGTH_LONG).show();
                                }
                                mDialog.dismiss();
                            }
                        });
                    }
                }
            }
        });
    }

    private Task<QuerySnapshot> getAllReservations() {
        return FirebaseFirestore.getInstance().collection("reservations").get();
    }

    public void deleteReservationsForOffer(Offer offer, Context context, String collection, String document) {
        List<Reservation> newListReservation = new ArrayList<>();
        getListOfReservations(collection, document).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    List<HashMap<String, Object>> reservationList = (List<HashMap<String, Object>>) task.getResult().get("reservations");
                    for (HashMap<String, Object> reservationMap : reservationList) {
                        for (Map.Entry<String, Object> entry : reservationMap.entrySet()) {
                            Reservation reservation = (Reservation) entry.getValue();
                            if (!reservation.getOfferID().equals(offer.getOfferID())) {
                                newListReservation.add(reservation);
                            }
                        }
                    }
                    saveNewReservationList(newListReservation, "reservations", FirebaseAuth.getInstance().getUid()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(context, "Reservation(s) deleted!", Toast.LENGTH_LONG).show();
                            }
                            mDialog.dismiss();
                        }
                    });
                }
            }
        });
    }

    private Task<Void> saveNewReservationList(List<Reservation> newListReservation, String collection, String docID) {
        return FirebaseFirestore.getInstance().collection(collection).document(docID).update("reservations", newListReservation);
    }


    public void deleteReservation(Reservation reservation,Context context,String collection,String document) {
        List<Reservation> newListReservation = new ArrayList<>();
        getListOfReservations(collection,document).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    List<HashMap<String, Object>> reservationList = (List<HashMap<String, Object>>) task.getResult().get("reservations");
                    for (HashMap<String, Object> reservationMap : reservationList) {
                        for (Map.Entry<String, Object> entry : reservationMap.entrySet()) {
                            Reservation reservation1 = (Reservation) entry.getValue();
                            if (!reservation1.getID().equals(reservation.getID())) {
                                newListReservation.add(reservation1);
                            }
                        }
                    }
                    saveNewReservationList(newListReservation, "reservations", FirebaseAuth.getInstance().getUid()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(context, "Reservation(s) deleted!", Toast.LENGTH_LONG).show();
                            }
                            mDialog.dismiss();
                        }
                    });
                }
            }
        });

    }

    private Task<DocumentSnapshot> getListOfReservations(String collection, String document) {
        return FirebaseFirestore.getInstance().collection(collection).document(document).get();
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
                    saveReservationToFirebase(reservation, context, offer.getManagerID());
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

    private void saveReservationToFirebase(Reservation reservation, Context context, String managerID) {
        Map<String, Object> currentReservation = new HashMap<>();
        reservation.setClientID(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reservation.setID(String.valueOf(UUID.randomUUID()));
        currentReservation.put(reservation.getID(), reservation);
        FirebaseFirestore.getInstance()
                .collection("reservations")
                .document(reservation.getClientID())
                .update("reservations", FieldValue.arrayUnion(currentReservation))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            addReservationToManager(reservation, managerID, context);

                        } else {
                            Toast.makeText(context, "Reservation Error!" + task.getException().toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void addReservationToManager(Reservation reservation, String managerID, Context context) {
        Map<String, Object> currentReservation = new HashMap<>();
        currentReservation.put(reservation.getID(), reservation);
        FirebaseFirestore.getInstance()
                .collection("reservationsManager")
                .document(managerID)
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

}
