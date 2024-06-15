package com.example.mytodolist;

import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytodolist.VoidHelpers.ColourDetermine;
import com.example.mytodolist.VoidHelpers.MyDateAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class activity_today_task extends AppCompatActivity {
    LinearLayout eachContains;
    JSONObject getByDateresponse;
    ColourDetermine colourDetermine = new ColourDetermine();
    JSONArray allTasks;
    View prevDateId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_task);

        
//        String allTask = getIntent().getStringExtra("");
//        setDateScroller();
//
//        try {
//            allTasks = new JSONArray(allTask);
//
//            String jsonObj = getIntent().getStringExtra("jsonResponse");
//            JSONObject jsonObject = new JSONObject(jsonObj);
//            int id = Integer.parseInt(jsonObject.getString("id"));
//            //observe this algorithm if it can be used to skip onclick
//            View allByDef = findViewById(R.id.all);
//            all(allByDef);
////            all.setOnClickListener(x -> {
////                try {
////                    allTask();
////                } catch (JSONException e) {
////                    throw new RuntimeException(e);
////                }
////            });
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        }


        RecyclerView recyclerView = findViewById(R.id.dateScroll);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);

        List<LocalDate> dateList = generateDateList();

        MyDateAdapter adapter = new MyDateAdapter(dateList);
        recyclerView.setAdapter(adapter);


        LinearLayoutManager layoutManager1 = (LinearLayoutManager) recyclerView.getLayoutManager();
       int firstPos =  layoutManager1.findFirstVisibleItemPosition();
       int lastPos =  layoutManager1.findLastVisibleItemPosition();
        System.out.println("First position: "+firstPos+"Last Pos: "+lastPos);
       int myPos = 1;
       if (myPos >= firstPos && myPos <= lastPos) {
           View view = layoutManager1.findViewByPosition(lastPos);
           if (view != null) {
               view.performClick();
           } else System.out.println("It a null");
       }else {
           recyclerView.scrollToPosition(firstPos);
       }


        menu();
        LinearLayout layout = findViewById(R.id.navagationIdCom);
        View child = layout.getChildAt(0);
        child.performClick();



    }

    public void menu(){
        LinearLayout layout = findViewById(R.id.navagationIdCom);
        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);

            child.setOnClickListener(x-> {
                ColorStateList colour = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dateClicked));
                if (child.getBackgroundTintList() != colour) {
                    for (int j = 0; j < layout.getChildCount(); j++) {
                        View child1 = layout.getChildAt(j);
                        child1.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.inProg)));
                        ((TextView)child1).setTextColor(ContextCompat.getColor(this, R.color.inProgText));
                    }
                    x.setBackgroundTintList(colour);
                    ((TextView)x).setTextColor(ContextCompat.getColor(this, R.color.white));

                }
            });
        }
    }

    private List<LocalDate> generateDateList() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate  today = LocalDate.now();
            LocalDate startDate = today.minusDays(today.getDayOfMonth());
            List<LocalDate> dateList = new ArrayList<>();
            while (!startDate.isAfter(today)) {
                dateList.add(startDate);
                startDate = startDate.plusDays(1);
            }
            return dateList;
        }else {
            throw new RuntimeException("Android.os.VERSION.SDK_INT ERROR");
        }
}


    public void allTask() throws JSONException {
//        JSONArray arrays = getByDateresponse.getJSONArray("items");
//        List<JSONObject> jsonObjects = new ArrayList<>();
//        for (int i = 0; i < arrays.length(); i++) {
//            JSONObject jsonObject = arrays.getJSONObject(i);
//            jsonObjects.add(jsonObject);
//        }
//        fillEach(jsonObjects);

    }

    private void fillEach(List<JSONObject> arrays) {
//        for (int i = 0; i < arrays.size(); i++) {
//            View copy = getLayoutInflater().inflate(R.layout.activity_today_task, null);
//            JSONObject items = arrays.get(i);
//            LinearLayout copyOfEach = copy.findViewById(R.id.containningAll);


//            TextView taskProjType = copyOfEach.findViewById(R.id.taskProjType);
//           taskProjType.setText(items.getString("taskType").replace('_', ' '));

//           ImageView projLogo = copyOfEach.findViewById(R.id.projLogo);

//           colourDetermine.imageAndColourDetermin(this, projLogo, items, i);

//           TextView completed = copyOfEach.findViewById(R.id.completed);
//           if (items.getBoolean("completed")){
//               completed.setText("Done");
//           }else {
//               completed.setText("In progress");
//           }
//           TextView taskTitle = copyOfEach.findViewById(R.id.taskTitle);
//           taskTitle.setText(items.getString("title"));

//            ((ViewGroup) copyOfEach.getParent()).removeView(copyOfEach);
//            eachContains.addView(copyOfEach);
//        }
    }

    public void all(View view) {

//        try {
//            LinearLayout containningAll = findViewById(R.id.containningAll);
//            for (int i = 0; i < allTasks.length(); i++) {
//                JSONArray innerArr = allTasks.getJSONArray(i);
//                for (int j = 0; j < innerArr.length(); j++) {
//                    JSONObject task = innerArr.getJSONObject(j);
//
//
//                    View eachTask = getLayoutInflater().inflate(R.layout.activity_today_task, null);
//                    TextView name = eachTask.findViewById(R.id.individualTask);
//                    name.setText(task.getString("title"));
//
//
//                }
//            }
//        } catch (JSONException e) {
//            System.out.println();
//        }
    }


    public void todo(View view) {

    }

    public void inProgress(View view) {
//        try {
//            JSONArray arrays = getByDateresponse.getJSONArray("items");
//            List<JSONObject> jsonObjects = new ArrayList<>();
//            for (int i = 0; i < arrays.length(); i++) {
//                JSONObject jsonObject = arrays.getJSONObject(i);
//                jsonObjects.add(jsonObject);
//            }
//            List<JSONObject> filteredByCompleted = new ArrayList<>();
//            jsonObjects.stream()
//                    .filter(x -> {
//                        try {
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M-d-yyyy");
//                                return LocalDate.parse(x.getString("dueDate"), formatter).isEqual(LocalDate.now());
//                            }
//                        } catch (JSONException e) {
//                            throw new RuntimeException(e);
//                        }
//                        return false;
//                    })
//                    .forEach(filteredByCompleted::add);
//            fillEach(filteredByCompleted);
//
//        } catch (JSONException ignored) {
//            System.out.println(ignored.getMessage());
//        }
    }


    public void completed(View view) {
//        try {
//            JSONArray arrays = getByDateresponse.getJSONArray("items");
//            List<JSONObject> jsonObjects = new ArrayList<>();
//            for (int i = 0; i < arrays.length(); i++) {
//                JSONObject jsonObject = arrays.getJSONObject(i);
//                jsonObjects.add(jsonObject);
//            }
//            List<JSONObject> filteredByCompleted = new ArrayList<>();
//            jsonObjects.stream()
//                    .filter(x -> {
//                        try {
//                            return x.getBoolean("completed");
//                        } catch (JSONException e) {
//                            throw new RuntimeException(e);
//                        }
//                    })
//                    .forEach(filteredByCompleted::add);
//            fillEach(filteredByCompleted);
//        } catch (JSONException e) {
//            System.out.println();
//        }
    }
public void dateColourChanger(View view){
    if (prevDateId != null){
        prevDateId.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,R.color.white)));
        TextView month = prevDateId.findViewById(R.id.month);
        month.setTextColor(ContextCompat.getColor(this,R.color.black));
        TextView date = prevDateId.findViewById(R.id.date);
        date.setTextColor(ContextCompat.getColor(this,R.color.black));
        TextView day = prevDateId.findViewById(R.id.day);
        day.setTextColor(ContextCompat.getColor(this,R.color.black));
        prevDateId = view;
    }else {
        prevDateId = view;
    }
    view.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,R.color.dateClicked)));
    TextView month = prevDateId.findViewById(R.id.month);
    month.setTextColor(ContextCompat.getColor(this,R.color.white));
    TextView date = prevDateId.findViewById(R.id.date);
    date.setTextColor(ContextCompat.getColor(this,R.color.white));
    TextView day = prevDateId.findViewById(R.id.day);
    day.setTextColor(ContextCompat.getColor(this,R.color.white));

}
          }



