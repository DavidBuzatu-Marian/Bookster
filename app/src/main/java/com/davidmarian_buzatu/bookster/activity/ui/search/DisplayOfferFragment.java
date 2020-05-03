package com.davidmarian_buzatu.bookster.activity.ui.search;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.davidmarian_buzatu.bookster.R;
import com.davidmarian_buzatu.bookster.activity.ui.search.helper.DateFormater;
import com.davidmarian_buzatu.bookster.activity.ui.search.helper.DialogShow;
import com.davidmarian_buzatu.bookster.adapter.ViewPagerImagesAdapter;
import com.davidmarian_buzatu.bookster.constant.Facilities;
import com.davidmarian_buzatu.bookster.model.Offer;
import com.davidmarian_buzatu.bookster.model.Reservation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.gson.GsonBuilder;


import org.threeten.bp.temporal.ChronoUnit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DisplayOfferFragment extends Fragment {

    private ViewPager2 mViewPager2;
    private Offer mOffer;
    private ProgressDialog mDialog;

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
            mOffer = new GsonBuilder().create().fromJson(offerStringified, Offer.class);
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
    }

    private void setUpButtonListener(View root) {
        Button buttonSubmit = root.findViewById(R.id.frag_displayOffer_BTN_reserve);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reserveOffer();
            }
        });
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
        DateFormater df = DateFormater.getInstance();
        double numberOfDays = ChronoUnit.DAYS.between(df.getDate(mOffer.getDateStart()), df.getDate(mOffer.getDateEnd()));
        return numberOfDays * Double.parseDouble(price);
    }

    private void setAvailability(View root) {
        DateFormater df = DateFormater.getInstance();
        TextView startDate = root.findViewById(R.id.frag_displayOffer_TV_check_in_date);
        TextView endDate = root.findViewById(R.id.frag_displayOffer_TV_check_out_date);

        startDate.setText(df.getFormattedDate(mOffer.getDateStart(), "EEEE dd MMMM YYYY"));
        endDate.setText(df.getFormattedDate(mOffer.getDateEnd(), "EEEE dd MMMM YYYY"));
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

    private void reserveOffer() {
        int nrRoomsAvailable = Integer.parseInt(mOffer.getRoomsAvailable());
        if (nrRoomsAvailable > 0) {
            mOffer.setRoomsAvailable(--nrRoomsAvailable + "");
        }

        displayLoadingDialog();
        updateOfferInFirebase().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Reservation reservation = new Reservation(mOffer.getDateStart(), mOffer.getDateEnd(), mOffer.getPrice(), mOffer.getCityName(), mOffer.getOfferID(), mOffer.getPresentationURL());
                    saveReservationToFirebase(reservation);
                } else {
                    mDialog.dismiss();
                    Toast.makeText(getContext(), "Error while making reservation", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void displayLoadingDialog() {
        mDialog = DialogShow.getInstance().getDisplayDialog(getContext(), R.string.frag_displayOffer_dialog_message);
        mDialog.show();
    }

    private void saveReservationToFirebase(Reservation reservation) {
        Map<String, Object> reservationMap = new HashMap<>();
        Map<String, Object> currentReservation = new HashMap<>();
        currentReservation.put(reservation.getOfferID(), reservation);
        reservationMap.put(reservation.getStartDate().toString(), currentReservation);
        FirebaseFirestore.getInstance()
                .collection("reservations")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .set(reservationMap, SetOptions.merge())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        mDialog.dismiss();
                        if(task.isSuccessful()) {
                            Toast.makeText(getContext(), "Reservation Made!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getContext(), "Reservation Error!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private Task<Void> updateOfferInFirebase() {
        return FirebaseFirestore.getInstance()
                .collection("offers")
                .document(mOffer.getOfferID())
                .set(mOffer);
    }

}
