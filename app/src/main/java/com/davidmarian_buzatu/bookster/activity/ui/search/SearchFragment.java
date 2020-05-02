package com.davidmarian_buzatu.bookster.activity.ui.search;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.davidmarian_buzatu.bookster.R;
import com.davidmarian_buzatu.bookster.activity.ui.search.helper.SearchList;
import com.davidmarian_buzatu.bookster.model.Client;
import com.davidmarian_buzatu.bookster.model.ListCities;
import com.davidmarian_buzatu.bookster.model.Manager;
import com.davidmarian_buzatu.bookster.model.User;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class SearchFragment extends Fragment {

    private User mCurrentUser;
    private String mCitySearched;
    private Long mStartDate, mEndDate;
    private String mLocation;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_search, container, false);


        getUserInstance(getArguments().getString("Type"));
        getUserInfo(root);
        setUpSearchView(root);
        setUpCalendarPicker(root);
        setUpNumberPicker(root);
        setUpSubmitButton(root);
        return root;
    }
//
//    private void addCitiesToFirebase() {
//        UploadCities uploadCities = new UploadCities();
//        uploadCities.uploadCitiesToDatabase();
//    }

    private void setUpSubmitButton(View root) {
        Button btnSearch = root.findViewById(R.id.frag_search_BTN_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCitySearched != null && mStartDate > 0 && mEndDate > 0) {
                    startListOffersFragment(root);
                } else {
                    Toast.makeText(getContext(), "Please complete all fields", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void getUserInfo(View root) {
        mCurrentUser.getUserInfo().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
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


    private void setUpSearchView(View root) {
        FloatingSearchView searchView = root.findViewById(R.id.frag_search_FSV);

        List<SearchList> listCities = ListCities.getInstance().getList();

        searchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                if (!oldQuery.equals("") && newQuery.equals("")) {
                    searchView.clearSuggestions();
                } else {

                    searchView.swapSuggestions(
                            getFilteredList(listCities, newQuery));
                }
            }
        });
        searchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
                searchView.setSearchText(searchSuggestion.getBody());
                searchView.clearSuggestions();
                // get location
                mLocation = searchSuggestion.getBody();
                // get only city name
                mCitySearched = searchSuggestion.getBody().split(",")[0];
            }

            @Override
            public void onSearchAction(String currentQuery) {
                searchView.setSearchText(currentQuery);
                searchView.clearSuggestions();
                // get location
                mLocation = currentQuery;
                // get only city name
                mCitySearched = currentQuery.split(",")[0];
            }
        });

    }

    private List<? extends SearchSuggestion> getFilteredList(List<SearchList> list, String newQuery) {
        List<SearchList> result = new ArrayList<>();
        for (SearchList sl : list) {
            // Transform string to lowercase for cases like 'rome' and 'Rome'
            if (sl.getBody().toLowerCase().contains(newQuery.toLowerCase())) {
                result.add(sl);
            }
        }
        return result;
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
                mStartDate = calendarStart.getTimeInMillis();
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
                mEndDate = calendarEnd.getTimeInMillis();
            }

        };
        setEditOnClick(edittextStart, dateStart, calendarStart);
        setEditOnClick(edittextEnd, dateEnd, calendarEnd);
    }

    private void setEditOnClick(EditText edittextEnd, DatePickerDialog.OnDateSetListener dateEnd, Calendar calendarEnd) {
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

    private void startListOffersFragment(View view) {
        ListOffersFragment nextFragment = new ListOffersFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Location", mLocation);
        bundle.putString("City", mCitySearched);
        bundle.putLong("StartDate", mStartDate);
        bundle.putLong("EndDate", mEndDate);
        nextFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment, nextFragment)
                .addToBackStack(null)
                .commit();
    }
}
