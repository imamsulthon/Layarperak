package com.tothon.layarperak.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tothon.layarperak.R;
import com.tothon.layarperak.config.Config;
import com.tothon.layarperak.model.Cast;
import com.tothon.layarperak.service.RetrofitAPI;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CastRecyclerViewAdapter extends RecyclerView.Adapter<CastRecyclerViewAdapter.CastViewHolder> {

    private Context context;
    private ArrayList<Cast> castArrayList;

    public CastRecyclerViewAdapter(Context context, ArrayList<Cast> castArrayList) {
        this.context = context;
        this.castArrayList = castArrayList;
    }

    @NonNull
    @Override
    public CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_cast_item, parent, false);
        return new CastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CastViewHolder holder, int position) {
        Cast cast = castArrayList.get(position);
        if (cast.getProfilePath() != null) {
            Picasso.with(context)
                    .load(RetrofitAPI.POSTER_BASE_URL_SMALL + cast.getProfilePath())
                    .error(R.drawable.tmdb_placeholder)
                    .into(holder.ivPhotoProfile);
        }
        holder.tvCastName.setText(cast.getName());
        holder.tvCharacter.setText(cast.getCharacter());
    }

    @Override
    public int getItemCount() {
        return castArrayList.size();
    }

    public class CastViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cast_iv)
        ImageView ivPhotoProfile;
        @BindView(R.id.tv_cast_name)
        TextView tvCastName;
        @BindView(R.id.tv_character)
        TextView tvCharacter;

        public CastViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            Config config = new Config(context);
            tvCastName.setTypeface(config.getTypeface());
            tvCharacter.setTypeface(config.getTypeface());
        }
    }
}
