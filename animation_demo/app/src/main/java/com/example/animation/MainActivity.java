package com.example.animation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    Button btn;
    Animation my_anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        btn = findViewById(R.id.btn);
        my_anim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_in);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.startAnimation(my_anim);
            }
        });
    }
}