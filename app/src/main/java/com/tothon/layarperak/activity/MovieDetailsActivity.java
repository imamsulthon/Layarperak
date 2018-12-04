package com.tothon.layarperak.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tothon.layarperak.R;
import com.tothon.layarperak.adapter.CastRecyclerViewAdapter;
import com.tothon.layarperak.adapter.CrewRecyclerViewAdapter;
import com.tothon.layarperak.adapter.GenreRecyclerViewAdapter;
import com.tothon.layarperak.adapter.ImageRecyclerViewAdapter;
import com.tothon.layarperak.adapter.MovieRecyclerViewAdapter;
import com.tothon.layarperak.adapter.ReviewRecyclerViewAdapter;
import com.tothon.layarperak.adapter.TrailerRecyclerViewAdapter;
import com.tothon.layarperak.config.Constants;
import com.tothon.layarperak.config.Utils;
import com.tothon.layarperak.model.Person;
import com.tothon.layarperak.model.Trailer;
import com.tothon.layarperak.model.response.TrailerResponse;
import com.tothon.layarperak.service.NetworkUtils;
import com.tothon.layarperak.fragment.support.PosterDialogFragment;
import com.tothon.layarperak.model.Backdrop;
import com.tothon.layarperak.model.Cast;
import com.tothon.layarperak.model.Crew;
import com.tothon.layarperak.model.Genre;
import com.tothon.layarperak.model.Movie;
import com.tothon.layarperak.model.Review;
import com.tothon.layarperak.model.response.CreditResponse;
import com.tothon.layarperak.model.response.ImagesResponse;
import com.tothon.layarperak.model.response.MovieResponse;
import com.tothon.layarperak.model.response.ReviewsResponse;
import com.tothon.layarperak.service.RetrofitAPI;

import java.util.ArrayList;
import java.util.List;

