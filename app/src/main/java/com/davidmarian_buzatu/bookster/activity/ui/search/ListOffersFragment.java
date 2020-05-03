package com.davidmarian_buzatu.bookster.activity.ui.search;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.davidmarian_buzatu.bookster.R;
import com.davidmarian_buzatu.bookster.activity.ui.search.helper.DateFormater;
import com.davidmarian_buzatu.bookster.activity.ui.search.helper.DialogShow;
import com.davidmarian_buzatu.bookster.adapter.ListOffersAdapter;
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
    private String mLocation;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_list_offers, container, false);
        setInformationInViews();
        return mRoot;
    }

    private void setInformationInViews() {
        TextView textViewStartDate = mRoot.findViewById(R.id.frag_listOffers_TV_start_date);
        TextView textViewEndDate = mRoot.findViewById(R.id.frag_listOffers_TV_end_date);
        TextView textViewLocation = mRoot.findViewById(R.id.frag_listOffers_TV_location);
        DateFormater df = DateFormater.getInstance();

        textViewStartDate.setText(df.getFormattedDate(mStartDate, "EEE dd MMMM YYYY"));
        textViewEndDate.setText(df.getFormattedDate(mEndDate, "EEE dd MMMM YYYY"));
        textViewLocation.setText(mLocation);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mLocation = bundle.getString("Location");
            mCity = bundle.getString("City");
            mStartDate = bundle.getLong("StartDate");
            mEndDate = bundle.getLong("EndDate");
        }
        displayLoadingDialog();
        getOffers();
    }

    private void displayLoadingDialog() {
        mDialog = DialogShow.getInstance().getDisplayDialog(getContext(), R.string.frag_listOffers_dialog_message);
        mDialog.show();
    }


    private void getOffers() {
        mOffers = new ArrayList<>();
        FirebaseFirestore.getInstance()
                .collection("offers")
                .whereEqualTo("cityName", mCity)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                Map<String, Object> mapOffer = doc.getData();
                                Offer offer = getOfferFromMap(mapOffer);
                                offer.setOfferID(doc.getId());
                                if (isValid(offer, mStartDate, mEndDate)) {
                                    mOffers.add(offer);
                                }
                            }
                            setUpRecyclerView(mRoot);
                        }
                        mDialog.dismiss();
                    }
                });
    }

    private boolean isValid(Offer offer, Long mStartDate, Long mEndDate) {
        return (offer.getDateStart() <= mStartDate && offer.getDateEnd() >= mEndDate && Integer.parseInt(offer.getRoomsAvailable()) > 0);
    }

    private Offer getOfferFromMap(Map<String, Object> mapOffer) {
        Offer offer = new Offer();
        for (Map.Entry<String, Object> entry : mapOffer.entrySet()) {
            switch (entry.getKey()) {
                case "country":
                    offer.setCountry((String) entry.getValue());
                    break;
                case "cityName":
                    offer.setCityName((String) entry.getValue());
                    break;
                case "dateEnd":
                    offer.setDateEnd((Long) entry.getValue());
                    break;
                case "dateStart":
                    offer.setDateStart((Long) entry.getValue());
                    break;
                case "description":
                    offer.setDescription((String) entry.getValue());
                    break;
                case "facilities":
                    offer.setFacilities((ArrayList<String>) entry.getValue());
                    break;
                case "name":
                    offer.setName((String) entry.getValue());
                    break;
                case "managerID":
                    offer.setManagerID((String) entry.getValue());
                    break;
                case "nrPerson":
                    offer.setNrPersons((String) entry.getValue());
                    break;
                case "pictures":
                    offer.setPictures((ArrayList<String>) entry.getValue());
                    break;
                case "popularFacilities":
                    offer.setPopularFacilities((ArrayList<String>) entry.getValue());
                    break;
                case "presentationURL":
                    offer.setPresentationURL((String) entry.getValue());
                    break;
                case "price":
                    offer.setPrice((String) entry.getValue());
                    break;
                case "rating":
                    offer.setRating((String) entry.getValue());
                    break;
                case "roomDescription":
                    offer.setRoomDescription((String) entry.getValue());
                    break;
                case "roomType":
                    offer.setRoomType((String) entry.getValue());
                    break;
                case "roomsAvailable":
                    offer.setRoomsAvailable((String) entry.getValue());
                    break;
                case "size":
                    offer.setSize((String) entry.getValue());
                    break;
                case "latitude":
                    offer.setLatitude((String) entry.getValue());
                    break;
                case "longitude":
                    offer.setLongitude((String) entry.getValue());
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
