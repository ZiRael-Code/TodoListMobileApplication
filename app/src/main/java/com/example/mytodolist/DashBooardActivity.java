package com.example.mytodolist;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.mytodolist.ChatConnect.ApiClient;
import com.example.mytodolist.ChatConnect.ApiService;
import com.example.mytodolist.VoidHelpers.BottomNavigationClickListener;
import com.example.mytodolist.VoidHelpers.ColourDetermine;
import com.example.mytodolist.VoidHelpers.ContextDetermine;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashBooardActivity extends AppCompatActivity {
    LinearLayout groupLayout;
    LinearLayout inprogress;
    TextView name;
    TextView inprog;
    ApiClient apiClient;
    Button viewTask;
    String allTask;
    String inprogressResponse;
    ColourDetermine colours = new ColourDetermine();
    int id;
    Button addProject;
    String jsonObj;
    ArrayList<String> taskType = new ArrayList<>();
    @Override
protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_booard);


        addProject = findViewById(R.id.addProject);
        viewTask = findViewById(R.id.viewTask);

        groupLayout = findViewById(R.id.groupLayout);
        inprogress = findViewById(R.id.inProg);
        name = findViewById(R.id.nameView);
        inprog = findViewById(R.id.taskInProgress);
        apiClient = new ApiClient();

        String jsonResponseString = getIntent().getStringExtra("jsonResponse");

        System.out.println(jsonResponseString);

        try {
            assert jsonResponseString != null;
            JSONObject jsonObject = new JSONObject(jsonResponseString);

            int id = jsonObject.getInt("id");
            this.id = id;
            this.jsonObj = jsonObject.toString();


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
                                allTask = response.body().toString();
                                JSONArray itemsArray = jsonObject1.getJSONArray("items");

                                JSONArray allGroupType = jsonObject1.getJSONArray("groups");
                                for (int i = 0; i < allGroupType.length(); i++) {
                                    taskType.add(String.valueOf(allGroupType.getString(i)));
                                }
                                projectGroup(itemsArray);
                            } catch (JSONException e) {
//                            Toast.makeText(DashBooardActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                Log.d("-> {}", e.getMessage().toString());
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


public void nextScreen(View view){
    ContextDetermine contextDetermine = new ContextDetermine();
    Class<?> context = contextDetermine.nextScreenDeterminator(view);
    assert context != null;

    Intent intent = new Intent(this, context);

    intent.putExtra("jsonResponse", jsonObj);
    intent.putStringArrayListExtra("projectGroups", taskType);
    String ids = String.valueOf(id);
    intent.putExtra("id", ids);


            this.startActivity(intent);
    finish();
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
            if (innerArray.length() == 0){
                continue;
            }

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
                    projName.setText(itemObject.getString("taskType").replace('_', ' '));
//                    taskType.add(itemObject.getString("taskType").replace('_', ' '));
                    int[] intColors = colours.realColours(this);
                    int[] cSpar = colours.realcoloursSpar(this);

                    setColourAndBackgroundImage(eachGroup, itemObject, cSpar, intColors, i, groupProgressPercent);
                    break;
                }

            inProgressText.setText(String.valueOf(currentProg));

            groupLayout.removeView(layout1);

            try {
                int percent = taskProgress / totalTask * 100;
                percentageTextView.setText(String.valueOf(percent));
                groupProgressPercent.setProgress(percent);
            }catch (ArithmeticException e){
                percentageTextView.setText(String.valueOf(0));
                groupProgressPercent.setProgress(0);
            }
            taskGroupCount.setText(String.valueOf(taskGroup));
                ((ViewGroup) eachGroup.getParent()).removeView(eachGroup);

                groupLayout.addView(eachGroup);

        }

    }


}

