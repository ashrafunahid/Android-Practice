package com.ibssbd.gobeautify.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
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

public class ChangeBackground extends AppCompatActivity {

    ImageView imageView, backButton, downloadButton, shareButton;
    FloatingActionButton floatingActionButton;
    String responseText;
    Uri imageUri;
    Uri backgroundUri = null;
    int IMAGE_REQUEST_CODE = 100;
    int BACKGROUND_REQUEST_CODE = 101;
    String downloadImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_background);

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

                ImagePicker.with(ChangeBackground.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(512)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start(IMAGE_REQUEST_CODE);
            }

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            imageUri = data.getData();
            Log.i("Nahid", imageUri.toString());

            showAlertDialog();

        }

        if (requestCode == BACKGROUND_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            backgroundUri = data.getData();
            Log.i("Nahid", imageUri.toString());
            Log.i("Nahid", backgroundUri.toString());

            new ChangeBackground.UploadImage(imageUri, backgroundUri).execute();

        }

        if (requestCode == BACKGROUND_REQUEST_CODE && resultCode == RESULT_CANCELED && data == null && data.getData() == null) {

//            backgroundUri = data.getData();
            Log.i("Nahid", imageUri.toString());
            Log.i("Nahid", backgroundUri.toString());

            new ChangeBackground.UploadImage(imageUri, backgroundUri).execute();

        }

    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ChangeBackground.this);
        builder.setTitle("Background Image");
        builder.setIcon(R.drawable.image_search_24);
        builder.setMessage("Please select the background image you want to use.");
        builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                ImagePicker.with(ChangeBackground.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(512)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start(BACKGROUND_REQUEST_CODE);

            }
        });
        builder.setNegativeButton("Not Interested", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                new ChangeBackground.UploadImage(imageUri, backgroundUri).execute();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setAllCaps(false);
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setAllCaps(false);
    }

    public class UploadImage extends AsyncTask<Void, Void, Void> {
        Uri imageUri, backgroundUri;
        public UploadImage(Uri imageUri, Uri backgroundUri) {
            this.imageUri = imageUri;
            this.backgroundUri = backgroundUri;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            CommonUtil.loadProgressDialog(ChangeBackground.this);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            File img = new File(imageUri.getEncodedPath());
            MediaType mediaType = MediaType.parse("multipart/form-data");
            RequestBody requestBody;
            if(backgroundUri != null) {
                File bg = new File(backgroundUri.getEncodedPath());
                requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("format", "PNG")
                        .addFormDataPart("output_type", "cutout")
                        .addFormDataPart("image", img.getName(), RequestBody.create(mediaType,img))
                        .addFormDataPart("bg_image", bg.getName(), RequestBody.create(mediaType,bg))
                        .build();
            }
            else {
                requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("format", "PNG")
                        .addFormDataPart("output_type", "cutout")
                        .addFormDataPart("image", img.getName(), RequestBody.create(mediaType, img))
                        .build();
            }

            try {
                responseText = NetworkUtil.postRun(requestBody, CommonUtil.BASE_URL + "removebg");
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