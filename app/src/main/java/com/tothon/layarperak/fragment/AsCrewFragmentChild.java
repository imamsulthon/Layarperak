package com.tothon.layarperak.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.tothon.layarperak.R;
import com.tothon.layarperak.adapter.MovieRecyclerViewGridAdapter;
import com.tothon.layarperak.adapter.MovieRecyclerViewLandAdapter;
import com.tothon.layarperak.model.Movie;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class AsCrewFragmentChild extends Fragment {

    private static final String TAG = "params";

    // region Model
    ArrayList<Movie> movieArrayList = new ArrayList<>();
    // endregion

    // region View
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.fab_recyclerview_type)
    FloatingActionButton fab;
    // endregion

    // region Presenter
    MovieRecyclerViewLandAdapter linearAdapter;
    MovieRecyclerViewGridAdapter gridAdapter;

    int recyclerviewType = 0;
    // endregion

    public AsCrewFragmentChild newInstance(ArrayList<Movie> movies) {
        AsCrewFragmentChild fragment = new AsCrewFragmentChild();
        Bundle args = new Bundle();
        args.putParcelableArrayList(TAG, movies);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movieArrayList = getArguments().getParcelableArrayList(TAG);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.child_layout_content, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerViewType(recyclerviewType);
        fab.setOnClickListener(item -> {
            if (recyclerviewType == 0) {
                recyclerviewType = 1;
            } else {
                recyclerviewType = 0;
            }
            initRecyclerViewType(recyclerviewType);
        });
    }

    public void initRecyclerViewType(int type) {
        switch (type) {
            case 0:
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));
                linearAdapter = new MovieRecyclerViewLandAdapter(getActivity(), movieArrayList);
                recyclerView.setAdapter(new ScaleInAnimationAdapter(linearAdapter));
                fab.setImageResource(R.drawable.ic_view_grid_white);
                break;
            case 1:
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
                gridAdapter = new MovieRecyclerViewGridAdapter(getActivity(), movieArrayList);
                recyclerView.setAdapter(new ScaleInAnimationAdapter(gridAdapter));
                fab.setImageResource(R.drawable.ic_view_list_white);
                break;
        }
    }
}
