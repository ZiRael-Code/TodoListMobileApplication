package com.example.mytodolist.ChatConnect;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.mytodolist.AppMain;
import com.example.mytodolist.DashBooardActivity;
import com.example.mytodolist.LoginActivity;
import com.example.mytodolist.MyModel.User;
import com.example.mytodolist.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class ApiClient {
    public static String LOCAL_STORAGE = "loginStatus";
    private static final String BASE_URL = "http:192.168.212.71:8080";
//    private static final String BASE_URL = "https://todolistapp-1-s2az.onrender.com";
    private final ApiService apiService;

    public ApiClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
//                .build();
        apiService = retrofit.create(ApiService.class);
    }


    public static void nextScreen(Context thisContext, Class<?> nextContext, JsonObject response) {
        Intent intent = new Intent(thisContext, nextContext);
        if (nextContext.equals(DashBooardActivity.class)) {
            intent.putExtra("jsonResponse", response.toString());
        }
        thisContext.startActivity(intent);
    }


    public void registerReq(RequestBody requestBody, TextView view, Context context) {
        Call<ResponseBody> postCall = apiService.sendRegisterReq(requestBody);
        postCall.enqueue(new Callback<ResponseBody>() {
            @Override
        public void onResponse(@NonNull  Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
            if (response.isSuccessful()) {
                if (response.body() != null) {
                    Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                    System.out.println(response.body());
                    nextScreen(context, LoginActivity.class, new JsonObject());
                } else {
                    view.setText(R.string.body_is_null);
                    Toast.makeText(context, "Sign Up Failed", Toast.LENGTH_SHORT).show();
                }
            } else {
                view.setText(R.string.request_was_not_successful , TextView.BufferType.valueOf(response.message()));
                Toast.makeText(context, "Sign Up Failed", Toast.LENGTH_SHORT).show();
            }
        }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                view.setText("Failed: " + t.getMessage());
                Toast.makeText(context, "Sign Up Failed", Toast.LENGTH_SHORT).show();
                System.out.println(t.fillInStackTrace());
            }
        });
    }


//    public void makeLoginRequest(RequestBody requestBody, Context context, TextView bodyView) {
//        Call<JsonObject> getCall = apiService.sendLoginReq(requestBody);
//        getCall.enqueue(new Callback<>() {
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                if (response.isSuccessful()) {
//                    if (response.body() != null) {
//                        Gson gson = new Gson();
//                        Type type = new TypeToken<HashMap<String, String>>() {}.getType();
//                        HashMap<String, String> user = gson.fromJson(userStr, type);
//                        user.replace("isLoggedIn", String.valueOf(true));
//                        editor.putString("user", gson.toJson(user));
//                        editor.apply();
//                        Intent intent = new Intent(this, AppMain.class);
//                        intent.putExtra("user", gson.toJson(user));
//                        startActivity(intent);
//
//                    } else {
//                        Toast.makeText(context, "Body is null ", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(context, "not successful ", Toast.LENGTH_SHORT).show();
//                    System.out.println(response.message());
//                    System.out.println(response.body());
//                }
//            }

//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    public ApiService returnApiService() {
        return apiService;
    }


    public void textRegister(int id, TextView view) {
        Call<User> call = apiService.textRegister(id);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                try {
                    if (response.isSuccessful()) {
                        User newUser = response.body();
                        Log.d("API_RESPONSE", response.raw().toString()); // Log the raw response

                        Log.d("API_RESPONSE_BODY", new ObjectMapper().writeValueAsString(response.body())); // Jackson ObjectMapper

                        view.setText(newUser.getMyNotification().get(0).getNotificationBody() + " it is working " + response.message());
                    } else {
                        Log.e("API_ERROR", response.errorBody().string());
                        view.setText("Error: " + response.message());
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                view.setText(t.getMessage());
                Log.e("API_FAILURE", t.getMessage(), t);
            }
        });



    }


}