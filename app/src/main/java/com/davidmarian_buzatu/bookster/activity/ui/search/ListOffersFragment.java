package com.davidmarian_buzatu.bookster.activity.ui.search;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.davidmarian_buzatu.bookster.R;
import com.davidmarian_buzatu.bookster.adapter.ListOffersAdapter;
import com.davidmarian_buzatu.bookster.model.City;
import com.davidmarian_buzatu.bookster.model.Offer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListOffersFragment extends Fragment {

    private RecyclerView.Adapter mAdapter;
    private View mRoot;
    private String mCity;
    private Long mStartDate, mEndDate;
    private ProgressDialog mDialog;
    private List<Offer> mOffers;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_list_offers, container, false);
        return mRoot;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mCity = bundle.getString("City");
            mStartDate = bundle.getLong("StartDate");
            mEndDate = bundle.getLong("EndDate");
        }
        displayLoadingDialog();
        getOffers();
    }

    private void displayLoadingDialog() {
        mDialog = new ProgressDialog(getContext());
        mDialog.setMessage(getString(R.string.frag_listOffers_dialog_message));
        mDialog.setIndeterminate(false);
        mDialog.setCancelable(false);
        mDialog.show();
    }

    private void getOffers() {
        mOffers = new ArrayList<>();
        FirebaseFirestore.getInstance()
                .collection("offers")
                .whereEqualTo("City", mCity)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                Map<String, Object> mapOffer = doc.getData();
                                Offer offer = getOfferFromMap(mapOffer);
                                if (isValid(offer, mStartDate, mEndDate)) {
                                    mOffers.add(offer);
                                }
                                Log.d("SEARCHED", doc.getId() + " => " + doc.getData());
                            }
                            setUpRecyclerView(mRoot);
                            mDialog.dismiss();
                        } else {
                            mDialog.dismiss();
                        }
                    }
                });
    }

    private boolean isValid(Offer offer, Long mStartDate, Long mEndDate) {
        return (offer.getDateStart() <= mStartDate && offer.getDateEnd() >= mEndDate);
    }

    private Offer getOfferFromMap(Map<String, Object> mapOffer) {
        Offer offer = new Offer();
        for (Map.Entry<String, Object> entry : mapOffer.entrySet()) {
            switch (entry.getKey()) {
                case "Country":
                    offer.setCity(new City(mCity, (String) entry.getValue()));
                    break;
                case "DateEnd":
                    offer.setDateEnd((Long) entry.getValue());
                    break;
                case "DateStart":
                    offer.setDateStart((Long) entry.getValue());
                    break;
                case "Description":
                    offer.setDescription((String) entry.getValue());
                    break;
                case "Facilities":
                    offer.setFacilities((ArrayList<String>) entry.getValue());
                    break;
                case "HotelName":
                    offer.setName((String) entry.getValue());
                    break;
                case "ManagerID":
                    offer.setManagerID((String) entry.getValue());
                    break;
                case "NrPerson":
                    offer.setNrPersons((String) entry.getValue());
                    break;
                case "Pictures":
                    offer.setPictures((ArrayList<String>) entry.getValue());
                    break;
                case "PopularFacilities":
                    offer.setPopularFacilities((ArrayList<String>) entry.getValue());
                    break;
                case "PresentationURL":
                    offer.setPresentationURL((String) entry.getValue());
                    break;
                case "Price":
                    offer.setPrice((String) entry.getValue());
                    break;
                case "Rating":
                    offer.setRating((String) entry.getValue());
                    break;
                case "RoomDescription":
                    offer.setRoomDescription((String) entry.getValue());
                    break;
                case "RoomType":
                    offer.setRoomType((String) entry.getValue());
                    break;
                case "RoomsAvailable":
                    offer.setRoomsAvailable((String) entry.getValue());
                    break;
                case "Size":
                    offer.setSize((String) entry.getValue());
                    break;
                default:
                    break;
            }
        }

        return offer;
    }

    private void setUpRecyclerView(View root) {
        RecyclerView recyclerView = root.findViewById(R.id.frag_listOffers_RV_offers);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new ListOffersAdapter(mOffers, getContext(), getActivity());
        recyclerView.setAdapter(mAdapter);
    }
}
