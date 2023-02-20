package com.example.picasso_library_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    Button prev, next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        prev = findViewById(R.id.prev);
        next = findViewById(R.id.next);

        Picasso.get()
                .load("https://rb.gy/mdypet")
                .placeholder(R.drawable.place_holder)
                .into(imageView);

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.get()
                        .load("https://rb.gy/mdypet")
                        .placeholder(R.drawable.place_holder)
                        .into(imageView);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.get()
                        .load("https://rb.gy/q4e4zi")
                        .placeholder(R.drawable.place_holder)
                        .into(imageView);
            }
        });
    }
}