package com.tothon.layarperak.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tothon.layarperak.R;
import com.tothon.layarperak.adapter.ImageRecyclerViewAdapter;
import com.tothon.layarperak.adapter.MovieRecyclerViewAdapter;
import com.tothon.layarperak.config.Config;
import com.tothon.layarperak.config.Constants;
import com.tothon.layarperak.config.Utils;
import com.tothon.layarperak.fragment.support.PosterDialogFragment;
import com.tothon.layarperak.model.Backdrop;
import com.tothon.layarperak.model.Movie;
import com.tothon.layarperak.model.Person;
import com.tothon.layarperak.model.response.ImagesResponse;
import com.tothon.layarperak.model.response.PersonMoviesResponse;
import com.tothon.layarperak.service.NetworkUtils;
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

public class PersonDetailsActivity extends AppCompatActivity {

    private static final String TAG = MovieDetailsActivity.class.getSimpleName();
    public static final String KEY = "person";
    private static final String TMDB_API_KEY = Constants.TMDB_API_KEY;

    ArrayList<Backdrop> imageArrayList = new ArrayList<>();
    ArrayList<Movie> knownForMovies = new ArrayList<>();

    @BindView(R.id.iv_photo_profile) ImageView thumbnail;
    @BindView(R.id.tv_person_name) TextView tvPersonName;
    @BindView(R.id.tv_also_known_as) TextView tvOtherPersonName;
    @BindView(R.id.tv_department) TextView tvDepartment;
    @BindView(R.id.layout_born) LinearLayout layoutBorn;
    @BindView(R.id.tv_birthday) TextView tvbirthday;
    @BindView(R.id.tv_place_of_birth) TextView tvBirthPlace;
    @BindView(R.id.tv_biography) ExpandableTextView tvBiography;
    @BindView(R.id.tv_gender) TextView tvGender;
    @BindView(R.id.tv_popularity) TextView tvPopularity;
    @BindView(R.id.rv_filmography) RecyclerView recyclerViewFilmography;
    @BindView(R.id.rv_images) RecyclerView recyclerViewImages;
    @BindView(R.id.iv_imdb) ImageView icImdb;
    @BindView(R.id.iv_google) ImageView icGoogle;
    @BindView(R.id.iv_homepage) ImageView icHomepage;

