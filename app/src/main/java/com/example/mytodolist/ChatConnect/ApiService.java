package com.example.mytodolist.ChatConnect;

import com.example.mytodolist.MyModel.DashboardResponse.Dashboard;
import com.example.mytodolist.MyModel.ToDoItem;
import com.example.mytodolist.MyModel.User;
import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
        @POST("/user/createAccount")
        retrofit2.Call<ResponseBody> sendRegisterReq(@Body RequestBody requestBody);

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

        @GET("/user/getUser/{userId}")
        Call<User> textRegister(@Path("userId") int userId);

        @GET("/user/getDashboard/{id}")
        Call<Dashboard> getDashboard(@Path("id") int id);
}
