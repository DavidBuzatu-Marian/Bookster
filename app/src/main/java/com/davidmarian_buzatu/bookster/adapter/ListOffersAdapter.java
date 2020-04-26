package com.davidmarian_buzatu.bookster.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.davidmarian_buzatu.bookster.R;
import com.davidmarian_buzatu.bookster.model.Offer;

import java.util.ArrayList;

public class ListOffersAdapter extends RecyclerView.Adapter<ListOffersAdapter.ListOfferViewHolder> {

    private ArrayList<Offer> mDataSet;

    public ListOffersAdapter(ArrayList<Offer> dataset) {
        mDataSet = dataset;
    }


    @NonNull
    @Override
    public ListOfferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ConstraintLayout _viewGroup = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_offer_item, parent, false);

        ListOfferViewHolder _vh = new ListOfferViewHolder(_viewGroup, parent);
        return _vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ListOfferViewHolder holder, int position) {
        Offer offer = mDataSet.get(position);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class ListOfferViewHolder extends RecyclerView.ViewHolder {

        public ListOfferViewHolder(@NonNull View itemView, @NonNull final ViewGroup parent) {
            super(itemView);

            // TODO: USE itemView TO GET VIEWS
        }
    }
}
