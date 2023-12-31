package com.ashrafunahid.metaaudienceadimplementation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.RewardedVideoAd;
import com.facebook.ads.RewardedVideoAdListener;

public class MainActivity extends AppCompatActivity {

    private AdView adView;
    Button showAdButton, showInterstitial;
    private final String TAG = this.getClass().getName();
    private RewardedVideoAd rewardedVideoAd;
    private InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showAdButton = findViewById(R.id.show_ad_button);
        showInterstitial = findViewById(R.id.show_interstitial);

        // Initialize the Audience Network SDK
        AudienceNetworkAds.initialize(this);
        // If you are using the default Google Android emulator, you'll add the following line of code before loading a test ad:
        AdSettings.addTestDevice("727270d2-7735-4f5c-802f-57dcfaa069c1");

        // Instantiate a RewardedVideoAd object.
        // NOTE: the placement ID will eventually identify this as your App, you can ignore it for
        // now, while you are testing and replace it later when you have signed up.
        // While you are using this temporary code you will only get test ads and if you release
        // your code like this to the Google Play your users will not receive ads (you will get
        // a no fill error).
        rewardedVideoAd = new RewardedVideoAd(this, "YOUR_PLACEMENT_ID");
        interstitialAd = new InterstitialAd(this, "YOUR_PLACEMENT_ID");

        loadAndShowBannerAd();
        loadRewardedAd();
//        loadInterstitialAd();

        showAdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rewardedVideoAd != null) {
                    rewardedVideoAd.show();
                }
//                if (rewardedVideoAd != null) {
//                    /**
//                     * Here is an example for displaying the ad with delay;
//                     * Please do not copy the Handler into your project
//                     */
//                    Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                        public void run() {
//                            // Check if rewardedVideoAd has been loaded successfully
//                            if (rewardedVideoAd == null || !rewardedVideoAd.isAdLoaded()) {
//                                loadRewardedAd();
//                            }
//                            // Check if ad is already expired or invalidated, and do not show ad if that is the case. You will not get paid to show an invalidated ad.
//                            if (rewardedVideoAd.isAdInvalidated()) {
//                                loadRewardedAd();
//                            }
//                            rewardedVideoAd.show();
//                        }
//                    }, 1000 * 60); // Show the ad after 1 minutes
//                }
            }
        });

        showInterstitial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadInterstitialAd();
//                if (interstitialAd != null) {
//                    interstitialAd.show();
//                }
//                /**
//                 * Here is an example for displaying the ad with delay;
//                 * Please do not copy the Handler into your project
//                 */
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    public void run() {
//                        // Check if interstitialAd has been loaded successfully
//                        if(interstitialAd == null || !interstitialAd.isAdLoaded()) {
//                            return;
//                        }
//                        // Check if ad is already expired or invalidated, and do not show ad if that is the case. You will not get paid to show an invalidated ad.
//                        if(interstitialAd.isAdInvalidated()) {
//                            return;
//                        }
//                        // Show the ad
//                        interstitialAd.show();
//                    }
//                }, 1000 * 60 * 15);
            }
        });

    }

    private void loadInterstitialAd() {
        // Create listeners for the Interstitial Ad
        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback
                Log.e(TAG, "Interstitial ad displayed.");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
                Log.e(TAG, "Interstitial ad dismissed.");
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
                Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
                // Show the ad
                interstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
                Log.d(TAG, "Interstitial ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
                Log.d(TAG, "Interstitial ad impression logged!");
            }
        };

        // For auto play video ads, it's recommended to load the ad
        // at least 30 seconds before it is shown
        interstitialAd.loadAd(
                interstitialAd.buildLoadAdConfig()
                        .withAdListener(interstitialAdListener)
                        .build());
    }

    public void loadAndShowBannerAd(){
        // Instantiate an AdView object.
        // NOTE: The placement ID from the Facebook Monetization Manager identifies your App.
        // To get test ads, add IMG_16_9_APP_INSTALL# to your placement id. Remove this when your app is ready to serve real ads.

        adView = new AdView(this, "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID", AdSize.BANNER_HEIGHT_50);

        // Find the Ad Container
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);

        // Add the ad view to your activity layout
        adContainer.addView(adView);

        AdListener adListener = new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
        // Ad error callback
                Toast.makeText(
                                MainActivity.this,
                                "Error: " + adError.getErrorMessage(),
                                Toast.LENGTH_LONG)
                        .show();
            }

            @Override
            public void onAdLoaded(Ad ad) {
        // Ad loaded callback
            }

            @Override
            public void onAdClicked(Ad ad) {
        // Ad clicked callback
            }

            @Override
            public void onLoggingImpression(Ad ad) {
        // Ad impression logged callback
            }
        };

        // Request an ad
        adView.loadAd(adView.buildLoadAdConfig().withAdListener(adListener).build());
    }

    public void loadRewardedAd(){
        RewardedVideoAdListener rewardedVideoAdListener = new RewardedVideoAdListener() {
            @Override
            public void onError(Ad ad, AdError error) {
                // Rewarded video ad failed to load
                Log.e(TAG, "Rewarded video ad failed to load: " + error.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Rewarded video ad is loaded and ready to be displayed
                Log.d(TAG, "Rewarded video ad is loaded and ready to be displayed!");
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Rewarded video ad clicked
                Log.d(TAG, "Rewarded video ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Rewarded Video ad impression - the event will fire when the
                // video starts playing
                Log.d(TAG, "Rewarded video ad impression logged!");
            }

            @Override
            public void onRewardedVideoCompleted() {
                // Rewarded Video View Complete - the video has been played to the end.
                // You can use this event to initialize your reward
                Log.d(TAG, "Rewarded video completed!");
                Log.d(TAG, "You've successfully earned the reward!");

                // Call method to give reward
                // giveReward();
            }

            @Override
            public void onRewardedVideoClosed() {
                // The Rewarded Video ad was closed - this can occur during the video
                // by closing the app, or closing the end card.
                Log.d(TAG, "Rewarded video ad closed!");
            }
        };
        rewardedVideoAd.loadAd(
                rewardedVideoAd.buildLoadAdConfig()
                        .withAdListener(rewardedVideoAdListener)
                        .build());
    }

    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        if (rewardedVideoAd != null) {
            rewardedVideoAd.destroy();
            rewardedVideoAd = null;
        }
        if (interstitialAd != null) {
            interstitialAd.destroy();
        }
        super.onDestroy();
    }
}