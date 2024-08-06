package com.example.mytodolist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mytodolist.ChatConnect.ApiClient;
import com.example.mytodolist.ChatConnect.ApiService;
import com.example.mytodolist.MyModel.ToDoItem;
import com.example.mytodolist.VoidHelpers.ProjectCustomAdapter;
import com.example.mytodolist.VoidHelpers.TodayItemCustomAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Dashboard extends Fragment {

    private static final String ARG_PARAM1 = "dashboard";

    // TODO: Rename and change types of parameters
    private String mParam1;

    public Dashboard() {
        // Required empty public constructor
    }


    public static Dashboard newInstance(String param1, String param2) {
        Dashboard fragment = new Dashboard();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_dash_booard);
            addProject = findViewById(R.id.addProject);
            viewTask = findViewById(R.id.viewTask);
            apiClient = new ApiClient();
            name = findViewById(R.id.nameView);
            String jsonResponseString = getIntent().getStringExtra("jsonResponse");
            System.out.println(jsonResponseString);

            try {
                assert jsonResponseString != null;
                JSONObject jsonObject = new JSONObject(jsonResponseString);
                this.id = jsonObject.getInt("id");
                this.jsonObj = jsonObject.toString();
                String nameResponse = jsonObject.getString("username");
                name.setText(nameResponse);


                ApiService apiService = apiClient.returnApiService();
                Call<com.example.mytodolist.MyModel.DashboardResponse.Dashboard> details = apiService.getDashboard(id);
                details.enqueue(new Callback<com.example.mytodolist.MyModel.DashboardResponse.Dashboard>() {
                    @Override
                    public void onResponse(Call<com.example.mytodolist.MyModel.DashboardResponse.Dashboard> call, Response<com.example.mytodolist.MyModel.DashboardResponse.Dashboard> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                com.example.mytodolist.MyModel.DashboardResponse.Dashboard dashboard = response.body();
                                arrangedItems = dashboard.getArrangedItems();
                                todayTask = dashboard.getTodayItem();

                                TextView topTotalComplete = findViewById(R.id.messageProgressPercentage);
                                topTotalComplete.setText(String.valueOf(dashboard.getTotalCompleted()) +"%");

                                RecyclerView inProgressRecyclerView = findViewById(R.id.recyclerView);
                                LinearLayoutManager layoutManager = new LinearLayoutManager(DashBooardActivity.this);
                                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                                inProgressRecyclerView.setLayoutManager(layoutManager);

                                if (todayTask.isEmpty()){
                                    ToDoItem toDoItem = new ToDoItem();
                                    toDoItem.setTaskType("NO PROGRESS");
                                    toDoItem.setTitle("No Task");
                                    toDoItem.setStartTimer("12:13 am");
                                    toDoItem.setEndTimer("12:23 am");
                                    todayTask.add(toDoItem);
                                }
                                TodayItemCustomAdapter customAdapter = new TodayItemCustomAdapter(todayTask);
                                inProgressRecyclerView.setAdapter(customAdapter);
                                TextView inProjSize = findViewById(R.id.inProjSize);
                                inProjSize.setText(String.valueOf(todayTask.size()));

                                List<HashMap<String, String>> all = arrangedItems;
                                RecyclerView  projectGroupRecyclerView  = findViewById(R.id.taskGroup1);
                                LinearLayoutManager layoutManager1 = new LinearLayoutManager(DashBooardActivity.this);
                                layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
                                projectGroupRecyclerView.setLayoutManager(layoutManager1);
                                ProjectCustomAdapter projectCustomAdapter = new ProjectCustomAdapter(all);
                                projectGroupRecyclerView.setAdapter(projectCustomAdapter);
                                TextView projectSize = findViewById(R.id.taskGroupSize);
                                projectSize.setText(String.valueOf(all.size()));
                                System.out.println(todayTask);
                            } else {
                                System.out.println("body null oo");
                                Toast.makeText(DashBooardActivity.this, "body null oo", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            System.out.println("________\nit is not successfully ooo --> "+response.raw()+""+response.body());
                            Toast.makeText(DashBooardActivity.this, "response.body()", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<com.example.mytodolist.MyModel.DashboardResponse.Dashboard> call, Throwable throwable) {
                        System.out.println(throwable.getMessage());
                        Toast.makeText(DashBooardActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (JSONException e) {
                Toast.makeText(this, "Error " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        return view;
    }
}