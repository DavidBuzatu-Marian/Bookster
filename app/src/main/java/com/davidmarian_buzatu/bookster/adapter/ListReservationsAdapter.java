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
import com.davidmarian_buzatu.bookster.activity.ui.search.helper.DateFormater;
import com.davidmarian_buzatu.bookster.model.Reservation;
import com.google.gson.GsonBuilder;

import java.util.List;

public class ListReservationsAdapter extends RecyclerView.Adapter<ListReservationsAdapter.ListReservationsViewHolder> {

    private final FragmentActivity mActivity;
    private List<Reservation> mDataSet;
    private Context mContext;

    public ListReservationsAdapter(List<Reservation> dataset, Context context, FragmentActivity activity) {
        mDataSet = dataset;
        mContext = context;
        mActivity = activity;
    }


    @NonNull
    @Override
    public ListReservationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ConstraintLayout _viewGroup = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_reservation_item, parent, false);

        ListReservationsViewHolder _vh = new ListReservationsViewHolder(_viewGroup, parent);
        return _vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ListReservationsViewHolder holder, int position) {
        Reservation reservation = mDataSet.get(position);
        holder.setInfoInViews(reservation, mContext);
    }


    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class ListReservationsViewHolder extends RecyclerView.ViewHolder {

        private Button mOpenOfferButton;
        private TextView mName, mStartDate, mEndDate, mPrice;
        private ImageView mPresentationImage;

        public ListReservationsViewHolder(@NonNull View itemView, @NonNull final ViewGroup parent) {
            super(itemView);

            mName = itemView.findViewById(R.id.adapter_listReservation_TV_name);
            mStartDate = itemView.findViewById(R.id.adapter_listReservation_TV_startDate);
            mEndDate = itemView.findViewById(R.id.adapter_listReservation_TV_endDate);
            mPrice = itemView.findViewById(R.id.adapter_listReservation_TV_price);
            mPresentationImage = itemView.findViewById(R.id.adapter_listReservation_IV_presentation);
            mOpenOfferButton = itemView.findViewById(R.id.adapter_listReservation_BTN_view);
        }

        private void setInfoInViews(Reservation reservation, Context context) {
            mName.setText(reservation.getLocation());
            mStartDate.setText(DateFormater.getInstance().getFormattedDate(reservation.getStartDate(), "EEE dd MMMM YYYY"));
            mEndDate.setText(DateFormater.getInstance().getFormattedDate(reservation.getEndDate(), "EEE dd MMMM YYYY"));
            mPrice.setText(new StringBuilder()
                    .append("Price: ")
                    .append(reservation.getPrice())
                    .append("€")
                    .toString());
            Glide.with(context).load(reservation.getPresentationURL()).into(mPresentationImage);
            setButtonListener(reservation.getOfferID());
        }

        private void setButtonListener(String offerID) {
            mOpenOfferButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    startDisplayOfferFragment(reservation);
                }
            });
        }

//    private void startDisplayOfferFragment(Reservation offer) {
//        DisplayOfferFragment nextFragment = new DisplayOfferFragment();
//        Bundle bundle = new Bundle();
//
//        String offerStringified = new GsonBuilder().create().toJson(offer);
//        bundle.putString("Offer", offerStringified);
//        nextFragment.setArguments(bundle);
//        mActivity.getSupportFragmentManager().beginTransaction()
//                .replace(R.id.nav_host_fragment, nextFragment)
//                .addToBackStack(null)
//                .commit();
//    }
    }
}