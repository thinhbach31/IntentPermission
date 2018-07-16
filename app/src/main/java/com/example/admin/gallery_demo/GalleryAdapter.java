package com.example.admin.gallery_demo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private LayoutInflater mlayoutInflater;
    private List<String> mListFileName;
    private Context mContext;

    public GalleryAdapter(List<String> mListFileName, Context mContext) {
        this.mListFileName = mListFileName;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mlayoutInflater == null) {
            mlayoutInflater = LayoutInflater.from(parent.getContext());
        }
        View v = mlayoutInflater.inflate(R.layout.items_picture, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String filename = mListFileName.get(position);
        File file = new File(filename);
        Picasso.get().load(file).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        //check null
        return mListFileName != null ? mListFileName.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_gallery);
        }
    }
}
