package com.example.susa;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.susa.Adapter.SearchCatAdapter2;
import com.example.susa.Adapter.SearchResourceAdapter;
import com.example.susa.Web_service.ApiClient;
import com.example.susa.Web_service.ApiInterface;
import com.example.susa.models.JsonObjectModalResponse;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResources extends AppCompatActivity {

    RecyclerView cat_rec, resource_rec;
    EditText search_txt;
    SharedPreferencesData sharedPreferencesData;
    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
    SearchCatAdapter2 searchCatAdapter2;
    SearchResourceAdapter searchResourceAdapter;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_resources);
        sharedPreferencesData = SharedPreferencesData.getInstance(this);

        search_txt = findViewById(R.id.search_txt);
        cat_rec = findViewById(R.id.cat_rec);
        resource_rec = findViewById(R.id.resource_rec);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        cat_rec.setLayoutManager(layoutManager2);
        resource_rec.setLayoutManager(layoutManager);

        get_cat(sharedPreferencesData.getUSER_id(), "");

        get_all_resourses(sharedPreferencesData.getUSER_id(), "");

        search_txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(searchResourceAdapter != null) {
                    if(editable != null) {
                        Log.d("thedsfa", "-----: " +  editable );
                        searchResourceAdapter.getFilter().filter(editable.toString());

                    }
                }
            }
        });
        
        
        
        
        
       
    }

    private void get_all_resourses(String user_id, String cat) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id",user_id);
        jsonObject.addProperty("limit","no");
        jsonObject.addProperty("cat",cat);
        Call<JsonObjectModalResponse> call = apiInterface.top_resources(jsonObject);
        call.enqueue(new Callback<JsonObjectModalResponse>() {
            @Override
            public void onResponse(Call<JsonObjectModalResponse> call, Response<JsonObjectModalResponse> response) {
                if (response.isSuccessful()) {

                    if (response.body().isSuccess()) {
                        JsonArray ja;
                        ja = response.body().getRecord().get("data").getAsJsonArray();
                        searchResourceAdapter = new SearchResourceAdapter(ja,SearchResources.this);
                        resource_rec.setAdapter(searchResourceAdapter);

                    }else {
                        JsonArray ja2 = new JsonArray();
                        searchResourceAdapter = new SearchResourceAdapter(ja2,SearchResources.this);
                        resource_rec.setAdapter(searchResourceAdapter);
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

    private void get_cat(String user_id, String selcted_id) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id",user_id);
        jsonObject.addProperty("resource","true");
        Call<JsonObjectModalResponse> call = apiInterface.get_catagories(jsonObject);
        call.enqueue(new Callback<JsonObjectModalResponse>() {
            @Override
            public void onResponse(Call<JsonObjectModalResponse> call, Response<JsonObjectModalResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("theresss", "THe res pnms is: "+ response.body().getRecord());
                    if(response.body().isSuccess()) {
                        JsonArray ja;
                        ja = response.body().getRecord().get("data").getAsJsonArray();
                        searchCatAdapter2 = new SearchCatAdapter2(ja,SearchResources.this, SearchResources.this, selcted_id);
                        cat_rec.setAdapter(searchCatAdapter2);
                    }else {
                        Toast.makeText(SearchResources.this, "THERE IS NO CATEGORIES", Toast.LENGTH_SHORT).show();
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
        get_all_resourses(sharedPreferencesData.getUSER_id(), _id);
    }

}