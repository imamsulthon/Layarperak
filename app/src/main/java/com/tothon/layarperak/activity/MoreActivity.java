package com.tothon.layarperak.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.tothon.layarperak.R;
import com.tothon.layarperak.adapter.MovieRecyclerViewAdapter;
import com.tothon.layarperak.config.Configuration;
import com.tothon.layarperak.config.NetworkUtils;
import com.tothon.layarperak.model.Movie;
import com.tothon.layarperak.model.response.MovieResponse;
import com.tothon.layarperak.service.RetrofitAPI;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoreActivity extends AppCompatActivity {

    public static final String MOVIE_TAG = "type";
    private static final String TMDB_API_KEY = Configuration.TMDB_API_KEY;
    private static String type;

    MovieRecyclerViewAdapter adapter;

    private ArrayList<Movie> movies = new ArrayList<>();
    private ArrayList<Movie> moviesNextPage = new ArrayList<>();

    private LinearLayoutManager layoutManager;
    private boolean userScrolled = true;
    int pageIndex, pastVisiblesItems, visibleItemCount, totalItemCount;

    Handler handler;
    Runnable runnable;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.layout_loading_bar)
    RelativeLayout loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_more);
        ButterKnife.bind(this);

        type = getIntent().getStringExtra(MOVIE_TAG);

        layoutManager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MovieRecyclerViewAdapter(getApplicationContext(), movies);
        recyclerView.setAdapter(new ScaleInAnimationAdapter(adapter));
        pageIndex = 1;

        if (runnable != null) {
            handler.removeCallbacks(runnable);
        }

        getMovieList(type);
        pagination();
    }

    private void getMovieList(String type) {
        RetrofitAPI retrofitAPI = NetworkUtils.getCacheEnabledRetrofit(getApplicationContext()).create(RetrofitAPI.class);
        Call<MovieResponse> movieResponseCall = null;
        movieResponseCall = retrofitAPI.getMovies(type, TMDB_API_KEY, "en-US", pageIndex);
        movieResponseCall.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse movieResponse = response.body();
                if (movieResponse != null) {
                    pageIndex++;
                    if (pageIndex >= 2) {
                        moviesNextPage = movieResponse.getResults();
                        movies.addAll(moviesNextPage);
                        adapter.notifyDataSetChanged();
                        recyclerView.scrollToPosition(pastVisiblesItems);
                    } else {
                        movies = movieResponse.getResults();
                        adapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error getting " + type, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void pagination() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    userScrolled = true;
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = layoutManager.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                if (userScrolled && (visibleItemCount + pastVisiblesItems) == totalItemCount) {
                    userScrolled = false;
                    updateRecylerView();
                }
            }
        });
    }

    private void updateRecylerView() {
        loadingBar.setVisibility(View.VISIBLE);
        handler = new Handler();
        runnable = () -> {
            getMovieList(type);
            loadingBar.setVisibility(View.GONE);
        };
        handler.postDelayed(runnable, 2000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (runnable != null) {
            handler.removeCallbacksAndMessages(runnable);
        }
    }
}
