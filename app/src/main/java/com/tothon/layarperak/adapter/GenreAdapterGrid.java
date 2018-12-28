package com.tothon.layarperak.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tothon.layarperak.R;
import com.tothon.layarperak.activity.MovieGridActivity;
import com.tothon.layarperak.model.Genre;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GenreAdapterGrid extends RecyclerView.Adapter<GenreAdapterGrid.GenreViewHolder> {

    private Context context;
    private String category;
    private ArrayList<Genre> genreArrayList;

    public GenreAdapterGrid(Context context, String category, ArrayList<Genre> genreArrayList) {
        this.context = context;
        this.category = category;
        this.genreArrayList = genreArrayList;
    }

    @NonNull
    @Override
    public GenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_item_genre_grid, parent, false);
        return new GenreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreViewHolder holder, int position) {
        Typeface metrophobic = ResourcesCompat.getFont(context, R.font.metrophobic);
        holder.tvGenre.setTypeface(metrophobic);
        Genre genre = genreArrayList.get(position);
        holder.tvGenre.setText(genre.getName());
        holder.layout.setOnClickListener(item -> {
            Intent intent = new Intent(context, MovieGridActivity.class);
            intent.putExtra(MovieGridActivity.GENRE_TAG, genre);
            intent.putExtra(MovieGridActivity.GENRE_TYPE, category);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return genreArrayList.size();
    }

    public class GenreViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.layout)
        CardView layout;
        @BindView(R.id.tv_title)
        TextView tvGenre;

        public GenreViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
