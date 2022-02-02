package com.ashrafunahid.eshop.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.ashrafunahid.eshop.R;
import com.ashrafunahid.eshop.models.ViewAllModels;
import com.bumptech.glide.Glide;

public class DetailedActivity extends AppCompatActivity {

    ImageView detailedImg;
    TextView price, rating, description;
    Button addToCart;
    ImageView addItem, removeItem;
//    Toolbar toolbar;
    ViewAllModels viewAllModels = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
//
//        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Object object = getIntent().getSerializableExtra("detail");
        if(object instanceof ViewAllModels){

            viewAllModels = (ViewAllModels) object;
        }

        detailedImg = findViewById(R.id.detailed_img);
        addItem = findViewById(R.id.add_item);
        removeItem = findViewById(R.id.remove_item);

        price = findViewById(R.id.detailed_price);
        rating = findViewById(R.id.detailed_rating);
        description = findViewById(R.id.detailed_dec);

        if(viewAllModels != null){
            Glide.with(getApplicationContext()).load(viewAllModels.getImg_url()).into(detailedImg);
            rating.setText(viewAllModels.getRating());
            description.setText(viewAllModels.getDescription());
            price.setText(viewAllModels.getPrice());
        }

        addToCart = findViewById(R.id.add_to_cart);

    }

//    private void setSupportActionBar(Toolbar toolbar) {
//    }
}