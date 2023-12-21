package com.ashrafunahid.inapppurchasev6;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesResponseListener;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.QueryPurchasesParams;

import java.util.List;

public class MainActivity extends AppCompatActivity implements PurchasesUpdatedListener {

    BillingClient billingClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpBillingClient();
    }

    public void setUpBillingClient() {

        billingClient = BillingClient.newBuilder(MainActivity.this).setListener(this).enablePendingPurchases().build();
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingServiceDisconnected() {
                Toast.makeText(MainActivity.this, "Service Disconnected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {

                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    // TODO Proceed purchase process
                    queryPurchase();
                }
            }
        });

    }

    public void queryPurchase() {
        billingClient.queryPurchasesAsync(QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.INAPP).build(),
                new PurchasesResponseListener() {
                    @Override
                    public void onQueryPurchasesResponse(@NonNull BillingResult billingResult, @NonNull List<Purchase> list) {
                        if (list.size() > 0) {
                            for (Purchase p : list) {
                                if (p.getProducts().equals("productSKUCode"));
                            }
                        }
                    }
                });
    }

    @Override
    public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> list) {
        billingClient = BillingClient.newBuilder(getApplicationContext())
                .setListener(this)
                .enablePendingPurchases()
                .build();

        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
            // TODO 
        } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED) {

        }
    }
}