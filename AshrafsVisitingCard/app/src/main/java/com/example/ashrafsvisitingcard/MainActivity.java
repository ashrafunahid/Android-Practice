package com.example.ashrafsvisitingcard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageButton address, phone, email, gitHub;
    TextView addressView, phoneView, emailView, gitHubView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);

        address = (ImageButton) findViewById(R.id.addressBtn);
        addressView = (TextView) findViewById(R.id.address);

        phone = (ImageButton) findViewById(R.id.phoneBtn);
        phoneView = (TextView) findViewById(R.id.phone);

        email = (ImageButton) findViewById(R.id.emailBtn);
        emailView = (TextView) findViewById(R.id.email);

        gitHub = (ImageButton) findViewById(R.id.gitHubBtn);
        gitHubView = (TextView) findViewById(R.id.gitHub);

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentCall = new Intent(Intent.ACTION_CALL);

                String number = phoneView.getText().toString();
                intentCall.setData(Uri.parse("tel:" + number));

                //  For require permission from user on Android Marshmallow version
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "Please Grant Permission", Toast.LENGTH_SHORT).show();
                    requestPermission();
                } else {
                    startActivity(intentCall);
                }
            }
        });

        gitHub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = gitHubView.getText().toString();
                Intent intentGit = new Intent(MainActivity.this, WebActivity.class);
                intentGit.putExtra("Url", url);
                startActivity(intentGit);


            }
        });
    }
    private void requestPermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.INTERNET}, 1);
    }
}