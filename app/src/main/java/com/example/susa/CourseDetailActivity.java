package com.example.susa;

import static com.example.susa.Web_service.ApiClient.Base_image_url;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.susa.Adapter.CatagoriesAdapter;
import com.example.susa.Adapter.CourseAdapter;
import com.example.susa.Web_service.ApiClient;
import com.example.susa.Web_service.ApiInterface;
import com.example.susa.models.JsonObjectModalResponse;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseDetailActivity extends AppCompatActivity {

    RecyclerView similar_courese_rec;
    CourseAdapter courseAdapter;
    SharedPreferencesData sharedPreferencesData;
    AppCompatButton enroll_btn,continue_btn;
    String Course_id;
    LinearLayout enroll_linear;
    ProgressBar progress_circular;
    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
    ImageView mentore_img,image_v1,bookmark_svgs;
    TextView views_num,Title_txt,discrepition,hourse_of,no_of_lessions,num_resoursces,cours_amount;

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
        progress_circular = findViewById(R.id.progress_circular);
        cours_amount = findViewById(R.id.cours_amount);
        enroll_linear = findViewById(R.id.enroll_linear);
        bookmark_svgs = findViewById(R.id.bookmark_svgs);
        continue_btn = findViewById(R.id.continue_btn);
        sharedPreferencesData = SharedPreferencesData.getInstance(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(CourseDetailActivity.this, LinearLayoutManager.HORIZONTAL, false);

        similar_courese_rec.setLayoutManager(layoutManager);
        JsonArray ja = new JsonArray();
        courseAdapter = new CourseAdapter(ja, CourseDetailActivity.this);
        similar_courese_rec.setAdapter(courseAdapter);
        Set<String> bookmarkedIds = sharedPreferencesData.getBookmarkedIds();



        enroll_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amt = cours_amount.getText().toString();
                String extractedNumber = amt.replaceAll("[^0-9]", "");
                open_payment_notifaiction_dilog(sharedPreferencesData.getUSER_id(), Course_id,extractedNumber);

            }
        });

        continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        Intent intent = new Intent(CourseDetailActivity.this,LessonsActivity.class);
                        intent.putExtra("course_id",Course_id );
                        startActivity(intent);
            }
        });
        bookmark_svgs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable bookmarkBorderDrawable = ContextCompat.getDrawable(CourseDetailActivity.this, R.drawable.bookmark_svg);
                Drawable bookmarkDrawable = ContextCompat.getDrawable(CourseDetailActivity.this, R.drawable.bookmarkmarked);

                String tag = bookmark_svgs.getTag().toString();
                if ("bookmark_border".equals(tag)) {
                    bookmark_svgs.setTag("bookmark");
                    bookmark_svgs.setImageDrawable(bookmarkDrawable);
                    bookmarkedIds.add(Course_id);
                } else if ("bookmark".equals(tag)) {
                    bookmark_svgs.setTag("bookmark_border");
                    bookmark_svgs.setImageDrawable(bookmarkBorderDrawable);
                    bookmarkedIds.remove(Course_id);

                }

                sharedPreferencesData.putBookmarkedIds(bookmarkedIds);
                updateBookmarkDrawable(bookmark_svgs, bookmarkedIds.contains(Course_id));
            }
        });


        Intent intent = getIntent();
        Course_id = intent.getStringExtra("course_id");

        updateBookmarkDrawable(bookmark_svgs,bookmarkedIds.contains(Course_id));
        progress_circular.setVisibility(View.VISIBLE);
        get_course_details(Course_id, sharedPreferencesData.getUSER_id());
    }

    private void updateBookmarkDrawable(ImageView imageView, boolean isBookmarked) {
        if (isBookmarked) {
            imageView.setImageResource(R.drawable.bookmarkmarked);
        } else {
            imageView.setImageResource(R.drawable.bookmark_svg);
        }
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
                    Boolean bought = response.body().getRecord().get("bought").getAsBoolean();
                    if(bought) {
                        continue_btn.setVisibility(View.VISIBLE);
                        enroll_btn.setVisibility(View.GONE);
                    }else {
                        continue_btn.setVisibility(View.GONE);
                        enroll_btn.setVisibility(View.VISIBLE);
                    }
                    JsonObject jo = ja.get(0).getAsJsonObject();
                    progress_circular.setVisibility(View.GONE);
                    Title_txt.setText(jo.get("title").getAsString());
                    views_num.setText(jo.get("likes").getAsString());
                    cours_amount.setText("$" + jo.get("amount").getAsString());
                    discrepition.setText(jo.get("discription").getAsString());
                    Integer sec;
                    JsonElement totalDurationElement = jo.get("total_duration");
                    if (totalDurationElement != null && !totalDurationElement.isJsonNull()) {
                        sec = totalDurationElement.getAsInt();
                    } else {
                        sec = 0;
                    }


                    hourse_of.setText( sec / 60 + " Minutes of Content");
                    no_of_lessions.setText("Total of " + jo.get("no_of_vids").getAsString() + " Lessons");
                    num_resoursces.setText(jo.get("attachments_count").getAsString() + " download Resources");

//                    if(jo.get("no_of_vids").getAsString().equalsIgnoreCase("0")){
//                        enroll_linear.setVisibility(View.GONE);
//                    }else {
//                        enroll_linear.setVisibility(View.VISIBLE);
//                    }

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


    private  void  open_payment_notifaiction_dilog(String user, String course, String amount) {
        final Dialog dialog = new Dialog(CourseDetailActivity.this); // Context, this, etc.
        dialog.setContentView(R.layout.payment_confirmation_dialog);
        dialog.setCancelable(true);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final AppCompatButton yes_btn = dialog.findViewById(R.id.yes_btn);
        final AppCompatButton no_btn = dialog.findViewById(R.id.no_btn);
        final TextView pay_message = dialog.findViewById(R.id.pay_message);



        yes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buy_course(user,course,amount);
            }
        });

        no_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });




        dialog.show();
    }


    private  void buy_course(String user,String cou_id, String amount){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id",user);
        jsonObject.addProperty("course_id",cou_id);
        jsonObject.addProperty("amount",amount);
        Call<JsonObjectModalResponse> call = apiInterface.buy_course(jsonObject);
        call.enqueue(new Callback<JsonObjectModalResponse>() {
            @Override
            public void onResponse(Call<JsonObjectModalResponse> call, Response<JsonObjectModalResponse> response) {
                if (response.isSuccessful()) {

                    if (response.body().isSuccess()) {
                        Toast.makeText(CourseDetailActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CourseDetailActivity.this,LessonsActivity.class);
                        intent.putExtra("course_id",Course_id );
                        startActivity(intent);
                    }else {
                        Toast.makeText(CourseDetailActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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