package com.company.admob_sample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class FullscreenAd extends AppCompatActivity {

        /*
    >>Source Code provided by
    >>Jubayer Hossain [https://www.facebook.com/JubayerHossainbd]
    >>Please don't forget to put a review on my site [www.bongoacademy.com]
    >>Share my course with your friends on Facebook
    >>Your positive words help me doing even better
     */

    TextView textView;
    Button showAdButton;
    InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullscreen_ad);

        //init Mobile Ads
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        textView = findViewById(R.id.textView);
        showAdButton = findViewById(R.id.showAdButton);


        //Request a fullscreen ad with a fuction
        loadFullscreenAd();


        showAdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show Ad
                showInterstitial();
            }
        });





    } //============================================ oncreate Ends here





    //==============================================
    private void showInterstitial() {
        // Show the ad if it's ready.
        if (mInterstitialAd != null ) {
            mInterstitialAd.show(this);
            textView.setText("Ad is displaying");
        } else {
            textView.setText("Ad is not loaded yet! Please wait...");
        }
    }
    //============================================


    // loadFullscreenAd method starts here.....
    private void loadFullscreenAd(){

        //Requesting for a fullscreen Ad
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                // The mInterstitialAd reference will be null until
                // an ad is loaded.
                mInterstitialAd = interstitialAd;
                textView.setText("InterstitialAd is Loaded");

                //Fullscreen callback || Requesting again when an ad is shown already
                mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        // Called when fullscreen content is dismissed.
                        textView.setText("The ad was dismissed.");
                        //User dismissed the previous ad. So we are requesting a new ad here
                        loadFullscreenAd();
                    }

                }); // FullScreen Callback Ends here


            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error
                textView.setText("Ad Failed to load");
                mInterstitialAd = null;
            }

        });

    }
    // loadFullscreenAd method ENDS  here..... >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>






}