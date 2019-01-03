package com.tothon.layarperak.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tothon.layarperak.R;
import com.tothon.layarperak.activity.AboutActivity;
import com.tothon.layarperak.activity.FavoriteActivity;
import com.tothon.layarperak.activity.SettingsActivity;
import com.tothon.layarperak.adapter.MovieRecyclerViewAdapter;
import com.tothon.layarperak.data.MovieDataSource;
import com.tothon.layarperak.model.Movie;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    // region Model
    private MovieDataSource dataSource;
    private ArrayList<Movie> movieArrayList = new ArrayList<>();
    // endregion

    // region View
    @BindView(R.id.layout_layarperak)
    LinearLayout layarperak;
    @BindView(R.id.layout_recent_activity)
    LinearLayout layoutRecentAcitivity;
    @BindView(R.id.rv_favorite_movies)
    RecyclerView rvFavoriteMovie;
    @BindView(R.id.layout_favorite)
    LinearLayout menuFavorite;
    @BindView(R.id.layout_watchlist)
    LinearLayout menuWatchlist;
    @BindView(R.id.layout_settings)
    LinearLayout menuSettings;
    @BindView(R.id.layout_about)
    LinearLayout menuAbout;
    // endregion

    // region Presenter
    private MovieRecyclerViewAdapter moviesAdapter;
    // endregion

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataSource = new MovieDataSource();
        dataSource.open();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvFavoriteMovie.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false));
        moviesAdapter = new MovieRecyclerViewAdapter(getActivity(), movieArrayList);
        rvFavoriteMovie.setAdapter(new ScaleInAnimationAdapter(moviesAdapter));

        layarperak.setOnClickListener(this);
        menuFavorite.setOnClickListener(this);
        menuWatchlist.setOnClickListener(this);
        menuSettings.setOnClickListener(this);
        menuAbout.setOnClickListener(this);

        fetchFavorite();
    }

    private void fetchFavorite() {
        movieArrayList.clear();
        ArrayList<Movie> movies = dataSource.getAllFavoriteMovies();
        if (movies.size() > 0) {
            layoutRecentAcitivity.setVisibility(View.VISIBLE);
            if (movies.size() <= 8) {
                movieArrayList.addAll(movies);
            } else {
                for (int i = 0; i < 8; i++) {
                    movieArrayList.add(movies.get(i));
                }
            }
            moviesAdapter.notifyDataSetChanged();
        } else {
            layoutRecentAcitivity.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_layarperak:
                Intent intentLayarperak = new Intent(getActivity(), AboutActivity.class);
                startActivity(intentLayarperak);
                break;
            case R.id.layout_favorite:
                Intent intentTofavorite = new Intent(getActivity(), FavoriteActivity.class);
                startActivity(intentTofavorite);
                break;
            case R.id.layout_watchlist:
                Toast.makeText(getActivity(), "This feature under developing", Toast.LENGTH_SHORT).show();
                break;
            case R.id.layout_settings:
                Intent intentSettings = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intentSettings);
                break;
            case R.id.layout_about:
                Intent intentAbout = new Intent(getActivity(), AboutActivity.class);
                startActivity(intentAbout);
                break;
            default:
        }
    }
}
