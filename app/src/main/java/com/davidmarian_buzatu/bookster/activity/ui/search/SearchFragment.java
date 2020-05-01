package com.davidmarian_buzatu.bookster.activity.ui.search;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.davidmarian_buzatu.bookster.R;
import com.davidmarian_buzatu.bookster.activity.ui.search.helper.SearchList;
import com.davidmarian_buzatu.bookster.model.Client;
import com.davidmarian_buzatu.bookster.model.Manager;
import com.davidmarian_buzatu.bookster.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class SearchFragment extends Fragment {

    private User mCurrentUser;

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
                startListOffersFragment(root);
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

        List<SearchList> list1=new ArrayList<>();

            FirebaseFirestore.getInstance()
                    .collection("cities")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                Log.d("LIST_VIEW","Task Succesful in list view");
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    list1.add(new SearchList(document.getId() + ", " + document.getData().get("Country")));
                                    Log.d("LIST_VIEW", document.getId() + ", " + document.getData().get("Country"));
                                }
                            } else {
                                Log.d("LIST_FAILED", "Fail to add documents");
                            }
                        }
                    });
            

        searchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                if (!oldQuery.equals("") && newQuery.equals("")) {
                    searchView.clearSuggestions();
                } else {

                    searchView.swapSuggestions(
                            getFilteredList(list1, newQuery));
                }
            }
        });
        searchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
                searchView.setSearchText(searchSuggestion.getBody());
                searchView.clearSuggestions();

            }

            @Override
            public void onSearchAction(String currentQuery) {

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
//        ListOffersFragment nextFragment = new ListOffersFragment();
        DisplayOfferFragment nextFragment = new DisplayOfferFragment();
        Bundle bundle = new Bundle();
        // TODO: PUT CITY VALUE FROM SEARCH INPUT
        bundle.putString("City", "Rome");
        nextFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment, nextFragment)
                .addToBackStack(null)
                .commit();
        Log.d("LIST_OFFER", "LISTED");
    }
}
