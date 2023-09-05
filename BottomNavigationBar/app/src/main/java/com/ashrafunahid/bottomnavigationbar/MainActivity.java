package com.ashrafunahid.bottomnavigationbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.ashrafunahid.bottomnavigationbar.Fragments.ChatFragment;
import com.ashrafunahid.bottomnavigationbar.Fragments.HomeFragment;
import com.ashrafunahid.bottomnavigationbar.Fragments.NotificationFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        loadFragment(new HomeFragment());

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;

        if(item.getItemId() == R.id.home) {
            fragment = new HomeFragment();
        } else if (item.getItemId() == R.id.chat) {
            fragment = new ChatFragment();
        } else if (item.getItemId() == R.id.notification) {
            fragment = new NotificationFragment();
        }

        if (fragment != null) {
            loadFragment(fragment);
        }

        return false;
    }

    void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, fragment).commit();
    }
}