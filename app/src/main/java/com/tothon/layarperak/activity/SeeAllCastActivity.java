package com.tothon.layarperak.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.tothon.layarperak.R;
import com.tothon.layarperak.adapter.FullCastListAdapter;
import com.tothon.layarperak.model.Cast;
import com.tothon.layarperak.model.Movie;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class SeeAllCastActivity extends AppCompatActivity {

    public static final String TAG = SeeAllCastActivity.class.getSimpleName();

    private ArrayList<Cast> casts = new ArrayList<>();

    private String subTitle;

    @BindView(R.id.recyclerview)
    RecyclerView castRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    FullCastListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_see_all_cast);
        ButterKnife.bind(this);

        casts = getIntent().getParcelableArrayListExtra(TAG);
        subTitle = getIntent().getStringExtra("subtitle");

        setupToolbar();

        castRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false));
        castRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
                DividerItemDecoration.VERTICAL));
        adapter = new FullCastListAdapter(getApplicationContext(), casts);
        castRecyclerView.setAdapter(new ScaleInAnimationAdapter(adapter));
    }

    private void setupToolbar() {
        toolbar.setTitle("All Cast");
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
