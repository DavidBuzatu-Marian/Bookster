package com.davidmarian_buzatu.bookster.activity.ui.offers;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.AutoCompleteTextView;
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
import com.davidmarian_buzatu.bookster.activity.ui.search.helper.DialogShow;
import com.davidmarian_buzatu.bookster.activity.ui.search.helper.UploadCities;
import com.davidmarian_buzatu.bookster.services.CalendarActions;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AddOfferFragment extends Fragment {
    private static final int SELECT_PICTURES = 1001;
    private List<Bitmap> mPicturesList = new ArrayList<>();
    private List<String> mPicturesLink, mFacilities, mPopularFacilities;
    private CalendarActions mCalendarActions;
    private List<TextInputEditText> mTIETList;
    private final int[] mTIETResIds = {R.id.frag_addOffer_TIET_name, R.id.frag_addOffer_TIET_description, R.id.frag_addOffer_TIET_latitude,
            R.id.frag_addOffer_TIET_longitude, R.id.frag_addOffer_TIET_room_description, R.id.frag_addOffer_TIET_room_facilities,
            R.id.frag_addOffer_TIET_room_price, R.id.frag_addOffer_TIET_room_rating, R.id.frag_addOffer_TIET_room_size};

    private final int[] mCBResIds = {R.id.frag_addOffer_CB_breakfast, R.id.frag_addOffer_CB_gym, R.id.frag_addOffer_CB_internet,
            R.id.frag_addOffer_CB_pool, R.id.frag_addOffer_CB_bar, R.id.frag_addOffer_CB_non_smoking, R.id.frag_addOffer_CB_spa};
    private final String[] mTIETKeys = {"name", "description", "latitude", "longitude", "roomDescription",
            "roomFacilities", "price", "rating", "size"};
    private View mRoot;
    private ProgressDialog mDialog;
    private boolean mErrorFound;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_offer, container, false);

        setUpView(root);
        mRoot = root;
        return root;
    }

    private void setUpView(View root) {
        setSpinnerRoomType(root);
        setCheckBoxFacilities(root);
        setUpCalendarPicker(root);
        setUpNumberPicker(root);
        setAddImagesButton(root);
        setSpinner(root, R.id.frag_addOffer_SP_city, new UploadCities().getCities()); // set spinner for city
        setSpinner(root, R.id.frag_addOffer_SP_country, new UploadCities().getCountries()); // set spinner for country
        setTIETList(root);
        setListenerForSubmit(root);
    }

    private void setTIETList(View root) {
        mTIETList = new ArrayList<>();
        for(Integer resId: mTIETResIds) {
            mTIETList.add(root.findViewById(resId));
        }
    }

    private void setListenerForSubmit(View root) {
        Button saveOfferBTN = root.findViewById(R.id.frag_addOffer_BTN_submit);
        saveOfferBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fieldsAreValid()) {
                    mFacilities = getFacilities();
                    mPopularFacilities = getPopularFacilities();
//                    mDialog = DialogShow.getInstance().getDisplayDialog(getContext(), R.string.frag_addOffer_dialog_message);
//                    saveImagesToStorage();
//                    // not sure about this
//                    // this blocks the main thread
//                    while(mPicturesLink.size() != mPicturesList.size() && !mErrorFound);
//                    if(!mErrorFound) {
//                        saveOfferToFirebase();
//                    }
                }
            }
        });
    }

    private List<String> getFacilities() {
        List<String> facilities = new ArrayList<>();
        String facilitiesString = mTIETList.get(5).getText().toString().trim();
        for(String facility: facilitiesString.split(",")) {
            facilities.add(facility.trim());
        }
        return facilities;
    }

    private List<String> getPopularFacilities() {
        String[] popularFacilities = getResources().getStringArray(R.array.room_popular_facilities);
        List<String> popFacilities = new ArrayList<>();
        int counter = 0;
        for(int resId: mCBResIds) {
            CheckBox cb = mRoot.findViewById(resId);
            if(cb.isChecked()) {
                popFacilities.add(popularFacilities[counter]);
            }
            ++counter;
        }
        return popFacilities;
    }


    private boolean fieldsAreValid() {
        for(TextInputEditText tiet: mTIETList) {
            if(tiet.getText().toString().length() == 0) {
                tiet.setError("Field required!");
                return false;
            }
        }
        if(mCalendarActions.getEndDate() == 0) {
            Toast.makeText(getContext(), "A valid period is required!", Toast.LENGTH_LONG).show();
            return false;
        }
        if(mCalendarActions.getStartDate() == 0) {
            Toast.makeText(getContext(), "A valid period is required!", Toast.LENGTH_LONG).show();
            return false;
        }
        if(mPicturesList.size() == 0) {
            Toast.makeText(getContext(), "At least two images are required!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void setSpinner(View root, int resId, String[] arrayOfVals) {
        Spinner countrySpinner = root.findViewById(resId);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, arrayOfVals);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinner.setAdapter(adapter);
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
            checkbox1.setId(mCBResIds[i]);
            checkbox1.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
            checkbox1.setText(popularFacilities[i]);

            if (i + 1 < popularFacilities.length) {
                CheckBox checkbox2 = new CheckBox(getContext());
                checkbox2.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
                checkbox2.setText(popularFacilities[i + 1]);
                checkbox2.setId(mCBResIds[i + 1]);
                parentCBs.addView(checkbox2);
            }
            parentCBs.addView(checkbox1);
            parentLL.addView(parentCBs);

        }
    }

    private void saveImagesToStorage() {
        for (Bitmap bitmap : mPicturesList) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();
            StorageReference propertiesRef = FirebaseStorage.getInstance().getReference().child("properties_all/" + UUID.randomUUID());
            UploadTask uploadTask = propertiesRef.putBytes(data);

            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return propertiesRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        mPicturesLink.add(task.toString());
                    }
                }
            });
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
                    mPicturesList = new ArrayList<>();
                    int count = data.getClipData().getItemCount();
                    getPictures(data, count);
                    Snackbar.make(mRoot, count + " images selected", BaseTransientBottomBar.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Please select at least 2 pictures", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getContext(), "Could not select images", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void getPictures(Intent data, int count) {
        for (int i = 0; i < count; i++) {
            final Uri imageUri = data.getClipData().getItemAt(i).getUri();
            final InputStream imageStream;
            try {
                imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                mPicturesList.add(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
