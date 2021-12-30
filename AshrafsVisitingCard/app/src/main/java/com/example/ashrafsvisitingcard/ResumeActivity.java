package com.example.ashrafsvisitingcard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;

import com.github.barteksc.pdfviewer.PDFView;

public class ResumeActivity extends AppCompatActivity {

    PDFView open;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_resume);

        open = findViewById(R.id.pdfView);
        open.fromAsset("ashrafuddin.pdf").load();

    }
}