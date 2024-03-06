package com.ashrafunahid.customvpn.View.Utils;

import android.net.Uri;

import com.ashrafunahid.customvpn.R;

public class Utils {
    // Convert drawable image Source to String
    public static String getImageUrl(int resourceId){
        return Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" +resourceId).toString();
    }
}
