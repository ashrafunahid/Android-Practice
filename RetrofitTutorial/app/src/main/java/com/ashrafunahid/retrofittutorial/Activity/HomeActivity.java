package com.ashrafunahid.retrofittutorial.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.ashrafunahid.retrofittutorial.BottomNavFragments.DashboardFragment;
import com.ashrafunahid.retrofittutorial.BottomNavFragments.ProfileFragment;
import com.ashrafunahid.retrofittutorial.BottomNavFragments.UsersFragment;
import com.ashrafunahid.retrofittutorial.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        loadFragment(new DashboardFragment());

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;

        if (item.getItemId() == R.id.dashboard) {
            fragment = new DashboardFragment();
        } else if (item.getItemId() == R.id.users) {
            fragment = new UsersFragment();
        } else if (item.getItemId() == R.id.profile) {
            fragment = new ProfileFragment();
        }

        if (fragment != null) {
            loadFragment(fragment);
        }

        return true;
    }

    void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.relative_layout, fragment).commit();
    }
}