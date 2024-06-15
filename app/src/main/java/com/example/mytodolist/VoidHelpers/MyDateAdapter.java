package com.example.mytodolist.VoidHelpers;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytodolist.R;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MyDateAdapter extends RecyclerView.Adapter<MyDateAdapter.ViewHolder> {
    private final List<LocalDate> dates;
    private static  List<View> itemViewList;
    public MyDateAdapter(List<LocalDate> dates) {
        this.dates = dates;
        itemViewList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.date_design, parent, false);
        LinearLayout layout = view.findViewById(R.id.dateLayout);
        ((ViewGroup)layout.getParent()).removeView(layout);
        return new ViewHolder(layout);
    }

    public View getItemAtPosition(int position){
        return itemViewList.get(position);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LocalDate currentDate = dates.get(position);
        holder.bind(currentDate);
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    public List<View> getLayoutList(){
        System.out.println("the size of the list inside the so called adpter is "+itemViewList.size());
        return itemViewList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView month;
        private TextView date;
        private TextView day;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            month = itemView.findViewById(R.id.month);
            date = itemView.findViewById(R.id.date);
            day = itemView.findViewById(R.id.day);
        }

        public void bind(LocalDate currentDate) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                month.setText(sentenceCase(currentDate.getMonth().toString()));
                date.setText(String.valueOf(currentDate.getDayOfMonth()));
                day.setText(sentenceCase(currentDate.getDayOfWeek().toString()));
                itemViewList.add(itemView);
            }
        }
        String sentenceCase(String s){
            String remains = "";
            for (int i = 1; i < s.length(); i++) {
                remains += s.charAt(i);
            }
            remains = remains.toLowerCase();
            return s.charAt(0) + remains;

        }
    }
}