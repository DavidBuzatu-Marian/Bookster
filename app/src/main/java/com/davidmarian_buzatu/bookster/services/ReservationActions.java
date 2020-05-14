package com.davidmarian_buzatu.bookster.services;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.davidmarian_buzatu.bookster.R;
import com.davidmarian_buzatu.bookster.activity.ui.search.helper.DialogShow;
import com.davidmarian_buzatu.bookster.model.Message;
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

public class ReservationActions {
    private static ReservationActions mReservationActions;
    private ProgressDialog mDialog;

    private ReservationActions() {
    }

    public static ReservationActions getInstance() {
        if (mReservationActions == null) {
            mReservationActions = new ReservationActions();
        }
        return mReservationActions;
    }

    public void deleteAllReservationsForOffer(Offer offer, Context context, ProgressDialog dialog) {
        mDialog = dialog;
        getAllReservations("reservations").addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    deleteAllReservations(task, offer, context, "reservations");
                }
                if(mDialog.isShowing()) {
                    mDialog.dismiss();
                }

            }
        });
        getAllReservations("reservationsManager").addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    deleteAllReservations(task, offer, context, "reservationsManager");
                }
                if(mDialog.isShowing()) {
                    mDialog.dismiss();
                }
            }
        });
    }

    private void deleteAllReservations(Task<QuerySnapshot> task, Offer offer, Context context, String collection) {
        List<Reservation> newReservationList = new ArrayList<>();
        for (QueryDocumentSnapshot doc : task.getResult()) {
            // we get a map which has a list of reservations
            Map<String, Object> reservationMap = doc.getData();
            List<HashMap<String, Object>> reservationMapList = (List<HashMap<String, Object>>) reservationMap.get("reservations");
            for (Map<String, Object> mapReservation : reservationMapList) {
                // Its a map in the database
                Reservation reservation = new Reservation();
                reservation.setReservationFromMap(mapReservation);
                if (!reservation.getOfferID().equals(offer.getOfferID())) {
                    newReservationList.add(reservation);
                }
            }
            saveReservationList(newReservationList, collection, doc.getId(), context);
        }
    }


    private Task<QuerySnapshot> getAllReservations(String collection) {
        return FirebaseFirestore.getInstance().collection(collection).get();
    }

    private void deleteReservationsForOffer(Offer offer, Context context, String collection, String document) {
        List<Reservation> newListReservation = new ArrayList<>();
        getListOfReservations(collection, document).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    List<HashMap<String, Object>> reservationList = (List<HashMap<String, Object>>) task.getResult().get("reservations");
                    for (HashMap<String, Object> reservationMap : reservationList) {
                        Reservation reservation = new Reservation();
                        reservation.setReservationFromMap(reservationMap);
                        if (!reservation.getOfferID().equals(offer.getOfferID())) {
                            newListReservation.add(reservation);
                        }
                    }

                    saveReservationList(newListReservation, collection, document, context);
                }
                if (mDialog.isShowing()) {
                    mDialog.dismiss();
                }
            }
        });
    }


    private Task<Void> saveNewReservationList(List<Reservation> newListReservation, String collection, String docID) {
        return FirebaseFirestore.getInstance().collection(collection).document(docID).update("reservations", newListReservation);
    }


    private void deleteReservationManager(Reservation reservation, Context context, String collection, String document) {
        List<Reservation> newListReservation = new ArrayList<>();
        getListOfReservations(collection, document).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    final List<Map<String, Object>> reservationList = (List<Map<String, Object>>) task.getResult().get("reservations");
                    for (Map<String, Object> reservationMap : reservationList) {
                        Reservation reservationCur = new Reservation();
                        reservationCur.setReservationFromMap(reservationMap);
                        if (!reservationCur.getID().equals(reservation.getID())) {
                            newListReservation.add(reservationCur);
                        }

                    }
                    saveReservationList(newListReservation, collection, document, context);
                }
                if (mDialog.isShowing()) {
                    mDialog.dismiss();
                }

            }
        });

    }


    private void deleteReservationUser(Reservation reservation, Context context, String collection, String document) {
        List<Reservation> newListReservation = new ArrayList<>();
        getListOfReservations(collection, document).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    List<HashMap<String, Object>> reservationList = (List<HashMap<String, Object>>) task.getResult().get("reservations");
                    for (HashMap<String, Object> reservationMap : reservationList) {
                        Reservation reservationCur = new Reservation();
                        reservationCur.setReservationFromMap(reservationMap);

                        if (!reservationCur.getID().equals(reservation.getID())) {
                            newListReservation.add(reservationCur);
                        }

                    }
                    saveReservationList(newListReservation, collection, document, context);
                }
                if (mDialog.isShowing()) {
                    mDialog.dismiss();
                }

            }
        });
    }

    private void saveReservationList(List<Reservation> newListReservation, String collection, String document, Context context) {
        saveNewReservationList(newListReservation, collection, document).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "Reservation(s) deleted!", Toast.LENGTH_LONG).show();
                }
                if (mDialog.isShowing()) {
                    mDialog.dismiss();
                }
            }
        });
    }

    private Task<DocumentSnapshot> getListOfReservations(String collection, String document) {
        return FirebaseFirestore.getInstance().collection(collection).document(document).get();
    }


    public Reservation createReservation(Offer offer, double totalPrice) {
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


    public void saveReservationToFirebase(Reservation reservation, Context context, String managerID, ProgressDialog dialog) {
        mDialog = dialog;
        reservation.setClientID(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reservation.setID(UUID.randomUUID().toString());
        FirebaseFirestore.getInstance()
                .collection("reservations")
                .document(reservation.getClientID())
                .update("reservations", FieldValue.arrayUnion(reservation))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            addReservationToManager(reservation, managerID, context);

                        } else {
                            Toast.makeText(context, "Reservation Error!" + task.getException().toString(), Toast.LENGTH_LONG).show();
                        }
                        mDialog.dismiss();
                    }
                });
    }

    private void addReservationToManager(Reservation reservation, String managerID, Context context) {
        FirebaseFirestore.getInstance()
                .collection("reservationsManager")
                .document(managerID)
                .update("reservations", FieldValue.arrayUnion(reservation))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(context, "Reservation Made!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(context, "Reservation Error!" + task.getException().toString(), Toast.LENGTH_LONG).show();
                        }
                        if (mDialog.isShowing()) {
                            mDialog.dismiss();
                        }
                    }
                });
    }

    public void deleteReservationFromManager(Reservation reservation, Context context, String collection, String document) {
        displayLoadingDialog(context, R.string.frag_displayOffer_dialog_delete_reservation_message);
        deleteReservationManager(reservation, context, collection, document);
        deleteReservationUser(reservation, context, "reservations", reservation.getClientID());
    }

    public void deleteReservationForClient(Offer offer, Context context, String collection, String document, FragmentActivity activity) {
        MessageActions messageActions=new MessageActions();
        displayLoadingDialog(context, R.string.frag_displayOffer_dialog_delete_reservation_message);
        deleteReservationsForOffer(offer, context, collection, document);
        deleteReservationsForOffer(offer, context, "reservationsManager", offer.getManagerID());
        messageActions.sendEmail(context,offer.getManagerID(),activity,"Reservation Canceled");
    }
}
