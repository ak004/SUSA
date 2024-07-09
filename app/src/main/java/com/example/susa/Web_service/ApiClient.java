package com.example.susa.Web_service;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.susa.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class    ApiClient {
    public static final String Tag = "ApiClient";
    private static MediaType MEDIA_TYPE_TEXT = MediaType.parse("multipart/form-data");
    private static Retrofit retrofit = null;
//    private static String BASE_URL = "http://192.168.1.8:3000/api/";
//    public static String Base_image_url = "http://192.168.1.8:3000/";

    private static String BASE_URL = "http://192.168.137.1:3000/api/";
    public static String Base_image_url = "http://192.168.137.1:3000/";

//    private static String BASE_URL = "http://susa.mascud.me/api/";
//    public static String Base_image_url = "http://susa.mascud.me/";

    public static MediaType MEDIA_TYPE_IMAGE = MediaType.parse("placeholder/*");

    private static Gson gson = new GsonBuilder()
            .setLenient()
            .create();


    public static Retrofit getClient() {
        if (retrofit == null) {

            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        return retrofit;
    }

    @NonNull
    public static RequestBody makeJSONRequestBody(JsonObject jsonObject) {
        String params = jsonObject.toString();
        return RequestBody.create(MEDIA_TYPE_TEXT, params);
    }

    @NonNull
    public static RequestBody makeTextRequestBody(Object stringData) {
        return RequestBody.create(MEDIA_TYPE_TEXT, String.valueOf(stringData));
    }


    @NonNull
    public static MultipartBody.Part makeMultipartRequestBody(Context context, String
            photoPath, String partName) {
        try {
            File file = new File(photoPath);
            RequestBody requestFile = RequestBody.create(MEDIA_TYPE_IMAGE, file);
            return MultipartBody.Part.createFormData(partName, context.getResources().getString(R
                            .string
                            .app_name),
                    requestFile);
        } catch (NullPointerException e) {
            Log.d("Tgggg", e.getMessage());
            return null;
        }

    }
}
