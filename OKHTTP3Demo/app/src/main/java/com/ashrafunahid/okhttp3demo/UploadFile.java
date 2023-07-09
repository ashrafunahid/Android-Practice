package com.ashrafunahid.okhttp3demo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;

public class UploadFile extends AppCompatActivity {

    ImageView back_arrow, add_image_btn, target_image;
    TextView app_title;

    int CAMERA_REQUEST_CODE = 100;
    int GALLERY_REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_file);

        back_arrow = (ImageView) findViewById(R.id.back_arrow);
        app_title = (TextView) findViewById(R.id.app_title);
        add_image_btn = (ImageView) findViewById(R.id.add_image_btn);
        target_image = (ImageView) findViewById(R.id.target_image);

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        app_title.setText(R.string.upload_image);

        Picasso.get()
                .load("https://cdn.pixabay.com/photo/2015/04/19/08/32/marguerite-729510_1280.jpg")
                .placeholder(R.drawable.baseline_image_24)
                .into(target_image);

        add_image_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Creating alert builder
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(UploadFile.this);
                alertBuilder.setCancelable(false);
                alertBuilder.setTitle("Choose Image From");
                alertBuilder.setIcon(R.drawable.baseline_image_24);
                alertBuilder.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, CAMERA_REQUEST_CODE);
                    }
                });
                alertBuilder.setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, GALLERY_REQUEST_CODE);
                    }
                });

//                Showing alert builder
                AlertDialog alertDialog = alertBuilder.create();
                alertDialog.show();


            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            if(requestCode == CAMERA_REQUEST_CODE) {

                Bitmap img = (Bitmap) data.getExtras().get("data");
                target_image.setImageBitmap(img);

            } else if (requestCode == GALLERY_REQUEST_CODE) {

                target_image.setImageURI(data.getData());

            }
        }
    }
}