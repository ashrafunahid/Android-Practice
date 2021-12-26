package com.example.callapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText myNumber;
    Button callButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myNumber = (EditText) findViewById(R.id.number);
        callButton = (Button) findViewById(R.id.btn);

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_CALL);
                String number = myNumber.getText().toString();

                if (number.trim().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please Enter a Number", Toast.LENGTH_SHORT).show();
                } else {

                    intent.setData(Uri.parse("tel:" + number));

                    //  For require permission from user on Android Marshmallow version
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(MainActivity.this, "Please Grant Permission", Toast.LENGTH_SHORT).show();
                        requestPermission();
                    } else {
                        startActivity(intent);
                    }
                }

            }
        });

    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
    }
}