package com.tothon.layarperak.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tothon.layarperak.R;
import com.tothon.layarperak.activity.PersonDetailsActivity;
import com.tothon.layarperak.config.Config;
import com.tothon.layarperak.fragment.PosterDialogFragment;
import com.tothon.layarperak.model.Cast;
import com.tothon.layarperak.service.RetrofitAPI;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FullCastListAdapter extends RecyclerView.Adapter<FullCastListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Cast> castArrayList;

    public FullCastListAdapter(Context context, ArrayList<Cast> castArrayList) {
        this.context = context;
        this.castArrayList = castArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_cast_item_landscape, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cast cast = castArrayList.get(position);
        if (cast.getProfilePath() != null) {
            Picasso.with(context)
                    .load(RetrofitAPI.POSTER_BASE_URL_SMALL + cast.getProfilePath())
                    .error(R.drawable.tmdb_placeholder)
                    .into(holder.ivPhotoProfile);
        }
        holder.tvCastName.setText(cast.getName());
        holder.tvCharacter.setText(cast.getCharacter());
        holder.layout.setOnClickListener(item -> {
            Intent intent = new Intent(context, PersonDetailsActivity.class);
            intent.putExtra(PersonDetailsActivity.KEY, cast);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return castArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.layout)
        RelativeLayout layout;
        @BindView(R.id.iv_thumbnail)
        ImageView ivPhotoProfile;
        @BindView(R.id.tv_name)
        TextView tvCastName;
        @BindView(R.id.tv_character)
        TextView tvCharacter;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            Config config = new Config(context);
            tvCastName.setTypeface(config.getTypeface());
            tvCharacter.setTypeface(config.getTypeface());
        }
    }
}
