package com.ashrafunahid.cryptotest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button encBtn, dcrBtn, selectFile;
    ViewFlipper viewFlip;
    ClipboardManager clipboardManager;
    Encryptor encryptor;
    Decryptor decryptor;


    private int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        encBtn = (Button) findViewById(R.id.encBtn);
        dcrBtn = (Button) findViewById(R.id.dcrBtn);
        selectFile = (Button) findViewById(R.id.selectFile);
        viewFlip = (ViewFlipper) findViewById(R.id.viewFlip);
        encryptor = Encryptor.getEncrypter(true);
        decryptor = Decryptor.getDecrypter(true);
        clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        int images[] = {};
//        int images[] = {R.drawable.one, R.drawable.two, R.drawable.three};
        for (int i = 0; i < images.length; i++) {
            flipper(images[i]);
        }


        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(MainActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }


        };

        TedPermission.create()
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();

        encBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent encScreen = new Intent(MainActivity.this, Encoder.class);
//                startActivity(encScreen);
                File src = new File("/sdcard/Documents");
                Log.i("File", src.toString());
                encryptor.encrypt(src, src);
            }
        });

        dcrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent dcrScreen = new Intent(MainActivity.this, Decoder.class);
//                startActivity(dcrScreen);
                File src = new File("/sdcard/Documents");
                Log.i("File", src.toString());
                decryptor.decrypt(src, src);
            }
        });

        selectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                throw new RuntimeException("Test Crash");
////                Checking if the permission is guranted or not
//                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
//                {
//
////                If permission not guranted then request again for permission
//                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
//
//                }
////                If permission guranted then select a new file from storage
//                else
//                {
//                    selectfile();
//                }
            }
        });

    }

//    private void selectfile() {
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        intent.setType("application/*");
//        startActivityForResult(Intent.createChooser(intent, "Select File"), REQUEST_CODE);
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
////        Repeatedly check if permission is granted or not
//        if(requestCode == REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED)
//        {
//            selectfile();
//        }
//        else
//        {
//            // Ask again for permission if not granted
//            Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT);
//            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null){
////            Uri uri = data.getData();
//            File src = new File("/sdcard/Download");
//            Log.i("File", src.toString());
////            Log.i("Data", uri);
////            encryptor.encrypt(src, src);
//            decryptor.decrypt(src, src);
//        }
//    }

    public void flipper(int images) {
        ImageView img = new ImageView(this);
        img.setBackgroundResource(images);
        viewFlip.addView(img);
        viewFlip.setFlipInterval(3000);
        viewFlip.setAutoStart(true);
        viewFlip.setInAnimation(this, android.R.anim.slide_in_left);
        viewFlip.setOutAnimation(this, android.R.anim.slide_out_right);
    }

}