    MovieRecyclerViewAdapter movieRecyclerViewAdapter;
    ImageRecyclerViewAdapter imageRecyclerViewAdapter;

    Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_person);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT > 21) {
            Slide slide = new Slide(Gravity.BOTTOM);
            getWindow().setEnterTransition(slide);
            postponeEnterTransition();
        }

        Config config = new Config(this);

        person = getIntent().getParcelableExtra(KEY);

        recyclerViewFilmography.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.HORIZONTAL, false));
        movieRecyclerViewAdapter = new MovieRecyclerViewAdapter(getApplicationContext(), knownForMovies);
        recyclerViewFilmography.setAdapter(new ScaleInAnimationAdapter(movieRecyclerViewAdapter));
        recyclerViewFilmography.setItemViewCacheSize(5);

        recyclerViewImages.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.HORIZONTAL, false));
        imageRecyclerViewAdapter = new ImageRecyclerViewAdapter(getApplicationContext(), imageArrayList);
        recyclerViewImages.setAdapter(new ScaleInAnimationAdapter(imageRecyclerViewAdapter));

        tvPersonName.setText(person.getName());
        if (person.getProfilePath() != null) {
            Picasso.with(this)
                    .load(RetrofitAPI.POSTER_BASE_URL_MEDIUM + person.getProfilePath())
                    .error(R.drawable.tmdb_placeholder)
                    .centerCrop()
                    .fit().into(thumbnail);
            thumbnail.setOnClickListener(item -> {
                PosterDialogFragment dialogFragment = new PosterDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putString(PosterDialogFragment.KEY, person.getProfilePath());
                dialogFragment.setArguments(bundle);
                FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
                dialogFragment.show(fm, PosterDialogFragment.POSTER_DIALOG_TAG);
            });
        }
        tvPopularity.setText(String.valueOf(person.getPopularity()));
        tvBiography.setTypeface(config.getTypeface());
        icGoogle.setOnClickListener(item -> {
            try {
                Uri uri = Uri.parse("https://www.google.com/search?q=" + person.getName());
                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        getPersonDetails();
        fetchPersonsImages();
        fetchPersonMovies();
    }

    private void getPersonDetails() {
        RetrofitAPI retrofitAPI = NetworkUtils.getCacheEnabledRetrofit(getApplicationContext()).create(RetrofitAPI.class);
        Call<Person> call = retrofitAPI.getPersonDetails(person.getId(), TMDB_API_KEY);
        call.enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                person = response.body();
                if (person != null) {
                    if (person.getBirthday() != null) {
                        layoutBorn.setVisibility(View.VISIBLE);
                        tvbirthday.setText(Utils.formatDate(person.getBirthday()));
                    }
                    tvBirthPlace.setText(person.getPlaceOfBirth());

                    tvDepartment.setText(person.getDepartment());
                    tvGender.setText(Utils.convertGenderString(person.getGender()));

                    if (person.getBiography() != null && !person.getBiography().equals("")) {
                        tvBiography.setVisibility(View.VISIBLE);
                        tvBiography.setText(person.getBiography());
                        tvBiography.setOnClickListener(item -> {
                            tvBiography.toggle();
                        });
                    } else {
                        tvBiography.setVisibility(View.GONE);
                    }

                    icImdb.setOnClickListener(item -> {
                        if (person.getImdbId() != null) {
                            try {
                                Uri uri = Uri.parse(Constants.IMDB_PERSON_URL + person.getImdbId() + "/");
                                startActivity(new Intent(Intent.ACTION_VIEW, uri));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "This person isn't on IMDB",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                    icHomepage.setOnClickListener(item -> {
                        if (person.getHomepage() != null && !person.getHomepage().equals("")) {
                            try {
                                Uri uri = Uri.parse(person.getHomepage());
                                Intent openHomepage = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(openHomepage);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Homepage of this person isn't available",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Person> call, Throwable t) {

            }
        });
    }

    private void fetchPersonMovies() {
        RetrofitAPI retrofitAPI = NetworkUtils.getCacheEnabledRetrofit(getApplicationContext()).create(RetrofitAPI.class);
        Call<PersonMoviesResponse> call = retrofitAPI.getPersonCredits(person.getId(), TMDB_API_KEY);
        call.enqueue(new Callback<PersonMoviesResponse>() {
           @Override
           public void onResponse(Call<PersonMoviesResponse> call, Response<PersonMoviesResponse> response) {
               PersonMoviesResponse personMoviesResponse = response.body();
               ArrayList<Movie> moviesAsCast = personMoviesResponse.getMoviesAsCast();
               if (moviesAsCast != null) {
                   if (moviesAsCast.size() < 8) {
                       knownForMovies.addAll(moviesAsCast);
                   } else {
                       for (int i = 0; i < 8; i++) {
                           knownForMovies.add(moviesAsCast.get(i));
                       }
                   }
                   movieRecyclerViewAdapter.notifyDataSetChanged();
               }
           }
           @Override
           public void onFailure(Call<PersonMoviesResponse> call, Throwable t) {
           }
       });
    }

    private void fetchPersonsImages() {
        RetrofitAPI retrofitAPI = NetworkUtils.getCacheEnabledRetrofit(getApplicationContext()).create(RetrofitAPI.class);
        Call<ImagesResponse> imagesResponseCall = retrofitAPI.getImages("person", person.getId(),
                TMDB_API_KEY);
        imagesResponseCall.enqueue(new Callback<ImagesResponse>() {
            @Override
            public void onResponse(Call<ImagesResponse> call, Response<ImagesResponse> response) {
                ImagesResponse imagesResponse = response.body();
                if (imagesResponse != null) {
                    List<Backdrop> backdrops = null;
                    try {
                        backdrops = response.body().getProfiles();
                        if (backdrops.size() > 0) {
                            if (backdrops.size() < 5) {
                                imageArrayList.addAll(backdrops);
                            } else {
                                for (int i = 0; i < 5; i++) {
                                    imageArrayList.add(backdrops.get(i));
                                }
                            }
                        }
                        imageRecyclerViewAdapter.notifyDataSetChanged();
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

}
