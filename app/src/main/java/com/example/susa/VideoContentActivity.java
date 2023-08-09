package com.example.susa;

import static com.example.susa.Web_service.ApiClient.Base_image_url;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoContentActivity extends AppCompatActivity {


    RecyclerView attachments_rec;
    AttachmentsAdapter attachmentsAdapter;
    SharedPreferencesData sharedPreferencesData;
    ImageView play_vid, vid_men;
    TextView vid_name,vid_desc;
    String vid_id, vid_url;
    ProgressBar progress_circular;
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

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        attachments_rec.setLayoutManager(layoutManager);
        JsonArray ja = new JsonArray();
        attachmentsAdapter = new AttachmentsAdapter( ja,this);
        attachments_rec.setAdapter(attachmentsAdapter);



        play_vid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  ine  = new Intent(VideoContentActivity.this, VideoPlayerActiviy.class);
                ine.putExtra("the_path",vid_url);
                startActivity(ine);
            }
        });


        Intent intent = getIntent();
        vid_id = intent.getStringExtra("vid_id");
        progress_circular.setVisibility(View.VISIBLE);
        get_course_details(vid_id, sharedPreferencesData.getUSER_id());

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

//                        lessonsAdapter = new LessonsAdapter(jo.get("videos").getAsJsonArray(),VideoContentActivity.this);
//                        lessons_rec.setAdapter(lessonsAdapter);
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