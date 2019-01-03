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
import com.tothon.layarperak.activity.MovieDetailsActivity;
import com.tothon.layarperak.model.Movie;
import com.tothon.layarperak.service.RetrofitAPI;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieRecyclerViewGridAdapter extends RecyclerView.Adapter<MovieRecyclerViewGridAdapter.MovieViewHolder>{

    private Context context;
    private ArrayList<Movie> movieArrayList;

    public MovieRecyclerViewGridAdapter(Context context, ArrayList<Movie> movieArrayList) {
        this.context = context;
        this.movieArrayList = movieArrayList;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_item_movie, parent, false);
        return new MovieRecyclerViewGridAdapter.MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieArrayList.get(position);
        if (movie.getPosterBytes() != null) {
            holder.layout.setVisibility(View.VISIBLE);
            holder.tvTitle.setText(movie.getTitle());
        } else {
            Picasso.with(context)
                    .load(RetrofitAPI.POSTER_BASE_URL_SMALL + movie.getPosterPath())
                    .error(R.drawable.tmdb_placeholder)
                    .centerCrop()
                    .fit()
                    .into(holder.posterImageView);
        }
        holder.posterImageView.setOnClickListener(view -> {
            Intent intent = new Intent(context, MovieDetailsActivity.class);
            intent.putExtra(MovieDetailsActivity.KEY, movie);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if (movieArrayList == null) {
            return 0;
        } else {
            return movieArrayList.size();
        }
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_poster)
        ImageView posterImageView;
        @BindView(R.id.layout)
        LinearLayout layout;
        @BindView(R.id.tv_title)
        TextView tvTitle;

        private MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
