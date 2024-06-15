package com.example.mytodolist.VoidHelpers;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytodolist.MyModel.ToDoItem;
import com.example.mytodolist.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class ProjectCustomAdapter extends RecyclerView.Adapter<ProjectCustomAdapter.TaskViewHolder> {
    private List<HashMap<String, String>> taskList;
    private Handler handler = new Handler(Looper.getMainLooper());

    public ProjectCustomAdapter(List<HashMap<String, String>> taskList) {
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.today_project_item, parent, false);
        View todayLayout = view.findViewById(R.id.projGroupLayout);
        ((ViewGroup) todayLayout.getParent()).removeView(todayLayout);
        return new ProjectCustomAdapter.TaskViewHolder(todayLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        HashMap<String, String> task = taskList.get(position);
        holder.bind(task);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder  {
        private final TextView ProjectName;
        private final ProgressBar progressBar;
        private final TextView progressBarTextView;
        private final TextView taskSize;
        private HashMap<String, String> currentTask;
        private Runnable timerRunnable;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            ProjectName = itemView.findViewById(R.id.projGroupName);
            progressBar = itemView.findViewById(R.id.projGroupProgress);
            progressBarTextView = itemView.findViewById(R.id.projGroupProgressPercentage);
            taskSize = itemView.findViewById(R.id.projGroupSize);
        }

        public void bind(HashMap<String, String> task) {
            currentTask = task;
            ProjectName.setText(task.get("projectName"));
            taskSize.setText(task.get("taskSizeInProject") +" Task");
            progressBarTextView.setText(String.valueOf(Integer.parseInt(task.get("totalCompletedTask"))+"%"));
//            taskName.setText(task.getTaskName());
            progressBar.setMax(Integer.parseInt(task.get("totalCompletedTask")));
//            startProgressUpdate();
        }

//        private void startProgressUpdate() {
//            System.out.println("Workin gtimer");
//            String startString = currentTask.getStartTimer(); //2:04 am
//            String endString = currentTask.getEndTimer();

//            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());

//            try {
//                Date startTime = sdf.parse(startString);
//                Date endTime = sdf.parse(endString);
//
//                long durationInMillis = endTime.getTime() - startTime.getTime();

//                handler = new Handler();
//                timerRunnable = new Runnable() {
//                    @Override
//                    public void run() {
//                        long currentTime = System.currentTimeMillis();
//                        long elapsedTime = currentTime - startTime.getTime();
//
//                        int progress = (int) (elapsedTime * 100 / durationInMillis);
//                        System.out.println(progress);
//                        progressBar.setProgress(progress);
//
//                        if (currentTime < endTime.getTime()) {
//                            handler.postDelayed(this, 1000);
//                        } else {
//                            progressBar.setProgress(100);
//                        }
//                    }
//                };
//
//                handler.post(timerRunnable);
//
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
        }
//    }
}
