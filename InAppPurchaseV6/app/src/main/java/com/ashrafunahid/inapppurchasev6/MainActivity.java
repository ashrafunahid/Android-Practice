package com.ashrafunahid.inapppurchasev6;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.AcknowledgePurchaseResponseListener;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.ProductDetailsResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesResponseListener;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.QueryProductDetailsParams;
import com.android.billingclient.api.QueryPurchasesParams;
import com.ashrafunahid.inapppurchasev6.Classes.Verify;
import com.ashrafunahid.inapppurchasev6.databinding.ActivityMainBinding;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.collect.ImmutableList;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    BillingClient billingClient;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializeBillingClient();

        binding.consumableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startConsumablePurchase();
            }
        });

    }

    private void startConsumablePurchase() {
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingServiceDisconnected() {

            }

            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    QueryProductDetailsParams queryProductDetailsParams =
                            QueryProductDetailsParams.newBuilder()
                                    .setProductList(
                                            ImmutableList.of(
                                                    QueryProductDetailsParams.Product.newBuilder()
                                                            .setProductId("product_id_example")
                                                            .setProductType(BillingClient.ProductType.INAPP)
                                                            .build()))
                                    .build();
                    billingClient.queryProductDetailsAsync(
                            queryProductDetailsParams,
                            new ProductDetailsResponseListener() {
                                public void onProductDetailsResponse(BillingResult billingResult,
                                                                     List<ProductDetails> productDetailsList) {
                                    // check billingResult
                                    // process returned productDetailsList
                                    for (ProductDetails productDetails : productDetailsList) {
                                        ImmutableList<BillingFlowParams.ProductDetailsParams> productDetailsParamsList =
                                                ImmutableList.of(
                                                        BillingFlowParams.ProductDetailsParams.newBuilder()
                                                                // retrieve a value for "productDetails" by calling queryProductDetailsAsync()
                                                                .setProductDetails(productDetails)
                                                                // For one-time products, "setOfferToken" method shouldn't be called.
                                                                // For subscriptions, to get an offer token, call
                                                                // ProductDetails.subscriptionOfferDetails() for a list of offers
                                                                // that are available to the user.
                                                                // .setOfferToken(selectedOfferToken)
                                                                .build()
                                                );
                                        BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                                                .setProductDetailsParamsList(productDetailsParamsList)
                                                .build();

                                        // Launch the billing flow
                                        billingClient.launchBillingFlow(MainActivity.this, billingFlowParams);
                                    }
                                }
                            }
                    );
                }
            }
        });
    }

    public void initializeBillingClient(){
        billingClient = BillingClient.newBuilder(this)
                .setListener(purchasesUpdatedListener)
                .enablePendingPurchases()
                .build();
    }

    PurchasesUpdatedListener purchasesUpdatedListener = new PurchasesUpdatedListener() {
        @Override
        public void onPurchasesUpdated(BillingResult billingResult, List<Purchase> purchases) {
            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && purchases != null){
                for (Purchase purchase : purchases) {
                    handlePurchase(purchase);
                }
            } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.BILLING_UNAVAILABLE) {
                Toast.makeText(MainActivity.this, "BILLING_UNAVAILABLE", Toast.LENGTH_SHORT).show();
            }
            else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED) {
                Toast.makeText(MainActivity.this, "ITEM_ALREADY_OWNED", Toast.LENGTH_SHORT).show();
            }
            else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.DEVELOPER_ERROR) {
                Toast.makeText(MainActivity.this, "ITEM_ALREADY_OWNED", Toast.LENGTH_SHORT).show();
            }
            else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.NETWORK_ERROR) {
                Toast.makeText(MainActivity.this, "NETWORK_ERROR", Toast.LENGTH_SHORT).show();
            }
            else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.SERVICE_DISCONNECTED) {
                Toast.makeText(MainActivity.this, "SERVICE_DISCONNECTED", Toast.LENGTH_SHORT).show();
            }
            else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.FEATURE_NOT_SUPPORTED) {
                Toast.makeText(MainActivity.this, "FEATURE_NOT_SUPPORTED", Toast.LENGTH_SHORT).show();
            }
            else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {
                Toast.makeText(MainActivity.this, "USER_CANCELED", Toast.LENGTH_SHORT).show();
            }
            else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.ITEM_UNAVAILABLE) {
                Toast.makeText(MainActivity.this, "ITEM_UNAVAILABLE", Toast.LENGTH_SHORT).show();
            }
            else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.ITEM_NOT_OWNED) {
                Toast.makeText(MainActivity.this, "ITEM_NOT_OWNED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, ""+billingResult.getDebugMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        private void handlePurchase(Purchase purchase) {
            ConsumeParams consumeParams =
                    ConsumeParams.newBuilder()
                            .setPurchaseToken(purchase.getPurchaseToken())
                            .build();

            ConsumeResponseListener listener = new ConsumeResponseListener() {
                @Override
                public void onConsumeResponse(BillingResult billingResult, String purchaseToken) {
                    if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                        // Handle the success of the consume operation.
                    }
                }
            };

            billingClient.consumeAsync(consumeParams, listener);

            // Verify
            if(purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
                // Verify
                // Verify signature of the purchase
                if (!verifyValidSignature(purchase.getOriginalJson(), purchase.getSignature())) {
                    Toast.makeText(MainActivity.this, "Error: Invalid Purchase", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Acknowledge the purchase if not acknowledged
                if (!purchase.isAcknowledged()) {
                    AcknowledgePurchaseParams acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder()
                            .setPurchaseToken(purchase.getPurchaseToken())
                            .build();
                    billingClient.acknowledgePurchase(acknowledgePurchaseParams, new AcknowledgePurchaseResponseListener() {
                        @Override
                        public void onAcknowledgePurchaseResponse(@NonNull BillingResult billingResult) {
                            Toast.makeText(MainActivity.this, "Acknowledged", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Toast.makeText(MainActivity.this, "Purchased", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Already Purchased", Toast.LENGTH_SHORT).show();
                }
            } else if (purchase.getPurchaseState() == Purchase.PurchaseState.UNSPECIFIED_STATE) {
                Toast.makeText(MainActivity.this, "UNSPECIFIED_PURCHASE_STATE", Toast.LENGTH_SHORT).show();
            } else if (purchase.getPurchaseState() == Purchase.PurchaseState.PENDING) {
                Toast.makeText(MainActivity.this, "PURCHASE_PENDING", Toast.LENGTH_SHORT).show();
            }
        }
        private boolean verifyValidSignature(String originalJson, String signature) {
            try {
                // Base64 public key verification
                String base64Key = "";
                return Verify.verifyPurchase(base64Key, originalJson, signature);
            } catch (IOException e) {
                return false;
            }
        }
    };

//    public void setUpBillingClient() {
//
//        billingClient = BillingClient.newBuilder(MainActivity.this).setListener(this).enablePendingPurchases().build();
//        billingClient.startConnection(new BillingClientStateListener() {
//            @Override
//            public void onBillingServiceDisconnected() {
//                Toast.makeText(MainActivity.this, "Service Disconnected", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
//
//                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
//                    // TODO Proceed purchase process
//                    queryPurchase();
//                }
//            }
//        });
//
//    }

//    public void queryPurchase() {
//        billingClient.queryPurchasesAsync(QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.INAPP).build(),
//                new PurchasesResponseListener() {
//                    @Override
//                    public void onQueryPurchasesResponse(@NonNull BillingResult billingResult, @NonNull List<Purchase> list) {
//                        if (list.size() > 0) {
//                            for (Purchase p : list) {
//                                if (p.getProducts().equals("productSKUCode"));
//                            }
//                        }
//                    }
//                });
//    }

//    @Override
//    public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> list) {
//        billingClient = BillingClient.newBuilder(getApplicationContext())
//                .setListener(this)
//                .enablePendingPurchases()
//                .build();
//
//        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
//            // TODO
//        } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED) {
//
//        }
//    }
}