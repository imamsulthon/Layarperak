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
import com.tothon.layarperak.activity.PersonDetailsActivity;
import com.tothon.layarperak.activity.TelevisionDetailsActivity;
import com.tothon.layarperak.config.Config;
import com.tothon.layarperak.model.Movie;
import com.tothon.layarperak.model.Person;
import com.tothon.layarperak.model.SearchResult;
import com.tothon.layarperak.model.Television;
import com.tothon.layarperak.service.RetrofitAPI;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private Context context;
    private ArrayList<SearchResult> results;

    public SearchAdapter(Context context, ArrayList<SearchResult> results) {
        this.context = context;
        this.results = results;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_search, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SearchResult object = results.get(position);

        switch (object.getMediaType()) {
            case "movie":
                holder.category.setImageResource(R.drawable.ic_film);
                holder.category.setVisibility(View.VISIBLE);
                Movie movie = new Movie();
                movie.setId(object.getId());
                movie.setTitle(object.getTitle());
                movie.setOverview(object.getOverview());
                movie.setDate(object.getDate());
                movie.setPosterPath(object.getPosterPath());
                movie.setVoteCount(object.getVoteCount());
                movie.setRating(object.getRating());
                if (movie.getPosterPath() != null) {
                    Picasso.with(context)
                            .load(RetrofitAPI.POSTER_BASE_URL_SMALL + movie.getPosterPath())
                            .centerCrop().fit().into(holder.thumbnail);
                }
                holder.tvTitle.setText(movie.getTitle());
                if (movie.getDate() != null) {
                    holder.tvYear.setText(movie.getDate());
                } else {
                    holder.tvYear.setText("Unknown Year");
                }
                holder.tvDescription.setText(movie.getOverview());
                holder.layout.setOnClickListener(view -> {
                    Intent intent = new Intent(context, MovieDetailsActivity.class);
                    intent.putExtra(MovieDetailsActivity.KEY, movie);
                    context.startActivity(intent);
                });
                break;
            case "tv":
                holder.category.setImageResource(R.drawable.ic_television);
                holder.category.setVisibility(View.VISIBLE);
                Television television = new Television();
                television.setId(object.getId());
                television.setTitle(object.getName());
                television.setOverview(object.getOverview());
                television.setFirstAirDate(object.getFirstAirDate());
                television.setPosterPath(object.getPosterPath());
                television.setRating(object.getRating());
                television.setVoteCount(object.getVoteCount());
                if (television.getPosterPath() != null) {
                    Picasso.with(context)
                            .load(RetrofitAPI.POSTER_BASE_URL_SMALL + television.getPosterPath())
                            .centerCrop().fit().into(holder.thumbnail);
                }
                holder.tvTitle.setText(television.getTitle());
                holder.tvYear.setText(television.getFirstAirDate());
                holder.tvDescription.setText(television.getOverview());
                holder.layout.setOnClickListener(view -> {
                    Intent intent = new Intent(context, TelevisionDetailsActivity.class);
                    intent.putExtra(TelevisionDetailsActivity.KEY, television);
                    context.startActivity(intent);
                });
                break;
            case "person":
                holder.category.setImageResource(R.drawable.ic_reputation);
                holder.category.setVisibility(View.VISIBLE);
                Person person = new Person();
                person.setId(object.getId());
                person.setName(object.getName());
                person.setMovies(object.getKnownFor());
                person.setProfilePath(object.getProfilePath());
                if (person.getProfilePath() != null) {
                    Picasso.with(context)
                            .load(RetrofitAPI.POSTER_BASE_URL_SMALL + person.getProfilePath())
                            .centerCrop().fit().into(holder.thumbnail);
                }
                holder.tvTitle.setText(person.getName());
                if (person.getMovies() != null) {
                    ArrayList<Movie> movies = person.getMovies();
                    StringBuilder sb = new StringBuilder();
                    String delim = "";
                    if (movies.size() <= 3) {
                        for (Movie m: movies) {
                            if (m.getTitle() != null) {
                                sb.append(delim).append(m.getTitle());
                                delim = ", ";
                            }
                        }
                    } else {
                        for (int i = 0; i < 3; i++) {
                            Movie m = movies.get(i);
                            if (m.getTitle() != null) {
                                sb.append(delim).append(m.getTitle());
                                delim = ", ";
                            }
                        }
                    }
                    holder.tvDescription.setText("Known for: " + "\n" + sb.toString());
                }
                holder.layout.setOnClickListener(view -> {
                    Intent intent = new Intent(context, PersonDetailsActivity.class);
                    intent.putExtra(PersonDetailsActivity.KEY, person);
                    context.startActivity(intent);
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
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
        @BindView(R.id.iv_category)
        ImageView category;

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
