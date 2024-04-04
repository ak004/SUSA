package com.example.susa;

import static com.example.susa.Web_service.ApiClient.Base_image_url;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.susa.Adapter.LessonsAdapter;
import com.example.susa.Web_service.ApiClient;
import com.example.susa.Web_service.ApiInterface;
import com.example.susa.models.JsonObjectModalResponse;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResourceDetails extends AppCompatActivity {

    TextView catagory,no_download,extenstion,title,username,desc;
    AppCompatButton download_btn;
    SharedPreferencesData sharedPreferencesData;
    ImageView back_btn;
    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_details);
        sharedPreferencesData = SharedPreferencesData.getInstance(this);


        catagory = findViewById(R.id.catagory);
        no_download = findViewById(R.id.no_download);
        extenstion = findViewById(R.id.extenstion);
        title = findViewById(R.id.title);
        username = findViewById(R.id.username);
        desc = findViewById(R.id.desc);
        download_btn = findViewById(R.id.download_btn);
        back_btn = findViewById(R.id.back_btn);

        Intent intent = getIntent();
        catagory.setText(intent.getStringExtra("catagory"));
        no_download.setText(intent.getStringExtra("no_download"));
        title.setText(intent.getStringExtra("title"));
        username.setText(intent.getStringExtra("username"));
        desc.setText(intent.getStringExtra("desc"));

        download_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    update_download_count(sharedPreferencesData.getUSER_id(),intent.getStringExtra("resource_id"));

                String documentUrl  = Base_image_url + intent.getStringExtra("url");
                Log.d("documentUrldocumentUrl", "documentUrl: "+ documentUrl);
                // Set the appropriate MIME type based on the file extension
                Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(documentUrl));
                try {
                    // Start the activity to open the document URL in a web browser
                    ResourceDetails.this.startActivity(intent1);
                } catch (ActivityNotFoundException e) {
                    Log.d("documentUrldocumentUrl", "errr r: "+ e);

                    // Handle exception if no suitable app is found to open the URL
                    // For example, show a message to the user or provide alternative options
                }
            }


        });


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ResourceDetails.this, MainActivity.class);
                startActivity(intent1);
            }
        });



    }
    private void update_download_count(String userId,String res_id) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id",userId);
        jsonObject.addProperty("res_id",res_id);
        Call<JsonObjectModalResponse> call = apiInterface.update_download_count(jsonObject);
        call.enqueue(new Callback<JsonObjectModalResponse>() {
            @Override
            public void onResponse(Call<JsonObjectModalResponse> call, Response<JsonObjectModalResponse> response) {
                if (response.isSuccessful()) {

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