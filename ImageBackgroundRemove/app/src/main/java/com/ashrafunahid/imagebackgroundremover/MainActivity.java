package com.ashrafunahid.imagebackgroundremover;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import com.ashrafunahid.imagebackgroundremover.databinding.ActivityMainBinding;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import dev.eren.removebg.RemoveBg;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    final int GALLERY_REQUEST_CODE = 100;
    final int CAMERA_REQUEST_CODE = 101;
    final int PERMISSION_REQUEST_CODE = 102;
    RemoveBg removeBg;
    String[] permissions = {android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        requestPermissions();
        removeBg = new RemoveBg(this);

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
        if (resultCode == RESULT_OK && requestCode ==  CAMERA_REQUEST_CODE) {
            try {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
////                Uri uri = getImageUri(bitmap);
////                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
////                intent.setData(uri);
////                startActivity(intent);
//                if (bitmap != null) {
//                    Continuation<? super Unit> Continuation = null;
//                    Flow<Bitmap> output = (Flow<Bitmap>) removeBg.clearBackground(bitmap).collect((bitmap1, continuation) -> bitmap, Continuation
//                    );
//                    Log.d("onActivityResult: ", output.toString());
//                }
                RemoveBg remover = new RemoveBg(this);

                remover.clearBackground(bitmap)
                        .collect(output -> {
                            bitmaptest = output;
                            return Unit.INSTANCE;
                        });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (resultCode == RESULT_OK && requestCode == GALLERY_REQUEST_CODE) {

        }
    }

    public Uri getImageUri(Bitmap bitmap){
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