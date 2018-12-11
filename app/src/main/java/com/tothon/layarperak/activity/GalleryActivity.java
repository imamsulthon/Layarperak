package com.tothon.layarperak.activity;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Window;
import android.view.WindowManager;

import com.tothon.layarperak.R;
import com.tothon.layarperak.adapter.GalleryAdapter;
import com.tothon.layarperak.model.Backdrop;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class GalleryActivity extends AppCompatActivity {

    public static final String KEY_TITLE = "title";
    public static final String KEY_IMAGES = "images";

    private String title;
    private ArrayList<Backdrop> images = new ArrayList<>();

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    GalleryAdapter adapter;
    private int numberOfColumn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);

        title = getIntent().getStringExtra(KEY_TITLE);
        images = getIntent().getParcelableArrayListExtra(KEY_IMAGES);

        recyclerView.setHasFixedSize(true);
        checkScreenSize();
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(
                numberOfColumn, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        adapter = new GalleryAdapter(getApplicationContext(), images, title);
        recyclerView.setAdapter(new ScaleInAnimationAdapter(adapter));

    }

    public void checkScreenSize() {
        int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;
        switch (screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                numberOfColumn = 4;
                break;
            case Configuration.SCREENLAYOUT_SIZE_UNDEFINED:
                numberOfColumn = 3;
                break;
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                numberOfColumn = 3;
                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                numberOfColumn = 2;
                break;
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                numberOfColumn = 2;
                break;
            default:
                numberOfColumn = 2;
        }
    }
}
