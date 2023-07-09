package com.ashrafunahid.retrofitdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiInterface  = RetrofitInstance.getRetrofit().create(ApiInterface.class);

        apiInterface.getPosts().enqueue(new Callback<List<PostPojo>>() {
            @Override
            public void onResponse(Call<List<PostPojo>> call, Response<List<PostPojo>> response) {

                if(response.body() != null)
                {
                    List<PostPojo> postPojo = response.body();
//                    postPojo.stream();
                    for (PostPojo posts : postPojo){
                        Log.i("Retrofit_Body", "Post ID: " + Float.toString(posts.getId()));
                        Log.i("Retrofit_Body", "User ID: " + Float.toString(posts.getUserId()));
                        Log.i("Retrofit_Body", "Title: " + posts.getTitle());
                        Log.i("Retrofit_Body", "Body: " + posts.getBody());
                    }
                    Log.i("Retrofit_Body", String.valueOf(response.raw()));
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Response Body is Null", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<PostPojo>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}