package com.tothon.layarperak.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.tothon.layarperak.R;
import com.tothon.layarperak.adapter.MovieRecyclerViewAdapter;
import com.tothon.layarperak.adapter.TelevisionAdapter;
import com.tothon.layarperak.config.Constants;
import com.tothon.layarperak.model.Genre;
import com.tothon.layarperak.model.Movie;
import com.tothon.layarperak.model.Television;
import com.tothon.layarperak.model.response.MovieResponse;
import com.tothon.layarperak.model.response.TelevisionResponse;
import com.tothon.layarperak.service.ApiClient;
import com.tothon.layarperak.service.RetrofitAPI;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieGridActivity extends AppCompatActivity {

    public static final String GENRE_TAG = "tag";
    public static final String GENRE_TYPE = "type";
    private static final String TMDB_API_KEY = Constants.TMDB_API_KEY;
    private static String type;
    private static String genreId;

    Genre genre;
    MovieRecyclerViewAdapter adapter;
    TelevisionAdapter televisionAdapter;

    private ArrayList<Movie> movies = new ArrayList<>();
    private ArrayList<Movie> moviesNextPage = new ArrayList<>();

    private ArrayList<Television> televisions = new ArrayList<>();
    private ArrayList<Television> televisionNextPage = new ArrayList<>();

    private LinearLayoutManager layoutManager;
    private boolean userScrolled = true;
    int pageIndex, pastVisiblesItems, visibleItemCount, totalItemCount;

    Handler handler;
    Runnable runnable;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.layout_loading_bar)
    RelativeLayout loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_movie_grid);
        ButterKnife.bind(this);

        type = getIntent().getStringExtra(GENRE_TYPE);
        genre = getIntent().getParcelableExtra(GENRE_TAG);
        genreId = String.valueOf(genre.getId());

        setupToolbar(type);

        layoutManager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(layoutManager);
        switch (type) {
            case "movie":
                adapter = new MovieRecyclerViewAdapter(getApplicationContext(), movies);
                recyclerView.setAdapter(new ScaleInAnimationAdapter(adapter));
                break;
            case "tv":
                televisionAdapter = new TelevisionAdapter(getApplicationContext(), televisions);
                recyclerView.setAdapter(new ScaleInAnimationAdapter(televisionAdapter));
                break;
        }
        pageIndex = 1;

        if (runnable != null) {
            handler.removeCallbacks(runnable);
        }

        getDataList(type, genreId);
        pagination();
    }

    private void getDataList(String type, String category) {
        RetrofitAPI retrofitAPI = ApiClient.getCacheEnabledRetrofit(getApplicationContext()).create(RetrofitAPI.class);
        switch (type) {
            case "movie":
                Call<MovieResponse> movieResponseCall = null;
                movieResponseCall = retrofitAPI.getMoviesByGenre(category, TMDB_API_KEY, "en-US", pageIndex);
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
                        Toast.makeText(getApplicationContext(), "Error getting " + category, Toast.LENGTH_LONG).show();
                    }
                });
                break;
            case "tv":
                Call<TelevisionResponse> televisionResponseCall = null;
                televisionResponseCall = retrofitAPI.getTelevisionsByGenre(category, TMDB_API_KEY, "en-US", pageIndex);
                televisionResponseCall.enqueue(new Callback<TelevisionResponse>() {
                    @Override
                    public void onResponse(Call<TelevisionResponse> call, Response<TelevisionResponse> response) {
                        TelevisionResponse televisionResponse = response.body();
                        if (televisionResponse != null) {
                            pageIndex++;
                            if (pageIndex >= 2) {
                                televisionNextPage = televisionResponse.getResults();
                                televisions.addAll(televisionNextPage);
                                televisionAdapter.notifyDataSetChanged();
                                recyclerView.scrollToPosition(pastVisiblesItems);
                            } else {
                                televisions = televisionResponse.getResults();
                                televisionAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<TelevisionResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Error getting " + category, Toast.LENGTH_LONG).show();
                    }
                });
        }
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
            getDataList(type, genreId);
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

    private void setupToolbar(String type) {
        if (type.equals("movie")) {
            toolbar.setTitle("Movies");
        } else if (type.equals("tv")) {
            toolbar.setTitle("Televisions");
        }
        toolbar.setSubtitle(genre.getName());
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
