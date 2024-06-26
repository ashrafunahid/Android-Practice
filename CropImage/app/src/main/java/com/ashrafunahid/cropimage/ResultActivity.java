package com.ashrafunahid.cropimage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ashrafunahid.cropimage.databinding.ActivityResultBinding;

public class ResultActivity extends AppCompatActivity {

    ActivityResultBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.resultImage.setImageURI(getIntent().getData());

    }
}