package com.tothon.layarperak.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tothon.layarperak.R;
import com.tothon.layarperak.activity.PersonDetailsActivity;
import com.tothon.layarperak.config.Config;
import com.tothon.layarperak.model.Person;
import com.tothon.layarperak.service.RetrofitAPI;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonViewHolder> {

    private Context context;
    private ArrayList<Person> personArrayList;

    public PersonAdapter(Context context, ArrayList<Person> personArrayList) {
        this.context = context;
        this.personArrayList = personArrayList;
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_item_people, parent, false);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        Person person = personArrayList.get(position);
        if (person.getProfilePath() != null) {
        Picasso.with(context)
                .load(RetrofitAPI.POSTER_BASE_URL_SMALL + person.getProfilePath())
                .error(R.drawable.tmdb_placeholder)
                .into(holder.ivThumbnail);
        }
        holder.tvName.setText(person.getName());
        holder.ivThumbnail.setOnClickListener(item -> {
            Intent intent = new Intent(context, PersonDetailsActivity.class);
            intent.putExtra(PersonDetailsActivity.KEY, person);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return personArrayList.size();
    }

    public class PersonViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_photo)
        ImageView ivThumbnail;
        @BindView(R.id.tv_name)
        TextView tvName;

        public PersonViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            Config config = new Config(context);
            tvName.setTypeface(config.getTypeface());
        }
    }
}
