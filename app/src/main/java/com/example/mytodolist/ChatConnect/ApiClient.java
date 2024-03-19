//package com.example.mytodolist.ChatConnect;
//
//import android.view.View;
//import android.widget.TextView;
//
//import com.google.gson.JsonObject;
//
//import okhttp3.RequestBody;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//public class ApiClient {
//    private static final String BASE_URL = "http:192.168.98.71:8080";
//    private ApiService apiService;
//    public ApiClient(){
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        apiService = retrofit.create(ApiService.class);
//    }
//
//    public  void  makePostRequest(RequestBody requestBody, TextView textView){
//        Call<JsonObject> postCall = apiService.sendPostReq(requestBody);
//        postCall.enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                if (response.isSuccessful()) {
//                    if (response.body() != null) {
//                        textView.setVisibility(View.VISIBLE);
//                        textView.setText("Successful\n");
//                        textView.append(response.body().toString());
//                    } else {
//                        textView.setVisibility(View.VISIBLE);
//                        textView.setText("Nothing is ComingBack\n");
//                    }
//                }else {
//                    textView.setVisibility(View.VISIBLE);
//                    textView.setText("This was not successful");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//                textView.setText("Failled\n"+t.getMessage().toString());
//            }
//        });
//    }
//
//    public void makeGetRequest(TextView textView){
//        Call<JsonObject> getCall = apiService.sendGetReq();
//        getCall.enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    textView.setVisibility(View.VISIBLE);
//                    textView.setText("Successful\n");
//                    textView.append(response.body().toString());
//                } else {
//                    textView.setVisibility(View.VISIBLE);
//                    textView.setText("Failed\n");
//                }
//            }
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//                textView.setVisibility(View.VISIBLE);
//                textView.setText("Failed\n");
//                textView.append(t.getMessage());
//            }
//        });
//    }
//}
