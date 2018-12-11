package com.tothon.layarperak.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;
import com.tothon.layarperak.R;
import com.tothon.layarperak.activity.ImageFullScreenActivity;
import com.tothon.layarperak.model.Backdrop;
import com.tothon.layarperak.service.RetrofitAPI;
import com.tothon.layarperak.widget.DynamicImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Backdrop> imageList;
    private String title;

    public GalleryAdapter(Context context, ArrayList<Backdrop> imageList, String title) {
        this.context = context;
        this.imageList = imageList;
        this.title = title;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_item_gallery, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Backdrop image = imageList.get(position);
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) holder.thumbnail.getLayoutParams();
        float height = image.getHeight();
        float width = image.getWidth();
        float ratio = height/width;
        rlp.height = (int) (rlp.width * ratio);
        holder.thumbnail.setLayoutParams(rlp);
        holder.thumbnail.setRatio(ratio);
        Picasso.with(context)
                .load(RetrofitAPI.BACKDROP_BASE_URL_SMALL + image.getFilePath())
                .placeholder(R.drawable.tmdb_placeholder_land)
                .into(holder.thumbnail);
        holder.thumbnail.setOnClickListener(v -> {
            Intent intent = new Intent(context, ImageFullScreenActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(ImageFullScreenActivity.KEY_TITLE, title);
            bundle.putParcelableArrayList(ImageFullScreenActivity.KEY_IMAGES, imageList);
            bundle.putInt(ImageFullScreenActivity.KEY_POSITION, position);
            intent.putExtras(bundle);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return (imageList == null) ? 0 : imageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_thumbnail)
        DynamicImageView thumbnail;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
