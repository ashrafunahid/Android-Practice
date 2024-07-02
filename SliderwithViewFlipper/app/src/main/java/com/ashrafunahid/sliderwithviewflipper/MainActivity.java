package com.ashrafunahid.sliderwithviewflipper;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
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

        findViewById(R.id.image_view_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToastMessage(getApplicationContext(), "Image View id 1");
            }
        });

        findViewById(R.id.image_view_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToastMessage(getApplicationContext(), "Image View id 2");
            }
        });

        findViewById(R.id.image_view_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToastMessage(getApplicationContext(), "Image View id 3");
            }
        });

        findViewById(R.id.image_view_4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToastMessage(getApplicationContext(), "Image View id 4");
            }
        });

    }

    public void showToastMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}