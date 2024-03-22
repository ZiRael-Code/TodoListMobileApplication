package com.example.mytodolist.ChatConnect;

import com.google.gson.JsonObject;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
        @POST("/User/createAccount")
        retrofit2.Call<JsonObject> sendRegisterReq(@Body RequestBody requestBody);

        @GET("/process/demoGet")
        retrofit2.Call<JsonObject> sendGetReq();

}
