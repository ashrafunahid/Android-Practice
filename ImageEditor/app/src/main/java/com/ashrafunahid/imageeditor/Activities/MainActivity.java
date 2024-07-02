package com.ashrafunahid.imageeditor.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import com.ashrafunahid.imageeditor.R;
import com.ashrafunahid.imageeditor.databinding.ActivityMainBinding;
import com.dsphotoeditor.sdk.activity.DsPhotoEditorActivity;
import com.dsphotoeditor.sdk.utils.DsPhotoEditorConstants;


import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    final int GALLERY_REQUEST_CODE = 100;
    final int CAMERA_REQUEST_CODE = 300;
    final int EDITOR_REQUEST_CODE = 200;
    int PERMISSION_REQUEST_CODE = 101;
    String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_REQUEST_CODE);
            }
        });

        binding.cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions();
                } else {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAMERA_REQUEST_CODE);
                }
            }
        });

        requestPermissions();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST_CODE) {
            if (data.getData() != null) {
                Uri imagePath = data.getData();
                Intent editorIntent = new Intent(MainActivity.this, DsPhotoEditorActivity.class);
                editorIntent.setData(imagePath);
                editorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_OUTPUT_DIRECTORY, "ImageEditor");

//                int[] toolsToHide = {DsPhotoEditorActivity.TOOL_ORIENTATION, DsPhotoEditorActivity.TOOL_CROP};
//                editorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_TOOLS_TO_HIDE, toolsToHide);
                startActivityForResult(editorIntent, EDITOR_REQUEST_CODE);
            }
        }

        if (requestCode == CAMERA_REQUEST_CODE) {
            Bitmap cameraPhoto = (Bitmap) data.getExtras().get("data");
            Uri uri = getImageUri(cameraPhoto);

            Intent editorIntent = new Intent(MainActivity.this, DsPhotoEditorActivity.class);
            editorIntent.setData(uri);
            editorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_OUTPUT_DIRECTORY, "ImageEditor");

//            int[] toolsToHide = {DsPhotoEditorActivity.TOOL_ORIENTATION, DsPhotoEditorActivity.TOOL_CROP};
//            editorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_TOOLS_TO_HIDE, toolsToHide);
            startActivityForResult(editorIntent, EDITOR_REQUEST_CODE);
        }

        if (requestCode == EDITOR_REQUEST_CODE) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    if (data.getData() != null) {
//                        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
//                        intent.setData(data.getData());
//                        startActivity(intent);
//                    }
//                }
//            });
            if (data.getData() != null) {
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                intent.setData(data.getData());
                startActivity(intent);
            }
        }

    }

    public Uri getImageUri(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Title", "Desc");
        return Uri.parse(path);
    }

    private boolean requestPermissions() {

        List<String> permissionList = new ArrayList<>();

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
                permissionList.add(permission);
            }
        }

        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]), PERMISSION_REQUEST_CODE);
            return false;
        }

        return true;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

//        if (requestCode == PERMISSION_REQUEST_CODE) {
//
//            HashMap<String, Integer> permissionResult = new HashMap<>();
//            int deniedCount = 0;
//
//            for (int i = 0; i < grantResults.length; i++) {
//                if(grantResults[i] != PackageManager.PERMISSION_GRANTED) {
//                    permissionResult.put(permissions[i], grantResults[i]);
//                    deniedCount++;
//                }
//            }
//
//            if(deniedCount == 0) {
//                Toast.makeText(this, "Permissions granted", Toast.LENGTH_SHORT).show();
//            }
//            else {
//                for(Map.Entry<String, Integer> entry: permissionResult.entrySet()){
//                    String perName = entry.getKey();
//                    int perResult = entry.getValue();
//
//                    if(ActivityCompat.shouldShowRequestPermissionRationale(this, perName)) {
//
//                        new AlertDialog.Builder(this)
//                                .setCancelable(false)
//                                .setMessage("These permissions are required to use all the features of this application.")
//                                .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.dismiss();
//                                        requestPermissions();
//                                    }
//                                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.dismiss();
//                                    }
//                                }).create().show();
//                    }
//                    else {
//                        new AlertDialog.Builder(this)
//                                .setCancelable(false)
//                                .setMessage("To use all the features please allow required permissions in [Settings] > [Permissions]")
//                                .setPositiveButton("Go to Settings", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.dismiss();
//                                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", getPackageName(), null));
//                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                        startActivity(intent);
//                                        finish();
//                                    }
//                                }).setNegativeButton("No, Exit App", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.dismiss();
//                                        finish();
//                                    }
//                                }).create().show();
//                        break;
//                    }
//                }
//            }
//
//        }

    }
}