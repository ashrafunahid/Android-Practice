package com.ashrafunahid.customlistview;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ashrafunahid.customlistview.Adapters.CountryAdapter;
import com.ashrafunahid.customlistview.Models.CountryModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<CountryModel> countries;
    CountryAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listView = findViewById(R.id.list_view);

        countries = new ArrayList<>();
//        countries.add(new CountryModel("Brazil", "5 Cup Achievement", R.drawable.brazil));
        countries.add(new CountryModel("France", "3 Cup Achievement", R.drawable.france));
        countries.add(new CountryModel("Germany", "2 Cup Achievement", R.drawable.germany));
        countries.add(new CountryModel("Saudi Arabia", "2 Cup Achievement", R.drawable.saudiarabia));
        countries.add(new CountryModel("Spain", "4 Cup Achievement", R.drawable.spain));
        countries.add(new CountryModel("United Kingdom", "1 Cup Achievement", R.drawable.unitedkingdom));
//        countries.add(new CountryModel("United States", "2 Cup Achievement", R.drawable.unitedstates));
//        countries.add(new CountryModel("Brazil", "5 Cup Achievement", R.drawable.brazil));
        countries.add(new CountryModel("France", "3 Cup Achievement", R.drawable.france));
        countries.add(new CountryModel("Germany", "2 Cup Achievement", R.drawable.germany));
        countries.add(new CountryModel("Saudi Arabia", "2 Cup Achievement", R.drawable.saudiarabia));
        countries.add(new CountryModel("Spain", "4 Cup Achievement", R.drawable.spain));
        countries.add(new CountryModel("United Kingdom", "1 Cup Achievement", R.drawable.unitedkingdom));
//        countries.add(new CountryModel("United States", "2 Cup Achievement", R.drawable.unitedstates));

        adapter = new CountryAdapter(countries, getApplicationContext());

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, "You Clicked on " + adapter.getItem(i).getCountryName(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}