package com.ashrafunahid.weatherupdate.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.ViewGroup;

import com.ashrafunahid.weatherupdate.Adapters.HourlyAdapter;
import com.ashrafunahid.weatherupdate.Domains.Hourly;
import com.ashrafunahid.weatherupdate.R;
import com.ashrafunahid.weatherupdate.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapterHourly;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        initRecyclerView();
    }

    private void initRecyclerView() {

        ArrayList<Hourly> items = new ArrayList<>();

        items.add(new Hourly("9 AM", 28, "cloudy"));
        items.add(new Hourly("10 AM", 29, "sunny"));
        items.add(new Hourly("11 AM", 30, "wind"));
        items.add(new Hourly("12 PM", 31, "rainy"));
        items.add(new Hourly("1 PM", 30, "storm"));

        recyclerView = findViewById(R.id.next_seven_day_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapterHourly = new HourlyAdapter(items, getApplicationContext());
        recyclerView.setAdapter(adapterHourly);
    }
}