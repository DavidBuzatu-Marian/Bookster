package com.davidmarian_buzatu.bookster.activity.ui.search;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.davidmarian_buzatu.bookster.R;
import com.davidmarian_buzatu.bookster.activity.MenuActivity;
import com.davidmarian_buzatu.bookster.activity.ui.search.helper.UploadCities;
import com.davidmarian_buzatu.bookster.model.Client;
import com.davidmarian_buzatu.bookster.model.Manager;
import com.davidmarian_buzatu.bookster.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SearchFragment extends Fragment {

    private User mCurrentUser;
    private UploadCities uploadCities=new UploadCities();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        
        View root = inflater.inflate(R.layout.fragment_search, container, false);

        uploadCities.uploadCitiesToDatabase();

        getUserInstance(getArguments().getString("Type"));
        getUserInfo(root);
        setUpCalendarPicker(root);
        setUpNumberPicker(root);
        return root;
    }

    private void getUserInfo(View root) {
        mCurrentUser.getUserInfo().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    setTVHeaderMessage(root);
                }
            }
        });
    }

    private void setTVHeaderMessage(View root) {
        TextView tv = root.findViewById(R.id.frag_search_TV_welcome);
        tv.setText(new StringBuilder()
                .append(getString(R.string.frag_search_TV_welcome_user))
                .append(", ")
                .append(mCurrentUser.getUserName()).toString());
    }

    private void getUserInstance(String type) {
        switch (type) {
            case "Client":
                mCurrentUser = Client.getInstance();
                break;
            case "Manager":
                mCurrentUser = Manager.getInstance();
                break;
            default:
                mCurrentUser = null;
        }
    }

    private void setUpNumberPicker(View root) {
        NumberPicker np = root.findViewById(R.id.frag_search_NP_persons);

        np.setMinValue(1);
        np.setMaxValue(10);
    }


    private void setUpCalendarPicker(View container) {
        final Calendar calendarStart = Calendar.getInstance();
        final Calendar calendarEnd = Calendar.getInstance();

        EditText edittextStart = container.findViewById(R.id.frag_search_ET_start_date);
        EditText edittextEnd = container.findViewById(R.id.frag_search_ET_end_date);
        DatePickerDialog.OnDateSetListener dateStart = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                calendarStart.set(Calendar.YEAR, year);
                calendarStart.set(Calendar.MONTH, monthOfYear);
                calendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(calendarStart, edittextStart);
            }

        };
        DatePickerDialog.OnDateSetListener dateEnd = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                calendarEnd.set(Calendar.YEAR, year);
                calendarEnd.set(Calendar.MONTH, monthOfYear);
                calendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(calendarEnd, edittextEnd);
            }

        };

        edittextStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), dateStart, calendarStart
                        .get(Calendar.YEAR), calendarStart.get(Calendar.MONTH),
                        calendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        edittextEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), dateEnd, calendarEnd
                        .get(Calendar.YEAR), calendarEnd.get(Calendar.MONTH),
                        calendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel(Calendar calendar, EditText edittext) {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edittext.setText(sdf.format(calendar.getTime()));
    }
}
