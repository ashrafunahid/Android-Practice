package com.ibssbd.gobeautify.Utils;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetworkUtil {

    private static OkHttpClient okHttpClient = new OkHttpClient();

    public static String getRun(String url) throws IOException {

        Request request = new Request.Builder().url(url).build();

        Response response = okHttpClient.newCall(request).execute();

        return response.body().string();

    }

    public static String postRun(RequestBody requestBody, String url) throws IOException {

        Request request = new Request.Builder()
                .url(url)
                .post( requestBody)
                .addHeader("accept", "application/json")
                .addHeader("content-type", "multipart/form-data")
                .addHeader("x-picsart-api-key", CommonUtil.API_KEY)
                .build();

        Response response = okHttpClient.newCall(request).execute();

        return response.body().string();

    }

    public static String editImage(RequestBody requestBody, String url) throws IOException {

        Request request = new Request.Builder()
                .url(url)
                .post( requestBody)
                .addHeader("Authorization", "Bearer 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")
                .build();

        Response response = okHttpClient.newCall(request).execute();

        return response.body().string();

    }

}

