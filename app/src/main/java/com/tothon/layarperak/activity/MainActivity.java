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
import com.tothon.layarperak.fragment.WatchlistFragment;
import com.tothon.layarperak.fragment.FavoriteFragment;
import com.tothon.layarperak.fragment.HomeFragment;
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
    HomeFragment homeFragment;
    SearchFragment searchFragment;
    FavoriteFragment favoriteFragment;
    WatchlistFragment watchlistFragment;
    ProfileFragment profileFragment;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        selectedFragment = new HomeFragment();
        homeFragment = new HomeFragment();
        searchFragment = new SearchFragment();
        favoriteFragment = new FavoriteFragment();
        watchlistFragment = new WatchlistFragment();
        profileFragment = new ProfileFragment();

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
                    selectedFragment = homeFragment;
                    break;
                case R.id.menu_search:
                    selectedFragment = searchFragment;
                    break;
                case R.id.menu_favorites:
                    selectedFragment = favoriteFragment;
                    break;
                case R.id.menu_watchlist:
                    selectedFragment = watchlistFragment;
                    break;
                case R.id.menu_profile:
                    selectedFragment = profileFragment;
                    break;
                default:
                    selectedFragment = homeFragment;
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
