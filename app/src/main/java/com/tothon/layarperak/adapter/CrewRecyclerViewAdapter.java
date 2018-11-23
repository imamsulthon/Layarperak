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
import com.tothon.layarperak.model.Crew;
import com.tothon.layarperak.service.RetrofitAPI;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CrewRecyclerViewAdapter extends RecyclerView.Adapter<CrewRecyclerViewAdapter.CrewViewHolder> {

    private Context context;
    private ArrayList<Crew> crewArrayList;

    public CrewRecyclerViewAdapter(Context context, ArrayList<Crew> crewArrayList) {
        this.context = context;
        this.crewArrayList = crewArrayList;
    }

    @NonNull
    @Override
    public CrewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_crew_item, parent, false);
        return new CrewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CrewViewHolder holder, int position) {
        Crew crew = crewArrayList.get(position);
        if (crew.getProfilePath() != null) {
            Picasso.with(context)
                    .load(RetrofitAPI.POSTER_BASE_URL_SMALL + crew.getProfilePath())
                    .error(R.drawable.tmdb_placeholder)
                    .into(holder.ivPhotoProfile);
        }
        holder.tvCrewName.setText(crew.getName());
        holder.tvJob.setText(crew.getJob());
    }

    @Override
    public int getItemCount() {
        return crewArrayList.size();
    }

    public class CrewViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_profile)
        ImageView ivPhotoProfile;
        @BindView(R.id.tv_crew_name)
        TextView tvCrewName;
        @BindView(R.id.tv_job)
        TextView tvJob;

        public CrewViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            Config config = new Config(context);
            tvCrewName.setTypeface(config.getTypeface());
            tvJob.setTypeface(config.getTypeface());
        }
    }
}
