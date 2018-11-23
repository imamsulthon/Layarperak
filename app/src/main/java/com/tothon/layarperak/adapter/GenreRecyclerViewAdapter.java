package com.tothon.layarperak.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tothon.layarperak.R;
import com.tothon.layarperak.model.Genre;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GenreRecyclerViewAdapter extends RecyclerView.Adapter<GenreRecyclerViewAdapter.GenreViewHolder> {

    private Context context;
    private ArrayList<Genre> genreArrayList;

    public GenreRecyclerViewAdapter(Context context, ArrayList<Genre> genreArrayList) {
        this.context = context;
        this.genreArrayList = genreArrayList;
    }

    @NonNull
    @Override
    public GenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_genre_item, parent, false);
        return new GenreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreViewHolder holder, int position) {
        Typeface metrophobic = ResourcesCompat.getFont(context, R.font.metrophobic);
        holder.tvGenre.setTypeface(metrophobic);
        Genre genre = genreArrayList.get(position);
        holder.tvGenre.setText(genre.getName());
    }

    @Override
    public int getItemCount() {
        if (genreArrayList != null) {
            return genreArrayList.size();
        } else {
            return 0;
        }
    }

    public class GenreViewHolder extends RecyclerView.ViewHolder {

        TextView tvGenre;

        public GenreViewHolder(View itemView) {
            super(itemView);
            tvGenre = itemView.findViewById(R.id.tv_genre);
        }
    }
}
