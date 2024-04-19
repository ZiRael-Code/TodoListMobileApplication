package com.example.mytodolist.ChatConnect;

import com.google.gson.JsonObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
        @POST("/user/createAccount")
        retrofit2.Call<JsonObject> sendRegisterReq(@Body RequestBody requestBody);

        @POST("/user/login")
        retrofit2.Call<JsonObject> sendLoginReq(@Body  RequestBody requestBody);

        @GET("/user/projectGroup/{id}")
        Call<JsonObject> sendGetProjReq(@Path("id") int id);

        @GET("/user/todayTasks/{id}")
        Call<JsonObject> sendGetTodayTask(@Path("id") int id);

        @GET("/user/getTaskByDate/{date}/{userId}")
         Call<JsonObject> sendGetTaskByDate(@Path("date")String date, @Path("userId") int userId);

        @POST("/user/addTask")
        Call<JsonObject>  sendAddTaskReq(@Body RequestBody requestBody);
}
