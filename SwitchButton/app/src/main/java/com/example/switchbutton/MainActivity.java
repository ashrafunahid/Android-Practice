package com.example.switchbutton;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Switch MySwitch;
    TextView MyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MySwitch =(Switch) findViewById(R.id.switch_button);
        MyText =(TextView) findViewById(R.id.text);

        MySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    MyText.setText(R.string.switch_on);
                }
                else{
                    MyText.setText(R.string.switch_off);
                }
            }
        });

    }
}