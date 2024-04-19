package com.example.mytodolist.VoidHelpers;



import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.mytodolist.AddTask;
import com.example.mytodolist.DashBooardActivity;
import com.example.mytodolist.R;
import com.example.mytodolist.ViewProjectTask;
import com.example.mytodolist.activity_today_task;


public class BottomNavigationClickListener implements View.OnClickListener {

    private Context context;

    public BottomNavigationClickListener(Context context) {
        this.context = context;
    }



    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.dashBoardHome) {
            context.startActivity(intentGetter(DashBooardActivity.class));
        } else if (view.getId() == R.id.todayTaskNava) {
            context.startActivity(intentGetter(activity_today_task.class));
        } else if (view.getId() == R.id.addProject) {
            context.startActivity(intentGetter(AddTask.class));
        } else if (view.getId() == R.id.viewTask) {
            context.startActivity(intentGetter(ViewProjectTask.class));
        } else if (view.getId() == R.id.myAccount) {
//            context.startActivity(intent);
        }

    }


    public Intent intentGetter(Class<?> thisContext) {
        DashBooardActivity booardActivity = new DashBooardActivity();
        Intent intent = new Intent(context, thisContext);
        intent.putExtra("jsonResponse", booardActivity.getJsonObject());
        intent.putStringArrayListExtra("projectGroups",booardActivity.getTasGroup());

//        Intent getIntent = new Intent(context, thisContext);
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
//            if (hashMap != null) {
//                    for (int i = 0; i < hashMap.size(); i++) {
//                        List<String> keys = hashMap.keySet().stream().toList();
//                        String key = keys.get(i);
//
//                        String info = hashMap.get(key);
//                        getIntent.putExtra(key, info);
//                    }
//            } else if (projectGroup != null) {
//                getIntent.putStringArrayListExtra("projectGroups", projectGroup);
//            }
//        }
        return intent;
    }
}