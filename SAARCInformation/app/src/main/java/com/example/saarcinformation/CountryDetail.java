package com.example.saarcinformation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CountryDetail extends AppCompatActivity {

    String bangladesh, india, pakistan, nepal, bhutan, srilanka, maldives, afghanistan;
    TextView country, countryDetail;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_detail);

        country = (TextView) findViewById(R.id.countryName);
        countryDetail = (TextView) findViewById(R.id.countryDetail);
        backButton = (Button) findViewById(R.id.backButton);

        bangladesh = getResources().getString(R.string.bangladesh);
        india = getResources().getString(R.string.india);
        pakistan = getResources().getString(R.string.pakistan);
        nepal = getResources().getString(R.string.nepal);
        bhutan = getResources().getString(R.string.bhutan);
        srilanka = getResources().getString(R.string.srilanka);
        maldives = getResources().getString(R.string.maldives);
        afghanistan = getResources().getString(R.string.afghanistan);

        Intent intent = getIntent();
        String countryName = intent.getStringExtra("countryName");


        switch (countryName) {
            case "Bangladesh":
                countryDetail.setText(bangladesh);
                break;
            case "Pakistan":
                countryDetail.setText(pakistan);
                break;
            case "India":
                countryDetail.setText(india);
                break;
            case "Nepal":
                countryDetail.setText(nepal);
                break;
            case "Bhutan":
                countryDetail.setText(bhutan);
                break;
            case "Sri Lanka":
                countryDetail.setText(srilanka);
                break;
            case "Maldives":
                countryDetail.setText(maldives);
                break;
            case "Afghanistan":
                countryDetail.setText(afghanistan);
                break;
            default:
                countryDetail.setText("Something Wrong");
        }

        country.setText(countryName);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(CountryDetail.this, MainActivity.class);
                startActivity(intent1);
            }
        });

    }
}