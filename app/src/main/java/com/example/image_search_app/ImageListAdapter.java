package com.example.image_search_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ViewHolder> {

    private List<String> mImageList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    ImageListAdapter(Context context, List<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mImageList = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.image_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String imageUrl = mImageList.get(position);
        new ImageLoadTask(imageUrl, holder.myImageView).execute();
    }

    @Override
    public int getItemCount() {
        return mImageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView myImageView;

        ViewHolder(View itemView) {
            super(itemView);
            myImageView = itemView.findViewById(R.id.image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setImageList(List<String> imageList) {
        this.mImageList = imageList;
        notifyDataSetChanged();
    }

    public void clear() {
        int size = mImageList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                mImageList.remove(0);
            }

            notifyItemRangeRemoved(0, size);
        }
    }
}