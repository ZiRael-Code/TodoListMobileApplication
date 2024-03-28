package com.example.mytodolist;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
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

import DateUtils.DateCheckings;
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
        name = findViewById(R.id.nameView);
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
                                JSONObject jsonObject1 = new JSONObject(response.body().toString());

                                JSONArray itemsArray = jsonObject1.getJSONArray("items");
                                projectGroup(itemsArray);

                            } catch (JSONException e) {
                                toast(e.getMessage().toString());
                            }

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
        int currentProg = 0;

        if (itemsArray.getJSONArray(0).getJSONObject(0).length() != 0) {
            LinearLayout layout1 = groupLayout.findViewById(R.id.projGroupLayout);
                groupLayout.removeView(layout1);

            TextView inProgressText = findViewById(R.id.taskInProgress);





            for (int i = 0; i < itemsArray.length(); i++) {
                JSONArray innerArray = itemsArray.getJSONArray(i);
                inProgressText.setText(String.valueOf(innerArray.length()));
                    View newLayout = getLayoutInflater().inflate(R.layout.activity_dash_booard, null);
                    LinearLayout eachGroup = newLayout.findViewById(R.id.projGroupLayout);

                    TextView projName = eachGroup.findViewById(R.id.projGroupName);
                    TextView projSize = eachGroup.findViewById(R.id.projGroupSize);
                    projSize.setText(innerArray.length() + " Task");

//                LinearLayout yourLayout = findViewById(R.id.toIncrease);

                    for (int j = 0; j < innerArray.length(); j++) {


                        JSONObject itemObject = innerArray.getJSONObject(j);
                        projName.setText(itemObject.getString("taskType"));




//                        String dueDate = itemObject.getString("dueDate");
//                        if (DateCheckings.DateUtils.isApiDateToday(dueDate)){
//                            currentProg++;
//                            LinearLayout indiTask = newLayout.findViewById(R.id.individualTaskLayout);
//                            TextView projType = indiTask.findViewById(R.id.projectType);
//                            TextView taskName = findViewById(R.id.projectName);
//
//                            projType.setText(itemObject.getString("taskType"));
//                            taskName.setText(itemObject.getString("title"));
//                            ((ViewGroup) indiTask.getParent()).removeView(indiTask);
//                            groupLayout.addView(indiTask);
//
//
//                        }
                        break;
                    }

                    ((ViewGroup) eachGroup.getParent()).removeView(eachGroup);
                    groupLayout.addView(eachGroup);
//                }
//                TextView currentTaskCount = layout1.findViewById(R.id.taskGroupCount);
//                currentTaskCount.setText(String.valueOf(currentProg));
//

            }
        }


    }



    public void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }





}