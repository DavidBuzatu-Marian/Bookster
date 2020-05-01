package com.davidmarian_buzatu.bookster.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.davidmarian_buzatu.bookster.R;
import com.davidmarian_buzatu.bookster.model.Offer;

import java.util.ArrayList;
import java.util.List;

public class ListOffersAdapter extends RecyclerView.Adapter<ListOffersAdapter.ListOfferViewHolder> {

    private List<Offer> mDataSet;
    private Context mContext;

    public ListOffersAdapter(List<Offer> dataset, Context context) {
        mDataSet = dataset;
        mContext = context;
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
        holder.setInfoInViews(offer, mContext);
    }



    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class ListOfferViewHolder extends RecyclerView.ViewHolder {

        private TextView mName, mDescription, mRating;
        private ImageView mPresentationImage;

        public ListOfferViewHolder(@NonNull View itemView, @NonNull final ViewGroup parent) {
            super(itemView);

            // TODO: USE itemView TO GET VIEWS
            mName = itemView.findViewById(R.id.adapter_listOffer_TV_name);
            mDescription = itemView.findViewById(R.id.adapter_listOffer_TV_description);
            mRating = itemView.findViewById(R.id.adapter_listOffer_TV_rating);
            mPresentationImage = itemView.findViewById(R.id.adapter_listOffer_IV_presentation);
        }

        private void setInfoInViews(Offer offer, Context context) {
            mName.setText(offer.getName());
            mDescription.setText(offer.getDescription());
            mRating.setText(offer.getRating());
            Glide.with(context).load(offer.getPresentaion()).into(mPresentationImage);
        }
    }
}
