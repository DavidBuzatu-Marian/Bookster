package com.davidmarian_buzatu.bookster.adapter;

import android.content.Context;
import android.content.Intent;
import android.opengl.Visibility;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.davidmarian_buzatu.bookster.R;
import com.davidmarian_buzatu.bookster.model.Message;
import com.davidmarian_buzatu.bookster.services.MessageActions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ListMessagesAdapter extends RecyclerView.Adapter<ListMessagesAdapter.ListMessagesViewHolder> {

    private final FragmentActivity mActivity;
    private List<Message> mDataSet;
    private Context mContext;

    public ListMessagesAdapter(List<Message> dataset, Context context, FragmentActivity activity) {
        this.mActivity = activity;
        this.mDataSet = dataset;
        this.mContext = context;
    }


    @NonNull
    @Override
    public ListMessagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ConstraintLayout _viewGroup = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_message_item, parent, false);

        ListMessagesViewHolder _vh = new ListMessagesViewHolder(_viewGroup, parent);
        return _vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ListMessagesAdapter.ListMessagesViewHolder holder, int position) {
        Message message = mDataSet.get(position);
        holder.setInfoInViews(message, mContext);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class ListMessagesViewHolder extends RecyclerView.ViewHolder {
        private Button mCheckEmailsButton;
        private TextView mOfferID;

        public ListMessagesViewHolder(@NonNull View itemView, @Nullable final ViewGroup parent) {
            super(itemView);

            mOfferID = itemView.findViewById(R.id.adapter_listMessages_TV_offerId);
            mCheckEmailsButton = itemView.findViewById(R.id.adapter_listMessages_BTN_mail);
        }

        private void setInfoInViews(Message message, Context context) {
            mOfferID.setText(new StringBuilder()
                    .append("Offer ID is: ")
                    .append(message.getOfferID())
            );
            setButtonListener(message);
        }

        private void setButtonListener(Message message) {
            mCheckEmailsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mailIntent = mActivity.getPackageManager().getLaunchIntentForPackage("com.google.android.gm");

                    try {
                        mActivity.startActivity(mailIntent);
                    } catch (android.content.ActivityNotFoundException e) {
                        Toast.makeText(mContext, "There is no email client installed", Toast.LENGTH_SHORT).show();
                    }
                    CardView cv = itemView.findViewById(R.id.adapter_listMessages_CV);
                    cv.setVisibility(View.GONE);

                    MessageActions.deleteMessage(message);
                }
            });
        }
    }
}
