package com.example.spinner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    String[] countryNames;
    String spinnerValue;
    private Spinner mySpinner;
    private Button myButton;
    private TextView myText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mySpinner = (Spinner) findViewById(R.id.countrySpinner);
        myButton = (Button) findViewById(R.id.btn);
        myText = (TextView) findViewById(R.id.textView);

        countryNames = getResources().getStringArray(R.array.country_name);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.country_name, R.id.country_text,countryNames);
        mySpinner.setAdapter(adapter);

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                spinnerValue = mySpinner.getSelectedItem().toString();
                myText.setText(spinnerValue);

            }
        });
    }
}