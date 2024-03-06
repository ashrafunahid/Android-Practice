package com.ashrafunahid.imageeditor.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ashrafunahid.imageeditor.R;
import com.ashrafunahid.imageeditor.databinding.ActivityResultBinding;

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