package com.davidmarian_buzatu.bookster.activity.ui.search;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.davidmarian_buzatu.bookster.R;
import com.davidmarian_buzatu.bookster.adapter.ListOffersAdapter;
import com.davidmarian_buzatu.bookster.model.Offer;

import java.util.ArrayList;
import java.util.List;

public class ListOffersFragment extends Fragment {

    private RecyclerView.Adapter mAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_list_offers, container, false);


        setUpRecyclerView(root);

        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if(bundle != null) {
            Log.d("SEARCHED", bundle.getString("City"));
        }
    }

    private void setUpRecyclerView(View root) {
        RecyclerView recyclerView = root.findViewById(R.id.frag_listOffers_RV_offers);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<Offer> test = new ArrayList<>();
        test.add(new Offer("Arad", "1423", "Dont know", "Exemplu", "ere", null));
        test.add(new Offer("Arad", "1423", "Dont know", "Exemplu", "ere", null));
        test.add(new Offer("Arad", "1423", "Dont know", "Exemplu", "ere", null));
        mAdapter = new ListOffersAdapter(test);
        recyclerView.setAdapter(mAdapter);
    }
}
