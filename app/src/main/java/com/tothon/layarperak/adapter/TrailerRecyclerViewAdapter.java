package com.tothon.layarperak.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tothon.layarperak.R;
import com.tothon.layarperak.config.Config;
import com.tothon.layarperak.config.Constants;
import com.tothon.layarperak.fragment.YoutubeDialogFragment;
import com.tothon.layarperak.model.Trailer;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.tothon.layarperak.fragment.YoutubeDialogFragment.TAG_YOUTUBE_FRAGMENT;

public class TrailerRecyclerViewAdapter extends RecyclerView.Adapter<TrailerRecyclerViewAdapter.TrailerViewHolder> {

    private Context context;
    private ArrayList<Trailer> trailerList;

    public TrailerRecyclerViewAdapter(Context context, ArrayList<Trailer> trailerArrayList) {
        this.context = context;
        this.trailerList = trailerArrayList;
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_trailer_item, parent, false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        Trailer trailer = trailerList.get(position);
        Config config = new Config(context);
        holder.title.setTypeface(config.getTypeface());
        if (trailer.getName() == null) {
            holder.title.setText("You aren't connected to the internet or maybe no trailers available for this movie");
        } else {
            holder.title.setText(trailer.getName());
        }
        Picasso.with(context)
                .load(Constants.YOUTUBE_BASE_URL + trailer.getKey()+"/0.jpg")
                .error(R.drawable.cursor_search)
                .centerCrop()
                .fit()
                .into(holder.thumbnail);

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    YoutubeDialogFragment dialogFragment = new YoutubeDialogFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(YoutubeDialogFragment.MOVIE_KEY, trailer.getKey());
                    dialogFragment.setArguments(bundle);
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
                    dialogFragment.show(fragmentTransaction, TAG_YOUTUBE_FRAGMENT);
                } catch (ClassCastException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (trailerList == null) {
            return 0;
        } else {
            return trailerList.size();
        }
    }

    public static class TrailerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title_trailer)
        TextView title;
        @BindView(R.id.iv_thumbnail_trailer)
        ImageView thumbnail;

        TrailerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
