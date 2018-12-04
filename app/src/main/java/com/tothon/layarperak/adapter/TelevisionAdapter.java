package com.tothon.layarperak.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tothon.layarperak.R;
import com.tothon.layarperak.activity.TelevisionDetailsActivity;
import com.tothon.layarperak.model.Television;
import com.tothon.layarperak.service.RetrofitAPI;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TelevisionAdapter extends RecyclerView.Adapter<TelevisionAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Television> televisionArrayList;

    public TelevisionAdapter(Context context, ArrayList<Television> televisions) {
        this.context = context;
        this.televisionArrayList = televisions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_item_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Television television = televisionArrayList.get(position);
        if (television != null) {
            if (television.getPosterBytes() != null) {
                holder.layout.setVisibility(View.VISIBLE);
                holder.tvTitle.setText(television.getTitle());
            } else {
                Picasso.with(context)
                        .load(RetrofitAPI.POSTER_BASE_URL_SMALL + television.getPosterPath())
                        .error(R.drawable.tmdb_placeholder)
                        .centerCrop()
                        .fit()
                        .into(holder.posterImageView);
            }
        }
        holder.posterImageView.setOnClickListener(view -> {
            Intent intent = new Intent(context, TelevisionDetailsActivity.class);
            intent.putExtra(TelevisionDetailsActivity.KEY, television);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if (televisionArrayList == null) {
            return 0;
        } else {
            return televisionArrayList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_poster)
        ImageView posterImageView;
        @BindView(R.id.layout)
        LinearLayout layout;
        @BindView(R.id.tv_title)
        TextView tvTitle;

        private ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
