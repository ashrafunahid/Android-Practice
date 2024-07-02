package com.ashrafunahid.themechangewithbutton;

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
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch themeSwitchButton;
    boolean userDefinedTheme;
    boolean nightMode;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

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
        sharedPreferences = getSharedPreferences("ThemeChangeWithButton", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        userDefinedTheme = sharedPreferences.getBoolean("userDefinedTheme", false);
        checkSystemThemeMode();
        themeSwitchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nightMode) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor.putBoolean("userDefinedTheme", true);
                    editor.putBoolean("nightMode", false);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor.putBoolean("userDefinedTheme", true);
                    editor.putBoolean("nightMode", true);
                }
                editor.apply();
            }
        });

    }

    private void checkSystemThemeMode() {
        if (userDefinedTheme) {
            nightMode = sharedPreferences.getBoolean("nightMode", false);
            setApplicationThemeMode();
        }
        else {
            if ((getApplicationContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_YES) == Configuration.UI_MODE_NIGHT_YES) {
                nightMode = true;
                setApplicationThemeMode();
            } else {
                nightMode = false;
                setApplicationThemeMode();
            }
        }

    }
    private void setApplicationThemeMode(){
        if (nightMode) {
            themeSwitchButton.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            themeSwitchButton.setChecked(false);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkSystemThemeMode();
    }

}