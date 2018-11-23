package com.tothon.layarperak.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tothon.layarperak.R;

public class ProfileFragment extends Fragment {

//    private static final String TMDB_API_TOKEN = Constants.TMDB_API_KEY;
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
//    public void onViewCreated(@NonNull View ic_view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(ic_view, savedInstanceState);
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
