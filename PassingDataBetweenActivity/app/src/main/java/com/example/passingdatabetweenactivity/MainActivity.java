package com.example.passingdatabetweenactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText myText;
    Button myBtn;
    String sendText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myText = (EditText) findViewById(R.id.editText);
        myBtn = (Button) findViewById(R.id.btn);

        myBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               sendText = myText.getText().toString();

               Intent intent = new Intent(MainActivity.this, SecondActivity.class);
               intent.putExtra("dataKey", sendText);
               startActivity(intent);


            }
        });

    }
}