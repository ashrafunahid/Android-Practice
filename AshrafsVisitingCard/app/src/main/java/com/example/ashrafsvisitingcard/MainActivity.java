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

    ImageButton address, phone, email, gitHub, resume;
    TextView addressView, phoneView, emailView, gitHubView, resumeView;

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

        resume = (ImageButton) findViewById(R.id.resueBtn);
        resumeView = (TextView) findViewById(R.id.resumeView);

        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMap();
            }
        });

        addressView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMap();
            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doCall();
            }
        });

        phoneView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doCall();
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToEmail();
            }
        });

        emailView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToEmail();
            }
        });

        gitHub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToGit();
            }
        });

        gitHubView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToGit();
            }
        });

        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToResume();
            }
        });

        resumeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToResume();
            }
        });
    }


//    Going to Map
    private void goToMap(){
        Intent intentMap = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(intentMap);
    }

//    Go to call screen and do a call
    private void doCall(){
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

//  Go to an email App
    private void goToEmail(){
        String recipient = emailView.getText().toString();

        Intent intentMail = new Intent(Intent.ACTION_SEND);
        intentMail.putExtra(Intent.EXTRA_EMAIL, recipient);
        intentMail.setType("message/rfc822");
        startActivity(Intent.createChooser(intentMail, "Select an app"));
    }

//    Go to a webview and go to git
    private void goToGit(){
        String url = gitHubView.getText().toString();
        Intent intentGit = new Intent(MainActivity.this, WebActivity.class);
        intentGit.putExtra("Url", url);
        startActivity(intentGit);
    }


//    Show a pdf file
    private void goToResume(){
        Intent resumeIntent = new Intent(MainActivity.this, ResumeActivity.class);
        startActivity(resumeIntent);

    }

//    Require user permission
    private void requestPermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.INTERNET}, 1);
    }
}