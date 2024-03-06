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
import com.ashrafunahid.customvpn.View.Model.Servers;
import com.ashrafunahid.customvpn.View.Utils.Utils;
import com.ashrafunahid.customvpn.databinding.ActivityServerListBinding;

import java.util.ArrayList;

public class ServerListActivity extends AppCompatActivity {
    ActivityServerListBinding binding;
    RecyclerView serverListRecycler;
    ArrayList<Servers> serverList;
    ServerListAdapter serverListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityServerListBinding.inflate(getLayoutInflater());
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
        serverList = getServerList();

//        Enable while using this activity
//        if (serverList != null) {
//            serverListAdapter = new ServerListAdapter(this, serverList);
//            serverListRecycler.setAdapter(serverListAdapter);
//        }

    }

    private ArrayList getServerList() {
        ArrayList<Servers> servers = new ArrayList<>();
        servers.add(new Servers("Algeria", Utils.getImageUrl(R.drawable.al), "Algeria.ovpn", "vpn", "vpn"));
        servers.add(new Servers("Canada", Utils.getImageUrl(R.drawable.ca), "Canada.ovpn", "vpn", "vpn"));
        servers.add(new Servers("Finland", Utils.getImageUrl(R.drawable.fi), "Finland.ovpn", "vpn", "vpn"));
        servers.add(new Servers("Japan", Utils.getImageUrl(R.drawable.jp), "Japan.ovpn", "vpn", "vpn"));
        servers.add(new Servers("Korea", Utils.getImageUrl(R.drawable.kr), "Korea.ovpn", "vpn", "vpn"));
        servers.add(new Servers("Russia", Utils.getImageUrl(R.drawable.ru), "Russia.ovpn", "vpn", "vpn"));
        servers.add(new Servers("Spain", Utils.getImageUrl(R.drawable.es), "Spain.ovpn", "vpn", "vpn"));
        servers.add(new Servers("Ukrein", Utils.getImageUrl(R.drawable.ua), "Urkein.ovpn", "vpn", "vpn"));
        servers.add(new Servers("US", Utils.getImageUrl(R.drawable.us), "US.ovpn", "vpn", "vpn"));
        servers.add(new Servers("Vietnam", Utils.getImageUrl(R.drawable.va), "Vietnam.ovpn", "vpn", "vpn"));
        return servers;
    }
}