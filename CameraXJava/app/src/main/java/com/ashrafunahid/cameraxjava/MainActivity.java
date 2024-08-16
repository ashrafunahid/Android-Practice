package com.ashrafunahid.cameraxjava;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.core.VideoCapture;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ImageAnalysis.Analyzer {

    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    PreviewView previewView;
    Button bTakePicture, bRecording;
    private ImageCapture imageCapture;
    @SuppressLint("RestrictedApi")
    private VideoCapture videoCapture;
    private ImageAnalysis imageAnalysis;

    String[] permissions = {android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
    String[] permissions33 = {android.Manifest.permission.CAMERA, Manifest.permission.READ_MEDIA_IMAGES};
    int PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bTakePicture = findViewById(R.id.bCapture);
        bRecording = findViewById(R.id.bRecord);
        previewView = findViewById(R.id.previewView);

        requestPermission();

        bTakePicture.setOnClickListener(this);
        bRecording.setOnClickListener(this);

        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                startCameraX(cameraProvider);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }, getExecutor());

    }

    private Executor getExecutor() {
        return ContextCompat.getMainExecutor(this);
    }

    @SuppressLint("RestrictedApi")
    private void startCameraX(ProcessCameraProvider cameraProvider) {
        cameraProvider.unbindAll();

        // Camera Selector
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        // Show Preview
        Preview preview = new Preview.Builder().build();
        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        // Image Capture
        imageCapture = new ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build();

        // Record Video
        videoCapture = new VideoCapture.Builder()
                .setVideoFrameRate(30)
                .build();

        // Image Analysis
        imageAnalysis = new ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build();

        cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, preview, imageCapture, videoCapture);

    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bCapture) {
            capturePhoto();
        } else if (v.getId() == R.id.bRecord) {
            if (bRecording.getText() == getResources().getText(R.string.record_video)) {
                bRecording.setText(getResources().getText(R.string.stop_recording));
                recordVideo();
            } else {
                bRecording.setText(getResources().getText(R.string.record_video));
                videoCapture.stopRecording();
            }
        }
    }

    @SuppressLint("RestrictedApi")
    private void recordVideo() {
        if (videoCapture != null) {
            File movieDir = new File("/mnt/sdcard/Movies/CameraXMovies");
            if (!movieDir.exists()) {
                movieDir.mkdir();
            }
            Date date = new Date();
            String timeStamp = String.valueOf(date.getTime());
            String videoFilePath = movieDir.getAbsolutePath() + "/" + timeStamp + ".mp4";
            File videoFile = new File(videoFilePath);

            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            videoCapture.startRecording(
                    new VideoCapture.OutputFileOptions.Builder(videoFile).build(),
                    getExecutor(),
                    new VideoCapture.OnVideoSavedCallback() {
                        @Override
                        public void onVideoSaved(@NonNull VideoCapture.OutputFileResults outputFileResults) {
                            Toast.makeText(MainActivity.this, "Video has been saved successfully.", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(int videoCaptureError, @NonNull String message, @Nullable Throwable cause) {
                            Toast.makeText(MainActivity.this, "Error saving video" + message, Toast.LENGTH_SHORT).show();
                        }
                    }
            );
        }
    }

    private void capturePhoto() {
        File photoDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        if (!photoDir.exists()) {
            photoDir.mkdir();
        }
        Date date = new Date();
        String timeStamp = String.valueOf(date.getTime());
        String photoFilePath = photoDir + "/" + timeStamp + ".jpg";

        File photoFile = new File(photoFilePath);
        imageCapture.takePicture(
                new ImageCapture.OutputFileOptions.Builder(photoFile).build(),
                getExecutor(),
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                        Toast.makeText(MainActivity.this, "Photo has been saved successfully.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        Toast.makeText(MainActivity.this, "Error Saving Photo" + exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

    }

    @Override
    public void analyze(@NonNull ImageProxy image) {
        // Image Processing Here for Current Frame
        Log.d("Nahid", "Analyze: Got The Frame At: " + image.getImageInfo().getTimestamp());
        image.close();
    }

    public boolean requestPermission() {

        List<String> permissionList = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        {
            for (String permission : permissions33) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    permissionList.add(permission);
                }
            }
        }
        else {
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    permissionList.add(permission);
                }
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

        if (requestCode == PERMISSION_REQUEST_CODE) {

            HashMap<String, Integer> permissionResult = new HashMap<>();
            int deniedCount = 0;

            for (int i = 0; i < grantResults.length; i++) {
                if(grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    permissionResult.put(permissions[i], grantResults[i]);
                    deniedCount++;
                }
            }

            if(deniedCount == 0) {
//                Toast.makeText(this, "Permissions granted", Toast.LENGTH_SHORT).show();
            }
            else {
                for(Map.Entry<String, Integer> entry: permissionResult.entrySet()){
                    String perName = entry.getKey();
                    int perResult = entry.getValue();

                    if(ActivityCompat.shouldShowRequestPermissionRationale(this, perName)) {

                        new AlertDialog.Builder(this)
                                .setCancelable(false)
                                .setMessage("These permissions are required to use all the features of this application.")
                                .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        requestPermission();
                                    }
                                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).create().show();
                    }
                    else {
                        new AlertDialog.Builder(this)
                                .setCancelable(false)
                                .setMessage("To use all the features please allow required permissions in [Settings] > [Permissions]")
                                .setPositiveButton("Go to Settings", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", getPackageName(), null));
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }).setNegativeButton("No, Exit App", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        finish();
                                    }
                                }).create().show();
                        break;
                    }
                }
            }

        }

    }
}