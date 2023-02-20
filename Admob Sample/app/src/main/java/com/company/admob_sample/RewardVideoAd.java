package com.company.admob_sample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class RewardVideoAd extends AppCompatActivity {

        /*
    >>Source Code provided by
    >>Jubayer Hossain [https://www.facebook.com/JubayerHossainbd]
    >>Please don't forget to put a review on my site [www.bongoacademy.com]
    >>Share my course with your friends on Facebook
    >>Your positive words help me doing even better
     */

    TextView textView;
    Button showAdButton;
    RewardedAd mRewardedAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reward_video_ad);

        //init Mobile Ads
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        textView = findViewById(R.id.textView);
        showAdButton = findViewById(R.id.showAdButton);

        // Load Rewarded Video Ad
        loadRewardedAd();


        showAdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //show Reward video ad
                showRewardAd();
            }
        });





    } //============================================ oncreate Ends here



    //===========================================
    //===========================================

    private void showRewardAd() {
        // Show the ad if it's ready.
        if (mRewardedAd != null) {

            mRewardedAd.show(RewardVideoAd.this, new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    // Handle the reward.

                    //Reward user and showing a dialog
                    AlertDialog alertDialog = new AlertDialog.Builder(RewardVideoAd.this).create();
                    alertDialog.setTitle("Congrats \uD83D\uDE03");
                    alertDialog.setMessage("Nice Job! You got some reward!");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                    //=========================================

                }
            });
        } else {
            textView.setText("The rewarded ad wasn't ready yet.");
        }
    }
    //============================================







    // Reward Video Ad Loading method starts here.....
    private void loadRewardedAd(){

        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(this, "ca-app-pub-3940256099942544/5224354917",
                adRequest, new RewardedAdLoadCallback(){
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        textView.setText("Failed to load reward video ad!");
                        mRewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                        textView.setText("Reward Vdo is loaded successfully");

                        //Handle callbacks
                        mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when ad is shown.
                                mRewardedAd = null;
                                textView.setText("Ad is shown successfully!");
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                // Called when ad fails to show.
                                textView.setText("Ad failed to show.");
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                // Called when ad is dismissed.
                                // Don't forget to set the ad reference to null so you
                                // don't show the ad a second time.
                                textView.setText("Ad was dismissed.");

                                // requesting a new ad here...
                                loadRewardedAd();
                            }
                        });



                    }

                });




        //===========================================
    }






    //===========================================
    //===========================================

 //===============================================
}