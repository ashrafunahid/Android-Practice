package com.example.startiodemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.startapp.sdk.adsbase.Ad;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.StartAppSDK;
import com.startapp.sdk.adsbase.adlisteners.AdDisplayListener;

public class MainActivity extends AppCompatActivity {
    private StartAppAd startAppAd = new StartAppAd(this);
    public void showAd(View view){
        startAppAd.loadAd(StartAppAd.AdMode.VIDEO);
        startAppAd.showAd(new AdDisplayListener() {
            @Override
            public void adHidden(Ad ad) {
                Log.i("Info", "On adHidden status");
            }
            @Override
            public void adDisplayed(Ad ad) {
                Log.i("Info", "On adDisplayed status");
            }
            @Override
            public void adClicked(Ad ad) {
                Log.i("Info", "On adClicked status");
            }
            @Override
            public void adNotDisplayed(Ad ad) {
                Log.i("Info", "On adNotDisplayed status");
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StartAppSDK.setTestAdsEnabled(BuildConfig.DEBUG);
        StartAppAd.disableSplash();


    }
    @Override
    public void onBackPressed() {
        StartAppAd.onBackPressed(this);
        super.onBackPressed();
    }
}