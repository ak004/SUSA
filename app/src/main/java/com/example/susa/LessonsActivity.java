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
import com.example.susa.Adapter.LessonsAdapter;
import com.example.susa.Web_service.ApiClient;
import com.example.susa.Web_service.ApiInterface;
import com.example.susa.models.JsonObjectModalResponse;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LessonsActivity extends AppCompatActivity {


    LessonsAdapter lessonsAdapter;
    RecyclerView lessons_rec;
    ProgressBar progress_circular;
    SharedPreferencesData sharedPreferencesData;
    String Course_id;
    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
    ImageView mento_img,course_img;
    TextView title,deese,views,amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);
        sharedPreferencesData = SharedPreferencesData.getInstance(this);

        progress_circular = findViewById(R.id.progress_circular);
        lessons_rec= findViewById(R.id.lessons_rec);
        mento_img= findViewById(R.id.mento_img);
        course_img= findViewById(R.id.course_img);
        title= findViewById(R.id.title);
        deese= findViewById(R.id.deese);
        views= findViewById(R.id.views);
        amount= findViewById(R.id.amount);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        lessons_rec.setLayoutManager(layoutManager);




        Intent intent = getIntent();
        Course_id = intent.getStringExtra("course_id");

        get_course_details(Course_id, sharedPreferencesData.getUSER_id());

    }

    private void get_course_details(String course,String user_id) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id",user_id);
        jsonObject.addProperty("course_id",course);
        Call<JsonObjectModalResponse> call = apiInterface.get_course_details(jsonObject);
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
                        title.setText(jo.get("title").getAsString());
                        views.setText(jo.get("likes").getAsString());
                        deese.setText(jo.get("discription").getAsString());
                        Glide.with(LessonsActivity.this)
                                .load(Base_image_url+jo.get("image").getAsString())
                                .centerCrop()
                                .placeholder(R.drawable.image_placeholder)
                                .into(course_img);

                        Glide.with(LessonsActivity.this)
                                .load(Base_image_url+jo.get("image").getAsString())
                                .centerCrop()
                                .placeholder(R.drawable.image_placeholder)
                                .into(mento_img);

                        lessonsAdapter = new LessonsAdapter(jo.get("videos").getAsJsonArray(),LessonsActivity.this);
                        lessons_rec.setAdapter(lessonsAdapter);
                    }else {
                        Toast.makeText(LessonsActivity.this, "Couldnt find data try again", Toast.LENGTH_SHORT).show();
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