package com.tothon.layarperak.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.tothon.layarperak.R;
import com.tothon.layarperak.activity.MovieDetailsActivity;
import com.tothon.layarperak.service.RetrofitAPI;
import com.tothon.layarperak.model.Movie;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.MovieViewHolder> {

    private Context context;
    private ArrayList<Movie> movieArrayList;

    public MovieRecyclerViewAdapter(Context context, ArrayList<Movie> mMoviesArrayList) {
        this.context = context;
        this.movieArrayList = mMoviesArrayList;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieArrayList.get(position);
        if (movie.getPosterBytes() != null) {
            Picasso.with(context)
                    .load(movie.getPosterPath())
                    .centerCrop()
                    .fit()
                    .error(R.drawable.tmdb_placeholder)
                    .into(holder.posterImageView);
        } else {
            Picasso.with(context)
                    .load(RetrofitAPI.POSTER_BASE_URL + movie.getPosterPath())
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

        @BindView(R.id.poster_image_view)
        ImageView posterImageView;

        private MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
