package com.ibssbd.iptest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Toast;

import com.ibssbd.iptest.Utils.NetworkUtil;
import com.ibssbd.iptest.Utils.OnlineCheckUtil;
import com.ibssbd.iptest.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    String url;
    String responseText, status, country, countryCode, region, regionName, city, zip, lat, lon, timezone, isp, org, as, query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.progressCircle.setVisibility(View.GONE);

        binding.goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = binding.urlAddress.getText().toString().trim();
                if (!url.isEmpty() && URLUtil.isValidUrl(url)) {
                    if (OnlineCheckUtil.isOnline(MainActivity.this)) {
                        new RunGetRequest().execute();
                    } else {
                        Toast.makeText(MainActivity.this, "Please check your internet connection...", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Please provide a valid url address.", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    class RunGetRequest extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            binding.progressCircle.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                JSONObject jsonObject = new JSONObject(NetworkUtil.getRun(url));
                responseText = String.valueOf(jsonObject);
                status = jsonObject.getString("status");
                country = jsonObject.getString("country");
                countryCode = jsonObject.getString("countryCode");
                status = jsonObject.getString("region");
                region = jsonObject.getString("regionName");
                city = jsonObject.getString("city");
                zip = jsonObject.getString("zip");
                lat = jsonObject.getString("lat");
                lon = jsonObject.getString("lon");
                timezone = jsonObject.getString("timezone");
                isp = jsonObject.getString("isp");
                org = jsonObject.getString("org");
                as = jsonObject.getString("as");
                query = jsonObject.getString("query");
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);

            try {
                binding.progressCircle.setVisibility(View.GONE);
//                binding.responseTime.setText(String.valueOf(TimeUnit.MILLISECONDS.toSeconds(NetworkUtil.responseDuration)));
                binding.responseTime.setText("Response Time: " + String.valueOf(NetworkUtil.responseDuration) + " Milliseconds");
                binding.responseText.setText(responseText);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}