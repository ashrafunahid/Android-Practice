package com.ashrafunahid.customvpn.View.Services;

import android.content.Intent;
import android.net.VpnService;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.ashrafunahid.customvpn.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicBoolean;

public class CustomVpnServices extends VpnService {
    private static final String TAG = "CustomVpnServices";
    private final AtomicBoolean isRunning = new AtomicBoolean(false);
    private ParcelFileDescriptor vpnInterface;
    private String serverIP;
    private int serverPortNumber;
    private Handler handler = new Handler(Looper.getMainLooper());
    public static final String ACTION_VPN_CONNECTED = "com.ashrafunahid.customvpn.View.Services";
    public static CustomVpnServices instance;

    public ParcelFileDescriptor getVpnInterface() {
        return vpnInterface;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent != null) {
            // Get the server ip address and port number from the intent
            serverIP = intent.getStringExtra("vpnIp");
            serverPortNumber = intent.getIntExtra("vpnPort", 0);


            // Start the VPN connection in separate thread
            Thread vpnThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    CustomVpnServices.this.runVpnConnection();
                }
            });
            vpnThread.start();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopVpnConnection();
    }

    private void runVpnConnection() {
        try {
            if (establishedVpnConnection()){
                readFromVpnInterface();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error on establish vpn connection :: " + e.getMessage());
        } finally {
            stopVpnConnection();
        }
    }

    public void stopVpnConnection() {
        isRunning.set(false);
        if (vpnInterface != null) {
            try {
                vpnInterface.close();
            } catch (Exception e) {
                Log.e(TAG, "Error closing vpn interface :: " + e.getMessage());
            }
        }
    }

    private boolean establishedVpnConnection() throws IOException {
        if (vpnInterface != null) {
            Builder builder = new Builder();
            builder.addAddress(serverIP, 32); // 32 is the prefix length for single ip address
            builder.addRoute("0.0.0.0", 0);

            vpnInterface = builder.setSession(getString(R.string.app_name))
                    .setConfigureIntent(null)
                    .establish();

            return vpnInterface != null;
        } else {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    onVpnConnectionSuccess();
                    Toast.makeText(CustomVpnServices.this, "Vpn connection already established", Toast.LENGTH_SHORT).show();
                }
            });
        }
        return true;
    }

//    Read from the vpnInterface and write to the network
    private void readFromVpnInterface() throws IOException {
        isRunning.set(true);
        ByteBuffer buffer = ByteBuffer.allocate(32676);
        while (isRunning.get()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(vpnInterface.getFileDescriptor());
                int length = fileInputStream.read(buffer.array());
                if (length > 0) {
                    String receivedData = new String(buffer.array(), 0, length);
                    // Send the receivedData to the MainActivity using local broadcast receiver
                    Intent intent = new Intent("received_data_from_vpn");
                    intent.putExtra("data", receivedData);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

                    // Write processed data to the Network
                    writeDataToNetwork(buffer, length);
                }
            } catch (Exception e) {
                Log.e(TAG, "Error receiving data from vpn interface :: " + e.getMessage());
            }
        }
    }

    private void writeDataToNetwork(ByteBuffer buffer, int length) {
        String processedData = new String(buffer.array(), 0, length);
        try {
            Socket socket = new Socket(serverIP, serverPortNumber);
            OutputStream outputStream = socket.getOutputStream();

            // Convert the processed data into bytes and write to the server
            byte[] dataBytes = processedData.getBytes(StandardCharsets.UTF_8);
            outputStream.write(dataBytes);

            // Close output stream and the socket after sending the data
            outputStream.close();
            socket.close();
        } catch (Exception e) {
            Log.e(TAG ,"Error sending data to ther server :: " + e.getMessage());
        }
    }

    private void onVpnConnectionSuccess() {
        // Send a local broadcast to the MainActivity to notify the user about the connection
        Intent intent = new Intent(ACTION_VPN_CONNECTED);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
