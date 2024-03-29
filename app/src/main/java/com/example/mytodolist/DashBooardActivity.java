package com.example.mytodolist;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
    LinearLayout inprogress;
    TextView name;
    TextView inprog;
    ApiClient apiClient = new ApiClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_booard);
        groupLayout = findViewById(R.id.groupLayout);
        inprogress = findViewById(R.id.inProg);
        name = findViewById(R.id.nameView);
        inprog = findViewById(R.id.taskInProgress);

        String jsonResponseString = getIntent().getStringExtra("jsonResponse");

        try {
            assert jsonResponseString != null;
            JSONObject jsonObject = new JSONObject(jsonResponseString);

            int id = jsonObject.getInt("id");
            String nameResponse = jsonObject.getString("username");
            name.setText(nameResponse);
            inprogress(id);
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
        int taskGroup = 0;

        if (itemsArray.getJSONArray(0).getJSONObject(0).length() != 0) {
            LinearLayout layout1 = groupLayout.findViewById(R.id.projGroupLayout);
                groupLayout.removeView(layout1);

            TextView inProgressText = findViewById(R.id.taskInProgress);



            for (int i = 0; i < itemsArray.length(); i++) {
                int totalTask = 0;
                int taskProgress = 0;
                JSONArray innerArray = itemsArray.getJSONArray(i);
                    View newLayout = getLayoutInflater().inflate(R.layout.activity_dash_booard, null);
                    LinearLayout eachGroup = newLayout.findViewById(R.id.projGroupLayout);

                    TextView projName = eachGroup.findViewById(R.id.projGroupName);
                    TextView projSize = eachGroup.findViewById(R.id.projGroupSize);
                    TextView taskGroupCount = findViewById(R.id.taskGroupCount);

                TextView percentageTextView = eachGroup.findViewById(R.id.projGroupProgressPercentage);
                ProgressBar groupProgressPercent = eachGroup.findViewById(R.id.projGroupProgress);

                    projSize.setText(innerArray.length() + " Task");

                    for (int j = 0; j < innerArray.length(); j++) {
                        JSONObject itemObject = innerArray.getJSONObject(j);
                        if (itemObject != null){
                            totalTask++;
                            taskGroup++;
                            if (itemObject.getBoolean("completed")){
                                taskProgress++;
                            }
                        }
                        projName.setText(itemObject.getString("taskType"));
                        break;
                    }

                inProgressText.setText(String.valueOf(currentProg));

                groupLayout.removeView(layout1);

                int percent = taskProgress / totalTask *100;
                percentageTextView.setText(String.valueOf(percent));
                groupProgressPercent.setProgress(percent);

                taskGroupCount.setText(String.valueOf(taskGroup));
                    ((ViewGroup) eachGroup.getParent()).removeView(eachGroup);
                    groupLayout.addView(eachGroup);

            }

        }


    }

    public void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    public void inprogress(int id){
        final ApiService[] apiService = {apiClient.returnApiService()};
        Call<JsonObject> todayTask = apiService[0].sendGetTodayTask(id);
        todayTask.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    JSONObject  jsonObject1 = new JSONObject(response.body().toString());
                    JSONArray itemsArray = jsonObject1.getJSONArray("items");
                    inprog.setText(String.valueOf(itemsArray.length()));

                    TextView topperPercentageTextView =findViewById(R.id.messageProgressPercentage);
                    ProgressBar topperGroupProgressPercent = findViewById(R.id.dailyProgress);

                    int totalTask = itemsArray.length();
                    int taskProgress = 0;

                    for (int i = 0; i < itemsArray.length(); i++) {

                        JSONObject itemObject = itemsArray.getJSONObject(i);
                        if (itemObject.getBoolean("completed")){
                            taskProgress++;
                        }
                        View newLayout = getLayoutInflater().inflate(R.layout.activity_dash_booard, null);
                        LinearLayout indiTask = newLayout.findViewById(R.id.individualTaskLayout);
                         TextView projType = indiTask.findViewById(R.id.projectType);
                            TextView taskName = findViewById(R.id.projectName);

                            projType.setText(itemObject.getString("taskType"));
                            taskName.setText(itemObject.getString("title"));
                            ((ViewGroup) indiTask.getParent()).removeView(indiTask);
                            inprogress.addView(indiTask);
                    }
                    int percent = taskProgress / totalTask *100;
                    topperPercentageTextView.setText(String.valueOf(percent));
                    topperGroupProgressPercent.setProgress(percent);

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable throwable) {

            }
        });

    }
}