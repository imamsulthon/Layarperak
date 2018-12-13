package com.tothon.layarperak.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.tothon.layarperak.R;
import com.tothon.layarperak.activity.ImageFullScreenActivity;
import com.tothon.layarperak.model.Image;
import com.tothon.layarperak.service.RetrofitAPI;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageRecyclerViewAdapter extends RecyclerView.Adapter<ImageRecyclerViewAdapter.ImageViewHolder> {

    private Context context;
    private ArrayList<Image> imageList;
    private String title;

    public ImageRecyclerViewAdapter(Context context, ArrayList<Image> imageList, String title) {
        this.context = context;
        this.imageList = imageList;
        this.title = title;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_item_image, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Image image = imageList.get(position);
        Picasso.with(context)
                .load(RetrofitAPI.BACKDROP_BASE_URL_SMALL + image.getFilePath())
                .error(R.drawable.tmdb_placeholder)
                .centerCrop()
                .fit()
                .into(holder.thumbnail);
        holder.thumbnail.setOnClickListener(item -> {
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
        return imageList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_thumbnail_image)
        ImageView thumbnail;

        ImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
