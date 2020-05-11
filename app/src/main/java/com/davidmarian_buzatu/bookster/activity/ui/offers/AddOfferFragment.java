package com.davidmarian_buzatu.bookster.activity.ui.offers;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.davidmarian_buzatu.bookster.R;
import com.davidmarian_buzatu.bookster.services.CalendarActions;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddOfferFragment extends Fragment {
    private static final int SELECT_PICTURES = 1001;
    private CalendarActions mCalendarActions;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_offer, container, false);

        setUpView(root);
        return root;
    }

    private void setUpView(View root) {
        setSpinnerRoomType(root);
        setCheckBoxFacilities(root);
        setUpCalendarPicker(root);
        setUpNumberPicker(root);
        setAddImagesButton(root);
    }

    private void setAddImagesButton(View root) {
        Button buttonAdd = root.findViewById(R.id.frag_addOffer_BTN_add_images);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(Intent.createChooser(galleryIntent, "Select offer pictures"), SELECT_PICTURES);
            }
        });
    }

    private void setUpNumberPicker(View root) {
        NumberPicker np = root.findViewById(R.id.frag_addOffer_NP_available);

        np.setMinValue(1);
        np.setMaxValue(100);
    }

    private void setUpCalendarPicker(View root) {
        mCalendarActions = new CalendarActions();
        mCalendarActions.setUpCalendarPicker(root, getContext(), R.id.frag_addOffer_ET_start_date, R.id.frag_addOffer_ET_end_date);
    }

    private void setCheckBoxFacilities(View root) {
        String[] popularFacilities = getResources().getStringArray(R.array.room_popular_facilities);
        LinearLayout parentLL = root.findViewById(R.id.frag_addOffer_LL_popular_facilities);
        for (int i = 0; i < popularFacilities.length; i += 2) {
            // create linear layout
            LinearLayout parentCBs = new LinearLayout(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 6, 0, 6);
            parentCBs.setLayoutParams(params);
            // create checkbox
            CheckBox checkbox1 = new CheckBox(getContext());
            checkbox1.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
            checkbox1.setText(popularFacilities[i]);

            if (i + 1 < popularFacilities.length) {
                CheckBox checkbox2 = new CheckBox(getContext());
                checkbox2.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
                checkbox2.setText(popularFacilities[i + 1]);
                parentCBs.addView(checkbox2);
            }
            parentCBs.addView(checkbox1);
            parentLL.addView(parentCBs);

        }
    }

    private void setSpinnerRoomType(View root) {
        Spinner spinner = root.findViewById(R.id.frag_addOffer_SP_room_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.room_type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == SELECT_PICTURES) {
            if (resultCode == Activity.RESULT_OK) {
                if (data.getClipData() != null) {
                    int count = data.getClipData().getItemCount();
                    for (int i = 0; i < count; i++) {
                        final Uri imageUri = data.getClipData().getItemAt(i).getUri();
                        final InputStream imageStream;
                        try {
                            imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                            Log.d("TEST", selectedImage.toString());
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                    }
                }
            } else {
                Toast.makeText(getContext(), "Could not select images", Toast.LENGTH_LONG).show();
            }
        }
    }
}
