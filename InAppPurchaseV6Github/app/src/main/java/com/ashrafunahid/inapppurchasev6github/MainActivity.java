package com.ashrafunahid.inapppurchasev6github;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.limurse.iap.DataWrappers;
import com.limurse.iap.IapConnector;
import com.limurse.iap.PurchaseServiceListener;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button button1, button2, button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = findViewById(R.id.button_1);
        button2 = findViewById(R.id.button_2);
        button3 = findViewById(R.id.button_3);

        MutableLiveData<Boolean> isBillingClientConnected = new MutableLiveData<>();
        isBillingClientConnected.setValue(false);
        List<String> nonConsumableList = Collections.singletonList("lifetime");
        List<String> consumableList = Arrays.asList("1dollar", "2dollar", "3dollar");
        List<String> subsList = Collections.singletonList("subscription");

        IapConnector iapConnector = new IapConnector(
                this,
                nonConsumableList,
                consumableList,
                subsList,
                getResources().getString(R.string.license),
                true);

        iapConnector.addBillingClientConnectionListener((status, billingResponseCode) -> {
            Log.d("IAP", "Status is " + status + ", Response code is " + billingResponseCode);
            isBillingClientConnected.setValue(status);
        });

        iapConnector.addPurchaseListener(new PurchaseServiceListener() {
            @Override
            public void onPricesUpdated(@NonNull Map<String, ? extends List<DataWrappers.ProductDetails>> map) {

            }

            @Override
            public void onProductPurchased(@NonNull DataWrappers.PurchaseInfo purchaseInfo) {

//                1dollar, 2 dollar, 3dollar is your SKU id
                if (purchaseInfo.getSku().equals("1dollar")){
                    Toast.makeText(MainActivity.this, "Thank you for purchasing $ 1", Toast.LENGTH_SHORT).show();
                }
                else if (purchaseInfo.getSku().equals("2dollar")){
                    Toast.makeText(MainActivity.this, "Thank you for purchasing $ 2", Toast.LENGTH_SHORT).show();
                }
                else if (purchaseInfo.getSku().equals("3dollar")){
                    Toast.makeText(MainActivity.this, "Thank you for purchasing $ 3", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onProductRestored(@NonNull DataWrappers.PurchaseInfo purchaseInfo) {

            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iapConnector.purchase(MainActivity.this, "1dollar", null, null);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iapConnector.purchase(MainActivity.this, "2dollar", null, null);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iapConnector.purchase(MainActivity.this, "3dollar", null, null);
            }
        });

    }
}