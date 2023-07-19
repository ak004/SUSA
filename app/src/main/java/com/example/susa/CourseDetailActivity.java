package com.example.susa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.susa.Adapter.CatagoriesAdapter;
import com.example.susa.Adapter.CourseAdapter;
import com.google.gson.JsonArray;

public class CourseDetailActivity extends AppCompatActivity {

    RecyclerView similar_courese_rec;
    CourseAdapter courseAdapter;

    AppCompatButton enroll_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        similar_courese_rec = findViewById(R.id.similar_courese_rec);
        enroll_btn = findViewById(R.id.enroll_btn);

        LinearLayoutManager layoutManager = new LinearLayoutManager(CourseDetailActivity.this, LinearLayoutManager.HORIZONTAL, false);

        similar_courese_rec.setLayoutManager(layoutManager);
        JsonArray ja = new JsonArray();
        courseAdapter = new CourseAdapter(ja, CourseDetailActivity.this);
        similar_courese_rec.setAdapter(courseAdapter);



        enroll_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseDetailActivity.this,LessonsActivity.class);
                startActivity(intent);
            }
        });



    }
}