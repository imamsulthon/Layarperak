package com.tothon.layarperak.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;

import com.tothon.layarperak.R;
import com.tothon.layarperak.adapter.ReviewRecyclerViewAdapter;
import com.tothon.layarperak.model.Movie;
import com.tothon.layarperak.model.Review;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class SeeAllReviewsActivity extends AppCompatActivity {

    public static final String REVIEW_TAG = "review";

    private ArrayList<Review> reviews = new ArrayList<>();
    private Movie movie;
    private String subTitle;

    @BindView(R.id.recyclerview)
    RecyclerView reviewRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    ReviewRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_see_all_reviews);
        ButterKnife.bind(this);

        reviews = getIntent().getParcelableArrayListExtra(REVIEW_TAG);
        subTitle = getIntent().getStringExtra("subtitle");

        setupToolbar();

        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false));
        adapter = new ReviewRecyclerViewAdapter(getApplicationContext(), reviews);
        reviewRecyclerView.setAdapter(new ScaleInAnimationAdapter(adapter));

    }
    private void setupToolbar() {
        toolbar.setTitle("All Reviews");
        toolbar.setSubtitle(subTitle);
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
