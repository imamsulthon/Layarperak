package com.tothon.layarperak.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tothon.layarperak.R;
import com.tothon.layarperak.adapter.FullScreenImageAdapter;
import com.tothon.layarperak.model.Image;
import com.tothon.layarperak.service.DownloadImage;
import com.tothon.layarperak.service.RetrofitAPI;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageFullScreenActivity extends AppCompatActivity implements
        FullScreenImageAdapter.FullScreenImageLoader {

    public static final String KEY_IMAGES = "images";
    public static final String KEY_POSITION = "position";
    public static final String KEY_TITLE = "title";

    private String title;
    private ArrayList<Image> images;
    private int position;

    @BindView(R.id.viewpager) ViewPager viewPager;
    @BindView(R.id.toolbar) Toolbar toolbar;

    private final ViewPager.OnPageChangeListener viewPagerOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }
        @Override
        public void onPageSelected(int position) {
            if (viewPager != null) {
                viewPager.setCurrentItem(position);
                setActionToolbarSubtitle(position);
            }
        }
        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_full_screen);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                images = extras.getParcelableArrayList(KEY_IMAGES);
                position = extras.getInt(KEY_POSITION);
                title = extras.getString(KEY_TITLE);
            }
        }

        viewPager = findViewById(R.id.viewpager);
        toolbar = findViewById(R.id.toolbar);
        setupToolbar();

        setUpViewPager();
    }

    private void setupToolbar() {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void setActionToolbarSubtitle(int position) {
        if (viewPager != null && images.size() > 1) {
            int totalPages = viewPager.getAdapter().getCount();
            if (getSupportActionBar() != null) {
                toolbar.setSubtitle(String.format("(%d/%d)", position + 1, totalPages));
                this.position = position;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_fullscreen_image, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_download:
                Toast.makeText(this, "Downloading...", Toast.LENGTH_LONG).show();
                new DownloadImage(this, images.get(position).getFilePath(), title.trim());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeListeners();
    }

    private void setUpViewPager() {
        FullScreenImageAdapter fullScreenImageAdapter = new FullScreenImageAdapter(images);
        fullScreenImageAdapter.setFullScreenImageLoader(this);
        viewPager.setAdapter(fullScreenImageAdapter);
        viewPager.addOnPageChangeListener(viewPagerOnPageChangeListener);
        viewPager.setCurrentItem(position);
        setActionToolbarSubtitle(position);
    }

    private void removeListeners() {
        viewPager.removeOnPageChangeListener(viewPagerOnPageChangeListener);
    }

    @Override
    public void loadFullScreenImage(ImageView iv, String imageUrl, TextView textView, String description, int width, RelativeLayout layout) {
        Picasso.with(getApplicationContext())
                .load(RetrofitAPI.BACKDROP_BASE_URL_LARGE + imageUrl)
                .into(iv);
        if (description != null && !description.equals("")) {
            textView.setText(description);
        }
    }
}
