package com.davidmarian_buzatu.bookster.activity.ui.offers;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.davidmarian_buzatu.bookster.R;
import com.davidmarian_buzatu.bookster.services.DialogShow;
import com.davidmarian_buzatu.bookster.adapter.ListOffersAdapter;
import com.davidmarian_buzatu.bookster.constant.DisplayOfferTypes;
import com.davidmarian_buzatu.bookster.model.Manager;
import com.davidmarian_buzatu.bookster.model.Offer;
import com.davidmarian_buzatu.bookster.services.OfferActions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class OffersFragment extends Fragment {
    private RecyclerView.Adapter mAdapter;
    private ProgressDialog mDialog;
    private List<Offer> mOffers;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_offers, container, false);
        setInformationInViews(root);
        setAddButtonListener(root);
        return root;
    }

    private void setAddButtonListener(View root) {
        Button addButton = root.findViewById(R.id.frag_listOffersAdmin_BTN_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, new AddOfferFragment())
                        .addToBackStack(AddOfferFragment.class.getName())
                        .commit();
            }
        });
    }

    private void setInformationInViews(View root) {
        displayLoadingDialog();
        Manager.getInstance().getUserInfo().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                getOffers(root);
            }
        });
    }

    private void displayLoadingDialog() {
        mDialog = DialogShow.getInstance().getDisplayDialog(getContext(), R.string.frag_listOffers_dialog_message);
        mDialog.show();
    }

    private void getOffers(View root) {
        mOffers = new ArrayList<>();
        OfferActions.getInstance().getOffersForField("managerID", Manager.getInstance().getUserID())
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            mOffers = OfferActions.getInstance().getOffersFromQuery(task);
                            setUpRecyclerView(root);
                        }
                        mDialog.dismiss();
                    }
                });
    }

    private void setUpRecyclerView(View root) {
        RecyclerView recyclerView = root.findViewById(R.id.frag_listOffersAdmin_RV_offers);

        if (mOffers.size() > 0) {
            root.findViewById(R.id.frag_listOffersAdmin_TV_empty).setVisibility(View.GONE);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);

            mAdapter = new ListOffersAdapter(mOffers, getContext(), getActivity(), DisplayOfferTypes.OFFER_MANAGER.name());
            recyclerView.setAdapter(mAdapter);
        } else {
            root.findViewById(R.id.frag_listOffersAdmin_CV_offers).setVisibility(View.GONE);
        }
    }

}
