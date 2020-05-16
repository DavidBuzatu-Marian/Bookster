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
import com.davidmarian_buzatu.bookster.activity.ui.search.services.DateFormatter;
import com.davidmarian_buzatu.bookster.services.DialogShow;
import com.davidmarian_buzatu.bookster.adapter.ListOffersAdapter;
import com.davidmarian_buzatu.bookster.constant.DisplayOfferTypes;
import com.davidmarian_buzatu.bookster.model.Offer;
import com.davidmarian_buzatu.bookster.services.OfferActions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
        DateFormatter df = DateFormatter.getInstance();

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
        OfferActions.getInstance().getOffersForField("cityName", mCity)
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            mOffers = OfferActions.getInstance().getOffersFromQueryWithinDate(task, mStartDate, mEndDate);
                            setUpRecyclerView(mRoot);
                        }
                        mDialog.dismiss();
                    }
                });
    }




    private void setUpRecyclerView(View root) {
        RecyclerView recyclerView = root.findViewById(R.id.frag_listOffers_RV_offers);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new ListOffersAdapter(mOffers, getContext(), getActivity(), DisplayOfferTypes.OFFER_CLIENT.name());
        recyclerView.setAdapter(mAdapter);
    }
}
