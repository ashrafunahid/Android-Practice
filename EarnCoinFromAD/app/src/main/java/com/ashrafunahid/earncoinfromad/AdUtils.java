package com.ashrafunahid.earncoinfromad;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AdUtils {
    public static long checkTimeDifference(String dateOld, String dateNow, String TAG) {
        Date nowDate;
        Date oldDate;
        try {
            oldDate = new SimpleDateFormat("dd MMM yyyy, HH:mm:ss", Locale.ENGLISH).parse(dateOld);
            Log.i(TAG, oldDate.toString());
            nowDate = new SimpleDateFormat("dd MMM yyyy, HH:mm:ss", Locale.ENGLISH).parse(dateNow);
            Log.i(TAG, nowDate.toString());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        long difference = nowDate.getTime() - oldDate.getTime();
        long days = (difference / (1000 * 60 * 60 * 24));
        long hours = ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
        long min = (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);
        long seconds = (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours) - (1000 * 60 * min)) / 1000;
        long totalDiffSec = ((min * 60) + seconds);
        hours = (hours < 0 ? -hours : hours);
//            Log.i("======= Hours"," :: "+hours);
//            Log.i(TAG, String.valueOf(totalDiffSec));
//            Toast.makeText(this, String.valueOf(seconds) , Toast.LENGTH_SHORT).show();
        return totalDiffSec;
    }
}
