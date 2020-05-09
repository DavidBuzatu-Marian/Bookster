package com.davidmarian_buzatu.bookster.activity.ui.messages;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.davidmarian_buzatu.bookster.R;
import com.davidmarian_buzatu.bookster.adapter.ListMessagesAdapter;
import com.davidmarian_buzatu.bookster.model.Message;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class MessagesFragment extends Fragment {
    private RecyclerView.Adapter mAdapter;
    private List<Message> mMessages;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_messages, container, false);


        getNotifications(root);
        return root;
    }

    private void getNotifications(View root) {
        mMessages = new ArrayList<>();

        String userId = FirebaseAuth.getInstance().getUid();
        FirebaseFirestore.getInstance()
                .collection("messages")
                .document(userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot doc = task.getResult();
                            List<Map<String, Object>> mapList;
                            mapList = (List<Map<String, Object>>) doc.getData().get("messages");
                            for (Map<String, Object> map : mapList) {
                                mMessages.add(new Message((String) map.get("offerID")));
                            }
                            setUpRecyclerView(root);
                        }
                    }
                });

    }

    private void setUpRecyclerView(View root) {
        RecyclerView recyclerView = root.findViewById(R.id.frag_messages_RV);

        if(mMessages.size() > 0) {
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            mAdapter = new ListMessagesAdapter(mMessages, getContext(), getActivity());
            recyclerView.setAdapter(mAdapter);
        } else {
            recyclerView.setVisibility(View.GONE);
            root.findViewById(R.id.frag_messages_CV_messages).setVisibility(View.GONE);
            root.findViewById(R.id.frag_messages_TV_empty).setVisibility(View.VISIBLE);
        }
    }
}
