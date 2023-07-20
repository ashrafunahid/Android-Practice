package com.ashrafunahid.firebasetutorial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ashrafunahid.firebasetutorial.Adapter.ImageAdapter;
import com.ashrafunahid.firebasetutorial.Models.UploadModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ShowUploads extends AppCompatActivity implements ImageAdapter.OnItemClickListener {

//    ProgressBar progressBar;
    RecyclerView recyclerView;
    TextView emptyView;
    ImageAdapter imageAdapter;
    FirebaseStorage firebaseStorage;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    List<UploadModel> uploadModelList;

    SwipeRefreshLayout swipeRefreshLayout;
    boolean isRefreshing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_uploads);

//        progressBar = (ProgressBar) findViewById(R.id.load_progress);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.RED, Color.GREEN);
//        For center align
        swipeRefreshLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    swipeRefreshLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    swipeRefreshLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }

                Rect rect = new Rect();
                swipeRefreshLayout.getDrawingRect(rect);
                swipeRefreshLayout.setProgressViewOffset(false, 0, rect.centerY() - (swipeRefreshLayout.getProgressCircleDiameter() / 2));
            }
        });
        isRefreshing = swipeRefreshLayout.isRefreshing();
        if ((isRefreshing == true)) {
            swipeRefreshLayout.setRefreshing(true);
        }
        else {
            swipeRefreshLayout.setRefreshing(false);
        }

        recyclerView = (RecyclerView) findViewById(R.id.show_uploads_recycler);
        emptyView = (TextView) findViewById(R.id.empty_view);
//
//        if(uploadModelList.isEmpty()) {
//            recyclerView.setVisibility(View.GONE);
//            emptyView.setVisibility(View.VISIBLE);
//        } else {
//            recyclerView.setVisibility(View.VISIBLE);
//            emptyView.setVisibility(View.GONE);
//        }

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        firebaseStorage = FirebaseStorage.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("uploads");

        loadData();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                loadData();

            }
        });
    }

    private void loadData() {

        uploadModelList = new ArrayList<>();

        imageAdapter = new ImageAdapter(ShowUploads.this, uploadModelList);

        recyclerView.setAdapter(imageAdapter);

        imageAdapter.setOnItemClickListener(ShowUploads.this);

        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                uploadModelList.clear();

                for (DataSnapshot singleDataSnapshot : snapshot.getChildren()) {

                    UploadModel uploadModel = singleDataSnapshot.getValue(UploadModel.class);
                    uploadModel.setUniqueKey(singleDataSnapshot.getKey());
                    uploadModelList.add(uploadModel);

                }

                if(uploadModelList.size() > 0) {
                    recyclerView.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);
                } else if(uploadModelList.size() == 0){
                    emptyView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }

                imageAdapter.notifyDataSetChanged();

                if ((isRefreshing == true)) {
                    swipeRefreshLayout.setRefreshing(true);
                }
                else {
                    swipeRefreshLayout.setRefreshing(false);
                }

//                progressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ShowUploads.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//                progressBar.setVisibility(View.INVISIBLE);
                if ((isRefreshing == true)) {
                    swipeRefreshLayout.setRefreshing(true);
                }
                else {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });

    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, "Normal click done with position: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onWhatEverClick(int position) {
        Toast.makeText(this, "WhatEver click done with position: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteClick(int position) {

        UploadModel selectedItem = uploadModelList.get(position);
        String selectedItemKey = selectedItem.getUniqueKey();

        StorageReference imageReference = firebaseStorage.getReferenceFromUrl(selectedItem.getImageUrl());
        imageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                databaseReference.child(selectedItemKey).removeValue();
                Toast.makeText(ShowUploads.this, "Item Deleted", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        databaseReference.removeEventListener(eventListener);

    }

}