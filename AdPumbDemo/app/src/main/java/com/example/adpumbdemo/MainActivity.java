package com.example.adpumbdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.adpumb.ads.display.AdCompletion;
import com.adpumb.ads.display.BannerEvent;
import com.adpumb.ads.display.BannerPlacement;
import com.adpumb.ads.display.BannerPlacementBuilder;
import com.adpumb.ads.display.DisplayManager;
import com.adpumb.ads.display.InterstitialPlacement;
import com.adpumb.ads.display.InterstitialPlacementBuilder;
import com.adpumb.ads.display.RewardedPlacement;
import com.adpumb.ads.display.RewardedPlacementBuilder;
import com.adpumb.ads.error.PlacementDisplayStatus;

public class MainActivity extends AppCompatActivity {

    Button interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        interstitialAd = findViewById(R.id.interstitialAd);

        interstitialAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RewardedPlacement rewardedPlacement = new RewardedPlacementBuilder()
                        .name("placementName")
                        .loaderTimeOutInSeconds(5)
                        .onAdCompletion(new AdCompletion() {
                            @Override
                            public void onAdCompletion(boolean success, PlacementDisplayStatus placementDisplayStatus) {
                                if (success){
                                    Toast.makeText(getApplicationContext(), "You have successfully watched Rewarded Ad", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(getApplicationContext(), "please watch Rewarded Ad - "+placementDisplayStatus.name(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .build();
                DisplayManager.getInstance().showAd(rewardedPlacement);
            }
        });

    }
}