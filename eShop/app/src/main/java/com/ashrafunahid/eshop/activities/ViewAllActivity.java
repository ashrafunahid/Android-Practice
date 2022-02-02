package com.ashrafunahid.eshop.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.ashrafunahid.eshop.R;
import com.ashrafunahid.eshop.adapters.ViewAllAdapter;
import com.ashrafunahid.eshop.models.ViewAllModels;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewAllActivity extends AppCompatActivity {

    FirebaseFirestore firestore;
    RecyclerView recyclerView;
    ViewAllAdapter viewAllAdapter;
    List<ViewAllModels> viewAllModelsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        firestore = FirebaseFirestore.getInstance();
        String type = getIntent().getStringExtra("type");

        recyclerView = findViewById(R.id.view_all_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewAllModelsList = new ArrayList<>();
        viewAllAdapter = new ViewAllAdapter(this, viewAllModelsList);
        recyclerView.setAdapter(viewAllAdapter);


//        For Fruit
        if(type != null && type.equalsIgnoreCase("fruit")){
            firestore.collection("AllProudct").whereEqualTo("type", "fruit").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for(DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                        ViewAllModels viewAllModels = documentSnapshot.toObject(ViewAllModels.class);
                        viewAllModelsList.add(viewAllModels);
                        viewAllAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

        //        For Vegetables
        if(type != null && type.equalsIgnoreCase("vegetables")){
            firestore.collection("AllProudct").whereEqualTo("type", "vegetables").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for(DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                        ViewAllModels viewAllModels = documentSnapshot.toObject(ViewAllModels.class);
                        viewAllModelsList.add(viewAllModels);
                        viewAllAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

    }
}