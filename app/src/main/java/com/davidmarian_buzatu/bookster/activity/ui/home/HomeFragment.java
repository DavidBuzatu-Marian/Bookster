package com.davidmarian_buzatu.bookster.activity.ui.home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.davidmarian_buzatu.bookster.R;
import com.davidmarian_buzatu.bookster.activity.ui.search.helper.DialogShow;
import com.davidmarian_buzatu.bookster.adapter.ListReservationsAdapter;
import com.davidmarian_buzatu.bookster.model.Client;
import com.davidmarian_buzatu.bookster.model.Manager;
import com.davidmarian_buzatu.bookster.model.Reservation;
import com.davidmarian_buzatu.bookster.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {
    private User mCurrentUser;
    private ProgressDialog mDialog;
    private ListReservationsAdapter mAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        getUserInstance(getArguments().getString("Type"));
        displayUserReservations(root);

        return root;
    }

    private void displayUserReservations(View root) {
        List<Reservation> reservationsList = new ArrayList<>();
        showLoadingDialog();
        getUserReservations(mCurrentUser).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    final List<Map<String, Object>> reservationsMapList = (List<Map<String, Object>>) task.getResult().get("reservations");
                    for (Map<String, Object> mapReservation: reservationsMapList) {
                        // Its a map in the database
                        Map.Entry<String, Object> entry = mapReservation.entrySet().iterator().next();
                        Reservation reservation = new Reservation();
                        reservation.setReservationFromMap(entry);
                        reservationsList.add(reservation);
                    }
                    setUpRecyclerView(root, reservationsList);

                } else {
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                }
                mDialog.dismiss();
            }
        });
    }

    private void setUpRecyclerView(View root, List<Reservation> reservationsList) {
        RecyclerView recyclerView = root.findViewById(R.id.frag_home_RV_reservations);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new ListReservationsAdapter(reservationsList, getContext(), getActivity());
        recyclerView.setAdapter(mAdapter);
    }

    private void showLoadingDialog() {
        mDialog = DialogShow.getInstance().getDisplayDialog(getContext(), R.string.frag_home_dialog_reservations_message);
        mDialog.show();
    }

    private Task<DocumentSnapshot> getUserReservations(User mCurrentUser) {
        return FirebaseFirestore.getInstance().collection("reservations").document(mCurrentUser.getUserID()).get();
    }


    private void getUserInstance(String type) {
        switch (type) {
            case "Client":
                mCurrentUser = Client.getInstance();
                break;
            case "Manager":
                mCurrentUser = Manager.getInstance();
                break;
            default:
                mCurrentUser = null;
        }
    }
}
