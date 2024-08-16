package com.ashrafunahid.themechangewithbutton.Classes;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class MyApp extends Application {

    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;

    @Override
    public void onCreate() {
        super.onCreate();

        sharedPreferences = getSharedPreferences("ThemeChangeWithButton", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }
}
