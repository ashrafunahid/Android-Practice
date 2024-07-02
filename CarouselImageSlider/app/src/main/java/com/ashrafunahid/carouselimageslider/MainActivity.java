package com.ashrafunahid.carouselimageslider;

import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    String TAG = "Carousel";
    RecyclerView recyclerView;
    MaterialToolbar toolbar;
    LinearLayoutManager layoutManager;
    int i = 0;
    int k = 0;
    boolean infiniteLoop = true;
    ArrayList<String> arrayList;
    CarouselImageAdapter adapter;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.carousel_image_recycler);
        toolbar = findViewById(R.id.tool_bar);
        arrayList = new ArrayList<>();

        arrayList.add("https://images.unsplash.com/photo-1719217469234-bb53c12ed515?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
        arrayList.add("https://images.unsplash.com/photo-1719150006655-62fdcadf01a7?q=80&w=1887&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
        arrayList.add("https://plus.unsplash.com/premium_photo-1661765778256-169bf5e561a6?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
        arrayList.add("https://images.unsplash.com/photo-1718062457138-2d6fcab216d9?q=80&w=2110&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
        arrayList.add("https://images.unsplash.com/photo-1718809070510-371f29994edd?q=80&w=1887&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");

//        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        recyclerView.setLayoutManager(layoutManager);

        adapter = new CarouselImageAdapter(MainActivity.this, arrayList);
        adapter.setOnItemClickListener(new CarouselImageAdapter.OnItemClickListener() {
            @Override
            public void onClick(ImageView imageView, String url) {
                startActivity(new Intent(MainActivity.this, ImageViewActivity.class).putExtra("image", url),
                        ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, imageView, "image").toBundle());
            }
        });
        recyclerView.setAdapter(adapter);

//        Log.i(TAG, "Array Size " + arrayList.size());
//        for (i = 0; i < arrayList.size(); i++) {
//            Log.i(TAG, "onCreate: " + i);
//            recyclerView.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    recyclerView.smoothScrollToPosition(i);
//                }
//            }, 2000);
//            recyclerView.smoothScrollToPosition(i);
//            Log.i(TAG, "onCreate: " + i);
//        }



    }
}