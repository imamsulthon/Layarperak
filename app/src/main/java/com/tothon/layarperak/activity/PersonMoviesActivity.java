package com.tothon.layarperak.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.tothon.layarperak.R;
import com.tothon.layarperak.adapter.ViewPagerAdapter;
import com.tothon.layarperak.config.Constants;
import com.tothon.layarperak.fragment.AsCastFragment;
import com.tothon.layarperak.fragment.AsCrewFragment;
import com.tothon.layarperak.model.Movie;
import com.tothon.layarperak.model.MovieGroupByCrew;
import com.tothon.layarperak.model.Person;
import com.tothon.layarperak.model.response.PersonMoviesResponse;
import com.tothon.layarperak.service.ApiClient;
import com.tothon.layarperak.service.RetrofitAPI;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonMoviesActivity extends AppCompatActivity {

    public static final String KEY_PERSON = "person";
    private static final String TMDB_API_KEY = Constants.TMDB_API_KEY;

    ArrayList<Movie> moviesAsCast = new ArrayList<>();
    ArrayList<MovieGroupByCrew> moviesAsCrew = new ArrayList<>();
    Person person;

    @BindView(R.id.progressBar_layout)
    FrameLayout loading;
    @BindView(R.id.layout_content)
    LinearLayout contentLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.mainTabs)
    TabLayout tabLayout;
    @BindView(R.id.container)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_person_movies);
        ButterKnife.bind(this);

        if (getIntent() != null) {
            person = getIntent().getParcelableExtra(KEY_PERSON);
        }
        getMovies(person.getId());
        setupToolbar();
    }

    private void setupViewpager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AsCastFragment().newInstance(moviesAsCast), "Cast" + " ("+
                moviesAsCast.size() + ")");
        adapter.addFragment(new AsCrewFragment().newInstance(moviesAsCrew), "Crew" + " ("+
                moviesAsCrew.size() + ")");
        viewPager.setAdapter(adapter);
    }

    private void getMovies(int personId) {
        RetrofitAPI retrofitAPI = ApiClient.getCacheEnabledRetrofit(getApplicationContext()).create(RetrofitAPI.class);
        Call<PersonMoviesResponse> call = retrofitAPI.getPersonCredits(personId, TMDB_API_KEY);
        call.enqueue(new Callback<PersonMoviesResponse>() {
            @Override
            public void onResponse(Call<PersonMoviesResponse> call, Response<PersonMoviesResponse> response) {
                loading.setVisibility(View.GONE);
                PersonMoviesResponse personMoviesResponse = response.body();
                if (personMoviesResponse != null) {
                    contentLayout.setVisibility(View.VISIBLE);
                    if (moviesAsCrew != null || moviesAsCast != null) {
                        moviesAsCast = personMoviesResponse.getMoviesAsCast();
                        moviesAsCrew = personMoviesResponse.getMoviesGroupAsCrews();
                        setupViewpager(viewPager);
                        tabLayout.setupWithViewPager(viewPager);
                    }
                }
            }
            @Override
            public void onFailure(Call<PersonMoviesResponse> call, Throwable t) {

            }
        });
    }

    private void setupToolbar() {
        toolbar.setTitle(person.getName());
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
