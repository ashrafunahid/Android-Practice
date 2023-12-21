package com.ashrafunahid.earncoinfromrewardedad;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeUtil {

    public static String getTime(){
        String pattern = "MM/dd/yyyy HH:mm:ss";
        DateFormat df = new SimpleDateFormat(pattern);
        Date currentTime = Calendar.getInstance().getTime();
        return df.format(currentTime);
    }
    public static Date getDate(){
        return Calendar.getInstance().getTime();
    }

    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillieSecond = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillieSecond, timeUnit);
    }

}
