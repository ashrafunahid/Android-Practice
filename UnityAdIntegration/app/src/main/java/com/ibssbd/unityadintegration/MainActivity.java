package com.ibssbd.unityadintegration;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.unity3d.ads.IUnityAdsInitializationListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.services.banners.BannerErrorInfo;
import com.unity3d.services.banners.BannerView;
import com.unity3d.services.banners.UnityBannerSize;

public class MainActivity extends AppCompatActivity {

    private String unityGameID = "5543847";
    private String bannerAdID = "banner";
    private String interstitialAdID = "interstitial";
    BannerView unityBanner;
    RelativeLayout bannerLayout;
    private Boolean testMode = true;
    Button interstitialButton;
    private String TAG = this.getClass().getName();


    // Listener for banner events:
    private BannerView.IListener bannerListener = new BannerView.IListener() {
        @Override
        public void onBannerLoaded(BannerView bannerAdView) {
            // Called when the banner is loaded.
            Log.v(TAG, "onBannerLoaded: " + bannerAdView.getPlacementId());
            // Enable the correct button to hide the ad
        }

        @Override
        public void onBannerClick(BannerView bannerAdView) {
            // Called when a banner is clicked.
            Log.v(TAG, "onBannerClick: " + bannerAdView.getPlacementId());
        }

        @Override
        public void onBannerFailedToLoad(BannerView bannerAdView, BannerErrorInfo errorInfo) {

        }

        @Override
        public void onBannerLeftApplication(BannerView bannerAdView) {
            // Called when the banner links out of the application.
            Log.v(TAG, "onBannerLeftApplication: " + bannerAdView.getPlacementId());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        interstitialButton = findViewById(R.id.interstitial_btn);
        interstitialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        bannerLayout = findViewById(R.id.banner_layout);

        unityBanner = new BannerView(MainActivity.this, bannerAdID, new UnityBannerSize(320, 50));
        // Set the listener for banner lifecycle events:
        unityBanner.setListener(bannerListener);
        LoadBannerAd(unityBanner, bannerLayout);

        UnityAds.initialize(getApplicationContext(), unityGameID, testMode, new IUnityAdsInitializationListener() {
            @Override
            public void onInitializationComplete() {
                Log.i(TAG, "Unity initialization complete");
            }

            @Override
            public void onInitializationFailed(UnityAds.UnityAdsInitializationError error, String message) {
                Log.i(TAG, "Unity initialization failed");
            }
        });

    }

    public void LoadBannerAd(BannerView bannerView, RelativeLayout bannerLayout) {
        // Request a banner ad:
        bannerView.load();
        // Associate the banner view object with the banner view:
        bannerLayout.addView(bannerView);
    }
}