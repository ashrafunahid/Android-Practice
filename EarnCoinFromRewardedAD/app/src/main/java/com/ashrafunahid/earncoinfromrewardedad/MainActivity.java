package com.ashrafunahid.earncoinfromrewardedad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ashrafunahid.earncoinfromrewardedad.databinding.ActivityMainBinding;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private final String TAG = this.getClass().getName();
    private static Handler mHandler;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private RewardedAd rewardedAd;
    private boolean alreadyRequested;
    private int totalCoin = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences = getSharedPreferences("GoBeautify", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        mHandler = new Handler();
        alreadyRequested = false;

        // Total Coin
        totalCoin = sharedPreferences.getInt("coin", 0);
        binding.coin.setText(String.valueOf(totalCoin));

        binding.coin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRewardedAd();
            }
        });

//        AdMob Initialization
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        loadRewardedAd();
    }

    private void loadRewardedAd() {
        if (!alreadyRequested) {
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            Date previousRequest;
            Date currentTime = TimeUtil.getDate();
            long timeLeft;
            try {
                previousRequest = format.parse(sharedPreferences.getString("previousRequest", "01/01/2000 00:00:00"));
                timeLeft = TimeUtil.getDateDiff(previousRequest, currentTime, TimeUnit.MILLISECONDS);

                Log.i(TAG, "run: Loaded: B "+timeLeft);
            }catch (Exception e){
                Log.i(TAG, "run: Loaded: "+e.getLocalizedMessage());
                timeLeft = 0;
            }

            if (timeLeft >= 60000) {
                requestRewardedAd();
                alreadyRequested = true;
            }else{
                mHandler.postDelayed(new Runnable() {
                    public void run() {
                        Log.i(TAG, "run: Loaded");
                        loadRewardedAd();
                    }
                }, (60000 - timeLeft));
            }
        }else{
            Log.i(TAG, "run: Loaded: Dada time nai");
        }

    }

    private void requestRewardedAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(this, "ca-app-pub-3940256099942544/5224354917",
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        Log.d(TAG, loadAdError.toString());
                        loadRewardedAd();
                        rewardedAd = null;
                        alreadyRequested = false;
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd ad) {
                        rewardedAd = ad;
                        Log.d(TAG, "run: Loaded:  Loaded");
                        editor.putString("previousRequest", TimeUtil.getTime());
                        editor.commit();
                        alreadyRequested = true;
                        rewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdClicked() {
                                super.onAdClicked();
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                super.onAdDismissedFullScreenContent();
                                loadRewardedAd();
                                alreadyRequested = false;
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                                super.onAdFailedToShowFullScreenContent(adError);
                                loadRewardedAd();
                                alreadyRequested = false;
                            }

                            @Override
                            public void onAdImpression() {
                                super.onAdImpression();
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                super.onAdShowedFullScreenContent();
                            }
                        });
                    }
                });
    }

    private void showRewardedAd() {
        if (rewardedAd != null) {
            rewardedAd.show(this, new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    totalCoin += 10;
                    editor.putInt("coin", totalCoin);
                    Log.i(TAG, "run: Loaded: Shown");
                    binding.coin.setText(String.valueOf(totalCoin));
                    alreadyRequested = false;
                }
            });
        }else{
            loadRewardedAd();
            Log.i(TAG, "run: Loaded: Null");
        }
    }

}