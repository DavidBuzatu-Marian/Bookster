package com.davidmarian_buzatu.bookster.activity.ui.search;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
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
import java.util.ArrayList;
import java.util.Calendar;
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

    private void setUpSearchView(View root){
        SearchView searchView=root.findViewById(R.id.frag_search_SV);
        ListView listView=root.findViewById(R.id.frag_search_LV);
        ArrayList<String> list;
        ArrayAdapter<String> adapter;

        list=new ArrayList<>(3);
        list.add("Rome");
        list.add("Paris");
        list.add("Timisoara");

        adapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if(list.contains(query)){
                    adapter.getFilter().filter(query);
                }
                else{
                    Toast.makeText(getContext(),"No match found",Toast.LENGTH_LONG).show();
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(list.contains(newText)){
                    adapter.getFilter().filter(newText);
                }
                else{
                    Toast.makeText(getContext(),"No match found",Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });

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
        ListOffersFragment nextFragment = new ListOffersFragment();
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
