package com.ashrafunahid.xmltagdemo;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void colorNameInFrench(View view) {
        Button button = (Button) view;
        MediaPlayer mediaPlayer = MediaPlayer.create(
                this,
                getResources().getIdentifier(view.getTag().toString(), "raw", getPackageName())
        );
        Toast.makeText(this, "You clicked: " + view.getTag().toString().toUpperCase() + " Button", Toast.LENGTH_SHORT).show();
    }
}