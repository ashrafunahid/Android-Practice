package com.ashrafunahid.retrofittutorial;

import android.content.Context;
import android.content.SharedPreferences;

import com.ashrafunahid.retrofittutorial.ResponseModel.User;

public class SharedPrefManager {

    private static String SHARED_PREF_NAME = "RetrofitTutorial";
    private SharedPreferences sharedPreferences;
    Context context;
    private SharedPreferences.Editor editor;

    public SharedPrefManager(Context context) {
        this.context = context;
    }

    public void saveUser(User user) {

        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putInt("id", user.getId());
        editor.putString("username", user.getUsername());
        editor.putString("email", user.getEmail());
        editor.putBoolean("loggedIn", true);
        editor.apply();

    }

    public boolean isLoggedIn() {

        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("loggedIn", false);

    }

    public User getUser() {

        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(sharedPreferences.getInt("id", -1),
                sharedPreferences.getString("username", null),
                sharedPreferences.getString("email", null)
        );

    }

    public void logout() {

        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

    }

}
