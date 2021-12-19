package com.example.saarcinformation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    String[] countryNames;
    private Spinner mySpinner;
    private Button myButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mySpinner = (Spinner) findViewById(R.id.countrySpinner);
        myButton = (Button) findViewById(R.id.btn);

        countryNames = getResources().getStringArray(R.array.country_name);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.saarc_country_names, R.id.showCountryName, countryNames);
        mySpinner.setAdapter(adapter);

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String spinnerData = mySpinner.getSelectedItem().toString();

                Intent intent = new Intent(MainActivity.this, CountryDetail.class);
                intent.putExtra("countryName", spinnerData);
                startActivity(intent);


            }
        });

    }
}