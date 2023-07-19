package com.example.susa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.susa.Adapter.AttachmentsAdapter;
import com.google.gson.JsonArray;

public class VideoContentActivity extends AppCompatActivity {


    RecyclerView attachments_rec;
    AttachmentsAdapter attachmentsAdapter;

    ImageView play_vid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_content);

        attachments_rec = findViewById(R.id.attachments_rec);
        play_vid = findViewById(R.id.play_vid);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        attachments_rec.setLayoutManager(layoutManager);
        JsonArray ja = new JsonArray();
        attachmentsAdapter = new AttachmentsAdapter( ja,this);
        attachments_rec.setAdapter(attachmentsAdapter);



        play_vid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  ine  = new Intent(VideoContentActivity.this, VideoPlayerActiviy.class);
                startActivity(ine);
            }
        });


    }
}