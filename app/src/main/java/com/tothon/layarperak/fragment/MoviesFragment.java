package com.tothon.layarperak.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tothon.layarperak.R;
import com.tothon.layarperak.activity.SeeMoreMoviesActivity;
import com.tothon.layarperak.adapter.GenreAdapterGrid;
import com.tothon.layarperak.adapter.MovieRecyclerViewAdapter;
import com.tothon.layarperak.config.Constants;
import com.tothon.layarperak.model.Genre;
import com.tothon.layarperak.model.Movie;
import com.tothon.layarperak.model.response.GenreResponse;
import com.tothon.layarperak.model.response.MovieResponse;
import com.tothon.layarperak.service.ApiClient;
import com.tothon.layarperak.service.RetrofitAPI;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesFragment extends Fragment {

    private static final String API_KEY = Constants.TMDB_API_KEY;

    ///region Model
    private ArrayList<Movie> popularMovieList = new ArrayList<>();
    private ArrayList<Movie> topRatedMovieList = new ArrayList<>();
    private ArrayList<Movie> upcomingMovieList = new ArrayList<>();
    private ArrayList<Movie> nowPlayingMovieList = new ArrayList<>();
    private ArrayList<Genre> genreArrayList = new ArrayList<>();
    //endregion

    //region View
    @BindView(R.id.loading_indicator) ProgressBar progressBar;
    @BindView(R.id.rv_popular) RecyclerView recyclerViewPopular;
    @BindView(R.id.rv_toprated) RecyclerView recyclerViewTopRated;
    @BindView(R.id.rv_upcoming) RecyclerView recyclerViewUpcoming;
    @BindView(R.id.rv_nowplaying) RecyclerView recyclerViewNowPlaying;
    @BindView(R.id.rv_genres) RecyclerView recyclerViewGenres;
    @BindView(R.id.see_more_popular) TextView morePopular;
    @BindView(R.id.see_more_now_playing) TextView moreNowPlaying;
    @BindView(R.id.see_more_top_rated) TextView moreTopRated;
    @BindView(R.id.see_more_upcoming) TextView moreUpcoming;
    //endregion

    //region Presenter
    private MovieRecyclerViewAdapter popularMoviesAdapter;
    private MovieRecyclerViewAdapter topRatedMoviesAdapter;
    private MovieRecyclerViewAdapter upcomingMoviesAdapter;
    private MovieRecyclerViewAdapter nowPlayingMoviesAdapter;
    private GenreAdapterGrid genreAdapter;
    //endregion

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
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
        topRatedMoviesAdapter = new MovieRecyclerViewAdapter(getActivity(), topRatedMovieList);
        recyclerViewTopRated.setAdapter(new ScaleInAnimationAdapter(topRatedMoviesAdapter));

        recyclerViewUpcoming.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager
                .HORIZONTAL, false));
        upcomingMoviesAdapter = new MovieRecyclerViewAdapter(getActivity(), upcomingMovieList);
        recyclerViewUpcoming.setAdapter(new ScaleInAnimationAdapter(upcomingMoviesAdapter));

        recyclerViewNowPlaying.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager
                .HORIZONTAL, false));
        nowPlayingMoviesAdapter = new MovieRecyclerViewAdapter(getActivity(), nowPlayingMovieList);
        recyclerViewNowPlaying.setAdapter(new ScaleInAnimationAdapter(nowPlayingMoviesAdapter));

        recyclerViewGenres.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        genreAdapter = new GenreAdapterGrid(getActivity(), "movie", genreArrayList);
        recyclerViewGenres.setAdapter(genreAdapter);

        fetchAllMovies();
        getAllGenre();

        moreNowPlaying.setOnClickListener(item -> {
            seeMoreMovieList("now_playing");
        });

        morePopular.setOnClickListener(item -> {
            seeMoreMovieList("popular");
        });

        moreTopRated.setOnClickListener(item -> {
            seeMoreMovieList("top_rated");
        });

        moreUpcoming.setOnClickListener(item -> {
            seeMoreMovieList("upcoming");
        });

    }

    private void fetchAllMovies() {
        RetrofitAPI retrofitAPI = ApiClient.getCacheEnabledRetrofit(getActivity().getApplicationContext())
                .create(RetrofitAPI.class);

        Call<MovieResponse> popularMoviesCall = retrofitAPI.getMovies("popular", API_KEY, "en-US", 1);
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

        Call<MovieResponse> topRatedMoviesCall = retrofitAPI.getMovies("top_rated", API_KEY, "en-US", 1);
        topRatedMoviesCall.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse movieResponse = response.body();
                topRatedMovieList.clear();
                if (movieResponse != null) {
                    topRatedMovieList.addAll(movieResponse.getResults());
                    topRatedMoviesAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
            }
        });

        Call<MovieResponse> upcomingMoviesCall = retrofitAPI.getMovies("upcoming", API_KEY, "en-US", 1);
        upcomingMoviesCall.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse movieResponse = response.body();
                upcomingMovieList.clear();
                if (movieResponse != null) {
                    upcomingMovieList.addAll(movieResponse.getResults());
                    upcomingMoviesAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
            }
        });

        Call<MovieResponse> nowPlayingMoviesCall = retrofitAPI.getMovies("now_playing", API_KEY, "en-US", 1);
        nowPlayingMoviesCall.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse movieResponse = response.body();
                nowPlayingMovieList.clear();
                if (movieResponse != null) {
                    nowPlayingMovieList.addAll(movieResponse.getResults());
                    nowPlayingMoviesAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
            }
        });
    }

    private void getAllGenre() {
        RetrofitAPI retrofitAPI = ApiClient.getCacheEnabledRetrofit(getActivity().getApplicationContext())
                .create(RetrofitAPI.class);
        Call<GenreResponse> responseCall = retrofitAPI.getGenres("movie", API_KEY);
        responseCall.enqueue(new Callback<GenreResponse>() {
            @Override
            public void onResponse(Call<GenreResponse> call, Response<GenreResponse> response) {
                GenreResponse genreResponse = response.body();
                genreArrayList.clear();
                if (genreResponse != null) {
                    genreArrayList.addAll(genreResponse.getGenres());
                    genreAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<GenreResponse> call, Throwable t) {
            }
        });
    }

    private void seeMoreMovieList(String type) {
        Intent intent = new Intent(getActivity(), SeeMoreMoviesActivity.class);
        intent.putExtra(SeeMoreMoviesActivity.MOVIE_TAG, type);
        startActivity(intent);
    }

}
