package com.example.nativeaddemo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;

public class MainActivity extends AppCompatActivity {

    Button nativeAdBtn;
    AdLoader adLoader;
    LinearLayout adLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nativeAdBtn = (Button) findViewById(R.id.nativeAdBtn);
        adLayout = (LinearLayout) findViewById(R.id.adLayout);

//      Initializing ad SDK
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

//      Showing Native Ad
        nativeAdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adLayout.setVisibility(View.VISIBLE);
                adLoader = new AdLoader.Builder(MainActivity.this, "ca-app-pub-3940256099942544/2247696110")
                        .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                            @Override
                            public void onNativeAdLoaded(NativeAd nativeAd) {
                                // Show the ad.
                                if (!adLoader.isLoading()){
                                    Toast.makeText(MainActivity.this, "Native Ad Loaded", Toast.LENGTH_SHORT).show();
                                }
                                if (isDestroyed()) {
                                    nativeAd.destroy();
                                }

                                NativeTemplateStyle styles = new
                                        NativeTemplateStyle.Builder().withMainBackgroundColor(new ColorDrawable(Color.WHITE)).build();

                                TemplateView template = findViewById(R.id.my_template);
                                TemplateView template2 = findViewById(R.id.my_template2);

                                template.setStyles(styles);
                                template.setNativeAd(nativeAd);

                                template2.setStyles(styles);
                                template2.setNativeAd(nativeAd);

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

//              For loading a single ad
//                adLoader.loadAd(new AdRequest.Builder().build());

//              For loading multiple ad (Upto 5)
                adLoader.loadAds(new AdRequest.Builder().build(), 3);

            }
        });


    }
}