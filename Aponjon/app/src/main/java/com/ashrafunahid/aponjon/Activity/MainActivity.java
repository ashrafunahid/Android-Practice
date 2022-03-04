package com.ashrafunahid.aponjon.Activity;

import androidx.appcompat.app.AppCompatActivity;
import static java.lang.Thread.sleep;

import android.content.Intent;
import android.os.Bundle;

import com.ashrafunahid.aponjon.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(4000);
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}