package com.example.susa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.susa.Adapter.CatagoriesAdapter;
import com.example.susa.Adapter.CourseAdapter;
import com.google.gson.JsonArray;

public class CourseDetailActivity extends AppCompatActivity {

    RecyclerView similar_courese_rec;
    CourseAdapter courseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        similar_courese_rec = findViewById(R.id.similar_courese_rec);

        LinearLayoutManager layoutManager = new LinearLayoutManager(CourseDetailActivity.this, LinearLayoutManager.HORIZONTAL, false);

        similar_courese_rec.setLayoutManager(layoutManager);
        JsonArray ja = new JsonArray();
        courseAdapter = new CourseAdapter(ja, CourseDetailActivity.this);
        similar_courese_rec.setAdapter(courseAdapter);
    }
}