private void setColourAndBackgroundImage(LinearLayout eachGroup,   JSONObject itemObject, int[] cSpar,int[] intColors,int i, ProgressBar groupProgressPercent) throws JSONException {
    LinearLayout frameLayout = eachGroup.findViewById(R.id.frameLayout);
    ImageView iconView = eachGroup.findViewById(R.id.iconView);
    int[] arrayColours =  colours.imageAndColourDetermin(this, iconView, itemObject, i);

    frameLayout.setBackgroundTintList(ColorStateList.valueOf(arrayColours[1]));//colour is  index 0
    groupProgressPercent.setBackgroundTintList(ColorStateList.valueOf(arrayColours[1]));

    groupProgressPercent.setProgressTintList(ColorStateList.valueOf(arrayColours[0]));
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
        if (response.isSuccessful()) {
            if (response.body() != null) {

                try {
                    JSONObject jsonObject1 = new JSONObject(response.body().toString());
                    inprogressResponse = response.body().toString();
                    JSONArray itemsArray = jsonObject1.getJSONArray("items");
                    inprog.setText(String.valueOf(itemsArray.length()));
//            inprogress = response
                    TextView topperPercentageTextView = findViewById(R.id.messageProgressPercentage);
                    ProgressBar topperGroupProgressPercent = findViewById(R.id.dailyProgress);

                    int totalTask = itemsArray.length();
                    int taskProgress = 0;

                    if (itemsArray.length() != 0) {
                        inprogress.removeView(inprogress.findViewById(R.id.individualTaskLayout));


                        for (int i = 0; i < itemsArray.length(); i++) {
                            JSONObject itemObject = itemsArray.getJSONObject(i);
                            if (itemObject.getBoolean("completed")) {
                                taskProgress++;
                            }
                            View newLayout = getLayoutInflater().inflate(R.layout.activity_dash_booard, null);
                            LinearLayout indiTask = newLayout.findViewById(R.id.individualTaskLayout);
                            TextView projType = indiTask.findViewById(R.id.projectType1);
                            TextView taskName = indiTask.findViewById(R.id.projectName); //error was i wasnt finding the obj by indiTask u know u allready removed the view so it coudnt find it

                            projType.setText(itemObject.getString("taskType").replace('_', ' '));
                            taskName.setText(itemObject.getString("title"));

                            int[] intColors = colours.realColours(DashBooardActivity.this);
                            int[] cSpar = colours.realcoloursSpar(DashBooardActivity.this);

                            ImageView iconView = indiTask.findViewById(R.id.projectTypeImg);
                            ProgressBar individualProgress = indiTask.findViewById(R.id.individualProgressVerti);

                            int[] coloursArray = colours.imageAndColourDetermin(DashBooardActivity.this, iconView, itemObject, i);

                            int sparColour = cSpar[i % cSpar.length];
                            int color = intColors[i % intColors.length];

                            LinearLayout individualTask = indiTask.findViewById(R.id.individualTaskLayout);
                            LinearLayout frameLayout = indiTask.findViewById(R.id.individualTaskframeLayout);

                            individualTask.setBackgroundTintList(ColorStateList.valueOf(sparColour));
                            individualProgress.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(DashBooardActivity.this, R.color.white)));
                            individualProgress.setProgressTintList(ColorStateList.valueOf(color));

                            frameLayout.setBackgroundTintList(ColorStateList.valueOf(coloursArray[1]));

                            ((ViewGroup) indiTask.getParent()).removeView(indiTask);
                            inprogress.addView(indiTask);
                        }
                        try {
                            int percent = taskProgress / totalTask * 100;
                            topperPercentageTextView.setText(String.valueOf(percent));
                            topperGroupProgressPercent.setProgress(percent);
                        } catch (ArithmeticException e) {
                            topperPercentageTextView.setText(String.valueOf(0));
                            topperGroupProgressPercent.setProgress(0);
                        }
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


            } else {
                Toast.makeText(DashBooardActivity.this, "Body is null ", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(DashBooardActivity.this, "not successful", Toast.LENGTH_SHORT).show();
        }
    }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable throwable) {

            }
        });

    }
    public String getJsonObject(){
        return jsonObj;
    }

    public ArrayList<String> getTasGroup(){
        return taskType;
    }
}