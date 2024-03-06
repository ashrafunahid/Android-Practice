package com.ashrafunahid.customvpn.View.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.ashrafunahid.customvpn.R;
import com.ashrafunahid.customvpn.View.Adapter.ServerListAdapter;
import com.ashrafunahid.customvpn.View.Model.ServerInfo;
import com.ashrafunahid.customvpn.databinding.ActivityServerListFromJsonBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ServerListFromJsonActivity extends AppCompatActivity {
    ActivityServerListFromJsonBinding binding;
    RecyclerView serverListRecycler;
    ServerListAdapter serverListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityServerListFromJsonBinding.inflate(getLayoutInflater());
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
//            mlp.leftMargin = insets.left;
//            mlp.bottomMargin = insets.bottom;
//            mlp.rightMargin = insets.right;
//            mlp.topMargin = insets.top;
            v.setLayoutParams(mlp);
            return WindowInsetsCompat.CONSUMED;
        });
        setContentView(binding.getRoot());

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        serverListRecycler = findViewById(R.id.server_list_recycler);
        serverListRecycler.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<ServerInfo> serverList = parseJsonData();

        serverListAdapter = new ServerListAdapter(this, serverList);
        serverListRecycler.setAdapter(serverListAdapter);
    }

    private ArrayList<ServerInfo> parseJsonData() {
        ArrayList<ServerInfo> serverList = new ArrayList<>();
        try{
//            Read the json file from Asset Folder
            InputStream inputStream = getAssets().open("server_list.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            String jsonData = new String(buffer, StandardCharsets.UTF_8);

//            Parsing the data
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String flag = jsonObject.getString("flag");
                String countryName = jsonObject.getString("country");
                String ipAddress = jsonObject.getString("ip");
                int port = jsonObject.getInt("port");
                int pings = jsonObject.getInt("ping");

//                Adding data with Model class
                ServerInfo serverInfo = new ServerInfo(countryName, flag, ipAddress, port, pings);
                serverList.add(serverInfo);

            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return serverList;
    }
}