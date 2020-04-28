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
        test.add(new Offer("Arad", "1423", "Dont know", "Lorem magic text with a lot of description without any sense lot of description without any sense", "5", "https://firebasestorage.googleapis.com/v0/b/bookster-9e512.appspot.com/o/properties_ref%2F184305239.jpg?alt=media&token=57ee7fe1-0110-4bfa-bb01-8795c7a3b159"  ,null));
        test.add(new Offer("Arad", "1423", "Dont know", "Lorem magic text with a lot of description without any sense lot of description without any sense", "4", "https://firebasestorage.googleapis.com/v0/b/bookster-9e512.appspot.com/o/properties_ref%2F5ea7ba25-596db591.jpg?alt=media&token=d24b8ca2-57c1-428b-96a8-6d20f55f6327", null));
        test.add(new Offer("Arad", "1423", "Dont know", "Lorem magic text with a lot of description without any sense lot of description without any sense", "3.4", "https://firebasestorage.googleapis.com/v0/b/bookster-9e512.appspot.com/o/properties_ref%2Fhotelreview1a.jpg?alt=media&token=028052e8-cdb0-4ca8-8d5e-848a23f8dc54", null));
        mAdapter = new ListOffersAdapter(test, getContext());
        recyclerView.setAdapter(mAdapter);
    }
}
