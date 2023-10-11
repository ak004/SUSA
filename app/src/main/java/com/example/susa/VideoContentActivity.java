package com.example.susa;

import static com.example.susa.Web_service.ApiClient.Base_image_url;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.susa.Adapter.AttachmentsAdapter;
import com.example.susa.Adapter.LessonsAdapter;
import com.example.susa.Web_service.ApiClient;
import com.example.susa.Web_service.ApiInterface;
import com.example.susa.models.JsonObjectModalResponse;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import nl.dionsegijn.konfetti.KonfettiView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoContentActivity extends AppCompatActivity {


    RecyclerView attachments_rec;
    AttachmentsAdapter attachmentsAdapter;
    SharedPreferencesData sharedPreferencesData;
    ImageView play_vid, vid_men;
    TextView vid_name,vid_desc;
    String vid_id, vid_url, is_last;
    ProgressBar progress_circular;
    KonfettiView congragulation_view;
    AppCompatButton completed_btn,ask_ai_btn;
    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_content);
        sharedPreferencesData = SharedPreferencesData.getInstance(this);

        attachments_rec = findViewById(R.id.attachments_rec);
        play_vid = findViewById(R.id.play_vid);
        progress_circular = findViewById(R.id.progress_circular);
        vid_men = findViewById(R.id.vid_men);
        vid_name = findViewById(R.id.vid_name);
        vid_desc = findViewById(R.id.vid_desc);
        completed_btn = findViewById(R.id.completed_btn);
        ask_ai_btn = findViewById(R.id.ask_ai_btn);
        congragulation_view = findViewById(R.id.congragulation_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        attachments_rec.setLayoutManager(layoutManager);




        play_vid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  ine  = new Intent(VideoContentActivity.this, VideoPlayerActiviy.class);
                if (vid_url.startsWith("/")) {
                    vid_url = vid_url.substring(1);
                }
                ine.putExtra("the_path",vid_url);
                startActivity(ine);
            }
        });
        completed_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                change_to_completd(vid_id, sharedPreferencesData.getUSER_id());

                congragulation_view.build()
                        .addColors(Color.YELLOW, Color.GREEN, Color.RED, Color.MAGENTA,Color.CYAN )
                        .setDirection(0.0, 209.0)
                        .setSpeed(1f, 5f)
                        .setFadeOutEnabled(true)
                        .setTimeToLive(2000L)
                        .setPosition(-50f, congragulation_view.getWidth() + 50f, -50f, -50f)
                        .stream(200, 2000L);

            }
        });

        Intent intent = getIntent();
        vid_id = intent.getStringExtra("vid_id");
        is_last = intent.getStringExtra("is_it_last");
        if(is_last.equalsIgnoreCase("1")) {
            completed_btn.setVisibility(View.VISIBLE);
        }else {
            completed_btn.setVisibility(View.INVISIBLE);
        }
        progress_circular.setVisibility(View.VISIBLE);
        get_course_details(vid_id, sharedPreferencesData.getUSER_id());

    }


    private void  change_to_completd(String vid, String user_id){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id",user_id);
        jsonObject.addProperty("vid_id",vid);
        Call<JsonObjectModalResponse> call = apiInterface.change_to_completd(jsonObject);
        call.enqueue(new Callback<JsonObjectModalResponse>() {
            @Override
            public void onResponse(Call<JsonObjectModalResponse> call, Response<JsonObjectModalResponse> response) {
                if (response.isSuccessful()) {
                    if(response.body().isSuccess()) {
                        Toast.makeText(VideoContentActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(VideoContentActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }


                }
            }
            //check something
            @Override
            public void onFailure(Call<JsonObjectModalResponse> call, Throwable t) {
                Log.d("sliding_category", t.getMessage());
            }
        });
    }



    private void get_course_details(String vid_id,String user_id) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id",user_id);
        jsonObject.addProperty("vid_id",vid_id);
        Call<JsonObjectModalResponse> call = apiInterface.vid_details(jsonObject);
        call.enqueue(new Callback<JsonObjectModalResponse>() {
            @Override
            public void onResponse(Call<JsonObjectModalResponse> call, Response<JsonObjectModalResponse> response) {
                if (response.isSuccessful()) {
                    if(response.body().isSuccess()) {
                        new JsonArray();
                        JsonArray ja;
                        ja = response.body().getRecord().get("data").getAsJsonArray();
                        JsonObject jo = ja.get(0).getAsJsonObject();
                        progress_circular.setVisibility(View.GONE);
                        vid_name.setText(jo.get("title").getAsString());
                        vid_desc.setText(jo.get("discription").getAsString());
                        vid_url = jo.get("path").getAsString();
                        Glide.with(VideoContentActivity.this)
                                .load(Base_image_url+jo.get("thumb_img").getAsString())
                                .centerCrop()
                                .placeholder(R.drawable.image_placeholder)
                                .into(play_vid);

                        Glide.with(VideoContentActivity.this)
                                .load(Base_image_url+jo.get("thumb_img").getAsString())
                                .centerCrop()
                                .placeholder(R.drawable.image_placeholder)
                                .into(vid_men);

                        attachmentsAdapter = new AttachmentsAdapter(jo.get("attachments").getAsJsonArray(),VideoContentActivity.this);
                        attachments_rec.setAdapter(attachmentsAdapter);
                    }else {
                        Toast.makeText(VideoContentActivity.this, "Couldnt find data try again", Toast.LENGTH_SHORT).show();
                    }


                }
            }
            //check something
            @Override
            public void onFailure(Call<JsonObjectModalResponse> call, Throwable t) {
                Log.d("sliding_category", t.getMessage());
            }
        });
    }


}