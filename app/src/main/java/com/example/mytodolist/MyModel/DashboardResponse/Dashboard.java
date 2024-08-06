package com.example.mytodolist.MyModel.DashboardResponse;

import com.example.mytodolist.MyModel.ToDoItem;

import java.util.HashMap;
import java.util.List;

public class Dashboard {
    private List<HashMap<String, String>> arrangedItems;
    private  List<ToDoItem> todayItem;
    private int totalCompleted;

    public List<HashMap<String, String>> getArrangedItems() {
        return arrangedItems;
    }

    public void setArrangedItems(List<HashMap<String, String>> arrangedItems) {
        this.arrangedItems = arrangedItems;
    }

    public int getTotalCompleted() {
        return totalCompleted;
    }

    public void setTotalCompleted(int totalCompleted) {
        this.totalCompleted = totalCompleted;
    }

    public List<ToDoItem> getTodayItem() {
        return todayItem;
    }

    public void setTodayItem(List<ToDoItem> todayItem) {
        this.todayItem = todayItem;
    }

    @Override
    public String toString() {
        return "DashboardFragment{" +
                "arrangedItems=" + arrangedItems +
                ", todayItem=" + todayItem +
                '}';
    }
}
