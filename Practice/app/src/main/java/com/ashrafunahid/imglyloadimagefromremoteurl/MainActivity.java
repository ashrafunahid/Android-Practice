package com.ashrafunahid.imglyloadimagefromremoteurl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ashrafunahid.imglyloadimagefromremoteurl.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    String url = "https://buffer.com/library/content/images/2023/10/free-images.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.launchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OpenPhotoFromRemoteURL.class);
                intent.putExtra("imagePath", url);
                startActivity(intent);
            }
        });
    }
}