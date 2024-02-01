package com.ashrafunahid.asynctaskbackground;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Toast;

import com.ashrafunahid.asynctaskbackground.Classes.AsynchronousRequest;
import com.ashrafunahid.asynctaskbackground.Utils.NetworkUtil;
import com.ashrafunahid.asynctaskbackground.databinding.ActivityMainBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    String url, responseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Activity mActivity = this;

        binding.progressCircle.setVisibility(View.GONE);

        binding.goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               url =  binding.urlAddress.getText().toString().trim();
               if(!url.isEmpty() && URLUtil.isValidUrl(url)){
//                   new RunRequest().execute();
                   binding.progressCircle.setVisibility(View.VISIBLE);

                    new AsynchronousRequest(mActivity) {
                       @Override
                       public void onPreExecute() {
                           Toast.makeText(mActivity, url, Toast.LENGTH_SHORT).show();
                       }

                       @Override
                       public void doInBackground() {
                           try {
                               JSONObject jsonObject = new JSONObject(NetworkUtil.getRun(url));
                               responseText = String.valueOf(jsonObject);
                           } catch (Exception e) {
                               e.printStackTrace();
                           }
                       }

                       @Override
                       public void onPostExecute() {
                           try {
                               binding.progressCircle.setVisibility(View.GONE);
                               binding.responseText.setText(responseText);
                               binding.responseTime.setText(("Response Time: " + String.valueOf(NetworkUtil.responseDuration) + " Milliseconds"));
                           } catch (Exception e) {
                               e.printStackTrace();
                           }
                       }
                   }.execute();

               } else {
                   Toast.makeText(MainActivity.this, "Please provide a valid url address", Toast.LENGTH_SHORT).show();
               }
            }
        });
    }

}