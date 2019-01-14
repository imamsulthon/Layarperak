package com.tothon.layarperak.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tothon.layarperak.R;
import com.tothon.layarperak.config.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends AppCompatActivity {

    @BindView(R.id.layout_layarperak)
    LinearLayout layarperak;
    @BindView(R.id.layout_created_by)
    LinearLayout createdBy;
    @BindView(R.id.iv_instagram)
    ImageView instagram;
    @BindView(R.id.iv_twitter)
    ImageView twitter;
    @BindView(R.id.iv_github)
    ImageView github;
    @BindView(R.id.iv_linkedin)
    ImageView linkedin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

        layarperak.setOnClickListener(item -> {

        });

        createdBy.setOnClickListener(item -> {
            Uri uri = Uri.parse(Constants.ACCOUNT_GITHUB);
            Intent intentToGithub = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intentToGithub);
        });

        instagram.setOnClickListener(item -> {
            Uri uri = Uri.parse(Constants.ACCOUNT_INSTAGRAM);
            Intent intentToInstagram = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intentToInstagram);
        });

        twitter.setOnClickListener(item -> {
            Uri uri = Uri.parse(Constants.ACCOUNT_TWITTER);
            Intent intentToTwitter = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intentToTwitter);
        });

        github.setOnClickListener(item -> {
            Uri uri = Uri.parse(Constants.ACCOUNT_GITHUB);
            Intent intentToGithub = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intentToGithub);
        });

        linkedin.setOnClickListener(item -> {
            Uri uri = Uri.parse(Constants.ACCOUNT_LINKEDIN);
            Intent intentToLinkedind = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intentToLinkedind);
        });
    }
}
