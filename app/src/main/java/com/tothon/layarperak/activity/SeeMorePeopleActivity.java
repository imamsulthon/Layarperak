package com.tothon.layarperak.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.tothon.layarperak.R;
import com.tothon.layarperak.adapter.PersonAdapter;
import com.tothon.layarperak.config.Constants;
import com.tothon.layarperak.model.Person;
import com.tothon.layarperak.model.response.PeopleResponse;
import com.tothon.layarperak.service.ApiClient;
import com.tothon.layarperak.service.RetrofitAPI;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeeMorePeopleActivity extends AppCompatActivity {

    public static final String TAG = "key";
    private static final String TMDB_API_KEY = Constants.TMDB_API_KEY;
    private String type;

    PersonAdapter adapter;

    private ArrayList<Person> peoples = new ArrayList<>();
    private ArrayList<Person> peoplesNextPage = new ArrayList<>();

    private LinearLayoutManager layoutManager;
    private boolean userScrolled = true;
    int pageIndex, pastVisiblesItems, visibleItemCount, totalItemCount;

    Handler handler;
    Runnable runnable;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.layout_loading_bar)
    RelativeLayout loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_see_more_people);
        ButterKnife.bind(this);

        type = getIntent().getStringExtra(TAG);

        layoutManager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PersonAdapter(getApplicationContext(), peoples);
        recyclerView.setAdapter(new ScaleInAnimationAdapter(adapter));
        pageIndex = 1;

        if (runnable != null) {
            handler.removeCallbacks(runnable);
        }

        getPersonList(type);
        pagination();
    }

    private void getPersonList(String type) {

        RetrofitAPI retrofitAPI = ApiClient.getCacheEnabledRetrofit(getApplicationContext()).create(RetrofitAPI.class);
        switch (type) {
            case "trending":
                Call<PeopleResponse> peopleResponseCall = retrofitAPI.getTrendingPeople(type, TMDB_API_KEY);
                peopleResponseCall.enqueue(new Callback<PeopleResponse>() {
                    @Override
                    public void onResponse(Call<PeopleResponse> call, Response<PeopleResponse> response) {
                        PeopleResponse peopleResponse = response.body();
                        if (peopleResponse != null) {
                            peoples.addAll(peopleResponse.getResults());
                            adapter.notifyDataSetChanged();
                        }
                    }
                    @Override
                    public void onFailure(Call<PeopleResponse> call, Throwable t) {
                    }
                });
                break;
            case "popular":
                Call<PeopleResponse> personResponseCall = retrofitAPI.getPopularPerson(TMDB_API_KEY, pageIndex);
                personResponseCall.enqueue(new Callback<PeopleResponse>() {
                    @Override
                    public void onResponse(Call<PeopleResponse> call, Response<PeopleResponse> response) {
                        PeopleResponse peopleResponse = response.body();
                        if (peopleResponse != null) {
                            pageIndex++;
                            if (pageIndex >= 2) {
                                peoplesNextPage = peopleResponse.getResults();
                                peoples.addAll(peoplesNextPage);
                                adapter.notifyDataSetChanged();
                                recyclerView.scrollToPosition(pastVisiblesItems);
                            } else {
                                peoples = peopleResponse.getResults();
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<PeopleResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Error getting people list" , Toast.LENGTH_LONG).show();
                    }
                });
                break;
        }
    }

    private void pagination() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    userScrolled = true;
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = layoutManager.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                if (userScrolled && (visibleItemCount + pastVisiblesItems) == totalItemCount) {
                    userScrolled = false;
                    updateRecylerView();
                }
            }
        });
    }

    private void updateRecylerView() {
        loadingBar.setVisibility(View.VISIBLE);
        handler = new Handler();
        runnable = () -> {
            getPersonList(type);
            loadingBar.setVisibility(View.GONE);
        };
        handler.postDelayed(runnable, 2000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (runnable != null) {
            handler.removeCallbacksAndMessages(runnable);
        }
    }
}