import at.blogc.android.views.ExpandableTextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsActivity extends AppCompatActivity {

    private static final String TAG = MovieDetailsActivity.class.getSimpleName();
    public static final String KEY = "movie";
    private static final String TMDB_API_KEY = Constants.TMDB_API_KEY;

    // region Models
    Movie movie;
    private ArrayList<Genre> genreArrayList = new ArrayList<>();
    private ArrayList<Cast> castArrayList = new ArrayList<>();
    private ArrayList<Crew> crewArrayList = new ArrayList<>();
    private ArrayList<Trailer> trailerArrayList = new ArrayList<>();
    private ArrayList<Backdrop> imageArrayList = new ArrayList<>();
    private ArrayList<Review> reviewArrayList = new ArrayList<>();
    private ArrayList<Review> allReviews = new ArrayList<>();
    private ArrayList<Movie> similarMovieList = new ArrayList<>();

    private Person director = null;
    // endregion

    // region Views
    @BindView(R.id.backdrop) ImageView ivBackdrop;
    @BindView(R.id.iv_poster) ImageView ivPoster;
    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.tv_original_title) TextView tvOriginalTitle;
    @BindView(R.id.tv_realease_year) TextView tvRealeaseYear;
    @BindView(R.id.tv_runtime) TextView tvRuntime;
    @BindView(R.id.tv_imdb) TextView tvImdb;
    @BindView(R.id.layout_director) LinearLayout layoutDirector;
    @BindView(R.id.tv_director) TextView tvDirector;
    @BindView(R.id.tv_tagline) TextView tvTagline;
    @BindView(R.id.tv_overview) ExpandableTextView tvOverview;
    @BindView(R.id.tv_vote) TextView tvVote;
    @BindView(R.id.ratingBar) RatingBar ratingBar;
    @BindView(R.id.tv_rating) TextView tvRating;
    @BindView(R.id.tv_realease_status) TextView tvRealeaseStatus;
    @BindView(R.id.tv_realease_date) TextView tvRealeaseDate;
    @BindView(R.id.rv_genre) RecyclerView recyclerViewGenre;
    @BindView(R.id.rv_cast) RecyclerView recyclerViewCast;
    @BindView(R.id.rv_crew) RecyclerView recyclerViewCrew;
    @BindView(R.id.rv_trailers) RecyclerView recyclerViewTrailer;
    @BindView(R.id.rv_images) RecyclerView recyclerViewImage;
    @BindView(R.id.rv_reviews) RecyclerView recyclerViewReviews;
    @BindView(R.id.rv_similar_movies) RecyclerView recyclerViewSimilarMovies;
    @BindView(R.id.see_all_cast) TextView seeAllCast;
    @BindView(R.id.see_all_crew) TextView seeAllCrew;
    @BindView(R.id.see_all_reviews) TextView seeAllReviews;
    @BindView(R.id.iv_imdb) ImageView icImdb;
    @BindView(R.id.iv_google) ImageView icGoogle;
    @BindView(R.id.iv_homepage) ImageView icHomepage;
    //endregion

    // region Presenter
    private GenreRecyclerViewAdapter genreRecyclerViewAdapter;
    private MovieRecyclerViewAdapter similarMoviesAdapter;
    private CastRecyclerViewAdapter castRecyclerViewAdapter;
    private CrewRecyclerViewAdapter crewRecyclerViewAdapter;
    private TrailerRecyclerViewAdapter trailerRecyclerViewAdapter;
    private ImageRecyclerViewAdapter imageRecyclerViewAdapter;
    private ReviewRecyclerViewAdapter reviewRecyclerViewAdapter;
    // endregion

    Handler handler;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_movie_details2);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT > 21) {
            Slide slide = new Slide(Gravity.BOTTOM);
            getWindow().setEnterTransition(slide);
            postponeEnterTransition();
        }

        movie = getIntent().getParcelableExtra(KEY);

        recyclerViewGenre.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.HORIZONTAL, false));
        genreRecyclerViewAdapter = new GenreRecyclerViewAdapter(getApplicationContext(), genreArrayList);
        recyclerViewGenre.setAdapter(new ScaleInAnimationAdapter(genreRecyclerViewAdapter));

        recyclerViewCast.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.HORIZONTAL, false));
        castRecyclerViewAdapter = new CastRecyclerViewAdapter(getApplicationContext(), castArrayList);
        recyclerViewCast.setAdapter(new ScaleInAnimationAdapter(castRecyclerViewAdapter));

        recyclerViewCrew.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.HORIZONTAL, false));
        crewRecyclerViewAdapter = new CrewRecyclerViewAdapter(getApplicationContext(), crewArrayList);
        recyclerViewCrew.setAdapter(new ScaleInAnimationAdapter(crewRecyclerViewAdapter));

        recyclerViewTrailer.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.HORIZONTAL, false));
        trailerRecyclerViewAdapter = new TrailerRecyclerViewAdapter(this, trailerArrayList);
        recyclerViewTrailer.setAdapter(new ScaleInAnimationAdapter(trailerRecyclerViewAdapter));

        recyclerViewImage.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.HORIZONTAL, false));
        imageRecyclerViewAdapter = new ImageRecyclerViewAdapter(getApplicationContext(), imageArrayList);
        recyclerViewImage.setAdapter(new ScaleInAnimationAdapter(imageRecyclerViewAdapter));

        recyclerViewReviews.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false));
        reviewRecyclerViewAdapter = new ReviewRecyclerViewAdapter(getApplicationContext(), reviewArrayList);
        recyclerViewReviews.setAdapter(reviewRecyclerViewAdapter);

        recyclerViewSimilarMovies.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.HORIZONTAL, false));
        similarMoviesAdapter = new MovieRecyclerViewAdapter(getApplicationContext(), similarMovieList);
        recyclerViewSimilarMovies.setAdapter(new ScaleInAnimationAdapter(similarMoviesAdapter));

        if (movie.getPosterPath() != null) {
            Picasso.with(this)
                    .load(RetrofitAPI.POSTER_BASE_URL_SMALL + movie.getPosterPath())
                    .error(R.drawable.tmdb_placeholder)
                    .centerCrop()
                    .fit()
                    .into(ivPoster);
            ivPoster.setOnClickListener(item -> {
                PosterDialogFragment dialogFragment = new PosterDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putString(PosterDialogFragment.KEY, movie.getPosterPath());
                dialogFragment.setArguments(bundle);
                FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
                dialogFragment.show(fm, PosterDialogFragment.POSTER_DIALOG_TAG);
            });
        }

        if (movie.getBackdropPath() != null) {
            Picasso.with(this)
                    .load(RetrofitAPI.BACKDROP_BASE_URL_MEDIUM + movie.getBackdropPath())
                    .centerCrop()
                    .fit()
                    .into(ivBackdrop);
        }

        tvTitle.setText(movie.getTitle());
        if (movie.getOriginalTitle() != null && !movie.getOriginalTitle().equals("") &&
                !movie.getOriginalTitle().equals(movie.getTitle())) {
            tvOriginalTitle.setVisibility(View.VISIBLE);
            tvOriginalTitle.setText(movie.getOriginalTitle());
        } else {
            tvOriginalTitle.setVisibility(View.GONE);
        }
        tvRealeaseYear.setText(movie.getDate().substring(0, 4));
        tvRealeaseDate.setText(Utils.formatDate(movie.getDate()));
        tvOverview.setText(movie.getOverview());
        tvRating.setText(String.valueOf(movie.getRating()));
        ratingBar.setRating(movie.getRating().floatValue()/2);
        tvVote.setText(String.valueOf(movie.getVoteCount()));
        Typeface typeface = ResourcesCompat.getFont(getApplicationContext(), R.font.metrophobic);
        tvOverview.setTypeface(typeface);
        tvOverview.setOnClickListener(item -> {
            tvOverview.toggle();
        });

        fetchCredits();
        fetchMoreDetails();
        fetchReviews();
        fetchSimilarMovies();
        fetchMoviesImage();
        fetchTrailer();

        icGoogle.setOnClickListener(item -> {
            try {
                Uri uri = Uri.parse("https://www.google.com/search?q=" + movie.getTitle());
                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        tvDirector.setOnClickListener(item -> {
            if (director != null) {
                Intent intent = new Intent(MovieDetailsActivity.this, PersonDetailsActivity.class);
                intent.putExtra(PersonDetailsActivity.KEY, getDirector());
                startActivity(intent);
            } else {
                Toast.makeText(MovieDetailsActivity.this, "Director not found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchMoreDetails() {
        RetrofitAPI retrofitAPI = NetworkUtils.getCacheEnabledRetrofit(getApplicationContext()).create(RetrofitAPI.class);
        Call<Movie> movieCall = retrofitAPI.getDetails(movie.getId(), TMDB_API_KEY, "en-US");
        movieCall.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                movie = response.body();
                if (movie != null) {

                    if (movie.getTagline() != null && !movie.getTagline().equals("")) {
                        tvTagline.setVisibility(View.VISIBLE);
                        tvTagline.setText(movie.getTagline());
                    } else {
                        tvTagline.setVisibility(View.GONE);
                    }
                    tvRuntime.setText(formatMoviesRuntime(movie.getRuntime()));

                    if (movie.getGenres() != null && movie.getGenres().size() != 0) {
                        genreArrayList.clear();
                        genreArrayList.addAll(movie.getGenres());
                        genreRecyclerViewAdapter.notifyDataSetChanged();
                    } else {
                        (findViewById(R.id.genres_label_tv)).setVisibility(View.GONE);
                        recyclerViewGenre.setVisibility(View.GONE);
                    }

                    tvRealeaseStatus.setText(movie.getStatus());
                    if (movie.getImdbId() != null) {
                        tvImdb.setText("IMDB");
                    }

                    tvImdb.setOnClickListener(item -> {
                        String imdbId = String.valueOf(movie.getImdbId());
                        try {
                            Uri uri;
                            if (!imdbId.equals("")) {
                                uri = Uri.parse(Constants.IMDB_MOVIE_URL + imdbId + "/");
                            } else {
                                Toast.makeText(getApplicationContext(), "Movie isn't on IMDB. " +
                                        "Here is a Google search for it instead", Toast.LENGTH_LONG).show();
                                uri = Uri.parse("https://www.google.com/search?q=" + movie.getTitle());
                            }
                            Intent imdbIntent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(imdbIntent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });

                    icImdb.setOnClickListener(item -> {
                        if (movie.getImdbId() != null) {
                            try {
                                Uri uri = Uri.parse(Constants.IMDB_MOVIE_URL + movie.getImdbId() + "/");
                                startActivity(new Intent(Intent.ACTION_VIEW, uri));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "This movie isn't on IMDB",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

                    icHomepage.setOnClickListener(item -> {
                        if (movie.getHomepage() != null && !movie.getHomepage().equals("")) {
                            try {
                                Uri uri = Uri.parse(movie.getHomepage());
                                Intent openHomepage = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(openHomepage);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Homepage isn't available", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });
    }

    private void fetchCredits() {
        RetrofitAPI retrofitAPI = NetworkUtils.getCacheEnabledRetrofit(getApplicationContext()).create(RetrofitAPI.class);
        final Call<CreditResponse> call = retrofitAPI.getCredits(movie.getId(), TMDB_API_KEY);
        call.enqueue(new Callback<CreditResponse>() {
            @Override
            public void onResponse(Call<CreditResponse> call, Response<CreditResponse> response) {
                CreditResponse creditResponse = response.body();
                if (creditResponse != null && creditResponse.getCast().size() != 0) {
                    castArrayList.addAll(creditResponse.getCast());
                    castRecyclerViewAdapter.notifyDataSetChanged();
                    seeAllCast.setOnClickListener(item -> {
                        Intent intent = new Intent(MovieDetailsActivity.this, SeeAllCastActivity.class);
                        intent.putExtra(SeeAllCastActivity.TAG, castArrayList);
                        String subtitle = movie.getTitle() + " (" + movie.getDate().substring(0, 4) + ")";
                        intent.putExtra("subtitle", subtitle);
                        startActivity(intent);
                    });
                }
                if (creditResponse != null && creditResponse.getCrew().size() != 0) {
                    crewArrayList.addAll(creditResponse.getCrew());
                    crewRecyclerViewAdapter.notifyDataSetChanged();
                    setDirector(crewArrayList);
                    seeAllCrew.setOnClickListener(item -> {
                        Intent intent = new Intent(MovieDetailsActivity.this, SeeAllCrewActivity.class);
                        String subtitle = movie.getTitle() + " (" + movie.getDate().substring(0, 4) + ")";
                        intent.putExtra("subtitle", subtitle);
                        intent.putExtra(SeeAllCrewActivity.TAG, crewArrayList);
                        startActivity(intent);
                    });
                }
            }

            @Override
            public void onFailure(Call<CreditResponse> call, Throwable t) {

            }
        });
    }

    public void setDirector(ArrayList<Crew> crews) {
        for (Crew crew: crews) {
            if (crew.getJob().equals("Director") || crew.getJob().equals("director")) {
                layoutDirector.setVisibility(View.VISIBLE);
                tvDirector.setText(crew.getName());
                this.director = crew;
                break;
            }
        };
    }

    public Person getDirector() {
        return director;
    }

    private void fetchTrailer() {
        RetrofitAPI retrofitAPI = NetworkUtils.getCacheEnabledRetrofit(getApplicationContext()).create(RetrofitAPI.class);
        Call<TrailerResponse> call = retrofitAPI.getTrailers(KEY, movie.getId(), TMDB_API_KEY, "en-US");
        call.enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                TrailerResponse trailerResponse = response.body();
                if (trailerResponse != null && trailerResponse.getResults().size() != 0) {
                    trailerArrayList.addAll(trailerResponse.getResults());
                    trailerRecyclerViewAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<TrailerResponse> call, Throwable t) {

            }
        });
    }

    private void fetchReviews() {
        RetrofitAPI retrofitAPI = NetworkUtils.getCacheEnabledRetrofit(getApplicationContext()).create(RetrofitAPI.class);
        Call<ReviewsResponse> reviewsResponseCall = retrofitAPI.getReviews(KEY, movie.getId(), TMDB_API_KEY, "en-US");
        reviewsResponseCall.enqueue(new Callback<ReviewsResponse>() {
            @Override
            public void onResponse(Call<ReviewsResponse> call, Response<ReviewsResponse> response) {
                ReviewsResponse reviewsResponse = response.body();
                if (reviewsResponse != null && reviewsResponse.getResults().size() != 0) {
                    if (reviewsResponse.getResults().size() < 3) {
                        reviewArrayList.addAll(reviewsResponse.getResults());
                    } else {
                        for (int i = 0; i < 3; i++) {
                            reviewArrayList.add(reviewsResponse.getResults().get(i));
                        }
                    }
                    allReviews.addAll(reviewsResponse.getResults());
                    reviewRecyclerViewAdapter.notifyDataSetChanged();
                    seeAllReviews.setVisibility(View.VISIBLE);
                    seeAllReviews.setOnClickListener(item -> {
                        Intent intent = new Intent(MovieDetailsActivity.this, SeeAllReviewsActivity.class);
                        intent.putExtra(SeeAllReviewsActivity.REVIEW_TAG, allReviews);
                        String subtitle = movie.getTitle() + " (" + movie.getDate().substring(0,4) + ")";
                        intent.putExtra("subtitle", subtitle);
                        startActivity(intent);
                    });
                }
            }
            @Override
            public void onFailure(Call<ReviewsResponse> call, Throwable t) {

            }
        });
    }

    private void fetchMoviesImage() {
        RetrofitAPI retrofitAPI = NetworkUtils.getCacheEnabledRetrofit(getApplicationContext()).create(RetrofitAPI.class);
        Call<ImagesResponse> imagesResponseCall = retrofitAPI.getImages("movie", movie.getId(),
                TMDB_API_KEY);
        imagesResponseCall.enqueue(new Callback<ImagesResponse>() {
            @Override
            public void onResponse(Call<ImagesResponse> call, Response<ImagesResponse> response) {
                ImagesResponse imagesResponse = response.body();
                if (imagesResponse != null) {
                    List<Backdrop> backdrops = null;
                    try {
                        backdrops = response.body().getBackdrops();
                        if (backdrops.size() > 0) {
                            imageArrayList.addAll(backdrops);
                            imageRecyclerViewAdapter.notifyDataSetChanged();
                            changeBackdrop(backdrops);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<ImagesResponse> call, Throwable t) {

            }
        });
    }

    private void fetchSimilarMovies() {
        RetrofitAPI retrofitAPI = NetworkUtils.getCacheEnabledRetrofit(getApplicationContext()).create(RetrofitAPI.class);
        Call<MovieResponse> call = retrofitAPI.getSimilarMovies(movie.getId(), TMDB_API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse movieResponse = response.body();
                if (movieResponse != null) {
                    similarMovieList.addAll(movieResponse.getResults());
                    similarMoviesAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e(TAG, "Error getting Similiar Movies");
            }
        });
    }

    private String formatMoviesRuntime(int t) {
        String runtime = null;
        try{
            int hours = t / 60;
            int minutes = t % 60;
            if(hours != 0) {
                runtime = String.valueOf(hours) + " h " + String.valueOf(minutes) + " min";
            } else {
                runtime = String.valueOf(minutes) + " min";
            }
        }catch (Exception e){
            Log.i("POSTER_DIALOG_TAG :",e.toString());
        }
        return runtime;
    }

    private void changeBackdrop(List<Backdrop> imageArray) {
        handler = new Handler();
        runnable = new Runnable() {
            int i = 0;
            @Override
            public void run() {
                Picasso.with(getApplicationContext())
                        .load(RetrofitAPI.BACKDROP_BASE_URL_MEDIUM + imageArray.get(i).getFilePath())
                        .into(ivBackdrop, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {
                                supportStartPostponedEnterTransition();
                            }

                            @Override
                            public void onError() {
                                supportStartPostponedEnterTransition();
                            }
                        });
                Log.e(TAG, "change image");
                i++;
                if (i > imageArray.size() - 1) {
                    i = 0;
                }
                handler.postDelayed(this, 10000);
            }
        };
        handler.postDelayed(runnable, 5000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (runnable != null) {
            handler.removeCallbacksAndMessages(null);
            handler.removeCallbacks(runnable);
        }
    }
}