package com.example.passingdatabetweenactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    TextView myTextView;
    String rcv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        myTextView = (TextView) findViewById(R.id.text);

        Intent intent = getIntent();
        rcv = intent.getStringExtra("dataKey");

        myTextView.setText(rcv);

    }
}