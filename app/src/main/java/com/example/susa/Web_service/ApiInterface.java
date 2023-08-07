package com.example.susa.Web_service;

import com.example.susa.models.JsonObjectModalResponse;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("catagories")
    Call<JsonObjectModalResponse> get_catagories(@Body JsonObject requestBody);

    @POST("trending_course")
    Call<JsonObjectModalResponse> getCourses(@Body JsonObject requestBody);

    @POST("get_course_details")
    Call<JsonObjectModalResponse> get_course_details(@Body JsonObject requestBody);
}
