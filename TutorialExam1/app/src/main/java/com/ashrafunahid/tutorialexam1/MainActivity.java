package com.ashrafunahid.tutorialexam1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText temperature, oxygen, heartRate;
    TextView result;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        temperature = (EditText) findViewById(R.id.temperature);
        oxygen = (EditText) findViewById(R.id.oxygen_saturation);
        heartRate = (EditText) findViewById(R.id.heartbeat);
        result = (TextView) findViewById(R.id.result);

        submit = (Button) findViewById(R.id.submit);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int temp = Integer.parseInt(temperature.getText().toString());
                int oxy = Integer.parseInt(oxygen.getText().toString());
                int hr = Integer.parseInt(heartRate.getText().toString());
                if(temp > 101){
                    if(oxy < 95 && hr < 70){
                        result.setText("Consult Hospital Immediately");
                    }
                    else{
                        result.setText("Please Make Covid Test");
                    }
                }
                else{
                    result.setText("Stay Safe and Carefull");
                }
            }
        });


    }
}