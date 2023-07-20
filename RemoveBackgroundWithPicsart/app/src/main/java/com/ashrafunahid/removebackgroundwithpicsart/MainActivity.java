package com.ashrafunahid.removebackgroundwithpicsart;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashrafunahid.removebackgroundwithpicsart.Utils.CommonUtil;
import com.ashrafunahid.removebackgroundwithpicsart.Utils.NetworkUtil;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity {

    ImageView back_arrow, add_image_btn, target_image;
    TextView app_title;
    Bitmap img;
    int CAMERA_REQUEST_CODE = 100;
    int GALLERY_REQUEST_CODE = 200;
    String responseText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        app_title.setText(R.string.remove_background);

        Picasso.get()
                .load("https://cdn.pixabay.com/photo/2015/04/19/08/32/marguerite-729510_1280.jpg")
                .placeholder(R.drawable.baseline_image_24)
                .into(target_image);

        add_image_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Creating alert builder
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
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
                alertBuilder.setNeutralButton("Check API", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new UploadImage().execute();
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

                img = (Bitmap) data.getExtras().get("data");
                target_image.setImageBitmap(img);

            } else if (requestCode == GALLERY_REQUEST_CODE) {

                target_image.setImageURI(data.getData());

            }
        }
    }

    class UploadImage extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            CommonUtil.loadProgressDialog(MainActivity.this);
        }

        @Override
        protected Void doInBackground(Void... voids) {

//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            img.compress(Bitmap.CompressFormat.PNG, 100, stream);
//            byte[] byteArray = stream.toByteArray();

            MediaType mediaType = MediaType.parse("multipart/form-data");
            RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("format", "PNG")
                    .addFormDataPart("output_type", "cutout")
                    .addFormDataPart("image_url","https://cdn.picsart.io/a8f229e2-5334-48e9-b177-797dd84d3edf")
                    .addFormDataPart("bg_image_url","https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885_1280.jpg")
                    .build();

            try {
                responseText = NetworkUtil.postRun(requestBody, "https://api.picsart.io/tools/1.0/removebg");
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            try {
                CommonUtil.dismissProgressDialog();
                Log.i("Nahid", responseText);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}