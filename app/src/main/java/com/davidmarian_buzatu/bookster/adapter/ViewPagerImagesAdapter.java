package com.davidmarian_buzatu.bookster.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.davidmarian_buzatu.bookster.R;

import java.util.List;

public class ViewPagerImagesAdapter extends RecyclerView.Adapter<ViewPagerImagesAdapter.ViewPagerImagesVH> {
    private List<String> mDataSet;
    private Context mContext;

    public ViewPagerImagesAdapter(Context context, List<String> data) {
        mDataSet = data;
        mContext = context;
    }

    public ViewPagerImagesVH onCreateViewHolder(ViewGroup parent, int viewType) {
        ConstraintLayout view = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.viewpager_pictures_item, parent, false);

        ViewPagerImagesVH _vh = new ViewPagerImagesVH(view, parent);
        return _vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPagerImagesAdapter.ViewPagerImagesVH holder, int position) {
        String imageURL = mDataSet.get(position);
        holder.setInfoInViews(imageURL, mContext);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class ViewPagerImagesVH extends RecyclerView.ViewHolder {

        private ImageView mImage;

        public ViewPagerImagesVH(@NonNull View itemView, @NonNull final ViewGroup parent) {
            super(itemView);

            // TODO: USE itemView TO GET VIEWS
            mImage = itemView.findViewById(R.id.viewpager_pictures_IV);
        }

        private void setInfoInViews(String imageURL, Context context) {
            Glide.with(context).load(imageURL).into(mImage);
        }
    }
}
