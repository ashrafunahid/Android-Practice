package com.ashrafunahid.filesystemdemoexternalstorage;

import static android.os.Environment.getExternalStorageDirectory;
import static android.os.Environment.getExternalStoragePublicDirectory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    TextView private_text_view, public_text_view;
    Button create_private, read_private, create_public, read_public;

    String FILE_NAME = "MyFile";

    private final int REQUEST_PERMISSION_WRITE = 1001;

    private boolean permissionGranted = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();

        editText = (EditText) findViewById(R.id.editText);
        private_text_view = (TextView) findViewById(R.id.private_text_view);
        public_text_view = (TextView) findViewById(R.id.public_text_view);
        create_private = (Button) findViewById(R.id.create_private);
        read_private = (Button) findViewById(R.id.read_private);
        create_public = (Button) findViewById(R.id.create_public);
        read_public = (Button) findViewById(R.id.read_public);

        File file = getExternalStoragePublicDirectory("MyDirectory");
        Log.d("FilePath", file.getAbsolutePath());

        create_private.setOnClickListener(this::createPrivateFiles);
        read_private.setOnClickListener(this::readPrivateFiles);
        create_public.setOnClickListener(this::createPublicfiles);
        read_public.setOnClickListener(this::readPublicFiles);

    }

    private void readPublicFiles(View view) {

        if(!permissionGranted){
            checkPermission();
        }

        File file = new File(getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), FILE_NAME);

        String result = readFromFile(file);

        public_text_view.setText(result);

    }

    private void createPublicfiles(View view) {

        if(!permissionGranted) {
            checkPermission();
        }

        File file = new File(getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), FILE_NAME);

        writeToFile(file);

    }

    private void readPrivateFiles(View view) {

        File file = new File(getExternalFilesDir(null), FILE_NAME);

        String result = readFromFile(file);

        private_text_view.setText(result);

    }

    private void createPrivateFiles(View view) {

        File file = new File(getExternalFilesDir(null), FILE_NAME);

        writeToFile(file);

    }

    private String readFromFile(File file) {
        FileInputStream fis = null;
        int read;
        StringBuilder sb = new StringBuilder();

        try {

            fis = new FileInputStream(file);

            while ((read = fis.read())!= -1) {
                sb.append((char) read);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        finally {
            if (fis!= null) {
                try {
                    fis.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return sb.toString();
    }

    private void writeToFile(File file){
        FileOutputStream fos = null;

        String data = editText.getText().toString();

        try {
            fos = new FileOutputStream(file);

            fos.write(data.getBytes());

            Log.d("File", "writeToFile: Data Written to File: "+ file.getName() + "Path: " + file.getPath());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        finally {
            if (fos!= null) {
                try {
                    fos.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private boolean isExternalStorageWriteable() {
        String state  = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    public boolean isExternalStorageReadable(){
        String state  = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

    private boolean checkPermission() {

        if(!isExternalStorageReadable() || !isExternalStorageWriteable()){
            Toast.makeText(getApplicationContext(), "This app is only works on devices with usable external storage", Toast.LENGTH_SHORT).show();
            return false;
        }

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_WRITE);
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case REQUEST_PERMISSION_WRITE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permissionGranted = true;
                    Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}