package com.example.mytodolist.ChatConnect;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mytodolist.LoginActivity;
import com.google.gson.JsonObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "http:192.168.177.71:8080";
    private ApiService apiService;
    public ApiClient(){
//        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }


    public static void afterSignup(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }


    public void makePostRequest(RequestBody requestBody, TextView view, Context context){
        Call<JsonObject> postCall = apiService.sendRegisterReq(requestBody);
        postCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        JsonObject responseBody = response.body();
                        String message = responseBody.get("message").getAsString();
                        view.setText(message);
                        Toast.makeText(context, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                        afterSignup(context);
                    } else {
                        view.setText("Body is null");
                        Toast.makeText(context, "Sign Up Failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    view.setText("Request was not successful: " + response.message());
                    Toast.makeText(context, "Sign Up Failed", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                view.setText("Failed: " + t.getMessage());
                Toast.makeText(context, "Sign Up Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void makeGetRequest(TextView textView){
        Call<JsonObject> getCall = apiService.sendGetReq();
        getCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful() && response.body() != null) {
                    textView.setVisibility(View.VISIBLE);
                    textView.setText("Successful\n");
                    textView.append(response.body().toString());
                } else {
                    textView.setVisibility(View.VISIBLE);
                    textView.setText("Failed\n");
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                textView.setVisibility(View.VISIBLE);
                textView.setText("Failed\n");
                textView.append(t.getMessage());
            }
        });
    }
}
