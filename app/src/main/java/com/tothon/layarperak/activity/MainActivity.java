package com.tothon.layarperak.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.tothon.layarperak.R;
import com.tothon.layarperak.fragment.MoviesFragment;
import com.tothon.layarperak.fragment.TelevisionFragment;
import com.tothon.layarperak.fragment.TrendingFragment;
import com.tothon.layarperak.fragment.ProfileFragment;
import com.tothon.layarperak.fragment.SearchFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.frame_container)
    FrameLayout frameLayout;

    Fragment selectedFragment;
    FragmentManager fragmentManager;

    TrendingFragment trendingFragment;
    MoviesFragment moviesFragment;
    TelevisionFragment televisionFragment;
    SearchFragment searchFragment;
    ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        trendingFragment = new TrendingFragment();
        moviesFragment = new MoviesFragment();
        televisionFragment = new TelevisionFragment();
        searchFragment = new SearchFragment();
        profileFragment = new ProfileFragment();
        selectedFragment = trendingFragment;

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, selectedFragment).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_home:
                    selectedFragment = trendingFragment;
                    break;
                case R.id.menu_movies:
                    selectedFragment = moviesFragment;
                    break;
                case R.id.menu_television:
                    selectedFragment = televisionFragment;
                    break;
                case R.id.menu_search:
                    selectedFragment = searchFragment;
                    break;
                case R.id.menu_profile:
                    selectedFragment = profileFragment;
                    break;
                default:
                    selectedFragment = trendingFragment;
                    break;
            }
            return loadFragment(selectedFragment);
        }
        private boolean loadFragment(Fragment fragment) {
            if (fragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_container, fragment)
                        .commit();
                return true;
            }
            return false;
        }
    };
}
