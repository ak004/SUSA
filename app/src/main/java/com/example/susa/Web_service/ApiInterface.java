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

    @POST("get_vid_details")
    Call<JsonObjectModalResponse> vid_details(@Body JsonObject requestBody);

    @POST("get_selected_course")
    Call<JsonObjectModalResponse> get_selected_course(@Body JsonObject requestBody);


    @POST("buy_course")
    Call<JsonObjectModalResponse> buy_course(@Body JsonObject requestBody);

    @POST("ongoing_and_completed_course")
    Call<JsonObjectModalResponse> ongoing_and_completed_course(@Body JsonObject requestBody);

    @POST("change_to_completd")
    Call<JsonObjectModalResponse> change_to_completd(@Body JsonObject requestBody);

    @POST("send_verification")
    Call<JsonObjectModalResponse> send_verification(@Body JsonObject requestBody);

    @POST("signup")
    Call<JsonObjectModalResponse> signup(@Body JsonObject requestBody);


    @POST("login")
    Call<JsonObjectModalResponse> login(@Body JsonObject requestBody);

    @POST("qr_validation")
    Call<JsonObjectModalResponse> cross_login(@Body JsonObject requestBody);

    @POST("top_mentors")
    Call<JsonObjectModalResponse> top_mentors(@Body JsonObject requestBody);

    @POST("similar_course")
    Call<JsonObjectModalResponse> similar_course(@Body JsonObject requestBody);

    @POST("top_resources")
    Call<JsonObjectModalResponse> top_resources(@Body JsonObject requestBody);

    @POST("update_download_count")
    Call<JsonObjectModalResponse> update_download_count(@Body JsonObject requestBody);

    @POST("get_chats")
    Call<JsonObjectModalResponse> get_chats(@Body JsonObject requestBody);

    @POST("send_chat")
    Call<JsonObjectModalResponse> send_chat(@Body JsonObject requestBody);

}
