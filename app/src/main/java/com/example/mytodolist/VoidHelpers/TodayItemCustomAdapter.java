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
import java.util.List;
import java.util.Locale;

public class TodayItemCustomAdapter extends RecyclerView.Adapter<TodayItemCustomAdapter.TaskViewHolder> {
    private List<ToDoItem> taskList;
    private Handler handler = new Handler(Looper.getMainLooper());

    public TodayItemCustomAdapter(List<ToDoItem> taskList) {
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.today_project_item, parent, false);
        View todayLayout = view.findViewById(R.id.individualTaskLayout);
        ((ViewGroup) todayLayout.getParent()).removeView(todayLayout);
        return new TaskViewHolder(todayLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        ToDoItem task = taskList.get(position);
        holder.bind(task);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder  {
        private final TextView taskName;
        private final ProgressBar progressBar;
        private ToDoItem currentTask;
        private Runnable timerRunnable;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.projectName);
            progressBar = itemView.findViewById(R.id.individualProgressVerti);
        }

        public void bind(ToDoItem task) {
            currentTask = task;
            taskName.setText(task.getTitle());
            progressBar.setMax(0);
            startProgressUpdate();
            }

        private void startProgressUpdate() {
            System.out.println("Workin gtimer");
            String startString = currentTask.getStartTimer(); //2:04 am
            String endString = currentTask.getEndTimer();

            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());

            try {
                Date startTime = sdf.parse(startString);
                Date endTime = sdf.parse(endString);

                long durationInMillis = endTime.getTime() - startTime.getTime();

                handler = new Handler();
                timerRunnable = new Runnable() {
                    @Override
                    public void run() {
                        long currentTime = System.currentTimeMillis();
                        long elapsedTime = currentTime - startTime.getTime();

                        int progress = (int) (elapsedTime * 100 / durationInMillis);
                        System.out.println(progress);
                        progressBar.setProgress(progress);

                        if (currentTime < endTime.getTime()) {
                            handler.postDelayed(this, 1000);
                        } else {
                            progressBar.setProgress(100);
                        }
                    }
                };

                handler.post(timerRunnable);

            } catch (ParseException e) {
                e.printStackTrace();
    }
        }
    }
}
