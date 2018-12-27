package com.tothon.layarperak.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tothon.layarperak.R;
import com.tothon.layarperak.activity.MovieDetailsActivity;
import com.tothon.layarperak.config.Config;
import com.tothon.layarperak.model.MovieGroupByCrew;
import com.tothon.layarperak.service.RetrofitAPI;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieRecyclerViewLandAdapter extends RecyclerView.Adapter<MovieRecyclerViewLandAdapter.ViewHolder> {

    private Context context;
    private ArrayList<MovieGroupByCrew> movieArrayList;

    public MovieRecyclerViewLandAdapter(Context context, ArrayList<MovieGroupByCrew> movieArrayList) {
        this.context = context;
        this.movieArrayList = movieArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_item_movie_child, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieGroupByCrew movie = movieArrayList.get(position);
        Picasso.with(context)
                .load(RetrofitAPI.POSTER_BASE_URL_SMALL + movie.getPosterPath())
                .error(R.drawable.tmdb_placeholder)
                .into(holder.thumbnail);
        holder.tvTitle.setText(movie.getTitle());
        holder.tvJob.setText(movie.getJob());
        holder.layout.setOnClickListener(item -> {
            Intent intent = new Intent(context, MovieDetailsActivity.class);
            intent.putExtra(MovieDetailsActivity.KEY, movie);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return movieArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.layout)
        RelativeLayout layout;
        @BindView(R.id.iv_thumbnail)
        ImageView thumbnail;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_job)
        TextView tvJob;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            Config config = new Config(context);
            tvTitle.setTypeface(config.getTypeface());
            tvJob.setTypeface(config.getTypeface());
        }
    }
}
