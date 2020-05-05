package com.davidmarian_buzatu.bookster.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.davidmarian_buzatu.bookster.R;
import com.davidmarian_buzatu.bookster.fragment.DisplayOfferFragment;
import com.davidmarian_buzatu.bookster.model.Offer;
import com.google.gson.GsonBuilder;

import java.util.List;

public class ListOffersAdapter extends RecyclerView.Adapter<ListOffersAdapter.ListOfferViewHolder> {

    private final FragmentActivity mActivity;
    private List<Offer> mDataSet;
    private Context mContext;

    public ListOffersAdapter(List<Offer> dataset, Context context, FragmentActivity activity) {
        mDataSet = dataset;
        mContext = context;
        mActivity = activity;
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

        private Button mOpenOfferButton;
        private TextView mName, mDescription, mRating;
        private ImageView mPresentationImage;

        public ListOfferViewHolder(@NonNull View itemView, @NonNull final ViewGroup parent) {
            super(itemView);

            // TODO: USE itemView TO GET VIEWS
            mName = itemView.findViewById(R.id.adapter_listOffer_TV_name);
            mDescription = itemView.findViewById(R.id.adapter_listOffer_TV_description);
            mRating = itemView.findViewById(R.id.adapter_listOffer_TV_rating);
            mPresentationImage = itemView.findViewById(R.id.adapter_listOffer_IV_presentation);
            mOpenOfferButton = itemView.findViewById(R.id.adapter_listOffer_BTN_view);
        }

        private void setInfoInViews(Offer offer, Context context) {
            mName.setText(offer.getName());
            mDescription.setText(offer.getDescription());
            mRating.setText(offer.getRating());
            Glide.with(context).load(offer.getPresentationURL()).into(mPresentationImage);
            setButtonListener(offer);
        }

        private void setButtonListener(Offer offer) {
            mOpenOfferButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startDisplayOfferFragment(offer);
                }
            });
        }

        private void startDisplayOfferFragment(Offer offer) {
            DisplayOfferFragment nextFragment = new DisplayOfferFragment();
            Bundle bundle = new Bundle();

            String offerStringified = new GsonBuilder().create().toJson(offer);
            bundle.putString("Offer", offerStringified);
            bundle.putString("displayOfferType", "ViewOfferClient");
            nextFragment.setArguments(bundle);
            mActivity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment, nextFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }
}
