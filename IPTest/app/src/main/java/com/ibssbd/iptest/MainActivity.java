package com.ibssbd.iptest;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.ibssbd.iptest.Utils.NetworkUtil;
import com.ibssbd.iptest.Utils.OnlineCheckUtil;
import com.ibssbd.iptest.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    String url;
    String responseText, status, country, countryCode, region, regionName, city, zip, lat, lon, timezone, isp, org, as, query;
    String USER_AGENT_STRING = "Mozilla/5.0 (Linux; Android 4.1.1; Galaxy Nexus Build/JRO03C) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.progressCircle.setVisibility(View.GONE);
        AllCertificatesAndHostsTruster.apply();

        binding.goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = binding.urlAddress.getText().toString().trim();
                if (!url.isEmpty() && URLUtil.isValidUrl(url)) {
                    if (OnlineCheckUtil.isOnline(MainActivity.this)) {
                        new RunGetRequest().execute();
                        loadAndShowWebRequest(url);
                    } else {
                        Toast.makeText(MainActivity.this, "Please check your internet connection...", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Please provide a valid url address.", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void loadAndShowWebRequest(String url){
        binding.webView.setFitsSystemWindows(false);
        binding.webView.setVerticalScrollBarEnabled(false);
        binding.webView.setHorizontalScrollBarEnabled(false);
        binding.webView.getSettings().setUserAgentString(USER_AGENT_STRING);
        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.getSettings().setLoadsImagesAutomatically(true);
        binding.webView.getSettings().setAllowFileAccess(true);
        binding.webView.getSettings().setLoadWithOverviewMode(false);
        binding.webView.getSettings().setUseWideViewPort(false);
        binding.webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        binding.webView.setWebViewClient(new HelloWebViewClient());
        binding.webView.setWebChromeClient(new ChromeClient());
        binding.webView.loadUrl(url);
    }

    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

    }

    private class ChromeClient extends WebChromeClient {
        // Defining some variables


        private View mCustomView;
        private WebChromeClient.CustomViewCallback mCustomViewCallback;
        protected FrameLayout mFullscreenContainer;
        private int mOriginalOrientation;
        private int mOriginalSystemUiVisibility;
        ChromeClient() {}

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);

            if(newProgress >= 100){
                // Page loading finish
                binding.progressCircle.setVisibility(View.GONE);

            }else{
                binding.progressCircle.setVisibility(View.VISIBLE);
            }
        }


        public Bitmap getDefaultVideoPoster()
        {
            if (mCustomView == null) {
                return null;
            }
            return BitmapFactory.decodeResource(getApplicationContext().getResources(), 2130837573);
        }

        public void onHideCustomView()
        {
            ((FrameLayout)getWindow().getDecorView()).removeView(this.mCustomView);
            this.mCustomView = null;
            getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
            setRequestedOrientation(this.mOriginalOrientation);
            this.mCustomViewCallback.onCustomViewHidden();
            this.mCustomViewCallback = null;
        }

        public void onShowCustomView(View paramView, CustomViewCallback paramCustomViewCallback)
        {
            if (this.mCustomView != null)
            {
                onHideCustomView();
                return;
            }
            this.mCustomView = paramView;
            this.mOriginalSystemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();
            this.mOriginalOrientation = getRequestedOrientation();
            this.mCustomViewCallback = paramCustomViewCallback;
            ((FrameLayout)getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1));
            getWindow().getDecorView().setSystemUiVisibility(3846 | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

    class RunGetRequest extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            binding.progressCircle.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                JSONObject jsonObject = new JSONObject(NetworkUtil.getRun(url));
                responseText = String.valueOf(jsonObject);
//                status = jsonObject.getString("status");
//                country = jsonObject.getString("country");
//                countryCode = jsonObject.getString("countryCode");
//                region = jsonObject.getString("region");
//                regionName = jsonObject.getString("regionName");
//                city = jsonObject.getString("city");
//                zip = jsonObject.getString("zip");
//                lat = jsonObject.getString("lat");
//                lon = jsonObject.getString("lon");
//                timezone = jsonObject.getString("timezone");
//                isp = jsonObject.getString("isp");
//                org = jsonObject.getString("org");
//                as = jsonObject.getString("as");
//                query = jsonObject.getString("query");
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);

            try {
                binding.progressCircle.setVisibility(View.GONE);
//                binding.responseTime.setText(String.valueOf(TimeUnit.MILLISECONDS.toSeconds(NetworkUtil.responseDuration)));
                binding.responseTime.setText("Response Time: " + String.valueOf(NetworkUtil.responseDuration) + " Milliseconds");
//                binding.responseText.setText(responseText);
//                binding.webView.loadData(responseText, "text/html; charset=UTF-8", null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}