package com.example.susa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class welcomeActivity extends AppCompatActivity implements View.OnClickListener {

    AppCompatButton login_btn,register_btn;

    SharedPreferencesData sharedPreferencesData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcom_activity_page);

        sharedPreferencesData = SharedPreferencesData.getInstance(this);

        if(sharedPreferencesData.getUSER_id().length() > 4) {
            Intent intent = new Intent(welcomeActivity.this,MainActivity.class);
            startActivity(intent);
        }

        login_btn = findViewById(R.id.login_btn);
        register_btn = findViewById(R.id.register_btn);

        login_btn.setOnClickListener(this);
        register_btn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.login_btn) {
            Intent intent = new Intent(welcomeActivity.this,LoginActivity.class);
            startActivity(intent);
        }

        if(v.getId() == R.id.register_btn) {
            Intent intent = new Intent(welcomeActivity.this,RegistrationActivity.class);
            startActivity(intent);
        }

    }
}