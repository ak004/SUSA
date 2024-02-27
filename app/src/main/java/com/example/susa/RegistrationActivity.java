package com.example.susa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.susa.Adapter.CatagoriesAdapter;
import com.example.susa.Web_service.ApiClient;
import com.example.susa.Web_service.ApiInterface;
import com.example.susa.models.JsonObjectModalResponse;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {

    AppCompatButton regiser_btn,back_btn;
    TextInputEditText email,code,fist_name,phone,username_register,register_pwd,confirm_pwd;
    LinearLayout email_layout, sign_up_layout;
    TextInputLayout verifiaction_code;
    TextView sign_in_txt;
    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
    public static String verifcation_code = "";
    SharedPreferencesData sharedPreferencesData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        sharedPreferencesData = SharedPreferencesData.getInstance(this);

        sign_in_txt = findViewById(R.id.sign_in_txt);
        regiser_btn = findViewById(R.id.regiser_btn);
        back_btn = findViewById(R.id.back_btn);
        email = findViewById(R.id.email);
        code = findViewById(R.id.code);
        fist_name = findViewById(R.id.fist_name);
        phone = findViewById(R.id.phone);
        username_register = findViewById(R.id.username_register);
        register_pwd = findViewById(R.id.register_pwd);
        confirm_pwd = findViewById(R.id.confirm_pwd);
        email_layout = findViewById(R.id.email_layout);
        sign_up_layout = findViewById(R.id.sign_up_layout);
        verifiaction_code = findViewById(R.id.verifiaction_code);



        regiser_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String buttonText = regiser_btn.getText().toString();

                if("Next".equalsIgnoreCase(buttonText)){
                    if(email.getText().toString().length() > 8) {
                        String emailText = email.getText().toString();
                        Pattern pattern = Pattern.compile(".*@simad\\.edu\\.so$");
                        Matcher matcher = pattern.matcher(emailText);
                        if (matcher.matches()) {
                            // The email contains the simad.edu.so extension
                            send_code(email.getText().toString());
                            email_layout.setVisibility(View.GONE);
                            back_btn.setVisibility(View.VISIBLE);
                            verifiaction_code.setVisibility(View.VISIBLE);
                            regiser_btn.setText("Verify");
                        } else {
                            // The email does not match the expected pattern
                            Toast.makeText(RegistrationActivity.this, "Simad University Email is required", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(RegistrationActivity.this, "Write a proper email", Toast.LENGTH_SHORT).show();
                    }
                }

                if("Verify".equalsIgnoreCase(buttonText)) {
                    if(code.getText().toString().length() >= 6) {
                        if(code.getText().toString().equalsIgnoreCase(verifcation_code)) {
                            verifiaction_code.setVisibility(View.GONE);
                            sign_up_layout.setVisibility(View.VISIBLE);
                            regiser_btn.setText("Register");
                        }else {
                            Toast.makeText(RegistrationActivity.this, "Incorrect Code", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                if("Register".equalsIgnoreCase(buttonText)) {
                    // Retrieve the values from TextInputEditText fields
                    String email2 = email.getText().toString();
                    String code2 = code.getText().toString();
                    String firstName = fist_name.getText().toString();
                    String phone2 = phone.getText().toString();
                    String username = username_register.getText().toString();
                    String password = register_pwd.getText().toString();
                    String confirmPassword = confirm_pwd.getText().toString();

                    // Check if the email is valid
                    if (!isValidEmail(email2)) {
                        email.setError("Invalid email address");
                        email.requestFocus();
                        return;
                    }
                    // Check if the code, first name, phone, username, and password fields are not empty
                    if (code2.isEmpty() || firstName.isEmpty() || phone2.isEmpty() || username.isEmpty() || password.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // Check if the password meets your criteria (e.g., length)
                    if (!isValidPassword(password)) {
                        register_pwd.setError("Password must be at least 6 characters long");
                        register_pwd.requestFocus();
                        return;
                    }
                    // Check if the password and confirm password match
                    if (!password.equals(confirmPassword)) {
                        confirm_pwd.setError("Passwords do not match");
                        confirm_pwd.requestFocus();
                        return;
                    }

                        register_user(email2,firstName,phone2,username,password);


                    }

                }
        });


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String buttonText = regiser_btn.getText().toString();

                if("Next".equalsIgnoreCase(buttonText)) {
                    back_btn.setVisibility(View.GONE);
                }

                if("Verify".equalsIgnoreCase(buttonText)) {
                    regiser_btn.setText("Next");
                    back_btn.setVisibility(View.GONE);
                    email_layout.setVisibility(View.VISIBLE);
                    verifiaction_code.setVisibility(View.GONE);
                    verifcation_code = "";
                }
                if("Register".equalsIgnoreCase(buttonText)) {
                    verifiaction_code.setVisibility(View.VISIBLE);
                    sign_up_layout.setVisibility(View.GONE);
                    regiser_btn.setText("Verify");
                }
            }
        });


        sign_in_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

    }


    private void send_code (String em) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email",em);
        Call<JsonObjectModalResponse> call = apiInterface.send_verification(jsonObject);
        call.enqueue(new Callback<JsonObjectModalResponse>() {
            @Override
            public void onResponse(Call<JsonObjectModalResponse> call, Response<JsonObjectModalResponse> response) {
                if (response.body().isSuccess()) {
                    Log.d("theresss", "THe res pnms is: "+ response.body().getRecord());
                    verifcation_code = response.body().getRecord().get("code").getAsString();

                }else {
                    Toast.makeText(RegistrationActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            //check something
            @Override
            public void onFailure(Call<JsonObjectModalResponse> call, Throwable t) {
                Log.d("sliding_category", t.getMessage());
            }
        });
    }

    // Helper method to check email validity using Android's Patterns class
    private boolean isValidEmail(CharSequence email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private boolean isValidPassword(String password) {
        return password.length() >= 6;
    }
    private void  register_user(String em, String fn,String ph, String un, String pwd) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name",fn);
        jsonObject.addProperty("email",em);
        jsonObject.addProperty("user_name",un);
        jsonObject.addProperty("phone",ph);
        jsonObject.addProperty("password",pwd);
        Call<JsonObjectModalResponse> call = apiInterface.signup(jsonObject);
        call.enqueue(new Callback<JsonObjectModalResponse>() {
            @Override
            public void onResponse(Call<JsonObjectModalResponse> call, Response<JsonObjectModalResponse> response) {
                if (response.body().isSuccess()) {
                    Intent i = new Intent(RegistrationActivity.this, MainActivity.class);
                    sharedPreferencesData.putuser_id("");
                    sharedPreferencesData.putuser_id(response.body().getRecord().get("user").getAsJsonObject().get("_id").getAsString());
                    startActivity(i);

                }else {
                    Toast.makeText(RegistrationActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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