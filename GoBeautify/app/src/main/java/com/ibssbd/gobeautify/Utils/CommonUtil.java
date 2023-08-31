package com.ibssbd.gobeautify.Utils;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Window;
import android.widget.DatePicker;

import com.ibssbd.gobeautify.R;

public class CommonUtil {
//    For Picsart
    public static String BASE_URL = "https://api.picsart.io/tools/1.0/";
    public static String API_KEY = "YOUR API KEY";

//    For ChatGPT
    public static String API_URL = "https://api.openai.com/v1/completions";
    public static String API = "YOUR API KEY";
    private static Dialog dialog = null;

    public static void loadProgressDialog(Context context) {

        try{
            dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.google_progress_dialog);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);
            if(!dialog.isShowing()){
                dialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void dismissProgressDialog() {

        try {
            if(dialog != null) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static boolean isOnline(Context context) {
        try{
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String pickDate(Context context){

        final String[] theDate = new String[1];

        DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                theDate[0] = String.valueOf(dayOfMonth) + "-" + String.valueOf(month+1) + "-" + String.valueOf(year);
            }
        }, 1990, 1, 15);
        dialog.show();
        return theDate[0];

    }

}
