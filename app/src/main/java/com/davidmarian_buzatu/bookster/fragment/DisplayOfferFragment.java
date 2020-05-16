package com.davidmarian_buzatu.bookster.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.davidmarian_buzatu.bookster.R;
import com.davidmarian_buzatu.bookster.activity.ui.search.services.DateFormatter;
import com.davidmarian_buzatu.bookster.adapter.ViewPagerImagesAdapter;
import com.davidmarian_buzatu.bookster.constant.DisplayOfferTypes;
import com.davidmarian_buzatu.bookster.constant.Facilities;
import com.davidmarian_buzatu.bookster.model.Message;
import com.davidmarian_buzatu.bookster.model.Offer;

import com.davidmarian_buzatu.bookster.model.Reservation;
import com.davidmarian_buzatu.bookster.services.MessageActions;
import com.davidmarian_buzatu.bookster.services.OfferActions;

import com.davidmarian_buzatu.bookster.services.ReservationActions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.GsonBuilder;


import org.threeten.bp.temporal.ChronoUnit;

import java.util.List;

import static com.davidmarian_buzatu.bookster.services.MessageActions.LAUNCH_MAIL_ACTIVITY;


public class DisplayOfferFragment extends Fragment {
    private ViewPager2 mViewPager2;
    private Offer mOffer;
    private Reservation mReservation;
    private String mDisplayOfferType;
    private Long mStartDate, mEndDate;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_display_offer, container, false);


        setUpViewPager(root);
        setUpInfoInXML(root);
        return root;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String offerStringified = bundle.getString("Offer");
            String reservationStringified = bundle.getString("Reservation");
            mOffer = new GsonBuilder().create().fromJson(offerStringified, Offer.class);
            mDisplayOfferType = bundle.getString("displayOfferType");
            mReservation = new GsonBuilder().create().fromJson(reservationStringified, Reservation.class);
            mStartDate = bundle.containsKey("startDate") ? bundle.getLong("startDate") : mReservation != null ? mReservation.getStartDate() : mOffer.getDateStart();
            mEndDate = bundle.containsKey("endDate") ? bundle.getLong("endDate") : mReservation != null ? mReservation.getEndDate() : mOffer.getDateEnd();
        }
    }

    private void setUpViewPager(View root) {
        mViewPager2 = root.findViewById(R.id.frag_displayOffer_VP_images);
        mViewPager2.setAdapter(new ViewPagerImagesAdapter(getContext(), mOffer.getPictures()));
    }


    private void setUpInfoInXML(View root) {
        setPresentationImage(root);
        setHeaderInfo(root);
        setRating(root);
        setHotelInfo(root);
        createAndSetFacilities(root, R.id.frag_displayOffer_LL_facilities, mOffer.getPopularFacilities(), true);
        setAvailability(root);
        setRoomInfo(root);
        createAndSetFacilities(root, R.id.frag_displayOffer_LL_room_facilities, mOffer.getFacilities(), false);
        setUpButtonListener(root);
        setContactManager(root);
    }


    private void showCurrentButton(View root, DisplayOfferTypes displayOfferTypes) {
        Button buttonCancelReservation = root.findViewById(R.id.frag_displayOffer_BTN_cancel_reservation);
        Button buttonReserve = root.findViewById(R.id.frag_displayOffer_BTN_reserve);
        Button buttonCancelOffer = root.findViewById(R.id.frag_displayOffer_BTN_cancel_offer);
        Button buttonCancelReservationManager = root.findViewById(R.id.frag_displayOffer_BTN_cancel_reservation_manager);
        switch (displayOfferTypes) {
            case OFFER_RESERVATION:
                buttonReserve.setVisibility(View.GONE);
                buttonCancelOffer.setVisibility(View.GONE);
                buttonCancelReservationManager.setVisibility(View.GONE);
                buttonCancelReservation.setVisibility(View.VISIBLE);
                break;
            case OFFER_CLIENT:
                buttonCancelOffer.setVisibility(View.GONE);
                buttonCancelReservation.setVisibility(View.GONE);
                buttonCancelReservationManager.setVisibility(View.GONE);
                buttonReserve.setVisibility(View.VISIBLE);
                break;
            case OFFER_MANAGER:
                buttonCancelReservation.setVisibility(View.GONE);
                buttonReserve.setVisibility(View.GONE);
                buttonCancelReservationManager.setVisibility(View.GONE);
                buttonCancelOffer.setVisibility(View.VISIBLE);
                break;
            case OFFER_MANAGER_RESERVATION:
                buttonCancelReservation.setVisibility(View.GONE);
                buttonReserve.setVisibility(View.GONE);
                buttonCancelOffer.setVisibility(View.GONE);
                buttonCancelReservationManager.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void setUpButtonListener(View root) {
        DisplayOfferTypes displayOfferTypes = DisplayOfferTypes.valueOf(mDisplayOfferType);
        Button curButton;
        final Fragment reference = this;
        showCurrentButton(root, displayOfferTypes);
        switch (displayOfferTypes) {
            case OFFER_RESERVATION:
                curButton = root.findViewById(R.id.frag_displayOffer_BTN_cancel_reservation);
                curButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ReservationActions.getInstance().deleteReservationForClient(mReservation, getContext(), "reservations", FirebaseAuth.getInstance().getUid(),mOffer.getManagerID(),getActivity());
                    }
                });
                break;
            case OFFER_CLIENT:
                curButton = root.findViewById(R.id.frag_displayOffer_BTN_reserve);
                curButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        OfferActions.getInstance().reserveOffer(mOffer, getContext(), getTotalPrice(mOffer.getPrice()), mStartDate, mEndDate);
                    }
                });
                break;
            case OFFER_MANAGER:
                curButton = root.findViewById(R.id.frag_displayOffer_BTN_cancel_offer);
                curButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        OfferActions.getInstance().deleteOffer(mOffer, getContext());
                        getActivity().getSupportFragmentManager().beginTransaction().remove(reference).commit();
                        getActivity().onBackPressed();
                    }
                });
                break;
            case OFFER_MANAGER_RESERVATION:
                curButton = root.findViewById(R.id.frag_displayOffer_BTN_cancel_reservation_manager);
                curButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ReservationActions.getInstance().deleteReservationFromManager(mReservation, getContext(), "reservationsManager", FirebaseAuth.getInstance().getUid(),getActivity());
                        getActivity().getSupportFragmentManager().beginTransaction().remove(reference).commit();
                        getActivity().onBackPressed();
                    }
                });
                break;
            default:
                break;
        }
    }


    private void setRoomInfo(View root) {
        TextView textViewRoomType = root.findViewById(R.id.frag_displayOffer_TV_room_type);
        TextView textViewRoomDescr = root.findViewById(R.id.frag_displayOffer_TV_room_description);
        TextView textViewRoomSize = root.findViewById(R.id.frag_displayOffer_TV_room_size);
        TextView textViewRoomPriceTotal = root.findViewById(R.id.frag_displayOffer_TV_price_total);
        TextView textViewRoomPrice = root.findViewById(R.id.frag_displayOffer_TV_price_per_night);

        textViewRoomType.setText(mOffer.getRoomType());
        textViewRoomDescr.setText(mOffer.getRoomDescription());
        textViewRoomSize.setText(Html.fromHtml("Size (metre sq.): " + mOffer.getSize()));
        textViewRoomPriceTotal.setText(new StringBuilder()
                .append(getTotalPrice(mOffer.getPrice()))
                .append(" €").toString());
        textViewRoomPrice.setText(new StringBuilder()
                .append(mOffer.getPrice())
                .append(" €")
                .toString());
    }

    private double getTotalPrice(String price) {
        DateFormatter df = DateFormatter.getInstance();
        double numberOfDays = ChronoUnit.DAYS.between(df.getDate(mStartDate), df.getDate(mEndDate));
        return numberOfDays * Double.parseDouble(price);
    }

    private void setAvailability(View root) {
        DateFormatter df = DateFormatter.getInstance();
        TextView startDate = root.findViewById(R.id.frag_displayOffer_TV_check_in_date);
        TextView endDate = root.findViewById(R.id.frag_displayOffer_TV_check_out_date);

        startDate.setText(df.getFormattedDate(mStartDate, "EEEE dd MMMM YYYY"));
        endDate.setText(df.getFormattedDate(mEndDate, "EEEE dd MMMM YYYY"));
    }

    private void createAndSetFacilities(View root, int parentId, List<String> facilities, boolean isPopularFacility) {
        LinearLayout parentLL = root.findViewById(parentId);
        List<String> offerPopularFacilities = facilities;
        for (int i = 0; i < offerPopularFacilities.size(); i += 2) {
            // We put 2 textviews per row
            LinearLayout parentTVs = new LinearLayout(getContext());
            parentTVs.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            parentTVs.setGravity(Gravity.CENTER);
            // Set margins parent
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) parentTVs.getLayoutParams();
            params.setMargins(0, 24, 0, 0);
            parentTVs.setLayoutParams(params);
            // Create textviews
            TextView tv1 = createTextView(offerPopularFacilities.get(i), isPopularFacility);
            TextView tv2 = null;
            if (i + 1 < offerPopularFacilities.size()) {
                tv2 = createTextView(offerPopularFacilities.get(i + 1), isPopularFacility);
            }
            // Add to layouts
            parentTVs.addView(tv1);
            if (tv2 != null) {
                parentTVs.addView(tv2);
            }
            parentLL.addView(parentTVs);
        }
    }

    private TextView createTextView(String text, boolean isPopularFacility) {
        TextView tv = new TextView(getContext());
        Integer drawableID = 0;
        tv.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
        // get correct drawable
        if (isPopularFacility) {
            drawableID = getDrawable(text);
            tv.setText(Facilities.valueOf(text).toString());
        } else {
            drawableID = R.drawable.ic_check_solid;
            tv.setText(text);
        }
        tv.setCompoundDrawablesWithIntrinsicBounds(drawableID, 0, 0, 0);
        tv.setCompoundDrawablePadding(12);
        tv.setTypeface(ResourcesCompat.getFont(getContext(), R.font.roboto_light));
        tv.setTextColor(getResources().getColor(R.color.colorAccent));
        tv.setTextSize(16);

        return tv;
    }

    private Integer getDrawable(String text) {
        Facilities facility = Facilities.valueOf(text);
        switch (facility) {
            case BREAKFAST:
                return R.drawable.ic_bacon_solid;
            case GYM:
                return R.drawable.ic_dumbbell_solid;
            case BAR:
                return R.drawable.ic_glass_cheers_solid;
            case SPA:
                return R.drawable.ic_spa_solid;
            case POOL:
                return R.drawable.ic_swimming_pool_solid;
            case INTERNET:
                return R.drawable.ic_wifi_solid;
            case NON_SMOKING:
                return R.drawable.ic_smoking_ban_solid;
            default:
                return R.drawable.ic_check_solid;
        }
    }

    private void setHotelInfo(View root) {
        TextView textViewHName = root.findViewById(R.id.frag_displayOffer_TV_name);
        TextView textViewHDescr = root.findViewById(R.id.frag_displayOffer_TV_description);
        textViewHName.setText(mOffer.getName());
        textViewHDescr.setText(mOffer.getDescription());
    }

    private void setRating(View root) {
        TextView textViewRating = root.findViewById(R.id.frag_displayOffer_TV_rating);
        textViewRating.setText(mOffer.getRating());
    }

    private void setHeaderInfo(View root) {
        TextView textViewCity = root.findViewById(R.id.frag_displayOffer_TV_header_city);
        ImageView imageViewMap = root.findViewById(R.id.frag_displayOffer_IV_header_map);
        textViewCity.setText(new StringBuilder()
                .append(mOffer.getCityName())
                .append(", ")
                .append(mOffer.getCountry()).toString());

        imageViewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMapsActivity();
            }
        });

    }

    private void startMapsActivity() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("www.google.com")
                .appendPath("maps")
                .appendPath("dir")
                .appendPath("")
                .appendQueryParameter("api", "1")
                .appendQueryParameter("destination", mOffer.getLatitude() + "," + mOffer.getLongitude());
        Intent maps = new Intent(Intent.ACTION_VIEW);
        maps.setData(Uri.parse(builder.build().toString()));
        startActivity(maps);
    }

    private void setPresentationImage(View root) {
        ImageView imageViewPresentation = root.findViewById(R.id.frag_displayOffer_IV_presentation);
        Glide.with(getContext()).load(mOffer.getPresentationURL()).into(imageViewPresentation);
    }

    private void setContactManager(View root) {
        MessageActions messageActions = new MessageActions();
        TextView textViewMail = root.findViewById(R.id.frag_displayOffer_TV_message);
        textViewMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageActions.sendEmail(root.getContext(), mOffer.getManagerID(), getActivity(),"");
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final Fragment reference = this;
        if (requestCode == LAUNCH_MAIL_ACTIVITY) {
            Message message = new Message(mOffer.getOfferID());
            MessageActions.addMessage(message, mOffer.getManagerID()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    getActivity().getSupportFragmentManager().beginTransaction().remove(reference).commit();
                    getActivity().onBackPressed();
                }
            });
        }
    }

}
