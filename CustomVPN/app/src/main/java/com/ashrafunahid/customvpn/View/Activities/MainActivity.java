package com.ashrafunahid.customvpn.View.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.VpnService;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.ashrafunahid.customvpn.R;
import com.ashrafunahid.customvpn.View.Services.CustomVpnServices;
import com.ashrafunahid.customvpn.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    ActivityMainBinding binding;
    private final int VPN_INTENT_REQUEST_CODE = 1;

    NavigationView navigationView;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
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

        binding.settingsButton.setVisibility(View.GONE);
        binding.powerOnVpnButton.setVisibility(View.VISIBLE);
        binding.powerOffVpnButton.setVisibility(View.GONE);

        navigationView = findViewById(R.id.navigation_drawer);
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

        binding.navigationDrawerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        binding.serverListCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ServerListFromJsonActivity.class);
                startActivity(intent);
            }
        });

        binding.powerOnVpnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.powerOnVpnButton.setVisibility(View.GONE);
                binding.powerOffVpnButton.setVisibility(View.VISIBLE);
                establishVpnConnection();
            }
        });

        LocalBroadcastManager.getInstance(this).registerReceiver(vpnConnectedReceiver, new IntentFilter(CustomVpnServices.ACTION_VPN_CONNECTED));

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        super.onBackPressed();
    }

    private void establishVpnConnection() {
        Intent vpnIntent = VpnService.prepare(MainActivity.this);
        if (vpnIntent != null) {
            startActivityForResult(vpnIntent, VPN_INTENT_REQUEST_CODE);
        } else {
            startVpnServiceWithIp();
        }
    }

    private void startVpnServiceWithIp() {
        Intent vpnIntent = new Intent(MainActivity.this, CustomVpnServices.class);
        vpnIntent.putExtra("vpnIp", "62.68.79.103");
        vpnIntent.putExtra("vpnPort", 51820);
        startService(vpnIntent);
    }

    private BroadcastReceiver vpnConnectedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            CustomVpnServices vpnServices = CustomVpnServices.instance;
            if (vpnServices != null && vpnServices.getVpnInterface() != null) {
                binding.connectionStatus.setText("Connected");
            } else {
                binding.connectionStatus.setText("Disconnected");
            }
        }
    };

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}