package com.tothon.layarperak.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tothon.layarperak.R;
import com.tothon.layarperak.adapter.MovieRecyclerViewAdapter;
import com.tothon.layarperak.adapter.PersonAdapter;
import com.tothon.layarperak.adapter.TelevisionAdapter;
import com.tothon.layarperak.config.Constants;
import com.tothon.layarperak.model.Movie;
import com.tothon.layarperak.model.Person;
import com.tothon.layarperak.model.Television;
import com.tothon.layarperak.model.response.MovieResponse;
import com.tothon.layarperak.model.response.PeopleResponse;
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

public class FavoriteFragment extends Fragment {

    private static final String TMDB_API_TOKEN = Constants.TMDB_API_KEY;

    private ArrayList<Movie> popularMovieList = new ArrayList<>();
    private ArrayList<Television> televisionList = new ArrayList<>();
    private ArrayList<Person> celebrityList = new ArrayList<>();

    private MovieRecyclerViewAdapter popularMoviesAdapter;
    private TelevisionAdapter televisionAdapter;
    private PersonAdapter celebrityAdapter;

    @BindView(R.id.rv_trending_movies)
    RecyclerView recyclerViewPopular;
    @BindView(R.id.rv_trending_television)
    RecyclerView recyclerViewTopRated;
    @BindView(R.id.rv_trending_celebrity)
    RecyclerView recyclerViewCelebrity;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewPopular.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager
                .HORIZONTAL, false));
        popularMoviesAdapter = new MovieRecyclerViewAdapter(getActivity(), popularMovieList);
        recyclerViewPopular.setAdapter(new ScaleInAnimationAdapter(popularMoviesAdapter));

        recyclerViewTopRated.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager
                .HORIZONTAL, false));
        televisionAdapter = new TelevisionAdapter(getActivity(), televisionList);
        recyclerViewTopRated.setAdapter(new ScaleInAnimationAdapter(televisionAdapter));

        recyclerViewCelebrity.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager
                .HORIZONTAL, false));
        celebrityAdapter = new PersonAdapter(getActivity(), celebrityList);
        recyclerViewCelebrity.setAdapter(new ScaleInAnimationAdapter(celebrityAdapter));

        fetchAllTrending("week");
    }

    private void fetchAllTrending(String type) {
        RetrofitAPI retrofitAPI = NetworkUtils.getCacheEnabledRetrofit(getActivity().getApplicationContext())
                .create(RetrofitAPI.class);

        Call<MovieResponse> popularMoviesCall = retrofitAPI.getTrendingMovies(type, TMDB_API_TOKEN);
        popularMoviesCall.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse movieResponse = response.body();
                popularMovieList.clear();
                if (movieResponse != null) {
                    popularMovieList.addAll(movieResponse.getResults());
                    popularMoviesAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
            }
        });

        Call<TelevisionResponse> televisionResponseCall = retrofitAPI.getTrendingTelevision(type, TMDB_API_TOKEN);
        televisionResponseCall.enqueue(new Callback<TelevisionResponse>() {
            @Override
            public void onResponse(Call<TelevisionResponse> call, Response<TelevisionResponse> response) {
                TelevisionResponse televisionResponse = response.body();
                if (televisionResponse != null) {
                    televisionList.addAll(televisionResponse.getResults());
                    televisionAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<TelevisionResponse> call, Throwable t) {

            }
        });

        Call<PeopleResponse> peopleResponseCall = retrofitAPI.getTrendingPeople(type, TMDB_API_TOKEN);
        peopleResponseCall.enqueue(new Callback<PeopleResponse>() {
            @Override
            public void onResponse(Call<PeopleResponse> call, Response<PeopleResponse> response) {
                PeopleResponse peopleResponse = response.body();
                if (peopleResponse != null) {
                    celebrityList.addAll(peopleResponse.getResults());
                    celebrityAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<PeopleResponse> call, Throwable t) {

            }
        });
    }

}
