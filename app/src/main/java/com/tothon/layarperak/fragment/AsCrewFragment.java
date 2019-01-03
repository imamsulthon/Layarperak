package com.tothon.layarperak.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.tothon.layarperak.R;
import com.tothon.layarperak.adapter.ViewPagerAdapter;
import com.tothon.layarperak.model.Movie;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AsCrewFragment extends Fragment {

    private static final String TAG = "tag";

    // region Model
    ArrayList<Movie> allMovies = new ArrayList<>();
    // endregion

    // region View
    @BindView(R.id.layout_empty_data)
    RelativeLayout layoutEmpty;
    @BindView(R.id.layout_content)
    LinearLayout layoutContent;
    @BindView(R.id.tablayout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    // endregion

    public AsCrewFragment newInstance(ArrayList<Movie> movies) {
        AsCrewFragment fragment = new AsCrewFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(TAG, movies);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            allMovies = getArguments().getParcelableArrayList(TAG);
        }
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_as_crew, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (allMovies.size() != 0) {
            layoutEmpty.setVisibility(View.GONE);
            layoutContent.setVisibility(View.VISIBLE);
            setupViewPager(viewPager);
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        List<String> titleList = getAllDepartmenNamet(allMovies);
        for (int i = 0; i < titleList.size(); i++) {
            String deptName = titleList.get(i);
            ArrayList<Movie> movieList = addMoviesInDepartment(deptName, allMovies);
            String tabTitle = deptName + " (" + movieList.size() + ")";
            adapter.addFragment(new AsCrewFragmentChild().newInstance(movieList), tabTitle);
        }
        viewPager.setAdapter(adapter);
    }

    private static List<String> getAllDepartmenNamet(ArrayList<Movie> movies) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < movies.size(); i++) {
            String department = movies.get(i).getDepartment();
            if (i != 0) {
                boolean isSimilar = false;
                for (int j = 0; j < result.size(); j++) {
                    if (department.equals(result.get(j))) {
                        isSimilar = true;
                        break;
                    }
                }
                if (!isSimilar) {
                    result.add(department);
                }
            } else {
                result.add(department);
            }
        }
        return result;
    }

    private static ArrayList<Movie> addMoviesInDepartment(String department, List<Movie> movies) {
        ArrayList<Movie> result = new ArrayList<>();
        for (int i = 0; i < movies.size(); i++) {
            if (movies.get(i).getDepartment().equals(department)) {
                result.add(movies.get(i));
            }
        }
        return result;
    }
}
