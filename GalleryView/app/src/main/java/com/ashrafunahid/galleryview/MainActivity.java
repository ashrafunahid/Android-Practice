package com.ashrafunahid.galleryview;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.TextView;
import android.widget.Toast;

import com.ashrafunahid.galleryview.Adapters.GalleryAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView galleryNumber;
    GalleryAdapter galleryAdapter;
    List<String> images;
    private static final int STORAGE_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        galleryNumber = findViewById(R.id.gallery_number);
        recyclerView = findViewById(R.id.recyclerview_gallery_images);

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{READ_EXTERNAL_STORAGE}, STORAGE_REQUEST_CODE);

        } else {
            loadImages();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{READ_EXTERNAL_STORAGE}, STORAGE_REQUEST_CODE);

        } else {
            loadImages();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{READ_EXTERNAL_STORAGE}, STORAGE_REQUEST_CODE);

        } else {
            loadImages();
        }
    }

    private void loadImages() {

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        images = ImagesGallery.listOfImages(this);
        galleryAdapter = new GalleryAdapter(this, images, new GalleryAdapter.PhotoListener() {
            @Override
            public void onPhotoClick(String path) {
                Toast.makeText(MainActivity.this, path, Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(galleryAdapter);
        galleryNumber.setText("Photos ("+images.size()+")");

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadImages();
            } else {
                Toast.makeText(MainActivity.this, "Permission Not Granted", Toast.LENGTH_SHORT).show();
            }
        }

    }
}