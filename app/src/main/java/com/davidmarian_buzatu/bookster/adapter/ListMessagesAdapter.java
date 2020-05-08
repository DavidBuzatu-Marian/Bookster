package com.davidmarian_buzatu.bookster.adapter;

import android.content.Context;
import android.opengl.Visibility;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.davidmarian_buzatu.bookster.R;
import com.davidmarian_buzatu.bookster.model.Message;

import java.util.List;

public class ListMessagesAdapter extends RecyclerView.Adapter<ListMessagesAdapter.ListMessagesViewHolder> {

    private final FragmentActivity mActivity;
    private List<Message> mDataSet;
    private Context mContext;

    public ListMessagesAdapter(List<Message> dataset, Context context, FragmentActivity activity){
        this.mActivity = activity;
        this.mDataSet=dataset;
        this.mContext=context;
    }


    @NonNull
    @Override
    public ListMessagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ConstraintLayout _viewGroup=(ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_message_item,parent,false);

        ListMessagesViewHolder _vh= new ListMessagesViewHolder(_viewGroup,parent);
        return _vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ListMessagesAdapter.ListMessagesViewHolder holder, int position) {
            Message message = mDataSet.get(position);
            holder.setInfoInViews(message,mContext);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class ListMessagesViewHolder extends RecyclerView.ViewHolder{
        private Button mCheckEmailsButton;
        private TextView mOfferID;

        public ListMessagesViewHolder(@NonNull View itemView, @Nullable final ViewGroup parent) {
            super(itemView);

            mOfferID=itemView.findViewById(R.id.adapter_listMessages_TV_offerId);
            mCheckEmailsButton=itemView.findViewById(R.id.adapter_listMessages_BTN_mail);
        }

        private void setInfoInViews(Message message,Context context){
            mOfferID.setText(new StringBuilder()
                    .append("You received this notification for: ")
                    .append(message.getmOfferID())
            );
            setButtonListener(message);
        }

        private void setButtonListener(Message message){
            mCheckEmailsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CardView cv=itemView.findViewById(R.id.adapter_listMessages_CV);
                    cv.setVisibility(View.GONE);
                    Log.d("MESSAGE_TEST","Pressed 'Check emails' button");
                }
            });
        }
    }
}
