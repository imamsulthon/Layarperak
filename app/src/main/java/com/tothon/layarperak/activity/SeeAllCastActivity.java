package com.tothon.layarperak.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tothon.layarperak.R;
import com.tothon.layarperak.adapter.FullCastListAdapter;
import com.tothon.layarperak.model.Cast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class SeeAllCastActivity extends AppCompatActivity {

    public static final String CAST_TAG = "cast";

    private ArrayList<Cast> casts = new ArrayList<>();

    @BindView(R.id.recyclerview)
    RecyclerView castRecyclerView;

    FullCastListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all_cast);
        ButterKnife.bind(this);

        casts = getIntent().getParcelableArrayListExtra(CAST_TAG);

        castRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false));
        castRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
                DividerItemDecoration.VERTICAL));
        adapter = new FullCastListAdapter(getApplicationContext(), casts);
        castRecyclerView.setAdapter(new ScaleInAnimationAdapter(adapter));
    }
}
