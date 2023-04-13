package com.example.admobpractice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class MainActivity extends AppCompatActivity {

    Button interstitialAdBtn, rewardedAdBtn, nativeAdBtn;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    private RewardedAd rewardedAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//      Initializing buttons
        interstitialAdBtn = (Button) findViewById(R.id.interstitialAdBtn);
        rewardedAdBtn = (Button) findViewById(R.id.rewardedAdBtn);
        nativeAdBtn = (Button) findViewById(R.id.nativeAdBtn);

//      Initializing Banner Ad id with xml file
        mAdView = findViewById(R.id.adView);

//      Initializing Mobile Ads SDK
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        showBannerAd();

        loadInterstitialAd();

        loadRewardedAd();

        loadNativeAd();

//      Showing interstitial ad
        interstitialAdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(MainActivity.this);
                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.");
                }
//      Controlling listener of interstitial ad
                mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdClicked() {
                        // Called when a click is recorded for an ad.
                        Toast.makeText(MainActivity.this, "On Ad Clicked Status", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        // Called when ad is dismissed.
                        // Set the ad reference to null so you don't show the ad a second time.
                        Toast.makeText(MainActivity.this, "OnAdDismissedFullScreenContent Status", Toast.LENGTH_SHORT).show();
                        loadInterstitialAd();
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        // Called when ad fails to show.
                        Toast.makeText(MainActivity.this, "OnAdFailedToShowFullScreenContent status", Toast.LENGTH_SHORT).show();
                        loadInterstitialAd();
                    }

                    @Override
                    public void onAdImpression() {
                        // Called when an impression is recorded for an ad.
                        Toast.makeText(MainActivity.this, "OnAdImpression Status", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        // Called when ad is shown.
                        Toast.makeText(MainActivity.this, "OnAdShowedFullScreenContent Status", Toast.LENGTH_SHORT).show();
                    }
                });
//      End of Controlling listener of interstitial ad
            }
        });
        
//      Showing Rewarded Ad
        rewardedAdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rewardedAd != null) {
                    Activity activityContext = MainActivity.this;
                    rewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                            // Handle the reward.
                            Toast.makeText(getApplicationContext(), "The user earned the reward.", Toast.LENGTH_SHORT).show();
                            int rewardAmount = rewardItem.getAmount();
//                            Toast.makeText(getApplicationContext(), rewardAmount, Toast.LENGTH_SHORT).show();
                            String rewardType = rewardItem.getType();
                        }
                    });
                } else {
                    Log.d("Info", "The rewarded ad wasn't ready yet.");
                }

                rewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdClicked() {
                        // Called when a click is recorded for an ad.
                        Log.d("TAG", "Ad was clicked.");
                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        // Called when ad is dismissed.
                        // Set the ad reference to null so you don't show the ad a second time.
                        Log.d("TAG", "Ad dismissed fullscreen content.");
                        loadRewardedAd();
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        // Called when ad fails to show.
                        Log.e("TAG", "Ad failed to show fullscreen content.");
                        loadRewardedAd();
                    }

                    @Override
                    public void onAdImpression() {
                        // Called when an impression is recorded for an ad.
                        Log.d("TAG", "Ad recorded an impression.");
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        // Called when ad is shown.
                        Log.d("TAG", "Ad showed fullscreen content.");
                    }
                });
            }
        });

//      Showing native Ad
        nativeAdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



    }

    private void showBannerAd(){
        //      Making a AdRequest
        AdRequest adRequest = new AdRequest.Builder().build();
//      Showing Banner Ad
        mAdView.loadAd(adRequest);

//      Controlling listener
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                Toast.makeText(getApplicationContext(), "OnAdClicked status", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
                Toast.makeText(getApplicationContext(), "OnAdClosed status", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
                Toast.makeText(getApplicationContext(), "OnAdFailedToLoad Status", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdImpression() {
                // Code to be executed when an impression is recorded
                // for an ad.
                Toast.makeText(MainActivity.this, "OnAdImpression Status", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Toast.makeText(MainActivity.this, "OnAdLoaded Status", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                Toast.makeText(MainActivity.this, "OnAdOpened Status", Toast.LENGTH_SHORT).show();
            }
        });
//        End of controlling listener
    }

    private void loadInterstitialAd(){
        //      Requesting for a new ad

        AdRequest adRequest = new AdRequest.Builder().build();

//        Starting of InterstitialAd
        InterstitialAd.load(this, "ca-app-pub-3940256099942544/1033173712", adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                // The mInterstitialAd reference will be null until
                // an ad is loaded.
                mInterstitialAd = interstitialAd;
                Log.i("Loader Info", "onAdLoaded");
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error
                Log.d("Error Info", loadAdError.toString());
                mInterstitialAd = null;
            }
        });

    }

    private void loadRewardedAd(){
        //      Requesting for a new ad
        AdRequest adRequest = new AdRequest.Builder().build();

//      Starting of Rewarded Ad
        RewardedAd.load(this, "ca-app-pub-3940256099942544/5224354917", adRequest, new RewardedAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error.
                Log.d("Info", loadAdError.toString());
                rewardedAd = null;
            }

            @Override
            public void onAdLoaded(@NonNull RewardedAd ad) {
                rewardedAd = ad;
                Log.d("Info", "Ad was loaded.");
            }
        });

    }

    private void loadNativeAd(){
        AdLoader adLoader = new AdLoader.Builder(getApplicationContext(), "ca-app-pub-3940256099942544/2247696110")
                .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                    @Override
                    public void onNativeAdLoaded(NativeAd nativeAd) {
                        // Show the ad

                    }
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(LoadAdError adError) {
                        // Handle the failure by logging, altering the UI, and so on.
                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder()
                        // Methods in the NativeAdOptions.Builder class can be
                        // used here to specify individual options settings.
                        .build())
                .build();
    }

}