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
import com.tothon.layarperak.adapter.TelevisionAdapter;
import com.tothon.layarperak.model.Television;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class FavoriteItemFragmentTv extends Fragment {

    private static final String TAG = "tag";

    // region Model
    ArrayList<Television> favTelevision = new ArrayList<>();
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
    TelevisionAdapter adapter;
    // endregion

    public FavoriteItemFragmentTv newInstance(ArrayList<Television> televisions) {
        FavoriteItemFragmentTv fragment = new FavoriteItemFragmentTv();
        Bundle args = new Bundle();
        args.putParcelableArrayList(TAG, televisions);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            favTelevision = getArguments().getParcelableArrayList(TAG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_tv, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (favTelevision.size() != 0) {
            layoutEmpty.setVisibility(View.GONE);
            layoutContent.setVisibility(View.VISIBLE);
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
            adapter = new TelevisionAdapter(getActivity(), favTelevision);
            recyclerView.setAdapter(new ScaleInAnimationAdapter(adapter));
        }
    }
}
