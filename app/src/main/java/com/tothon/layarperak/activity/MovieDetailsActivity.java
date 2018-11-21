package com.tothon.layarperak.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tothon.layarperak.R;
import com.tothon.layarperak.model.Movie;
import com.tothon.layarperak.service.RetrofitAPI;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailsActivity extends AppCompatActivity {

    public static final String KEY = "movie";

    @BindView(R.id.backdrop)
    ImageView ivBackdrop;
    @BindView(R.id.iv_poster)
    ImageView ivPoster;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_realease_year)
    TextView tvRealeaseYear;
    @BindView(R.id.tv_tagline)
    TextView tvTagline;

    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_movie_details2);
        ButterKnife.bind(this);

        movie = getIntent().getParcelableExtra(KEY);

        if (movie.getPosterBytes() != null) {
            Picasso.with(this)
                    .load(movie.getPosterPath())
                    .centerCrop()
                    .fit()
                    .error(R.drawable.tmdb_placeholder)
                    .into(ivPoster);
        } else {
            Picasso.with(this)
                    .load(RetrofitAPI.POSTER_BASE_URL + movie.getPosterPath())
                    .error(R.drawable.tmdb_placeholder)
                    .centerCrop()
                    .fit()
                    .into(ivPoster);
        }

        if (movie.getBackdropPath() != null) {
            Picasso.with(this)
                    .load(RetrofitAPI.BACKDROP_BASE_URL + movie.getBackdropPath())
                    .centerCrop()
                    .fit()
                    .into(ivBackdrop);
        }

        tvTitle.setText(movie.getTitle());
        tvRealeaseYear.setText(movie.getDate().substring(0, 4));
        tvTagline.setText(movie.getPlot());
    }
}
