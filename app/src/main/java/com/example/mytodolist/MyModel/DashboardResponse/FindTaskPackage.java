package com.example.mytodolist.MyModel.DashboardResponse;

import com.example.mytodolist.MyModel.ToDoItem;

import java.util.List;

public class FindTaskPackage {
    private List<ToDoItem> allTask;

    public List<ToDoItem> getAllTask() {
        return allTask;
    }

    public void setAllTask(List<ToDoItem> allTask) {
        this.allTask = allTask;
    }
}
