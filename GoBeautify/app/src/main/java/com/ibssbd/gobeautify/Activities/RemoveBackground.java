package com.ibssbd.gobeautify.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.URLUtil;
import android.widget.ImageView;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ibssbd.gobeautify.R;
import com.ibssbd.gobeautify.Utils.CommonUtil;
import com.ibssbd.gobeautify.Utils.NetworkUtil;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class RemoveBackground extends AppCompatActivity {

    ImageView imageView, backButton, downloadButton, shareButton;
    FloatingActionButton floatingActionButton;
    String responseText;
    String downloadImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_background);

        imageView = (ImageView) findViewById(R.id.image_view);
        backButton = (ImageView) findViewById(R.id.back_button);
        downloadButton = (ImageView) findViewById(R.id.download_button);
        shareButton = (ImageView) findViewById(R.id.share_button);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.add_image);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        downloadButton.setVisibility(View.INVISIBLE);
        shareButton.setVisibility(View.INVISIBLE);

        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DownloadManager.Request downloadRequest = new DownloadManager.Request(Uri.parse(downloadImageUrl));
                String title = URLUtil.guessFileName(downloadImageUrl, null, null);
                downloadRequest.setTitle(title);
                downloadRequest.setDescription("Downloading image...");
                String cookie = CookieManager.getInstance().getCookie(downloadImageUrl);
                downloadRequest.addRequestHeader("cookie", cookie);
                downloadRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                downloadRequest.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title);

                DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                downloadManager.enqueue(downloadRequest);

            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImagePicker.with(RemoveBackground.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(512)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri imageUri = data.getData();
            Log.i("Nahid", imageUri.toString());

            new RemoveBackground.UploadImage(imageUri).execute();

        }

    }

    public class UploadImage extends AsyncTask<Void, Void, Void> {
        Uri imageUri;
        public UploadImage(Uri imageUri) {
            this.imageUri = imageUri;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            CommonUtil.loadProgressDialog(RemoveBackground.this);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            File file = new File(imageUri.getEncodedPath());

//            MediaType mediaType = MediaType.parse("image/png");
            MediaType mediaType = MediaType.parse("multipart/form-data");
            RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("size", "1024x1024")
                    .addFormDataPart("image", file.getName(), RequestBody.create(mediaType,file))
                    .addFormDataPart("prompt", "on a beach")
                    .build();

            try {
                responseText = NetworkUtil.editImage(requestBody, "https://api.openai.com/v1/images/edits");
                Log.i("Nahid", responseText);
                JSONObject jsonObject = new JSONObject(responseText);
                JSONObject data = new JSONObject(jsonObject.getString("data"));
                downloadImageUrl = data.getString("url");
                Log.i("Nahid", downloadImageUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            try {
                Picasso.get()
                        .load(downloadImageUrl)
                        .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                        .networkPolicy(NetworkPolicy.NO_CACHE)
                        .into(imageView);
                CommonUtil.dismissProgressDialog();
                downloadButton.setVisibility(View.VISIBLE);
                shareButton.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}