package com.tothon.layarperak.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.tothon.layarperak.R;
import com.tothon.layarperak.service.RetrofitAPI;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PosterDialogFragment extends DialogFragment {

    public static final String KEY = "imagePath";
    public static String POSTER_DIALOG_TAG = "POSTER_FRAGMENT";
    private static String IMAGE_URL;

    @BindView(R.id.iv_poster)
    ImageView ivPoster;

    ScaleGestureDetector scaleGestureDetector;
    private float scaleFactor = 1.0f;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_poster_view, container, false);
        ButterKnife.bind(this, rootView);

        IMAGE_URL = getArguments().getString(KEY);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (IMAGE_URL != null && !IMAGE_URL.equals("")) {
            loadPoster();
        }
    }

    private void loadPoster() {
        Picasso.with(getContext())
                .load(RetrofitAPI.POSTER_BASE_URL_MEDIUM + IMAGE_URL)
                .error(R.drawable.tmdb_placeholder)
                .into(ivPoster);
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scaleFactor *= scaleGestureDetector.getScaleFactor();
            ivPoster.setScaleX(scaleFactor);
            ivPoster.setScaleY(scaleFactor);
            return true;
        }
    }
}
