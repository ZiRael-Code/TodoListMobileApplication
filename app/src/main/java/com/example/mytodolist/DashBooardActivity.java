package com.example.mytodolist;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mytodolist.ChatConnect.ApiClient;
import com.example.mytodolist.ChatConnect.ApiService;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashBooardActivity extends AppCompatActivity {
    LinearLayout groupLayout;
    TextView name;
    ApiClient apiClient = new ApiClient();
//    TextView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_booard);
        groupLayout = findViewById(R.id.groupLayout);
//        textLoginResponse = findViewById(R.id.testLoginResponse);
        name = findViewById(R.id.nameView);
//        view = findViewById(R.id.projGroupErrorLogger);
        String jsonResponseString = getIntent().getStringExtra("jsonResponse");
        try {
            assert jsonResponseString != null;
            JSONObject jsonObject = new JSONObject(jsonResponseString);

            int id = jsonObject.getInt("id");
            String nameResponse = jsonObject.getString("username");
            name.setText(nameResponse);

            ApiService apiService = apiClient.returnApiService();
            Call<JsonObject> details = apiService.sendGetProjReq(id);
            details.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            try {

//                                view.setText(response.body().toString());
//                                view.setVisibility(View.GONE);
                                JSONObject jsonObject1 = new JSONObject(response.body().toString());

                                JSONArray itemsArray = jsonObject1.getJSONArray("items");
                                projectGroup(itemsArray);

                            } catch (JSONException e) {
                                toast(e.getMessage().toString());
                            }
//                           

                        } else {
                            toast("Body is null ");
                        }

                    } else {
                        toast("not successful ");
                    }

                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable throwable) {
                   toast(throwable.getMessage().toString());
                    System.out.println(throwable.getMessage());
                }
            });

        } catch (JSONException e) {
            Toast.makeText(this, "Error " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void projectGroup(JSONArray itemsArray) throws JSONException {



        if (itemsArray.getJSONArray(0).length() != 0) {
            JSONArray innerArrays = itemsArray.getJSONArray(0);
            JSONObject itemObjects = innerArrays.getJSONObject(0);
            LinearLayout layout1 = groupLayout.findViewById(R.id.projGroupLayout);
            TextView name = layout1.findViewById(R.id.projGroupName);
            name.setText(itemObjects.getString("taskType"));
            TextView projSizes = layout1.findViewById(R.id.projGroupSize);
            projSizes.setText(String.valueOf(innerArrays.length()) + " Task");

            for (int i = 1; i < itemsArray.length(); i++) {
                JSONArray innerArray = itemsArray.getJSONArray(i);
                if (innerArray.length() != 0) {

                    View newLayout = getLayoutInflater().inflate(R.layout.activity_dash_booard, null);
//            LinearLayout layout = groupLayout.findViewById(R.id.projGroupLayout);
                    LinearLayout eachGroup = newLayout.findViewById(R.id.projGroupLayout);

                    TextView projName = eachGroup.findViewById(R.id.projGroupName);
                    TextView projSize = eachGroup.findViewById(R.id.projGroupSize);
                    projSize.setText(String.valueOf(innerArray.length()) + " Task");
                    TextView percentage = eachGroup.findViewById(R.id.projGroupProgressPercentage);
                    for (int j = 0; j < innerArray.length(); j++) {
                        JSONObject itemObject = innerArray.getJSONObject(j);
                        projName.setText(itemObject.getString("taskType"));
                        break;
                    }

                    ((ViewGroup) eachGroup.getParent()).removeView(eachGroup);
                    groupLayout.addView(eachGroup);
                }
            }
        }


    }



    public void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }





}