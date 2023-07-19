package com.example.susa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.susa.Adapter.LessonsAdapter;
import com.google.gson.JsonArray;

public class LessonsActivity extends AppCompatActivity {


    LessonsAdapter lessonsAdapter;
    RecyclerView lessons_rec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);

        lessons_rec= findViewById(R.id.lessons_rec);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        lessons_rec.setLayoutManager(layoutManager);

        JsonArray ja = new JsonArray();
        lessonsAdapter = new LessonsAdapter(ja,this);
        lessons_rec.setAdapter(lessonsAdapter);



    }
}