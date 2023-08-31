package com.ashrafunahid.recyclerviewdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> courseList;
    RecyclerView courseRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        courseRecycler = (RecyclerView) findViewById(R.id.course_recycler);
        courseRecycler.setLayoutManager(new LinearLayoutManager(this));
        courseRecycler.setHasFixedSize(true);

        courseList = new ArrayList<>();
        for (int i = 1; i <=20; i++) {
            courseList.add("Course # " + i);
        }

        CourseAdapter adapter = new CourseAdapter(courseList);
        courseRecycler.setAdapter(adapter);

    }
}