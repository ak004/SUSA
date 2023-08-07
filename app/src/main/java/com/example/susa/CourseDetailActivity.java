package com.example.susa;

import static com.example.susa.Web_service.ApiClient.Base_image_url;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.susa.Adapter.CatagoriesAdapter;
import com.example.susa.Adapter.CourseAdapter;
import com.example.susa.Web_service.ApiClient;
import com.example.susa.Web_service.ApiInterface;
import com.example.susa.models.JsonObjectModalResponse;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseDetailActivity extends AppCompatActivity {

    RecyclerView similar_courese_rec;
    CourseAdapter courseAdapter;
    SharedPreferencesData sharedPreferencesData;
    AppCompatButton enroll_btn;
    String Course_id;
    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
    ImageView mentore_img,image_v1;
    TextView views_num,Title_txt,discrepition,hourse_of,no_of_lessions,num_resoursces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        similar_courese_rec = findViewById(R.id.similar_courese_rec);
        enroll_btn = findViewById(R.id.enroll_btn);
        mentore_img = findViewById(R.id.mentore_img);
        image_v1 = findViewById(R.id.image_v1);
        views_num = findViewById(R.id.views_num);
        Title_txt = findViewById(R.id.Title_txt);
        discrepition = findViewById(R.id.discrepition);
        hourse_of = findViewById(R.id.hourse_of);
        no_of_lessions = findViewById(R.id.no_of_lessions);
        num_resoursces = findViewById(R.id.num_resoursces);
        sharedPreferencesData = SharedPreferencesData.getInstance(this);

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
                    new JsonArray();
                    JsonArray ja;
                    ja = response.body().getRecord().get("data").getAsJsonArray();
                    JsonObject jo = ja.get(0).getAsJsonObject();

                    Title_txt.setText(jo.get("title").getAsString());
                    views_num.setText(jo.get("likes").getAsString());
                    discrepition.setText(jo.get("discription").getAsString());
                    Integer sec = jo.get("total_duration").getAsInt();

                    hourse_of.setText( sec / 60 + " Minutes of Content");
                    no_of_lessions.setText("Total of " + jo.get("no_of_vids").getAsString() + " Lessons");
                    num_resoursces.setText(jo.get("attachments_count").getAsString() + " download Resources");

                    Glide.with(CourseDetailActivity.this)
                            .load(Base_image_url+jo.get("image").getAsString())
                            .centerCrop()
                            .placeholder(R.drawable.image_placeholder)
                            .into(mentore_img);

                    Glide.with(CourseDetailActivity.this)
                            .load(Base_image_url+jo.get("image").getAsString())
                            .centerCrop()
                            .placeholder(R.drawable.image_placeholder)
                            .into(image_v1);


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