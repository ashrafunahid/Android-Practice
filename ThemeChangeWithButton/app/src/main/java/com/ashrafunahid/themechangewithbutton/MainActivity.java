package com.ashrafunahid.themechangewithbutton;

import static com.ashrafunahid.themechangewithbutton.Classes.MyApp.editor;
import static com.ashrafunahid.themechangewithbutton.Classes.MyApp.sharedPreferences;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch themeSwitchButton;
    boolean nightModeEnable;
    String TAG = "ThemeCheck";

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

        themeSwitchButton = findViewById(R.id.theme_switch_button);
        themeSwitchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeThemeMode(!nightModeEnable, true);
            }
        });

    }

    private void changeThemeMode(boolean mode, boolean check){
        AppCompatDelegate.setDefaultNightMode(mode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
        themeSwitchButton.setChecked(mode);
        if (check) {
            editor.putString("nightMode", String.valueOf(nightModeEnable = mode));
            editor.commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
        if (sharedPreferences.getString("nightMode", "").equals("")){
            nightModeEnable = (getApplicationContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;
//            changeThemeMode(nightModeEnable, false);
            themeSwitchButton.setChecked(nightModeEnable);
        } else {
            nightModeEnable = !sharedPreferences.getString("nightMode", "").equals("false");
            changeThemeMode(nightModeEnable, true);
        }
    }

//    @Override
//    public void onConfigurationChanged(@NonNull Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        Log.i(TAG, "onConfigurationChanged: ");
//        if (sharedPreferences.getString("nightMode", "").equals("")){
//            nightModeEnable = (newConfig.uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;
////            changeThemeMode(nightModeEnable, false);
//            themeSwitchButton.setChecked(nightModeEnable);
//        } else {
//            nightModeEnable = !sharedPreferences.getString("nightMode", "").equals("false");
//            changeThemeMode(nightModeEnable, true);
//        }
//    }
}