package com.ashrafunahid.okhttp3demo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ashrafunahid.okhttp3demo.Utils.CommonUtil;
import com.ashrafunahid.okhttp3demo.Utils.NetworkUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {

    EditText username, email, password;
    Button POSTButton, GETButton, UploadButton;
    TextView responseTV;
    String responseText = "";
//    ProgressDialog progressDialog;
    String BASE_URL = "https://api.ashrafunahid.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);


//            progressDialog = new ProgressDialog(MainActivity.this);
//            progressDialog.setMessage("Loading...");

            username = (EditText) findViewById(R.id.username);
            email = (EditText) findViewById(R.id.email);
            password = (EditText) findViewById(R.id.password);
            POSTButton = (Button) findViewById(R.id.post_request_btn);
            GETButton = (Button) findViewById(R.id.get_request_btn);
            UploadButton = (Button) findViewById(R.id.file_upload_btn);
            responseTV = (TextView) findViewById(R.id.response_tv);

            UploadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, UploadFile.class);
                    startActivity(intent);
                }
            });

            POSTButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(username.getText().toString().isEmpty()) {
                        username.requestFocus();
                        username.setError("Please enter your username");
                    }
                    else if(email.getText().toString().isEmpty()) {
                        email.requestFocus();
                        email.setError("Please enter your email address");
                    }
                    else if(!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                        email.requestFocus();
                        email.setError("Please enter a valid email address");
                    } else if (password.getText().toString().isEmpty()) {
                        password.requestFocus();
                        password.setError("Please enter a new password");
                    }
                    else {
                        if (CommonUtil.isOnline(MainActivity.this)){
                            new RunPOSTRequest().execute();
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Please check your internet connection...", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

            GETButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (CommonUtil.isOnline(MainActivity.this)) {
                        new RunGetRequest().execute();
                    } else {
                        Toast.makeText(MainActivity.this, "Please check your internet connection...", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }

    class RunPOSTRequest extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            CommonUtil.loadProgressDialog(MainActivity.this);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            RequestBody requestBody = new FormBody.Builder()
                    .add("username", username.getText().toString())
                    .add("email", email.getText().toString())
                    .add("password", password.getText().toString())
                    .build();

            try {
                responseText = NetworkUtil.postRun(requestBody, BASE_URL + "register.php");
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            try {
                CommonUtil.dismissProgressDialog();
                responseTV.setText(responseText);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    class RunGetRequest extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            CommonUtil.loadProgressDialog(MainActivity.this);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
//                responseText = NetworkUtil.getRun(BASE_URL + "fetchusers.php");
                JSONObject jsonObject = new JSONObject(NetworkUtil.getRun(BASE_URL + "fetchusers.php"));
                responseText = jsonObject.getString("error");
                JSONArray users = jsonObject.getJSONArray("users");
//                FetchUserResponse response = new FetchUserResponse(users, responseText);
                for (int i = 0; i < users.length(); i++) {
                    JSONObject user = users.getJSONObject(i);
                    Log.i("Nahid", user.getString("id"));
                    Log.i("Nahid", user.getString("username"));
                    Log.i("Nahid", user.getString("email"));
                    responseText = user.getString("username");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);

            try {
                CommonUtil.dismissProgressDialog();
                responseTV.setText(responseText);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
//    private void makeGetrequest(){
//        String url = BASE_URL + "fetchusers.php";
//        Request getRequest = new Request.Builder()
//                .url(url)
//                .build();
//
//        Log.i("Nahid", "Request Build Done");
//        okHttpClient.newCall(getRequest).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//                if (response.isSuccessful()) {
//                    Log.i("Nahid", response.body().string());
//                    responseTV.setText(response.body().byteString().toString());
//                    MainActivity.this.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//
//                            } catch (Exception e) {
//                                throw new RuntimeException(e);
//                            }
//                        }
//                    });
//                }
//            }
//        });
//    }

}

