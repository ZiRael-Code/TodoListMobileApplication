package com.example.mytodolist;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewProjectTask extends AppCompatActivity {

    private String jsonObj;
    private int id;
    EditText searchBox;
    JSONArray tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_project_task);
        searchBox = findViewById(R.id.searchBox);

        ArrayList<String> taskGroup   = getIntent().getStringArrayListExtra("projectGroups");



        try {

            jsonObj = getIntent().getStringExtra("jsonResponse");
            JSONObject object = new JSONObject(jsonObj);
            id = Integer.parseInt(object.getString("id"));

            String taskList = getIntent().getStringExtra("itemsArray");
            assert taskList != null;
//            JSONObject jsonObject = new JSONObject(taskList);
            tasks = new JSONArray(taskList);

            populateTaskByDefaultFirstProjectInTheArray(tasks.getJSONArray(0));



//            populateTaskAccordingToProjectSelected(tasks);

        } catch (Exception e) {
            System.out.println("-->{oncreate: Exception. Click }"+("message: "+e.getMessage()+ "code: "+e.fillInStackTrace()));


        }

        assert taskGroup != null;
        populateProjectSelectors(taskGroup);

        LinearLayout forClick = findViewById(R.id.mainTaskSelectorBox);
        for (int i = 0; i < forClick.getChildCount(); i++) {
            View child = forClick.getChildAt(i);
            LinearLayout k = child.findViewById(R.id.projectsLayout);
            for (int j = 0; j < k.getChildCount(); j++) {
                View child1 = k.getChildAt(j);
                if (child1 instanceof TextView) {
                    LinearLayout layout = findViewById(R.id.taskBox);
                    layout.removeAllViewsInLayout();
                    child1.setOnClickListener(this::populateByTaskTypeSelected);
                }
            }

        }

        }

        public void populateByTaskTypeSelected(View view)  {
            try {
                for (int i = 0; i < tasks.length(); i++) {
                    JSONArray projGroupArr = tasks.getJSONArray(i);
                    for (int j = 0; j < projGroupArr.length(); j++) {
                        JSONObject task = projGroupArr.getJSONObject(j);

                        //might be solution
                        // this was actually the solution lol😂😂😂
                        // i was finding the central textview instead of finding by the views
                        // REMOVE THAT MARK AS COMPLETE FROM THE PROJECT TASK AND PUT IT IN THE VIEW ALL PLACE AT THE IN PROGRESS PLACE, AND ADD the delete button in the completed part of the viewll all task place
                        // u know they might want to delete a completed task
                       //instead of using loop at ur view all task to check if blah blah blah, just get an object that contains completed, tido, and all from ur back end so the backend do the work
                        TextView types = view.findViewById(view.getId());

                        if (task.getString("taskType").replace('_', ' ').equals(types.getText().toString())) {
                            System.out.println(task.getString("taskType").replace('_', ' ')+"Compare sucessfully "+types.getText().toString());
                            populateTaskByDefaultFirstProjectInTheArray(tasks.getJSONArray(i));
                            System.out.println("successfully broke");
                            break;
                        }else {
                            System.out.println(task.getString("taskType").replace('_', ' ')+"Compare failled "+types.getText().toString());

//                            System.out.println("nyashiii na nyashii oooo");
//                            System.out.println("trying to compare "+task.getString("taskType")+" with "+ types.getText().toString());
                            break;
                        }
                    }
                }
            } catch (JSONException e) {
                System.out.println("-->{populateByTaskTypeSelected: }\n"+("message: "+e.getMessage()+ "code: "+e.fillInStackTrace()));

            }
        }



    // add this to the soo called layout and inst  ead of removing the default if the user has tas just set it invisible
//{String lil = [`"-"-"-"`]}

    private void populateTaskByDefaultFirstProjectInTheArray(JSONArray tasks){
        try {
            LinearLayout mainTaskBox = findViewById(R.id.mainTaskBoss);
        if (mainTaskBox.getChildCount() > 1){
            mainTaskBox.removeAllViewsInLayout();
        }
            if (tasks . length() != 0) {
                for (int i = 0; i < tasks . length(); i++) {
                    View taskList = getLayoutInflater().inflate(R.layout.activity_view_project_task, null);
                    LinearLayout taskLayout = taskList.findViewById(R.id.taskBox);

//                    LinearLayout behind3d = taskLayout.findViewWithTag(R.id.threeDUnderTask);
                    LinearLayout mainTaskLayout = taskLayout.findViewById(R.id.whiteContaingTaskBox);

                    JSONObject task = tasks.getJSONObject(i);
                    //taskType
                    TextView projectTypeView = mainTaskLayout.findViewById(R.id.projectTypeView);
                    projectTypeView.setText(task.getString("taskType"));

                    //taskTitle
                    TextView taskTitle = mainTaskLayout.findViewById(R.id.individualTask);
                    taskTitle.setText(task.getString("title"));

                    //Priority
                    TextView priority = mainTaskLayout.findViewById(R.id.priority);
                    priority.setText(task.getString("priority"));

                    ((ViewGroup) taskLayout.getParent()).removeView(taskLayout);
//                    ((ViewGroup) behind3d.getParent()).removeView(behind3d);
                    mainTaskBox.addView(taskLayout);
//                    mainTaskBox.addView(behind3d);
                    System.out.println("this is a succesfully code");
                }

            } else {
                callNothingToShow();
                System.out.println("This is the else for the task array is zero");
            }
        }catch (JSONException e){
            System.out.println("-->{populateTaskByDeafutFirstProjectInTheArray: }\n"+("message: "+e.getMessage()+ "code: "+e.fillInStackTrace()));

        }
    }

    private void callNothingToShow() {
    }


    private void populateProjectSelectors( ArrayList<String> taskGroup ) {
        LinearLayout mainTaskTypeContainer = findViewById(R.id.mainTaskSelectorBox);

        if (taskGroup.isEmpty()){
            LinearLayout taskTypeContainer = findViewById(R.id.projectsLayout);
            taskTypeContainer.setVisibility(View.GONE);
        }

        for (int i = 0; i < taskGroup.size(); i++) {
            View taskPutter = getLayoutInflater().inflate(R.layout.activity_view_project_task, null);
            LinearLayout taskTypeContainer = taskPutter.findViewById(R.id.projectsLayout);

            TextView groupName = taskTypeContainer.findViewById(R.id.tasksName);
            groupName.setText(taskGroup.get(i));

            ((ViewGroup) taskTypeContainer.getParent()).removeView(taskTypeContainer);
            mainTaskTypeContainer.addView(taskTypeContainer);
        }

//        LinearLayout taskTypeContainer = findViewById(R.id.projectsLayout);
//        taskTypeContainer.setVisibility(View.GONE);
//        mainTaskTypeContainer.removeView(taskTypeContainer);
    }


}
