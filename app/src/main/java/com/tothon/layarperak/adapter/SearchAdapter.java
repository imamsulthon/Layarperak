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
import com.tothon.layarperak.model.Movie;
import com.tothon.layarperak.service.RetrofitAPI;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Movie> movies;

    public SearchAdapter(Context context, ArrayList<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_search, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = movies.get(position);
        if (movie.getPosterPath() != null) {
            Picasso.with(context)
                    .load(RetrofitAPI.POSTER_BASE_URL_SMALL + movie.getPosterPath())
                    .centerCrop()
                    .fit()
                    .into(holder.thumbnail);
        }

        holder.tvTitle.setText(movie.getTitle());
        holder.tvYear.setText(movie.getDate().substring(0,4));
        holder.tvDescription.setText(movie.getOverview());
        holder.layout.setOnClickListener(view -> {
            Intent intent = new Intent(context, MovieDetailsActivity.class);
            intent.putExtra(MovieDetailsActivity.KEY, movie);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.layout)
        RelativeLayout layout;
        @BindView(R.id.iv_thumbnail)
        ImageView thumbnail;
        @BindView(R.id.tv_year)
        TextView tvYear;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_description)
        TextView tvDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            Config config = new Config(context);
            tvTitle.setTypeface(config.getTypeface());
            tvYear.setTypeface(config.getTypeface());
            tvDescription.setTypeface(config.getTypeface());
        }
    }
}
