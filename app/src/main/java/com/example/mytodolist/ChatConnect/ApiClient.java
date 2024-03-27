package com.example.mytodolist.ChatConnect;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mytodolist.DashBooardActivity;
import com.example.mytodolist.LoginActivity;
import com.google.gson.JsonObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "http:192.168.74.1:8080";
    private ApiService apiService;
    public ApiClient(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }


    public static void nextScreen(Context thisContext, Class<?> nextContext, JsonObject response) {
        Intent intent = new Intent(thisContext, nextContext);
        if (nextContext.equals(DashBooardActivity.class)) {
            intent.putExtra("jsonResponse", response.toString());
        }
        thisContext.startActivity(intent);
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
                        nextScreen(context, LoginActivity.class, responseBody);
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


    public void makelLoginRequest(RequestBody requestBody, Context context, TextView bodyView){
        Call<JsonObject> getCall = apiService.sendLoginReq(requestBody);
        getCall.enqueue(new Callback<JsonObject>()  {

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        JsonObject responseBody = response.body();
                        String message = responseBody.get("message").getAsString();
//                        bodyView.setText(responseBody.get("username").getAsString());
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        nextScreen(context, DashBooardActivity.class, responseBody);
                    }else {
                        Toast.makeText(context, "Body is null ", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(context, "not successful ", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

//    public String getTaskGroup(int id, TextView view){
//        Call<JsonObject> details = apiService.sendGetProjReq(id);
//        final String[] responses = {""};
//        details.enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                if (response.isSuccessful()){
//                    if (response.body() != null){
////                        view.setVisibility(View.GONE);
//                        responses[0] = response.body().toString();
////                       view.setText(responses[0]);
//
//                    }else {
//                            view.setText("Body is null ");
//                    }
//
//                }else {
//                    view.setText("not successful ");
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable throwable) {
//                view.setText(throwable.getMessage());
//                System.out.println(throwable.getMessage());
//            }
//        });
//        return responses[0];
//    }

public ApiService returnApiService(){
        return apiService;
}

}
