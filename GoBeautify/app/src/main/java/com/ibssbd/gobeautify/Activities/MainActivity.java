package com.ibssbd.gobeautify.Activities;

import static android.os.Environment.getExternalStoragePublicDirectory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonObject;
import com.ibssbd.gobeautify.BottomNavigationsFragments.ChatFragment;
import com.ibssbd.gobeautify.BottomNavigationsFragments.FeedbackFragment;
import com.ibssbd.gobeautify.BottomNavigationsFragments.HomeFragment;
import com.ibssbd.gobeautify.BottomNavigationsFragments.NotificationsFragment;
import com.ibssbd.gobeautify.BottomNavigationsFragments.ProfileFragment;
import com.ibssbd.gobeautify.BottomNavigationsFragments.TextToImageGeneratorFragment;
import com.ibssbd.gobeautify.R;
import com.ibssbd.gobeautify.Utils.CommonUtil;
import com.ibssbd.gobeautify.Utils.NetworkUtil;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.security.Permission;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    int PERMISSION_REQUEST_CODE = 100;
    String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        For hide status bar
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        OR
//        WindowInsetsControllerCompat windowInsetsCompat = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
//        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
//
//        windowInsetsCompat.hide(WindowInsetsCompat.Type.statusBars());
//        windowInsetsCompat.setSystemBarsBehavior(WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
//        if (Build.VERSION.SDK_INT >= 21) {
//            Window window = this.getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.setStatusBarColor(this.getResources().getColor(R.color.transparent));
//        }
        setContentView(R.layout.activity_main);

//
//// root layout of your activity
//        ConstraintLayout root = findViewById(R.id.root_layout);
//
//        ViewCompat.setOnApplyWindowInsetsListener(
//                root
//                , (view, windowInsets) -> {
//                    Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
//                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
//                    params.bottomMargin = insets.bottom;
//                    return WindowInsetsCompat.CONSUMED;
//                });

//        Request for user permission
        requestPermissions();

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        loadFragment(new HomeFragment());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public boolean requestPermissions() {

        List<String> permissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
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
                                        requestPermissions();
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
                                    }
                                }).setNegativeButton("I'll do it later.", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).create().show();
                        break;
                    }
                }
            }

        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;

        if(item.getItemId() == R.id.home){
            fragment = new HomeFragment();
        } else if(item.getItemId() == R.id.chat) {
            fragment = new ChatFragment();
        } else if (item.getItemId() == R.id.generator) {
            fragment = new TextToImageGeneratorFragment();
        }

        if (fragment != null) {
            loadFragment(fragment);
        }

        return true;
    }

    void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, fragment).commit();
    }

}