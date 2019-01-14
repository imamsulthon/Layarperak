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
import com.tothon.layarperak.data.WatchlistDataSource;
import com.tothon.layarperak.fragment.ItemMovieFragment;
import com.tothon.layarperak.fragment.ItemTelevisionFragment;
import com.tothon.layarperak.model.Movie;
import com.tothon.layarperak.model.Television;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WatchlistActivity extends AppCompatActivity {

    // region Model
    private WatchlistDataSource dataSource;
    private ArrayList<Movie> movieArrayList = new ArrayList<>();
    private ArrayList<Television> televisionArrayList = new ArrayList<>();
    // endregion

    // region View
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
    // endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_watchlist);
        ButterKnife.bind(this);

        dataSource = new WatchlistDataSource();
        dataSource.open();

        getFavorite();
        setupToolbar();
    }


    private void setupViewpager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ItemMovieFragment().newInstance(movieArrayList), "Movie"
                + " ("+ movieArrayList.size() + ")");
        adapter.addFragment(new ItemTelevisionFragment().newInstance(televisionArrayList), "Television"
                + " (" + televisionArrayList.size() + ")");
        viewPager.setAdapter(adapter);
    }

    private void getFavorite() {
        ArrayList<Movie> movies = dataSource.getAllWatchlistMovies();
        ArrayList<Television> televisions = dataSource.getAllWatchlistTelevisions();
        movieArrayList.clear();
        televisionArrayList.clear();
        if (movies.size() > 0 || televisions.size() > 0) {
            loading.setVisibility(View.GONE);
            contentLayout.setVisibility(View.VISIBLE);
            movieArrayList = movies;
            televisionArrayList = televisions;
            setupViewpager(viewPager);
            tabLayout.setupWithViewPager(viewPager);
        } else {
            loading.setVisibility(View.VISIBLE);
            contentLayout.setVisibility(View.GONE);
        }
    }

    private void setupToolbar() {
        toolbar.setTitle("Watchlist");
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataSource.close();
    }
}
