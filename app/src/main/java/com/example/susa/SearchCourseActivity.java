package com.example.susa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.susa.Adapter.CatagoriesAdapter;
import com.example.susa.Adapter.CourseAdapter;
import com.example.susa.Adapter.SearchCatAdapter;
import com.example.susa.Adapter.SearchCourseAdapter;
import com.example.susa.Web_service.ApiClient;
import com.example.susa.Web_service.ApiInterface;
import com.example.susa.models.JsonObjectModalResponse;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchCourseActivity extends AppCompatActivity {

    RecyclerView cat_rec, course_rec;
    EditText search_txt;
    SharedPreferencesData sharedPreferencesData;
    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
    SearchCatAdapter searchCatAdapter;
    SearchCourseAdapter searchCourseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_course);
        sharedPreferencesData = SharedPreferencesData.getInstance(this);

        search_txt = findViewById(R.id.search_txt);
        cat_rec = findViewById(R.id.cat_rec);
        course_rec = findViewById(R.id.course_rec);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        cat_rec.setLayoutManager(layoutManager2);
        course_rec.setLayoutManager(layoutManager);
        get_cat(sharedPreferencesData.getUSER_id(), "");

        get_all_course(sharedPreferencesData.getUSER_id(), "");

        search_txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(searchCourseAdapter != null) {
                    if(editable != null) {
                        Log.d("thedsfa", "-----: " +  editable );
                        searchCourseAdapter.getFilter().filter(editable.toString());

                    }
                }
            }
        });


        Intent intent = getIntent();

        try {
            if(intent.getStringExtra("cat_id").length() > 5) {
                get_all_course(sharedPreferencesData.getUSER_id(),intent.getStringExtra("cat_id"));
                get_cat(sharedPreferencesData.getUSER_id(), intent.getStringExtra("cat_id"));
            }
        }catch (NullPointerException e) {
            Log.d("thenull_pointer", "Null pointer from intent in searchcourseactivity: " + e);
        }
    }

    private void get_all_course(String user_id, String cat) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id",user_id);
        jsonObject.addProperty("limit","no");
        jsonObject.addProperty("cat",cat);
        Call<JsonObjectModalResponse> call = apiInterface.getCourses(jsonObject);
        call.enqueue(new Callback<JsonObjectModalResponse>() {
            @Override
            public void onResponse(Call<JsonObjectModalResponse> call, Response<JsonObjectModalResponse> response) {
                if (response.isSuccessful()) {

                    if (response.body().isSuccess()) {
                        JsonArray ja;
                        ja = response.body().getRecord().get("data").getAsJsonArray();
                        searchCourseAdapter = new SearchCourseAdapter(ja,SearchCourseActivity.this);
                        course_rec.setAdapter(searchCourseAdapter);

                    }else {
                        JsonArray ja2 = new JsonArray();
                        searchCourseAdapter = new SearchCourseAdapter(ja2,SearchCourseActivity.this);
                        course_rec.setAdapter(searchCourseAdapter);
//                        Toast.makeText(SearchCourseActivity.this, "THERE IS NO COURSE", Toast.LENGTH_SHORT).show();
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

    private void  get_cat(String user_id, String selcted_id) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id",user_id);
        Call<JsonObjectModalResponse> call = apiInterface.get_catagories(jsonObject);
        call.enqueue(new Callback<JsonObjectModalResponse>() {
            @Override
            public void onResponse(Call<JsonObjectModalResponse> call, Response<JsonObjectModalResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("theresss", "THe res pnms is: "+ response.body().getRecord());
                    if(response.body().isSuccess()) {
                        JsonArray ja;
                        ja = response.body().getRecord().get("data").getAsJsonArray();
                        searchCatAdapter = new SearchCatAdapter(ja,SearchCourseActivity.this, SearchCourseActivity.this, selcted_id);
                        cat_rec.setAdapter(searchCatAdapter);
                    }else {
                        Toast.makeText(SearchCourseActivity.this, "THERE IS NO CATEGORIES", Toast.LENGTH_SHORT).show();
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

    public   void  filter_cat( String _id) {
        get_all_course(sharedPreferencesData.getUSER_id(), _id);

    }
}