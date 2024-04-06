package com.example.susa;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.window.OnBackInvokedCallback;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.susa.Adapter.ChatAdapter;
import com.example.susa.Web_service.ApiClient;
import com.example.susa.Web_service.ApiInterface;
import com.example.susa.models.JsonObjectModalResponse;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class chatActivity extends AppCompatActivity {


    RecyclerView mesg_rec;
    EditText question_txt;
    CardView send_btn;
    SharedPreferencesData sharedPreferencesData;
    ChatAdapter chatAdapter;
    ImageView back_btn;
    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        sharedPreferencesData = SharedPreferencesData.getInstance(this);
        mesg_rec = findViewById(R.id.mesg_rec);
        question_txt = findViewById(R.id.question_txt);
        send_btn = findViewById(R.id.send_btn);
        back_btn = findViewById(R.id.back_btn);

        Intent intent = getIntent();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mesg_rec.setLayoutManager(layoutManager);

        getChatApi(sharedPreferencesData.getUSER_id(),intent.getStringExtra("course_id"));


        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(question_txt.getText().toString().isEmpty()) {
                    Toast.makeText(chatActivity.this, "Please ask something first", Toast.LENGTH_SHORT).show();
                }else {
                    sendMessage(sharedPreferencesData.getUSER_id(),question_txt.getText().toString(),intent.getStringExtra("course_id"));
                }
            }


        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void sendMessage(String userId, String message ,String courseId) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id",userId);
        jsonObject.addProperty("course_id",courseId);
        jsonObject.addProperty("msg",message);
        Call<JsonObjectModalResponse> call = apiInterface.send_chat(jsonObject);
        call.enqueue(new Callback<JsonObjectModalResponse>() {
            @Override
            public void onResponse(Call<JsonObjectModalResponse> call, Response<JsonObjectModalResponse> response) {
                if (response.isSuccessful()) {
                    getChatApi(userId,courseId);
                    question_txt.setText("");
                }else {
                    Toast.makeText(chatActivity.this, "Count send your message try again", Toast.LENGTH_SHORT).show();
                }
            }
            //check something
            @Override
            public void onFailure(Call<JsonObjectModalResponse> call, Throwable t) {
                Log.d("sliding_category", t.getMessage());
            }
        });
    }


    private void getChatApi(String userId, String courseId) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id",userId);
        jsonObject.addProperty("courseId",courseId);
        Call<JsonObjectModalResponse> call = apiInterface.get_chats(jsonObject);
        call.enqueue(new Callback<JsonObjectModalResponse>() {
            @Override
            public void onResponse(Call<JsonObjectModalResponse> call, Response<JsonObjectModalResponse> response) {
                if (response.isSuccessful()) {
                    JsonArray ja = new JsonArray();
                    ja = response.body().getRecord().get("data").getAsJsonArray();
                    chatAdapter = new ChatAdapter(ja, chatActivity.this);
                    mesg_rec.setAdapter(chatAdapter);

                    mesg_rec.post(() -> {
                        // Smooth scroll to the bottom
                        mesg_rec.smoothScrollToPosition(chatAdapter.getItemCount());
                        // If you want to instantly scroll to the bottom without animation, use scrollToPosition
                        // mesg_rec.scrollToPosition(adapter.getItemCount());
                    });


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