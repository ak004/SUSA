package com.example.susa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.susa.Web_service.ApiClient;
import com.example.susa.Web_service.ApiInterface;

public class ContactUsActivity extends AppCompatActivity {


    SharedPreferencesData sharedPreferencesData;
    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        sharedPreferencesData = SharedPreferencesData.getInstance(this);


    }
}