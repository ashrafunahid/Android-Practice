package com.example.ashrafsvisitingcard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

public class WebActivity extends AppCompatActivity {

    private WebView webView;
    ImageButton closeBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_web);

        webView = (WebView) findViewById(R.id.web);
        closeBtn = (ImageButton) findViewById(R.id.backBtn);

        Intent intent = getIntent();
        String url = intent.getStringExtra("Url");

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(WebActivity.this, MainActivity.class);
                startActivity(intent1);
            }
        });
    }
}