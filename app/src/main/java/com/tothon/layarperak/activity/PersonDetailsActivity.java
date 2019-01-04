package com.tothon.layarperak.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tothon.layarperak.R;
import com.tothon.layarperak.adapter.ImageRecyclerViewAdapter;
import com.tothon.layarperak.adapter.MovieRecyclerViewAdapter;
import com.tothon.layarperak.config.Config;
import com.tothon.layarperak.config.Constants;
import com.tothon.layarperak.config.Utils;
import com.tothon.layarperak.data.PersonDataSource;
import com.tothon.layarperak.fragment.support.PosterDialogFragment;
import com.tothon.layarperak.model.Image;
import com.tothon.layarperak.model.Movie;
import com.tothon.layarperak.model.Person;
import com.tothon.layarperak.model.response.ImagesResponse;
import com.tothon.layarperak.model.response.PersonMoviesResponse;
import com.tothon.layarperak.model.response.TaggedImageResponse;
import com.tothon.layarperak.service.ApiClient;
import com.tothon.layarperak.service.RetrofitAPI;

import org.aviran.cookiebar2.CookieBar;

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

    // region Model
    Person person, tempPerson;
    ArrayList<Image> imageArrayList = new ArrayList<>();
    ArrayList<Image> allImages = new ArrayList<>();
    ArrayList<Movie> knownForMovies = new ArrayList<>();

    private PersonDataSource dataSource;
    // endregion

    // region View
    @BindView(R.id.backdrop) ImageView ivBackdrop;
    @BindView(R.id.tv_backdrop_desc) LinearLayout backdropDesc;
    @BindView(R.id.tv_backdrop_title) TextView backdropTitle;
    @BindView(R.id.tv_backdrop_year) TextView backdropYear;
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
    @BindView(R.id.layout_filmography) LinearLayout layoutFilmography;
    @BindView(R.id.layout_images) LinearLayout layoutImages;
    @BindView(R.id.see_all_filmography) TextView seeAllFilmography;
    @BindView(R.id.see_all_images) TextView seeAllImages;
    @BindView(R.id.fav_button) FloatingActionButton fabFavorite;
    @BindView(R.id.iv_imdb) ImageView icImdb;
    @BindView(R.id.iv_google) ImageView icGoogle;
    @BindView(R.id.iv_homepage) ImageView icHomepage;
    // endregion

    // region Presenter
    MovieRecyclerViewAdapter movieRecyclerViewAdapter;
    ImageRecyclerViewAdapter imageRecyclerViewAdapter;
    // endregion

    Handler handler;
    Runnable runnable;

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
        dataSource = new PersonDataSource(this);
        dataSource.open();

        favButtonInit(person.getId());

        recyclerViewFilmography.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.HORIZONTAL, false));
        movieRecyclerViewAdapter = new MovieRecyclerViewAdapter(getApplicationContext(), knownForMovies);
        recyclerViewFilmography.setAdapter(new ScaleInAnimationAdapter(movieRecyclerViewAdapter));
        recyclerViewFilmography.setItemViewCacheSize(5);

        recyclerViewImages.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.HORIZONTAL, false));
        imageRecyclerViewAdapter = new ImageRecyclerViewAdapter(getApplicationContext(), imageArrayList, person.getName());
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
                dialogFragment.show(fm, PosterDialogFragment.TAG);
            });
        }
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
        fetchPersonsMovies();

    }

    private boolean checkedPerson(int id) {
        boolean isChecked = false;
        ArrayList<Person> personArrayList = dataSource.getAllFavoritePerson();
        if (personArrayList != null && personArrayList.size() > 0) {
            for (Person p: personArrayList) {
                if (p.getId() == id) {
                    isChecked = true;
                    break;
                }
            }
        }
        return isChecked;
    }

    private void favButtonInit(int id) {
        boolean isChecked = false;
        ArrayList<Person> personArrayList = dataSource.getAllFavoritePerson();
        if (personArrayList != null && personArrayList.size() > 0) {
            for (Person p: personArrayList) {
                if (p.getId() == id) {
                    isChecked = true;
                    break;
                }
            }
            if (isChecked) {
                fabFavorite.setImageResource(R.drawable.ic_favorite);
            } else {
                fabFavorite.setImageResource(R.drawable.ic_favorite_border);
            }
        }
        fabFavorite.setOnClickListener(view -> {
            if (checkedPerson(id)) {
                dataSource.removePersonFromFavorite(person);
                fabFavorite.setImageResource(R.drawable.ic_favorite_border);
                CookieBar.build(PersonDetailsActivity.this)
                        .setBackgroundColor(android.R.color.holo_red_dark)
                        .setTitle(person.getName())
                        .setMessage("This person has been removed from your favorites")
                        .show();
            } else {
                tempPerson = person;
                dataSource.addFavoritePerson(tempPerson);
                fabFavorite.setImageResource(R.drawable.ic_favorite);
                CookieBar.build(PersonDetailsActivity.this)
                        .setBackgroundColor(R.color.colorAccentGreen)
                        .setTitle(person.getName())
                        .setMessage("This person has been add to your favorite")
                        .show();
            }
        });
    }

    private void getPersonDetails() {
        RetrofitAPI retrofitAPI = ApiClient.getCacheEnabledRetrofit(getApplicationContext()).create(RetrofitAPI.class);
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

                    tvPopularity.setText(String.valueOf(person.getPopularity()));
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

    private void fetchPersonsMovies() {
        RetrofitAPI retrofitAPI = ApiClient.getCacheEnabledRetrofit(getApplicationContext()).create(RetrofitAPI.class);
        Call<PersonMoviesResponse> call = retrofitAPI.getPersonCredits(person.getId(), TMDB_API_KEY);
        call.enqueue(new Callback<PersonMoviesResponse>() {
           @Override
           public void onResponse(Call<PersonMoviesResponse> call, Response<PersonMoviesResponse> response) {
               PersonMoviesResponse personMoviesResponse = response.body();
               ArrayList<Movie> moviesAsCast = personMoviesResponse.getMoviesAsCast();
               ArrayList<Movie> moviesAsCrew = personMoviesResponse.getMoviesAsCrew();
               if (moviesAsCast.size() != 0 || moviesAsCrew.size() != 0) {
                   if (moviesAsCast.size() != 0) {
                       if (moviesAsCast.size() < 8) {
                           knownForMovies.addAll(moviesAsCast);
                       } else {
                           for (int i = 0; i < 8; i++) {
                               knownForMovies.add(moviesAsCast.get(i));
                           }
                       }
                       movieRecyclerViewAdapter.notifyDataSetChanged();
                   } else if (moviesAsCrew.size() != 0) {
                       if (moviesAsCrew.size() < 8) {
                           knownForMovies.addAll(moviesAsCrew);
                       } else {
                           for (int i = 0; i < 8; i++) {
                               knownForMovies.add(moviesAsCrew.get(i));
                           }
                       }
                       movieRecyclerViewAdapter.notifyDataSetChanged();
                   }
                   seeAllFilmography.setOnClickListener(item -> {
                       Intent intent = new Intent(PersonDetailsActivity.this, PersonMoviesActivity.class);
                       intent.putExtra(PersonMoviesActivity.KEY_PERSON, person);
                       startActivity(intent);
                   });
               } else {
                   layoutFilmography.setVisibility(View.GONE);
               }
           }
           @Override
           public void onFailure(Call<PersonMoviesResponse> call, Throwable t) {
           }
       });
    }

    private void fetchPersonsImages() {
        RetrofitAPI retrofitAPI = ApiClient.getCacheEnabledRetrofit(getApplicationContext()).create(RetrofitAPI.class);

        Call<ImagesResponse> imagesResponseCall = retrofitAPI.getImages("person", person.getId(),
                TMDB_API_KEY);
        imagesResponseCall.enqueue(new Callback<ImagesResponse>() {
            @Override
            public void onResponse(Call<ImagesResponse> call, Response<ImagesResponse> response) {
                ImagesResponse imagesResponse = response.body();
                if (imagesResponse != null && imagesResponse.getProfiles().size() != 0) {
                    List<Image> mainImages = null;
                    try {
                        mainImages = response.body().getProfiles();
                        if (mainImages.size() > 0) {
                            if (mainImages.size() < 5) {
                                imageArrayList.addAll(mainImages);
                            } else {
                                for (int i = 0; i < 5; i++) {
                                    imageArrayList.add(mainImages.get(i));
                                }
                            }
                            imageRecyclerViewAdapter.notifyDataSetChanged();
                            allImages.addAll(mainImages);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    layoutImages.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<ImagesResponse> call, Throwable t) {
            }
        });

        Call<TaggedImageResponse> taggedImageResponseCall = retrofitAPI.getTaggedImage("person",
                person.getId(), TMDB_API_KEY);
        taggedImageResponseCall.enqueue(new Callback<TaggedImageResponse>() {
            @Override
            public void onResponse(Call<TaggedImageResponse> call, Response<TaggedImageResponse> response) {
                TaggedImageResponse taggedImageResponse = response.body();
                if (taggedImageResponse != null) {
                    List<Image> taggedImages = null;
                    try {
                        taggedImages = taggedImageResponse.getResults();
                        if (taggedImages.size() > 0) {
                            changeBackdrop(taggedImages);
                            allImages.addAll(taggedImages);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<TaggedImageResponse> call, Throwable t) {
            }
        });

        seeAllImages.setOnClickListener(item -> {
            Intent intent = new Intent(PersonDetailsActivity.this, GalleryActivity.class);
            intent.putExtra(GalleryActivity.KEY_TITLE, person.getName());
            intent.putExtra(GalleryActivity.KEY_IMAGES, allImages);
            startActivity(intent);
        });
    }

    private void changeBackdrop(List<Image> imageArray) {
        handler = new Handler();
        runnable = new Runnable() {
            int i = 0;
            @Override
            public void run() {
                Image image = imageArray.get(i);
                if (image.getMedia().getTitle() != null && ((image.getMedia().getDate() != null) || (image.getMedia().getFirstAirdate() != null))) {
                    backdropDesc.setVisibility(View.VISIBLE);
                    backdropTitle.setText(image.getMedia().getTitle());
                    if (image.getMediaType().equals("movie")) {
                        if (image.getMedia().getDate() != null) {
                            backdropYear.setText(image.getMedia().getDate().substring(0, 4));
                        }
                    } else if (image.getMediaType().equals("tv")) {
                        if (image.getMedia().getFirstAirdate() != null) {
                            backdropYear.setText(image.getMedia().getFirstAirdate().substring(0, 4));
                        }
                    }
                } else {
                    backdropDesc.setVisibility(View.GONE);
                }

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataSource.close();
    }
}
