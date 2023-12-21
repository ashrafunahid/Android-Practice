package com.ashrafunahid.earncoinfromad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    TextView coinInfo;
    Button earnCoinButton;
    private final String TAG = this.getClass().getName();
    private RewardedAd rewardedAd;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy, HH:mm:ss");
    String dateOld, dateNow;
    int coin;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coinInfo = findViewById(R.id.coin_info);
        earnCoinButton = findViewById(R.id.earn_coin_button);
        earnCoinButton.setEnabled(false);

        sharedPreferences = getSharedPreferences("GoBeautify", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        coin = sharedPreferences.getInt("coin", 0);
        coinInfo.setText("You have " + coin + " coins");

        //      Initializing Mobile Ads SDK
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        requestRewardedAd();

        earnCoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rewardedAd != null) {
                    rewardedAd.show(MainActivity.this, new OnUserEarnedRewardListener() {
                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                            // Handle the reward.
                            int rewardAmount = rewardItem.getAmount();
//                            String rewardType = rewardItem.getType();
                            coin = coin + rewardAmount;
                            editor.putInt("coin", coin);
                            editor.commit();
                            coin = sharedPreferences.getInt("coin", 0);
                            coinInfo.setText("You have " + coin + " coins");
                        }
                    });
                } else {
                    Log.d(TAG, "The rewarded ad wasn't ready yet.");
                }

            }
        });

    }

    private void requestRewardedAd() {
        dateOld = sharedPreferences.getString("date", "01 01 2000, 11:59:30");
        dateNow = df.format(Calendar.getInstance().getTime());

        long difference = AdUtils.checkTimeDifference(dateOld, dateNow, TAG);
        if (difference > 60) {
            loadRewardedAd();
        } else {
            Log.i(TAG, "Rewarded ad will be loaded after " + String.valueOf(60 - difference) + " seconds");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadRewardedAd();
                }
            }, (60 - difference) * 1000);
        }
    }

    private void loadRewardedAd() {
 //      Requesting for a new ad
        AdRequest adRequest = new AdRequest.Builder().build();
//      Starting of Rewarded Ad
        RewardedAd.load(this, "ca-app-pub-3940256099942544/5224354917", adRequest, new RewardedAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error.
                Log.d(TAG, loadAdError.toString());
                rewardedAd = null;
                requestRewardedAd();
            }

            @Override
            public void onAdLoaded(@NonNull RewardedAd ad) {
                rewardedAd = ad;
                Log.d(TAG, "Ad was loaded.");
                editor.putString("date", df.format(Calendar.getInstance().getTime()));
                editor.commit();
                earnCoinButton.setEnabled(true);

                rewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdClicked() {
                        // Called when a click is recorded for an ad.
                        Log.d(TAG, "Ad was clicked.");
                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        // Called when ad is dismissed.
                        // Set the ad reference to null so you don't show the ad a second time.
                        Log.d(TAG, "Ad dismissed fullscreen content.");
                        earnCoinButton.setEnabled(false);
                        requestRewardedAd();
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        // Called when ad fails to show.
                        Log.e(TAG, "Ad failed to show fullscreen content.");
                        earnCoinButton.setEnabled(false);
                        requestRewardedAd();
                    }

                    @Override
                    public void onAdImpression() {
                        // Called when an impression is recorded for an ad.
                        Log.d(TAG, "Ad recorded an impression.");
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        // Called when ad is shown.
                        Log.d(TAG, "Ad showed fullscreen content.");
                    }
                });
            }
        });

    }

}