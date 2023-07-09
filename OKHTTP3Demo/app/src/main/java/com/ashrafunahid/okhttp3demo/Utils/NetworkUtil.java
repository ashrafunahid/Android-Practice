package com.ashrafunahid.okhttp3demo.Utils;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class NetworkUtil {

    private static OkHttpClient okHttpClient = new OkHttpClient();

    public static String getRun(String url) throws IOException {

        Request request = new Request.Builder().url(url).build();

        Response response = okHttpClient.newCall(request).execute();

        return response.body().string();

    }

    public static String postRun(RequestBody requestBody, String url) throws IOException {

        Request request = new Request.Builder().url(url).post(requestBody).build();

        Response response = okHttpClient.newCall(request).execute();

        return response.body().string();

    }

}
