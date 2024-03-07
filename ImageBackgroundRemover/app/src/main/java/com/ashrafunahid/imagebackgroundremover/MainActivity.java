package com.ashrafunahid.imagebackgroundremover;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.ashrafunahid.imagebackgroundremover.databinding.ActivityMainBinding;
import com.slowmac.autobackgroundremover.BackgroundRemover;
import com.slowmac.autobackgroundremover.OnBackgroundChangeListener;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import kotlin.jvm.JvmField;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    final int GALLERY_REQUEST_CODE = 100;
    final int CAMERA_REQUEST_CODE = 101;
    final int PERMISSION_REQUEST_CODE = 102;
    BackgroundRemover backgroundRemover;
    String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        requestPermissions();

        backgroundRemover = BackgroundRemover.INSTANCE;

        binding.cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                    requestPermissions();
                } else {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAMERA_REQUEST_CODE);
                }
            }
        });

        binding.galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_REQUEST_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST_CODE) {
            try {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                if (bitmap != null) {
                    backgroundRemover.bitmapForProcessing(bitmap, true, new OnBackgroundChangeListener() {
                        @Override
                        public void onSuccess(@NonNull Bitmap bitmap) {
                            Uri uri = getImageUri(bitmap);
                            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                            intent.setData(uri);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailed(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, "Failed to remove background from the image.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (resultCode == RESULT_OK && requestCode == GALLERY_REQUEST_CODE) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver() , data.getData());
                if (bitmap != null) {
                    backgroundRemover.bitmapForProcessing(bitmap, true, new OnBackgroundChangeListener() {
                        @Override
                        public void onSuccess(@NonNull Bitmap bitmap) {
                            Uri uri = getImageUri(bitmap);
                            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                            intent.setData(uri);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailed(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, "Failed to remove background from the image.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public Uri getImageUri(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Title", "Desc");
        return Uri.parse(path);
    }

    private boolean requestPermissions(){

        List<String> permissionList = new ArrayList<>();

        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            }
        }

        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]), PERMISSION_REQUEST_CODE);
            return false;
        }

        return true;

    }
}