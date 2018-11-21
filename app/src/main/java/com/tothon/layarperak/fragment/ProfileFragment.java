package com.tothon.layarperak.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.squareup.picasso.Picasso;
import com.tothon.layarperak.R;
import com.tothon.layarperak.config.Configuration;
import com.tothon.layarperak.config.NetworkUtils;
import com.tothon.layarperak.model.Movie;
import com.tothon.layarperak.model.response.MovieResponse;
import com.tothon.layarperak.service.RetrofitAPI;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

//    private static final String TMDB_API_TOKEN = Configuration.TMDB_API_KEY;
//
//    @BindView(R.id.slider)
//    SliderLayout mDemoSlider;
//    @BindView(R.id.backdrop)
//    ImageView imageView;
//
//    private ArrayList<Movie> popularMovieList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        return view;
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        fetchMovies();
//    }
//
//    private void fetchMovies() {
//        RetrofitAPI retrofitAPI = NetworkUtils.getCacheEnabledRetrofit(getActivity().getApplicationContext())
//                .create(RetrofitAPI.class);
//
//        Call<MovieResponse> popularMoviesCall = retrofitAPI.getMovies("popular", TMDB_API_TOKEN, "en-US", 1);
//        popularMoviesCall.enqueue(new Callback<MovieResponse>() {
//            @Override
//            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
//                MovieResponse movieResponse = response.body();
//                popularMovieList.clear();
//                if (movieResponse != null) {
//                    popularMovieList.addAll(movieResponse.getResults());
//                    setupSlider(popularMovieList);
//                }
//            }
//            @Override
//            public void onFailure(Call<MovieResponse> call, Throwable t) {
//                Toast.makeText(getContext(), "Error getting popular", Toast.LENGTH_LONG).show();
//            }
//        });
//    }
//    private void setupSlider(final List<Movie> movieList) {
//        for (Movie movie: movieList) {
//            TextSliderView textSliderView = new TextSliderView(getContext());
//            textSliderView.image(movie.getBackdropPath()).setScaleType(BaseSliderView.ScaleType.CenterCrop);
//            mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
//            mDemoSlider.setCustomAnimation(new DescriptionAnimation());
//            mDemoSlider.setDuration(2000);
//            Picasso.with(getContext())
//                    .load(RetrofitAPI.BACKDROP_BASE_URL + movie.getBackdropPath())
//                    .centerCrop()
//                    .fit()
//                    .placeholder(R.drawable.tmdb_placeholder_land)
//                    .error(R.drawable.tmdb_placeholder)
//                    .into(imageView);
//        }
//    }

}
