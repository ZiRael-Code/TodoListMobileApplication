package com.example.mytodolist.VoidHelpers;

import android.view.View;

import com.example.mytodolist.AddTask;
import com.example.mytodolist.DashBooardActivity;
import com.example.mytodolist.R;
import com.example.mytodolist.ViewProjectTask;
import com.example.mytodolist.activity_today_task;

public class ContextDetermine {
    public Class<?> nextScreenDeterminator(View view) {
        if (view.getId() == R.id.dashBoardHome) {
            return (DashBooardActivity.class);
        } else if (view.getId() == R.id.todayTaskNava) {
           return (activity_today_task.class);
        } else if (view.getId() == R.id.addProject) {
            return (AddTask.class);
        } else if (view.getId() == R.id.viewTask) {
            return(ViewProjectTask.class);
        } else if (view.getId() == R.id.myAccount) {
//            context.startActivity(intent);
        }
        return null;
    }
}
