package com.tothon.layarperak.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tothon.layarperak.R;
import com.tothon.layarperak.activity.SeeMoreMoviesActivity;
import com.tothon.layarperak.adapter.TelevisionAdapter;
import com.tothon.layarperak.config.Constants;
import com.tothon.layarperak.model.Television;
import com.tothon.layarperak.model.response.TelevisionResponse;
import com.tothon.layarperak.service.NetworkUtils;
import com.tothon.layarperak.service.RetrofitAPI;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TelevisionFragment extends Fragment {

    private static final String TMDB_API_TOKEN = Constants.TMDB_API_KEY;

    private ArrayList<Television> onAirTelevisionList = new ArrayList<>();
    private ArrayList<Television> popularTelevisionList = new ArrayList<>();
    private ArrayList<Television> topRatedTelevisionList = new ArrayList<>();

    private TelevisionAdapter onAirAdapter;
    private TelevisionAdapter popularAdapter;
    private TelevisionAdapter topRatedAdapter;

    @BindView(R.id.rv_on_air) RecyclerView recyclerViewOnAir;
    @BindView(R.id.rv_popular) RecyclerView recyclerViewPopular;
    @BindView(R.id.rv_toprated) RecyclerView recyclerViewTopRated;
    @BindView(R.id.see_more_on_air) TextView moreOnAir;
    @BindView(R.id.see_more_popular) TextView morePopular;
    @BindView(R.id.see_more_top_rated) TextView moreTopRated;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_television, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewOnAir.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager
                .HORIZONTAL, false));
        onAirAdapter = new TelevisionAdapter(getActivity(), onAirTelevisionList);
        recyclerViewOnAir.setAdapter(new ScaleInAnimationAdapter(onAirAdapter));

        recyclerViewPopular.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager
                .HORIZONTAL, false));
        popularAdapter = new TelevisionAdapter(getActivity(), popularTelevisionList);
        recyclerViewPopular.setAdapter(new ScaleInAnimationAdapter(popularAdapter));

        recyclerViewTopRated.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager
                .HORIZONTAL, false));
        topRatedAdapter = new TelevisionAdapter(getActivity(), topRatedTelevisionList);
        recyclerViewTopRated.setAdapter(new ScaleInAnimationAdapter(topRatedAdapter));

        fetchAlltelevision();

        moreOnAir.setOnClickListener(item -> {
            seeMoreMovieList("on_the_air");
        });

        morePopular.setOnClickListener(item -> {
            seeMoreMovieList("popular");
        });

        moreTopRated.setOnClickListener(item -> {
            seeMoreMovieList("top_rated");
        });
    }

    private void fetchAlltelevision() {
        RetrofitAPI retrofitAPI = NetworkUtils.getCacheEnabledRetrofit(getActivity().getApplicationContext())
                .create(RetrofitAPI.class);

        Call<TelevisionResponse> onAirCall = retrofitAPI.getTelevision("on_the_air", TMDB_API_TOKEN, 1);
        onAirCall.enqueue(new Callback<TelevisionResponse>() {
            @Override
            public void onResponse(Call<TelevisionResponse> call, Response<TelevisionResponse> response) {
                TelevisionResponse televisionResponse = response.body();
                if (televisionResponse != null) {
                    onAirTelevisionList.addAll(televisionResponse.getResults());
                    onAirAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<TelevisionResponse> call, Throwable t) {
            }
        });

        Call<TelevisionResponse> popularCall = retrofitAPI.getTelevision("popular", TMDB_API_TOKEN, 1);
        popularCall.enqueue(new Callback<TelevisionResponse>() {
            @Override
            public void onResponse(Call<TelevisionResponse> call, Response<TelevisionResponse> response) {
                TelevisionResponse televisionResponse = response.body();
                if (televisionResponse != null) {
                    popularTelevisionList.addAll(televisionResponse.getResults());
                    popularAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<TelevisionResponse> call, Throwable t) {
            }
        });

        Call<TelevisionResponse> topRatedCall = retrofitAPI.getTelevision("top_rated", TMDB_API_TOKEN, 1);
        topRatedCall.enqueue(new Callback<TelevisionResponse>() {
            @Override
            public void onResponse(Call<TelevisionResponse> call, Response<TelevisionResponse> response) {
                TelevisionResponse televisionResponse = response.body();
                if (televisionResponse != null) {
                    topRatedTelevisionList.addAll(televisionResponse.getResults());
                    topRatedAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<TelevisionResponse> call, Throwable t) {
            }
        });
    }

    private void seeMoreMovieList(String type) {
    }
}