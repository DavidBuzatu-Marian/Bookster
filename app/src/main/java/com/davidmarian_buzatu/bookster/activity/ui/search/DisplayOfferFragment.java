package com.davidmarian_buzatu.bookster.activity.ui.search;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.davidmarian_buzatu.bookster.R;
import com.davidmarian_buzatu.bookster.adapter.ViewPagerImagesAdapter;
import com.davidmarian_buzatu.bookster.constant.Facilities;
import com.davidmarian_buzatu.bookster.model.Offer;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class DisplayOfferFragment extends Fragment {

    private ViewPager2 mViewPager2;
    private Offer mOffer;

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
        createAndSetFacilities(root);
    }

    private void createAndSetFacilities(View root) {
        LinearLayout parentLL = root.findViewById(R.id.frag_displayOffer_LL_facilities);
        List<String> offerPopularFacilities = mOffer.getPopularFacilities();
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
            TextView tv1 = createTextView(offerPopularFacilities.get(i));
            TextView tv2 = null;
            if (i + 1 < offerPopularFacilities.size()) {
                tv2 = createTextView(offerPopularFacilities.get(i + 1));
            }
            // Add to layouts
            parentTVs.addView(tv1);
            if (tv2 != null) {
                parentTVs.addView(tv2);
            }
            parentLL.addView(parentTVs);
        }
    }

    private TextView createTextView(String text) {
        TextView tv = new TextView(getContext());
        Integer drawableID = 0;
        tv.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
        // get correct drawable
        drawableID = getDrawable(text);
        tv.setCompoundDrawablesWithIntrinsicBounds(drawableID, 0, 0, 0);
        tv.setCompoundDrawablePadding(12);
        tv.setTypeface(ResourcesCompat.getFont(getContext(), R.font.roboto_light));
        tv.setText(Facilities.valueOf(text).toString());
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
        textViewCity.setText(new StringBuilder()
                .append(mOffer.getCity().getCityName())
                .append(", ")
                .append(mOffer.getCity().getCountry()).toString());
        // TODO: LOOK INTO DISTANCE CALCULATOR WITH GOOGLE MAPS
    }

    private void setPresentationImage(View root) {
        ImageView imageViewPresentation = root.findViewById(R.id.frag_displayOffer_IV_presentation);
        Glide.with(getContext()).load(mOffer.getPresentaion()).into(imageViewPresentation);
    }

}
