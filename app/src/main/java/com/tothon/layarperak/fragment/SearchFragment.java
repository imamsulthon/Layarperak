package com.tothon.layarperak.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tothon.layarperak.R;
import com.tothon.layarperak.adapter.SearchAdapter;
import com.tothon.layarperak.config.Constants;
import com.tothon.layarperak.model.SearchResult;
import com.tothon.layarperak.model.response.SearchResponse;
import com.tothon.layarperak.service.ApiClient;
import com.tothon.layarperak.service.RetrofitAPI;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    private static final String API_KEY = Constants.TMDB_API_KEY;

    ArrayList<SearchResult> searchResults = new ArrayList<>();

    @BindView(R.id.edit_query) EditText editTextQuery;
    @BindView(R.id.recyclerview_search) RecyclerView recyclerView;
    @BindView(R.id.layout_search) LinearLayout blankLayout;
    @BindView(R.id.layout_offline) LinearLayout offlineLayout;
    @BindView(R.id.clear_search) ImageView btnClear;

    Context context;
    SearchAdapter searchAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);
        context = this.getActivity();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        searchAdapter = new SearchAdapter(context, searchResults);
        recyclerView.setAdapter(searchAdapter);

        editTextQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                recyclerView.setVisibility(View.VISIBLE);
                blankLayout.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (! isNetworkAvailable(context)) {
                    offlineLayout.setVisibility(View.VISIBLE);
                } else {
                    offlineLayout.setVisibility(View.GONE);
                    getResult(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String query = editTextQuery.getText().toString().trim();
                if (query.isEmpty() || query.length() == 0 || query.equals("") || query == null) {
                    recyclerView.setVisibility(View.GONE);
                    blankLayout.setVisibility(View.VISIBLE);
                    btnClear.setVisibility(View.GONE);
                } else {
                    btnClear.setVisibility(View.VISIBLE);
                }
            }
        });

        editTextQuery.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                editTextQuery.setHint("");
                btnClear.setVisibility(View.VISIBLE);
            }
        });

        btnClear.setOnClickListener(item -> {
            editTextQuery.setText("");
        });

        hideKeyboard();
    }

    private void getResult(CharSequence charSequence) {
        String query = charSequence.toString();
        RetrofitAPI retrofitAPI = ApiClient.getCacheEnabledRetrofit(getActivity()).create(RetrofitAPI.class);
        Call<SearchResponse> searchResponseCall = retrofitAPI.getSearchResult("multi", query, API_KEY);
        searchResponseCall.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                SearchResponse searchResponse = response.body();
                try {
                    if (searchResponse != null) {
                        searchResults.clear();
                        searchResults = searchResponse.getResults();
                        recyclerView.setLayoutManager(new LinearLayoutManager(context,
                                LinearLayoutManager.VERTICAL, false));
                        recyclerView.setAdapter(new SearchAdapter(context, searchResults));
                    }
                } catch (Exception e) {
                    Log.e("exception: ", e.toString());
                }
            }
            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                Log.e("exception: ", t.toString());
            }
        });
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if ((activeNetworkInfo != null) && (activeNetworkInfo.isConnected())) {
            return true;
        } else {
            return false;
        }
    }

    private void hideKeyboard() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                View view = getActivity().getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });
    }
}
