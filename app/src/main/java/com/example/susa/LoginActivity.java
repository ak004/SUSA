package com.example.susa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.susa.Web_service.ApiClient;
import com.example.susa.Web_service.ApiInterface;
import com.example.susa.models.JsonObjectModalResponse;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextView sign_txt;
    AppCompatButton login_btn;
    TextInputEditText username,password;
    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
    SharedPreferencesData sharedPreferencesData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferencesData = SharedPreferencesData.getInstance(this);

        if(sharedPreferencesData.getUSER_id().length() > 4) {
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        }
        sign_txt = findViewById(R.id.sign_txt);
        login_btn = findViewById(R.id.login_btn);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        sign_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(i);
            }
        });
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!username.getText().toString().isEmpty()) {
                    if(!password.getText().toString().isEmpty()) {
                        signIn(username.getText().toString(),password.getText().toString());
                    }else {
                        Toast.makeText(LoginActivity.this, "username and password is required", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(LoginActivity.this, "username and password is required", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void signIn(String em, String pwd) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username",em);
        jsonObject.addProperty("password",pwd);
        Call<JsonObjectModalResponse> call = apiInterface.login(jsonObject);
        call.enqueue(new Callback<JsonObjectModalResponse>() {
            @Override
            public void onResponse(Call<JsonObjectModalResponse> call, Response<JsonObjectModalResponse> response) {
                if (response.body().isSuccess()) {

                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    sharedPreferencesData.putuser_id("");
                    sharedPreferencesData.putuser_id(response.body().getRecord().get("user").getAsJsonObject().get("_id").getAsString());
                    startActivity(i);

                }else {
                    Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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