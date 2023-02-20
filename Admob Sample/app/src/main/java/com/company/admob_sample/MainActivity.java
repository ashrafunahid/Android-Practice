package com.company.admob_sample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MainActivity extends AppCompatActivity {

    Button buttonBanner, buttonFullscreen, buttonReward;

    /*
    >>Source Code provided by
    >>Jubayer Hossain [https://www.facebook.com/JubayerHossainbd]
    >>Please don't forget to put a review on my site [www.bongoacademy.com]
    >>Share my course with your friends on Facebook
    >>Your positive words help me doing even better
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonBanner = findViewById(R.id.buttonBanner);
        buttonFullscreen = findViewById(R.id.buttonFullscreen);
        buttonReward = findViewById(R.id.buttonReward);



        buttonBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, BannerAd.class);
                startActivity(intent);
            }
        });


        buttonFullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, FullscreenAd.class);
                startActivity(intent);
            }
        });


        buttonReward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, RewardVideoAd.class);
                startActivity(intent);
            }
        });






    }
    //onCreate Ends here...

    //==============================================
}