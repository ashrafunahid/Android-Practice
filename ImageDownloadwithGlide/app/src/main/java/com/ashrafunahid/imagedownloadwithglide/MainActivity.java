package com.ashrafunahid.imagedownloadwithglide;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    final String imageUrl = "https://assets.gqindia.com/photos/5dfb53babd97c00008f3a0bf/16:9/w_2560%2Cc_limit/top-image.jpg";
    ImageView imageView;
    Button downloadWithGlide, downloadWithPicasso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imageView = findViewById(R.id.image_view);
        downloadWithGlide = findViewById(R.id.download_with_glide);
        downloadWithPicasso = findViewById(R.id.download_with_picasso);

        downloadWithGlide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadImageWithGlide(imageUrl);
            }
        });

        downloadWithPicasso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Picasso.get()
                        .load(imageUrl)
                        .into(imageDownloadWithPicasso(imageUrl));
            }
        });

    }

    void downloadImageWithGlide(String imageURL){

        if (!verifyPermissions()) {
            return;
        }

//        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + getString(R.string.app_name) + "/";
//
//        final File dir = new File(dirPath);

//        final String fileName = imageURL.substring(imageURL.lastIndexOf('/') + 1);

        Glide.with(MainActivity.this)
                .load(imageURL)
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, @Nullable Object model, @NonNull com.bumptech.glide.request.target.Target<Drawable> target, boolean isFirstResource) {
                        Toast.makeText(MainActivity.this, "On Load Failed", Toast.LENGTH_SHORT).show();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(@NonNull Drawable resource, @NonNull Object model, com.bumptech.glide.request.target.Target<Drawable> target, @NonNull DataSource dataSource, boolean isFirstResource) {
                        Toast.makeText(MainActivity.this, "On Resource Ready", Toast.LENGTH_SHORT).show();
                        final String fileName = imageURL.substring(imageURL.lastIndexOf('/') + 1);
                        Bitmap bitmap = ((BitmapDrawable)resource).getBitmap();
                        Toast.makeText(MainActivity.this, "Saving Image...", Toast.LENGTH_SHORT).show();
                        saveImageWithGlide(bitmap, fileName);
                        return false;
                    }
                })
                .into(imageView);

    }
    private void saveImageWithGlide(Bitmap image, String imageFileName) {

        File imageFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), imageFileName + ".png");
        try {
            OutputStream fOut = new FileOutputStream(imageFile);
            image.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.close();
            Toast.makeText(MainActivity.this, "Image Saved at: " + imageFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "Error while saving image!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    //Save Image with Picasso
    private Target imageDownloadWithPicasso(final String url){
        Target target = new Target(){

            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {

                        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + url);
                        try {
                            file.createNewFile();
                            FileOutputStream ostream = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, ostream);
                            ostream.flush();
                            ostream.close();
                            Toast.makeText(MainActivity.this, "Image Save Successfull", Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            Log.e("IOException", e.getLocalizedMessage());
                        }
                    }
                }).start();

            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                Toast.makeText(MainActivity.this, "Bitmap Load Failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                Toast.makeText(MainActivity.this, "On Prepare Load", Toast.LENGTH_SHORT).show();
            }
        };
        return target;
    }

    public Boolean verifyPermissions() {

        // This will return the current Status
        int permissionExternalMemory = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionExternalMemory != PackageManager.PERMISSION_GRANTED) {

            String[] STORAGE_PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            // If permission not granted then ask for permission real time.
            ActivityCompat.requestPermissions(this, STORAGE_PERMISSIONS, 1);
            return false;
        }

        return true;

    }
}