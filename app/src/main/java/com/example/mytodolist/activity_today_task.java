package com.example.mytodolist;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mytodolist.ChatConnect.ApiClient;
import com.example.mytodolist.VoidHelpers.ColourDetermine;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_today_task extends AppCompatActivity {
    LinearLayout eachContains;
    ApiClient apiClient;
    JSONObject getByDateresponse;
    ColourDetermine colourDetermine = new ColourDetermine();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_task);
        apiClient = new ApiClient();
        Button all = findViewById(R.id.all);


        String jsonObj =  getIntent().getStringExtra("jsonResponse");
    try {
        JSONObject jsonObject = new JSONObject(jsonObj);
        int id = Integer.parseInt(jsonObject.getString("id"));
        eachContains = findViewById(R.id.eachContains);
        String date = "4-4-2024";
        Call<JsonObject> getTaskByDate = apiClient.returnApiService().sendGetTaskByDate(date, id);
        getTaskByDate.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                String responses = response.body().toString();

//                try {
//                    getByDateresponse = new JSONObject(responses);
//                        allTask();
//
//                } catch (JSONException e) {
//                    throw new RuntimeException(e);
//                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable throwable) {

            }
        });
        all.setOnClickListener(x->{
            try {
                allTask();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }

    public void  allTask() throws JSONException {
        JSONArray arrays = getByDateresponse.getJSONArray("items");
        List<JSONObject> jsonObjects = new ArrayList<>();
        for (int i = 0; i < arrays.length(); i++) {
            JSONObject jsonObject = arrays.getJSONObject(i);
            jsonObjects.add(jsonObject);
        }
        fillEach(jsonObjects);

    }

    private void fillEach(List<JSONObject> arrays) throws JSONException {
        for (int i = 0; i < arrays.size(); i++) {
            View copy = getLayoutInflater().inflate(R.layout.activity_today_task, null);
            JSONObject items = arrays.get(i);
            LinearLayout copyOfEach = copy.findViewById(R.id.containningAll);
            TextView taskProjType = copyOfEach.findViewById(R.id.taskProjType);
           taskProjType.setText(items.getString("taskType").replace('_', ' '));

           ImageView projLogo = copyOfEach.findViewById(R.id.projLogo);

           colourDetermine.imageAndColourDetermin(this, projLogo, items, i);

           TextView completed = copyOfEach.findViewById(R.id.completed);
           if (items.getBoolean("completed")){
               completed.setText("Done");
           }else {
               completed.setText("In progress");
           }
           TextView taskTitle = copyOfEach.findViewById(R.id.taskTitle);
           taskTitle.setText(items.getString("title"));

            ((ViewGroup) copyOfEach.getParent()).removeView(copyOfEach);
            eachContains.addView(copyOfEach);
        }
    }

    public void todo (View view) {

    }

    public void inProgress (View view) throws JSONException {
        JSONArray arrays = getByDateresponse.getJSONArray("items");
        List<JSONObject> jsonObjects = new ArrayList<>();
        for (int i = 0; i < arrays.length(); i++) {
            JSONObject jsonObject = arrays.getJSONObject(i);
            jsonObjects.add(jsonObject);
        }
        List<JSONObject> filteredByCompleted = new ArrayList<>();
        jsonObjects.stream()
                .filter(x-> {
                    try {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M-d-yyyy");
                            return LocalDate.parse(x.getString("dueDate"), formatter).isEqual(LocalDate.now());
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    return false;
                })
                .forEach(filteredByCompleted::add);
        fillEach(filteredByCompleted);
    }



    public void completed (View view) throws JSONException {
        JSONArray arrays = getByDateresponse.getJSONArray("items");
        List<JSONObject> jsonObjects = new ArrayList<>();
        for (int i = 0; i < arrays.length(); i++) {
            JSONObject jsonObject = arrays.getJSONObject(i);
            jsonObjects.add(jsonObject);
        }
        List<JSONObject> filteredByCompleted = new ArrayList<>();
         jsonObjects.stream()
                        .filter(x-> {
                            try {
                                return x.getBoolean("completed");
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        })
                 .forEach(filteredByCompleted::add);
        fillEach(filteredByCompleted);
    }

}
