package com.ashrafunahid.asynctaskbackground.Utils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetworkUtil {

    private static OkHttpClient okHttpClient = new OkHttpClient();
    public static long responseDuration;

    public static String getRun(String url) throws IOException {

        Request request = new Request.Builder().url(url).build();

        Response response = okHttpClient.newCall(request).execute();

        long requestTime = response.sentRequestAtMillis();
        long responseTime = response.receivedResponseAtMillis();
        responseDuration = getDateDiff(requestTime, responseTime, TimeUnit.MILLISECONDS);

        return response.body().string();

    }

    public static String postRun(RequestBody requestBody, String url) throws IOException {

        Request request = new Request.Builder().url(url).post(requestBody).build();

        Response response = okHttpClient.newCall(request).execute();

        return response.body().string();

    }

    public static long getDateDiff(long requestTime, long responseTime, TimeUnit timeUnit) {
        long diffInMillieSecond = responseTime - requestTime;
        return timeUnit.convert(diffInMillieSecond, timeUnit);
    }

}

