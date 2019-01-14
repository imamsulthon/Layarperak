package com.tothon.layarperak.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.tothon.layarperak.R;
import com.tothon.layarperak.adapter.MovieRecyclerViewAdapter;
import com.tothon.layarperak.model.Movie;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class AsCastFragment extends Fragment {

    private static final String TAG = "tag";

    // region Model
    ArrayList<Movie> allMovies = new ArrayList<>();
    // endregion

    // region View
    @BindView(R.id.layout_empty_data)
    RelativeLayout layoutEmpty;
    @BindView(R.id.layout_content)
    LinearLayout layoutContent;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    // endregion

    // region Presenter
    MovieRecyclerViewAdapter adapter;
    // endregion

    public AsCastFragment newInstance(ArrayList<Movie> movieArrayList) {
        AsCastFragment fragment = new AsCastFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(TAG, movieArrayList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            allMovies = getArguments().getParcelableArrayList(TAG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_movie, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (allMovies.size() != 0) {
            layoutEmpty.setVisibility(View.GONE);
            layoutContent.setVisibility(View.VISIBLE);
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
            adapter = new MovieRecyclerViewAdapter(getActivity(), allMovies);
            recyclerView.setAdapter(new ScaleInAnimationAdapter(adapter));
        }
    }
}
